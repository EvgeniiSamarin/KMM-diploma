package ru.kpfu.itis.samarin.fsm.core

abstract class Effect<STATE: ru.kpfu.itis.samarin.fsm.core.State> {

    private var transitions: List<Transition<out STATE, out STATE>>? = null

    fun setTransitions(transitions: List<Transition<out STATE, out STATE>>) {
        this.transitions = transitions
    }

    open fun getTransitions(): List<Transition<out STATE, out STATE>> {
        return transitions ?: error(
            "Добавить обработку ошибок"
        )
    }

    fun run(oldState: STATE, callbacks: TransitionCallbacks<STATE>?): STATE {
        callbacks?.onActionLaunched(this, oldState)

        val availableTransitions = getAvailableTransitions(oldState)

        if (availableTransitions.size > 1) {
            callbacks?.onMultipleTransitionError(this, oldState)
        }

        val selectedTransition = availableTransitions.firstOrNull()

        if (selectedTransition == null) {
            callbacks?.onNoTransitionError(this, oldState)
            return oldState
        }

        callbacks?.onTransitionSelected(this, selectedTransition, oldState)

        val newState = selectedTransition.transform(oldState)

        callbacks?.onNewStateReduced(this, selectedTransition, oldState, newState)

        return newState
    }

    private fun getAvailableTransitions(oldState: STATE): List<Transition<STATE, STATE>> =
        (getTransitions() as List<Transition<STATE, STATE>>).filter { isCorrectTransition(it, oldState) }

    private fun isCorrectTransition(
        transition: Transition<STATE, STATE>,
        oldState: STATE,
    ): Boolean =
        (transition.fromState == oldState::class) && transition.predicate(oldState)
}