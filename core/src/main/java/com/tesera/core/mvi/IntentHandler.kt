package com.tesera.core.mvi

interface IntentHandler<T> {
    fun obtainIntent(intent: T)
}