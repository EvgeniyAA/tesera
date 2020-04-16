package com.gemini.base.mvi

import androidx.lifecycle.LifecycleOwner
import com.gemini.base.ui.base.UiScope
import kotlinx.coroutines.CoroutineScope

abstract class Binding<T : Any>(lifecycleOwner: LifecycleOwner) {
    protected val binder = Binder(lifecycleOwner.lifecycle)

    abstract fun setup(view: T)
}