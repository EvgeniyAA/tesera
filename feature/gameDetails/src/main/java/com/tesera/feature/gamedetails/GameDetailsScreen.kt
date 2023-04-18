package com.tesera.feature.gamedetails

import android.text.Html
import android.text.Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
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
import com.tesera.feature.gamedetails.models.GameDetailsButtonModel
import com.tesera.feature.gamedetails.models.GameDetailsIntent

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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
                        val roundedCorners = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .zIndex(1f)
                                .background(
                                    color = AppTheme.colors.primaryBackground,
                                    shape = roundedCorners
                                )
                            //.shadow(0.1.dp, shape = roundedCorners)
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
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(optionsList) { item ->
                                    GameDetailsButton(
                                        color = item.buttonStartColor,
                                        text = item.title,
                                        item.image,
                                        count = item.count
                                    ) {

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
                            Text(
                                text = stringResource(id = R.string.related_games),
                                style = AppTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                            )
                            LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
                                items(allInfo.relatedGames) { gameModel ->
                                    HorizontalGamePreviewContent(modifier = minimumHeightStateModifier, game = gameModel.toPreview()) {
                                        gameDetailsViewModel.obtainIntent(
                                            GameDetailsIntent.GameDetailsClicked(it)
                                        )
                                    }
                                }
                            }
                            Text(
                                text = stringResource(id = R.string.similar_games),
                                style = AppTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                            )
                            LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
                                items(allInfo.similarGames) { gameModel ->
                                    HorizontalGamePreviewContent(modifier = minimumHeightStateModifier, game = gameModel.toPreview()) {
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
        }
    }

    LaunchedEffect(key1 = alias) {
        gameDetailsViewModel.obtainIntent(GameDetailsIntent.GetGameDetails(alias))
    }
    LaunchedEffect(key1 = viewState.value.action) {
        when (val action = viewState.value.action) {
            is GameDetailsAction.ToGameDetails ->
                navController.navigate("${NavigationTree.GamesDetails.name}/${action.game.alias}")
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
            AppTheme.colors.teseraBackgroundGradientStart,
            AppTheme.colors.teseraBackgroundGradientEnd,
        ),
        GameDetailsButtonModel(
            stringResource(id = R.string.has_game),
            R.drawable.ic_has_game,
            allGameInfo.ownersTotal,
            AppTheme.colors.teseraBackgroundGradientStart,
            AppTheme.colors.teseraBackgroundGradientEnd,
        ),
        GameDetailsButtonModel(
            stringResource(id = R.string.comments),
            R.drawable.ic_game_comments_total,
            allGameInfo.commentsTotal,
            AppTheme.colors.teseraBackgroundGradientStart,
            AppTheme.colors.teseraBackgroundGradientEnd,
        ),
        GameDetailsButtonModel(
            stringResource(id = R.string.game_reports),
            R.drawable.ic_game_reports,
            allGameInfo.reportsTotal,
            AppTheme.colors.teseraBackgroundGradientStart,
            AppTheme.colors.teseraBackgroundGradientEnd,
        ),
        GameDetailsButtonModel(
            stringResource(id = R.string.people_sell),
            R.drawable.ic_sell,
            allGameInfo.sellTotal,
            AppTheme.colors.teseraBackgroundGradientStart,
            AppTheme.colors.teseraBackgroundGradientEnd,
        ),
        GameDetailsButtonModel(
            stringResource(id = R.string.people_buy),
            R.drawable.ic_buy,
            allGameInfo.buyTotal,
            AppTheme.colors.teseraBackgroundGradientStart,
            AppTheme.colors.teseraBackgroundGradientEnd,
        )
    )


fun Modifier.minimumHeightModifier(state: MinimumHeightState, density: Density) = onSizeChanged { size ->
    val itemHeight = with(density) {
        val height = size.height
        height.toDp()
    }

    if (itemHeight > (state.minHeight ?: 0.dp)) {
        state.minHeight = itemHeight
    }
}.defaultMinSize(minHeight = state.minHeight ?: Dp.Unspecified)

class MinimumHeightState(minHeight: Dp? = null) {
    var minHeight by mutableStateOf(minHeight)
}