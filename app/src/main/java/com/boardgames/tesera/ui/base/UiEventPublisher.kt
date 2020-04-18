package com.boardgames.tesera.ui.base

import com.boardgames.tesera.mvi.Producer
import kotlinx.coroutines.flow.Flow

interface  UiEventPublisher<T> : Producer<T> {
    override fun getFlow(): Flow<T>
    suspend fun emit(event: T)
}