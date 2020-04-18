package com.boardgames.tesera.ui.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


class UiScopeProvider: UiScope {
    private val job = Job()

    override fun clear() {
        job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
}