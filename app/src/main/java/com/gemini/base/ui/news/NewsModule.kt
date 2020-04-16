package com.gemini.base.ui.news

import toothpick.config.Module

class NewsModule(newsController: NewsController) : Module() {
    init {
        val news = NewsFeature()
        bind(NewsFeature::class.java).toInstance(news)
        bind(NewsBinding::class.java).toInstance(NewsBinding(newsController, news))
    }
}