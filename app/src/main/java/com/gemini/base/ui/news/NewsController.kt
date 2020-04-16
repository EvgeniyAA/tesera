package com.gemini.base.ui.news

import android.view.View
import com.gemini.base.R
import com.gemini.base.mvi.Consumer
import com.gemini.base.ui.base.*
import com.gemini.base.ui.base.UiScope
import com.gemini.base.ui.base.UiScopeProvider
import core.BaseController
import kotlinx.android.synthetic.main.news_controller.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks
import timber.log.Timber
import toothpick.Scope

@ExperimentalCoroutinesApi
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
