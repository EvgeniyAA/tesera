package com.tesera.core.mvi

import timber.log.Timber

fun String.logInvalidIntent(intent: Any, currentState: Any) {
    Timber.w(this, "Invalid $intent for state $currentState")
}