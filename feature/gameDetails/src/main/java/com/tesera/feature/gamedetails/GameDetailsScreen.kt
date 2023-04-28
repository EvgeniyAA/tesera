package com.tesera.feature.gamedetails

import android.text.Html
import android.text.Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tesera.core.ui.NavigationTree
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.components.*
import com.tesera.domain.model.GameDetailsModel
import com.tesera.domain.model.toPreview
import com.tesera.feature.gamedetails.models.GameDetailsAction
import com.tesera.feature.gamedetails.models.GameDetailsIntent
import com.tesera.feature.gamedetails.models.GameDetailsIntent.GameDetailsButtonClicked

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameDetailsScreen(
    navController: NavController,
    alias: String,
    gameDetailsViewModel: GameDetailsViewModel = hiltViewModel(),
) {

    val viewState = gameDetailsViewModel.gameDetailsViewState.collectAsState()
    val state = viewState.value
    when {
        state.isLoading -> Unit
        state.allGameInfo != null -> {
            val allInfo = state.allGameInfo
            val game = allInfo.game
            val images = listOf(game.photoUrl) + state.allGameInfo.photos.map { it.photoUrl }
            Scaffold(
                modifier = Modifier.fillMaxHeight(),
                topBar = TeseraToolbar(title = game.title, description = game.year.toString()) {
                    navController.popBackStack()
                }
            ) {
                Box(modifier = Modifier.padding(it)) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        SlidingCarousel(
                            itemsCount = images.size,
                            itemContent = { index ->
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(images[index])
                                        .build(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(200.dp)
                                        .padding(vertical = 16.dp)
                                )
                            }
                        )
                        val roundedCorners =
                            RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .zIndex(1f)
                                .background(
                                    color = AppTheme.colors.primaryBackground,
                                    shape = roundedCorners
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                RatingView(
                                    startBackgroundColor = AppTheme.colors.bggBackgroundGradientStart,
                                    endBackgroundColor = AppTheme.colors.bggBackgroundGradientEnd,
                                    icon = R.drawable.ic_bgg,
                                    rating = game.bggRating,
                                    votesNum = game.bggNumVotes,
                                    shape = RoundedCornerShape(
                                        topStart = 16.dp,
                                        bottomStart = 16.dp
                                    )
                                )
                                RatingView(
                                    startBackgroundColor = AppTheme.colors.teseraBackgroundGradientStart,
                                    endBackgroundColor = AppTheme.colors.teseraBackgroundGradientEnd,
                                    icon = R.drawable.ic_tesera,
                                    rating = game.ratingUser,
                                    votesNum = game.numVotes,
                                    shape = RoundedCornerShape(
                                        topEnd = 16.dp,
                                        bottomEnd = 16.dp
                                    )
                                )
                            }

                            val optionsList = getContentForDetailsButtons(state.allGameInfo)
                            LazyVerticalGrid(
                                GridCells.Fixed(2),
                                modifier = Modifier.height(270.dp),
                                contentPadding = PaddingValues(
                                    horizontal = 16.dp,
                                    vertical = 8.dp
                                ),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(optionsList) { item ->
                                    GameDetailsButton(item) { type ->
                                        gameDetailsViewModel.obtainIntent(
                                            GameDetailsButtonClicked(allInfo, type)
                                        )
                                    }
                                }
                            }

                            ExpandableText(
                                text = Html.fromHtml(
                                    game.description,
                                    FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH
                                ).toString(),
                                minimizedMaxLines = 4,
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp
                                )
                            )

                            val density = LocalDensity.current

                            val minimumHeightState = remember { MinimumHeightState() }
                            val minimumHeightStateModifier = Modifier.minimumHeightModifier(
                                minimumHeightState,
                                density
                            )
                            GameOfferBlock(
                                text = stringResource(R.string.related_games),
                                items = allInfo.relatedGames.map { it.toPreview() },
                                modifier = minimumHeightStateModifier
                            ) { gamePreview ->
                                gameDetailsViewModel.obtainIntent(
                                    GameDetailsIntent.GameDetailsClicked(gamePreview)
                                )
                            }
                            GameOfferBlock(
                                text = stringResource(id = R.string.similar_games),
                                items = allInfo.similarGames.map { it.toPreview() },
                                modifier = minimumHeightStateModifier
                            ) {
                                gameDetailsViewModel.obtainIntent(
                                    GameDetailsIntent.GameDetailsClicked(it)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = alias) {
        gameDetailsViewModel.obtainIntent(GameDetailsIntent.GetGameDetails(alias))
    }
    LaunchedEffect(key1 = viewState.value.action) {
        when (val action = viewState.value.action) {
            is GameDetailsAction.ToGameDetails ->
                navController.navigate("${NavigationTree.GamesDetails.name}/${action.game.alias}")
            is GameDetailsAction.ToComments ->
                navController.navigate("${NavigationTree.Comments.name}/${action.game.game.alias}/games")
            is GameDetailsAction.ToMedia -> navController.navigate("${NavigationTree.Media.name}/${action.game.game.alias}")
            else -> Unit
        }
    }
    DisposableEffect(key1 = Unit) {
        onDispose {
            gameDetailsViewModel.obtainIntent(GameDetailsIntent.ActionInvoked)
        }
    }
}

@Composable
private fun getContentForDetailsButtons(allGameInfo: GameDetailsModel) =
    listOf(
        GameDetailsButtonModel(
            stringResource(id = R.string.publications_and_files),
            R.drawable.ic_file,
            allGameInfo.filesTotal + allGameInfo.linksTotal,
            GameDetailsButtonType.Media,
            AppTheme.colors.teseraBackgroundGradientStart,
            AppTheme.colors.teseraBackgroundGradientEnd,
        ),
        GameDetailsButtonModel(
            stringResource(id = R.string.has_game),
            R.drawable.ic_has_game,
            allGameInfo.ownersTotal,
            GameDetailsButtonType.HasGame,
            AppTheme.colors.teseraBackgroundGradientStart,
            AppTheme.colors.teseraBackgroundGradientEnd,
        ),
        GameDetailsButtonModel(
            stringResource(id = R.string.comments),
            R.drawable.ic_game_comments_total,
            allGameInfo.commentsTotal,
            GameDetailsButtonType.Comments,
            AppTheme.colors.teseraBackgroundGradientStart,
            AppTheme.colors.teseraBackgroundGradientEnd,
        ),
        GameDetailsButtonModel(
            stringResource(id = R.string.game_reports),
            R.drawable.ic_game_reports,
            allGameInfo.reportsTotal,
            GameDetailsButtonType.GameReports,
            AppTheme.colors.teseraBackgroundGradientStart,
            AppTheme.colors.teseraBackgroundGradientEnd,
        ),
        GameDetailsButtonModel(
            stringResource(id = R.string.people_sell),
            R.drawable.ic_sell,
            allGameInfo.sellTotal,
            GameDetailsButtonType.Sell,
            AppTheme.colors.teseraBackgroundGradientStart,
            AppTheme.colors.teseraBackgroundGradientEnd,
        ),
        GameDetailsButtonModel(
            stringResource(id = R.string.people_buy),
            R.drawable.ic_buy,
            allGameInfo.buyTotal,
            GameDetailsButtonType.Buy,
            AppTheme.colors.teseraBackgroundGradientStart,
            AppTheme.colors.teseraBackgroundGradientEnd,
        )
    )