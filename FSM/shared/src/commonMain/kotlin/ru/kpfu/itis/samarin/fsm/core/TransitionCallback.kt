package ru.kpfu.itis.samarin.fsm.core

interface TransitionCallbacks<STATE : State> {

    fun onEffectLaunched(
        action: Effect<STATE>,
        currentState: STATE
    )

    fun onTransitionSelected(
        effect: Effect<STATE>,
        transition: Transition<STATE, STATE>,
        currentState: STATE
    )

    fun onNewStateReduced(
        effect: Effect<STATE>,
        transition: Transition<STATE, STATE>,
        oldState: STATE,
        newState: STATE
    )

    fun onNoTransitionError(
        effect: Effect<STATE>,
        currentState: STATE
    )

    fun onMultipleTransitionError(
        effect: Effect<STATE>,
        currentState: STATE
    )
}