package com.boardgames.tesera.ui.news

import com.boardgames.tesera.features.news.NewsFeature
import toothpick.config.Module

class NewsModule(newsController: NewsController) : Module() {
    init {
        val news = NewsFeature()
        bind(NewsFeature::class.java).toInstance(news)
        bind(NewsBinding::class.java).toInstance(NewsBinding(newsController, news))
    }
}