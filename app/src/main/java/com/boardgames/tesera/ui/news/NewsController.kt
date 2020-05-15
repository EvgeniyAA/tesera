package com.boardgames.tesera.ui.news

import android.view.View
import com.boardgames.tesera.R
import com.boardgames.tesera.features.news.NewsFeature
import com.boardgames.tesera.mvi.Consumer
import com.boardgames.tesera.ui.base.UiEventPublisher
import com.boardgames.tesera.ui.base.UiEventPublisherProvider
import com.boardgames.tesera.ui.base.UiScope
import com.boardgames.tesera.ui.base.UiScopeProvider
import core.BaseController
import kotlinx.android.synthetic.main.news_controller.view.*
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks
import toothpick.Scope

class NewsController : BaseController(R.layout.news_controller),
    UiEventPublisher<NewsFeature.Wish> by UiEventPublisherProvider(),
    Consumer<NewsFeature.State>,
    UiScope by UiScopeProvider() {

    val binding: NewsBinding by lazy { fromScope<NewsBinding>() }


    override fun installModules(scope: Scope) {
        scope.installModules(NewsModule(this))
    }


    override fun initializeView(view: View) {
        binding.setup(this)
        with(view) {
            newsText.clicks()
                .onEach {
                    emit(NewsFeature.Wish.Click)
                }
                .withUiScope()

        }

    }


    override fun accept(value: NewsFeature.State) {
        view?.run {
            newsText.text = value.counter.toString()
        }
    }

}