package com.tesera.core.mvi

interface TimeCapsule<STATE : UiState> {
    fun addState(state: STATE)
    fun selectState(position: Int)
    fun getStates(): List<STATE>
}

class TimeTravelCapsule<STATE : UiState>(
    private val onStateSelected: (STATE) -> Unit,
) : TimeCapsule<STATE> {
    private val states = mutableListOf<STATE>()
    override fun addState(state: STATE) {
        states.add(state)
    }

    override fun selectState(position: Int) {
        onStateSelected(states[position])
    }

    override fun getStates(): List<STATE> = states
}