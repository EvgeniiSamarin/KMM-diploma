package ru.kpfu.itis.samarin.fsm.core

import kotlin.reflect.KClass

abstract class Transition<FROM : State, TO : State>() {

    var _fromState: KClass<FROM>? = null
    var _toState: KClass<TO>? = null

    constructor(
        fromState: KClass<FROM>,
        toState: KClass<TO>
    ) : this() {
        this._fromState = fromState
        this._toState = toState
    }

    val fromState: KClass<FROM>
        get() = _fromState ?: error(
            "Error"
        )

    val toState: KClass<TO>
        get() = _toState ?: error(
            "Error"
        )

    open fun predicate(state: FROM): Boolean {
        return true
    }

    abstract fun transform(state: FROM): TO
}