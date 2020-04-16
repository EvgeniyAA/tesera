package com.gemini.base.mvi

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
abstract class Feature<Wish, Action, in Effect, State, News>(
    initialState: State,
    actor: Actor<State, Action, Effect>,
    private val reducer: Reducer<State, Effect>,
    wishToAction: WishToAction<Wish, Action>,
    bootstrapper: Bootstrapper<Action> = EmptyBootstrapper<Action>(),
    private val newsPublisher: NewsPublisher<Action, Effect, State, News> = EmptyNewsPublisher(),
    private val postProcessor: PostProcessor<Action, Effect, State>? = null
) : CoroutineScope, Consumer<Wish>,
    Producer<State> {

    private val job: Job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val states: ConflatedBroadcastChannel<State> =
        ConflatedBroadcastChannel(initialState)

    val state: State
        get() = states.value

    override fun getFlow(): Flow<State> = states.asFlow()


    private val wishChanel: BroadcastChannel<Wish> = BroadcastChannel(10)
    private val actionChanel: BroadcastChannel<Action> = BroadcastChannel(10)
    private val newsChanel: BroadcastChannel<News> = BroadcastChannel(10)

    init {
        wishChanel.asFlow()
            .onEach { actionChanel.send(wishToAction(it)) }
            .launchIn(this)

        merge(bootstrapper.invoke(), actionChanel.asFlow())
            .flatMapConcat { wish ->
                actor.invoke(state, wish)
                    .map { Triple(wish, state, it) }
            }
            .onEach { (wish, state, effect) ->
                processEffect(state, wish, effect)
            }
            .launchIn(this)

    }


    suspend fun processEffect(state: State, action: Action, effect: Effect) {
        val newState = reducer.invoke(state, effect)
        states.send(newState)
        invokePostProcessor(action, effect, newState)
        invokeNewsPublisher(action, effect, newState)
    }

    private fun invokePostProcessor(action: Action, effect: Effect, state: State) {
        postProcessor?.invoke(action, effect, state)?.let {
            launch {
                actionChanel.send(action)
            }
        }
    }

    override fun accept(value: Wish) {
        launch {
            wishChanel.send(value)
        }
    }

    private suspend fun invokeNewsPublisher(action: Action, effect: Effect, state: State) {
        newsPublisher.invoke(action, effect, state)?.let {
            newsChanel.send(it)
        }
    }

}


interface Bootstrapper<T> {
    fun invoke(): Flow<T>
}

typealias Reducer<State, Effect> = (state: State, effect: Effect) -> State

typealias  Actor<State, Wish, Effect> =
            (state: State, wish: Wish) -> Flow<Effect>

typealias NewsPublisher<Action, Effect, State, News> =
            (action: Action, effect: Effect, state: State) -> News?

typealias PostProcessor<Action, Effect, State> =
            (action: Action, effect: Effect, state: State) -> Action?

interface Consumer<T> {
    fun accept(value: T)
}

interface Producer<T> {
    fun getFlow(): Flow<T>
}

class EmptyBootstrapper<T> : Bootstrapper<T> {
    override fun invoke(): Flow<T> = emptyFlow()
}
typealias WishToAction<Wish, Action> = (Wish) -> Action

class EmptyNewsPublisher<Action, Effect, State, News> :
    NewsPublisher<Action, Effect, State, News> {
    override fun invoke(action: Action, effect: Effect, state: State): News? = null
}