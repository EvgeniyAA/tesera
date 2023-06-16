package com.tesera.feature.gamedetails

import android.text.Html
import android.text.Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.components.DisplayViewError
import com.tesera.designsystem.theme.components.DisplayViewLoading
import com.tesera.designsystem.theme.components.ExpandableText
import com.tesera.designsystem.theme.components.GameDetailsButton
import com.tesera.designsystem.theme.components.GameDetailsButtonModel
import com.tesera.designsystem.theme.components.GameDetailsButtonType
import com.tesera.designsystem.theme.components.GameOfferBlock
import com.tesera.designsystem.theme.components.MinimumHeightState
import com.tesera.designsystem.theme.components.NewsPreviewContent
import com.tesera.designsystem.theme.components.RatingView
import com.tesera.designsystem.theme.components.SlidingCarousel
import com.tesera.designsystem.theme.components.TeseraToolbar
import com.tesera.designsystem.theme.components.minimumHeightModifier
import com.tesera.domain.market.MarketType
import com.tesera.domain.model.GameDetails
import com.tesera.domain.model.NewsPreview
import com.tesera.domain.model.toPreview

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameDetailsScreen(
    onBack: () -> Unit,
    onGameDetails: (String) -> Unit,
    onComments: (String, String) -> Unit,
    onMedia: (String, Int, Int) -> Unit,
    onNewsDetails: (NewsPreview) -> Unit,
    onGameOwners: (String) -> Unit,
    onMarket: (String?, String?, MarketType) -> Unit,
    onUserClicked: (String) -> Unit,
    viewModel: GameDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxHeight(),
        topBar = {
            TeseraToolbar(
                titleText = state.allGameInfo?.game?.title ?: "",
                description = state.allGameInfo?.game?.year.takeIf { it != null && it > 0 }
                    ?.toString(),
                navAction = onBack
            )
        }
    ) {
        val refreshing by remember { mutableStateOf(false) }

        val pullRefreshState = rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = viewModel::getGameDetails
        )

        Box(
            modifier = Modifier
                .padding(it)
                .pullRefresh(pullRefreshState)
        ) {
            if (state.isLoading) DisplayViewLoading()

            state.error?.let {
                DisplayViewError(
                    modifier = Modifier.fillMaxSize(),
                    viewModel::getGameDetails
                )
            }

            state.allGameInfo?.let {
                GameDetailsContent(
                    it,
                    state.isDescriptionExpanded,
                    onGameDetails,
                    onNewsDetails,
                    onMedia,
                    onComments,
                    onGameOwners,
                    onMarket,
                    onUserClicked,
                    viewModel::onExpandClicked
                )
            }

            PullRefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GameDetailsContent(
    allInfo: GameDetails,
    isExpanded: Boolean,
    onGamePreviewClicked: (String) -> Unit,
    onNewsDetails: (NewsPreview) -> Unit,
    onMedia: (String, Int, Int) -> Unit,
    onComments: (String, String) -> Unit,
    onGameOwners: (String) -> Unit,
    onMarket: (String?, String?, MarketType) -> Unit,
    onUserClicked: (String) -> Unit,
    onExpandClicked: () -> Unit,
) {

    val game = allInfo.game
    val images = listOf(game.photoUrl) + allInfo.photos.map { it.photoUrl }

    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            val pagerState = remember { PagerState() }
            SlidingCarousel(
                itemsCount = images.size,
                pagerState = pagerState,
                itemContent = { index ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(images[index])
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(vertical = AppTheme.padding.medium),

                        )
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .zIndex(1f)
                    .background(
                        color = AppTheme.colors.primaryBackground,
                        shape = AppTheme.shapes.mediumTop
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.padding.medium)
                ) {
                    RatingView(
                        startBackgroundColor = AppTheme.colors.bggBackgroundGradientStart,
                        endBackgroundColor = AppTheme.colors.bggBackgroundGradientEnd,
                        icon = R.drawable.ic_bgg,
                        rating = game.bggRating,
                        votesNum = game.bggNumVotes,
                        shape = RoundedCornerShape(
                            topStart = AppTheme.padding.medium,
                            bottomStart = AppTheme.padding.medium
                        )
                    )
                    RatingView(
                        startBackgroundColor = AppTheme.colors.teseraBackgroundGradientStart,
                        endBackgroundColor = AppTheme.colors.teseraBackgroundGradientEnd,
                        icon = R.drawable.ic_tesera,
                        rating = game.ratingUser,
                        votesNum = game.numVotes,
                        shape = RoundedCornerShape(
                            topEnd = AppTheme.padding.medium,
                            bottomEnd = AppTheme.padding.medium
                        )
                    )
                }
            }

            val optionsList = getContentForDetailsButtons(allInfo)
            LazyVerticalGrid(
                GridCells.Fixed(2),
                modifier = Modifier.height(270.dp),
                contentPadding = PaddingValues(
                    horizontal = AppTheme.padding.medium,
                    vertical = AppTheme.padding.small
                ),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(optionsList) { item ->
                    GameDetailsButton(item) { type ->
                        when (type) {
                            GameDetailsButtonType.Media -> onMedia(
                                allInfo.game.alias,
                                allInfo.linksTotal,
                                allInfo.filesTotal
                            )

                            GameDetailsButtonType.Comments -> onComments(game.alias, "games")
                            GameDetailsButtonType.HasGame -> onGameOwners(game.alias)
                            GameDetailsButtonType.Sell ->
                                onMarket(game.alias, null, MarketType.Sell)

                            GameDetailsButtonType.Buy -> onMarket(game.alias, null, MarketType.Buy)
                            GameDetailsButtonType.GameReports -> onComments(game.alias, "games")
                        }
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
                    start = AppTheme.padding.medium,
                    end = AppTheme.padding.medium
                ),
                isExpanded = isExpanded,
                onExpandedClicked = onExpandClicked
            )

            val density = LocalDensity.current

            val minimumHeightState = remember { MinimumHeightState() }
            val minimumHeightStateModifier = Modifier.minimumHeightModifier(
                minimumHeightState,
                density
            )
            if (allInfo.relatedGames.isNotEmpty())
                GameOfferBlock(
                    text = stringResource(R.string.related_games),
                    items = allInfo.relatedGames.map { it.toPreview() },
                    modifier = minimumHeightStateModifier
                ) { onGamePreviewClicked(it.alias) }
            if (allInfo.similarGames.isNotEmpty())
                GameOfferBlock(
                    text = stringResource(id = R.string.similar_games),
                    items = allInfo.similarGames.map { it.toPreview() },
                    modifier = minimumHeightStateModifier
                ) { onGamePreviewClicked(it.alias) }
        }
        if (allInfo.news.isNotEmpty()) {
            items(
                items = allInfo.news,
                key = { newsItem -> newsItem.objectId }
            ) {
                NewsPreviewContent(
                    it,
                    onUserClicked = onUserClicked,
                    onClick = onNewsDetails
                )
            }
        }
    }
}

@Composable
private fun getContentForDetailsButtons(allGameInfo: GameDetails) =
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