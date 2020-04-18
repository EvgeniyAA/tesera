package com.boardgames.tesera.ui.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

interface UiScope : CoroutineScope {
    fun <T> Flow<T>.withUiScope(): Job {
        return this.launchIn(this@UiScope)
    }

    fun clear()
}