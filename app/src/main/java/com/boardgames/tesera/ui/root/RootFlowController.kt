package com.boardgames.tesera.ui.root

import android.view.View
import com.boardgames.tesera.R
import com.boardgames.tesera.ui.NavigationTestController
import com.boardgames.tesera.ui.news.NewsController
import core.BaseController
import core.utils.TabRouter
import core.utils.TabScreen
import kotlinx.android.synthetic.main.root_flow_controller.view.*

class RootFlowController : BaseController(R.layout.root_flow_controller) {
    private lateinit var newsScreen: TabScreen
    private lateinit var searchScreen: TabScreen
    private lateinit var profileScreen: TabScreen
    private lateinit var tabRouter: TabRouter
    override fun initializeView(view: View) = with(view) {
        initBottomNavigation(view)
    }

    private fun initBottomNavigation(view: View) {
        newsScreen = TabScreen(NewsController())
        searchScreen = TabScreen(NavigationTestController("search"))
        profileScreen = TabScreen(NavigationTestController("profile"))
        tabRouter = TabRouter(
            controller = this@RootFlowController,
            navLayout = view.root_changeHandlerFrameLayout,
            startTabScreen = newsScreen
        )
        tabRouter.bindBottomNavigation(view.bottom_navigation){
            newsScreen to R.id.news
            searchScreen to R.id.search
            profileScreen to R.id.profile
        }
    }

    override fun handleBack(): Boolean {
        return tabRouter.handleBack()
    }
}


