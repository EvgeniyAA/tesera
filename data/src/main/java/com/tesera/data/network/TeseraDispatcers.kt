package com.tesera.data.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val teseraDispatcher: TeseraDispatchers)

enum class TeseraDispatchers {
    IO,
}