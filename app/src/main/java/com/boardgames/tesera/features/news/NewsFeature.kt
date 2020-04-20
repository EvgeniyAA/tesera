package com.boardgames.tesera.features.news

import com.boardgames.tesera.mvi.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsFeature :
    ActorReducerFeature<NewsFeature.Wish, NewsFeature.Effect, NewsFeature.State, NewsFeature.News>(
        initialState = State(0),
        reducer = Reducer(),
        actor = Actor()
    ) {


    sealed class Wish {
        object Click : Wish()
    }

    sealed class Effect {
        object Increment : Effect()
    }

    data class State(val counter: Int)
    sealed class News


    class Reducer : MviReducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State = when (effect) {
            is Effect.Increment -> state.copy(counter = state.counter + 1)
        }

    }

    class Actor : MviActor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Flow<Effect> = when (wish) {
            is Wish.Click -> flow { emit(
                Effect.Increment
            ) }
        }
    }

}