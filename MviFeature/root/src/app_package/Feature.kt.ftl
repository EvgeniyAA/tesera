package ${packageName}
import com.boardgames.tesera.mvi.MviActor
import com.boardgames.tesera.mvi.ActorReducerFeature
import com.boardgames.tesera.mvi.MviReducer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ${featureClass}:
    ActorReducerFeature<${featureClass}.Wish, ${featureClass}.Effect, ${featureClass}.State, ${featureClass}.News>(
        initialState = State(0),
        reducer = Reducer(),
        actor = Actor()
    ) {


    sealed class Wish {
    }

    sealed class Effect {
    }

    data class State(val test: Int)
    sealed class News

    class Reducer : MviReducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State = when (effect) {
        }

    }

    class Actor : MviActor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Flow<Effect> = when (wish) {
          
        }
    }

