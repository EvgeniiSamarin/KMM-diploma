package ru.kpfu.itis.samarin.fsm.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class Store<STATE : State, EFFECT : Effect<STATE>>(
    initialState: STATE, private val transitionCallbacks: TransitionCallbacks<STATE>?
) {

    private val stateFlow = MutableStateFlow(initialState)

    internal fun observeState(): StateFlow<STATE> {
        return stateFlow.asStateFlow()
    }

    internal fun getCurrentState(): STATE {
        return stateFlow.value
    }

    internal fun proceed(action: EFFECT) {
        val currentState = stateFlow.value
        val newState = reduce(action, currentState)
        val changed = newState != currentState
        if (changed) {
            stateFlow.value = newState
        }
    }

    private fun reduce(
        action: EFFECT, state: STATE
    ): STATE {
        return action.run(state, transitionCallbacks)
    }
}
