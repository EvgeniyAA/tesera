package com.boardgames.tesera.ui.news

import com.boardgames.tesera.mvi.Actor
import com.boardgames.tesera.mvi.ActorReducerFeature
import com.boardgames.tesera.mvi.Reducer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsFeature :
    ActorReducerFeature<NewsFeature.Wish, NewsFeature.Effect, NewsFeature.State, NewsFeature.News>(
        initialState = State(0),
        reducer = NewsReducer(),
        actor = NewsActor()
    ) {


    sealed class Wish {
        object Click : Wish()
    }

    sealed class Effect {
        object Increment : Effect()
    }

    data class State(val counter: Int)
    sealed class News


    class NewsReducer : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State = when (effect) {
            is Effect.Increment -> state.copy(counter = state.counter + 1)
        }

    }

    class NewsActor : Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Flow<Effect> = when (wish) {
            is Wish.Click -> flow { emit(Effect.Increment) }
        }
    }

}