package com.tesera.feature.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tesera.base.newsdetails.NewsDetailsScreen
import com.tesera.core.constants.KEY_ALIAS
import com.tesera.core.constants.KEY_MARKET_TYPE
import com.tesera.core.constants.KEY_OBJECT_TYPE
import com.tesera.core.constants.KEY_USER
import com.tesera.core.ui.NavigationTree
import com.tesera.domain.market.MarketType
import com.tesera.domain.model.NewsPreview
import com.tesera.domain.model.NewsType
import com.tesera.feature.comments.CommentsScreen
import com.tesera.feature.gamedetails.GameDetailsScreen
import com.tesera.feature.games.GamesScreen
import com.tesera.feature.home.HomeScreen
import com.tesera.feature.market.MarketScreen
import com.tesera.feature.media.MediaScreen
import com.tesera.feature.news.NewsScreen
import com.tesera.feature.profile.ProfileScreen
import com.tesera.feature.search.SearchScreen
import com.tesera.feature.users.UserListScreen


@Composable
fun DashboardNavGraph() {
    val navController = rememberNavController()
    val onBack: () -> Unit = remember(navController) { { navController.popBackStack() } }
    val onNewsDetailsScreen =
        remember(navController) { { news: NewsPreview -> navController.navigate("${NavigationTree.NewsDetails.name}/${news.objectType.name}/${news.alias}") } }
    val onGameDetailsScreen = remember(navController) {
        { alias: String ->
            navController.navigate("${NavigationTree.GamesDetails.name}/$alias")
        }
    }
    val onGames = remember(navController) { { navController.navigate(NavigationTree.Games.name) } }
    val onNews = remember(navController) { { navController.navigate(NavigationTree.News.name) } }
    val onComments =
        remember(navController) { { alias: String, objectType: String -> navController.navigate("${NavigationTree.Comments.name}/$alias/$objectType") } }
    val onMedia = remember(navController) {
        { alias: String, linksTotal: Int, filesTotal: Int ->
            navController.navigate("${NavigationTree.Media.name}/$alias/$linksTotal/$filesTotal")
        }
    }
    val onGameOwners = remember(navController) {
        { alias: String ->
            navController.navigate("${NavigationTree.Owners.name}/$alias")
        }
    }

    val onMarket = remember(navController) {
        { alias: String?, user: String?, marketType: MarketType ->
            when {
                alias.isNullOrEmpty() && user.isNullOrEmpty() -> navController.navigate("${NavigationTree.Market.name}/$marketType")
                alias.isNullOrEmpty() -> navController.navigate("${NavigationTree.Market.name}/$marketType?user=$user")
                user.isNullOrEmpty() -> navController.navigate("${NavigationTree.Market.name}/$marketType?alias=$alias")
                else -> navController.navigate("${NavigationTree.Market.name}/$marketType?alias=$alias?user=$user")
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = NavigationTree.Home.name
    ) {
        composable(route = NavigationTree.Home.name) {
            HomeScreen(
                onGameDetails = onGameDetailsScreen,
                onGames = onGames,
                onNews = onNews,
                onNewsDetails = onNewsDetailsScreen
            )
        }
        composable(route = NavigationTree.Search.name) {
            SearchScreen(navController)
        }
        composable(route = NavigationTree.Profile.name) {
            ProfileScreen(navController)
        }
        composable(route = NavigationTree.Games.name) {
            GamesScreen(navController)
        }
        composable(
            route = "${NavigationTree.GamesDetails.name}/{alias}",
            arguments = listOf(navArgument(KEY_ALIAS) { type = NavType.StringType })
        ) {
            GameDetailsScreen(
                onBack = onBack,
                onGameDetails = onGameDetailsScreen,
                onComments = onComments,
                onMedia = onMedia,
                onNewsDetails = onNewsDetailsScreen,
                onGameOwners = onGameOwners,
                onMarket = onMarket
            )
        }
        composable(
            route = "${NavigationTree.Comments.name}/{alias}/{objectType}",
            arguments = listOf(navArgument(KEY_ALIAS) { type = NavType.StringType },
                navArgument(KEY_OBJECT_TYPE) { type = NavType.StringType })
        ) {
            CommentsScreen(onBack = onBack)
        }
        composable(route = "${NavigationTree.Media.name}/{alias}/{linksLimit}/{filesLimit}") {
            MediaScreen(
                alias = it.arguments?.getString("alias").orEmpty(),
                linksLimit = it.arguments?.getString("linksLimit")?.toInt() ?: 0,
                filesLimit = it.arguments?.getString("filesLimit")?.toInt() ?: 0,
                navController = navController
            )
        }

        composable(route = NavigationTree.News.name) {
            NewsScreen(onBack = onBack, onDetailsScreen = onNewsDetailsScreen)
        }
        composable(route = "${NavigationTree.NewsDetails.name}/{newsType}/{alias}") {
            NewsDetailsScreen(
                newsType = NewsType.valueOf(it.arguments?.getString("newsType").orEmpty()),
                alias = it.arguments?.getString("alias").orEmpty(),
                navController = navController
            )
        }
        composable(
            route = "${NavigationTree.Owners.name}/{alias}",
            arguments = listOf(navArgument(KEY_ALIAS) { type = NavType.StringType })
        ) {
            UserListScreen(onBack = onBack, onAuthorClicked = {})
        }
        composable(
            route = "${NavigationTree.Market.name}/{marketType}?alias={alias}&user={user}",
            arguments = listOf(
                navArgument(KEY_MARKET_TYPE) { type = NavType.EnumType(MarketType::class.java) },
                navArgument(KEY_ALIAS) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(KEY_USER) {
                    type = NavType.StringType
                    nullable = true
                },
            )
        ) {
            MarketScreen(onBack, onGameDetailsScreen)
        }
    }
}