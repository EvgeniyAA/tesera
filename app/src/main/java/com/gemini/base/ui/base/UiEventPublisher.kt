package com.gemini.base.ui.base

import com.gemini.base.mvi.Producer
import kotlinx.coroutines.flow.Flow

interface  UiEventPublisher<T> : Producer<T> {
    override fun getFlow(): Flow<T>
    suspend fun emit(event: T)
}