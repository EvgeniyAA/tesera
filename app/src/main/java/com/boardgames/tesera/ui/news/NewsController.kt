package com.boardgames.tesera.ui.news

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.boardgames.tesera.R
import com.boardgames.tesera.data.model.BoardgameItem
import com.boardgames.tesera.data.model.HeaderHotnessGame
import com.boardgames.tesera.data.model.HotnessGameItem
import com.boardgames.tesera.mvi.Consumer
import com.boardgames.tesera.ui.base.UiEventPublisher
import com.boardgames.tesera.ui.base.UiEventPublisherProvider
import com.boardgames.tesera.ui.base.UiScope
import com.boardgames.tesera.ui.base.UiScopeProvider
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter
import core.BaseController
import kotlinx.android.synthetic.main.news_controller.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks
import toothpick.Scope

@ExperimentalCoroutinesApi
class NewsController : BaseController(R.layout.news_controller),
    UiEventPublisher<NewsFeature.Wish> by UiEventPublisherProvider(),
    Consumer<NewsFeature.State>,
    UiScope by UiScopeProvider() {

    val binding: NewsBinding by lazy { fromScope<NewsBinding>() }

    private val adapter by lazy { NewsDelegateAdapter().createAdapter { } }

    override fun installModules(scope: Scope) {
        scope.installModules(NewsModule(this))
    }


    override fun initializeView(view: View) {
        binding.setup(this)
        with(view) {
            setupAdapter(view)
        }

    }

    private fun setupAdapter(view: View) {
        var lManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        view.hotnessGamesRecycler.layoutManager = lManager
        view.hotnessGamesRecycler.adapter = adapter
        adapter.items = listOf(
            HotnessGameItem("Nemesis"),
            HotnessGameItem("Покорение марса"),
            HotnessGameItem("Nemesis:Lockdown"),
            HotnessGameItem("Покорение марса.Пролог"))
        adapter.notifyDataSetChanged()
    }


    override fun accept(value: NewsFeature.State) {
        view?.run {
            //newsText.text = value.counter.toString()
        }
    }
    fun <T> AbsDelegationAdapter<T>.setData(data: T) {
        items = data
        notifyDataSetChanged()
    }
}