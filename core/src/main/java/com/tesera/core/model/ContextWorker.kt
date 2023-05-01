package com.tesera.core.model

import android.content.Context

class ContextWorker(val base: Context) {
    inline fun <R> withContext(block: (context: Context) -> R): R = block.invoke(base)
    fun getContext() = base
}