package com.boardgames.tesera.mvi

import androidx.lifecycle.LifecycleOwner

abstract class Binding<T : Any>(lifecycleOwner: LifecycleOwner) {
    protected val binder = Binder(lifecycleOwner.lifecycle)

    abstract fun setup(view: T)
}