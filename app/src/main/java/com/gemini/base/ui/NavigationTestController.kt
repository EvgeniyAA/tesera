package com.gemini.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction

private const val CONDUCT_TEXT = "ARG"

class NavigationTestController(text: String = "") : Controller(Bundle().apply { putString(CONDUCT_TEXT, text) }) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val button = AppCompatButton(container.context)
        button.text = args.getString(CONDUCT_TEXT)
        button.setOnClickListener {
            router.pushController(RouterTransaction.with(NavigationTestController(args.getString(CONDUCT_TEXT) + " child")))
        }
        return button
    }

}