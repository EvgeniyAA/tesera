package com.gemini.base.ui.news

import com.gemini.base.mvi.Binding
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class NewsBinding(
    controller: NewsController,
    private val newsFeature: NewsFeature
) : Binding<NewsController>(controller) {

    override fun setup(view: NewsController) {
        binder.bind(view to newsFeature)
        binder.bind(newsFeature to view)
    }
}