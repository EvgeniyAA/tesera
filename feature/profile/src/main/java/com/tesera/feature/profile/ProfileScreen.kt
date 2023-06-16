package com.tesera.feature.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.components.avatarTextPainter
import com.tesera.designsystem.theme.components.HorizontalPhotoBlock
import com.tesera.designsystem.theme.components.IconWithText
import com.tesera.designsystem.theme.components.MarketItemHorizontalBlock
import com.tesera.designsystem.theme.components.MinimumHeightState
import com.tesera.designsystem.theme.components.ProfileListButton
import com.tesera.designsystem.theme.components.minimumHeightModifier
import com.tesera.domain.model.User
import com.tesera.feature.profile.models.ProfileData

const val AVATAR_RADIUS = 40f

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val unauthorized = remember { state.unauthorized }
    val profileData = state.data

    val scroll: ScrollState = rememberScrollState(0)
    val headerHeightPx = with(LocalDensity.current) { headerHeight.dp.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }


    when {
        unauthorized -> Column(modifier = Modifier.fillMaxSize()) { Text(text = "unauthorized") }
        state.isLoading -> Unit
        profileData != null -> Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            AppTheme.colors.secondaryBackground,
                            AppTheme.colors.interactiveBackground
                        ),
                        startY = toolbarHeightPx + AVATAR_RADIUS,
                        endY = headerHeightPx
                    )
                )
        ) {
            Header(scroll = scroll, headerHeightPx)
            Body(scroll = scroll, profileData)
            Toolbar(
                scroll = scroll,
                toolbarHeightPx = toolbarHeightPx,
                state.data?.profile?.user?.login,
                onBack
            )
        }
    }
}

private const val headerHeight = 200
private val toolbarHeight = 60.dp


@Composable
private fun Header(scroll: ScrollState, headerHeightPx: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight.dp)
            .graphicsLayer {
                translationY = -scroll.value.toFloat() / 2f
                alpha = (-1f / headerHeightPx) * scroll.value + 1
            }
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun Body(scroll: ScrollState, profileData: ProfileData) {
    val headerHeightPx = with(LocalDensity.current) { headerHeight.dp.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }
    Column(
        modifier = Modifier
            .verticalScroll(state = scroll)
            .fillMaxSize()

    ) {
        Spacer(modifier = Modifier.height(headerHeight.dp / 2))
        val user = profileData.profile.user
        Column {
            CollapsingAvatar(
                modifier = Modifier,
                scroll = scroll,
                headerHeightPx = headerHeightPx / 2,
                toolbarHeightPx = toolbarHeightPx,
                user = user
            )
            Card(
                shape = RoundedCornerShape(
                    topStart = AppTheme.padding.medium,
                    topEnd = AppTheme.padding.medium,
                    bottomStart = AppTheme.padding.small,
                    bottomEnd = AppTheme.padding.small
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppTheme.colors.primaryBackground,
                    contentColor = AppTheme.colors.primaryBackground
                ),
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(
                            top = AVATAR_RADIUS.dp + AppTheme.padding.medium,
                            start = AppTheme.padding.medium,
                            end = AppTheme.padding.medium,
                            bottom = AppTheme.padding.small
                        )
                ) {


                    if (user.name.isNotEmpty())
                        Text(
                            text = user.name,
                            modifier = Modifier.padding(bottom = AppTheme.padding.small),
                            color = AppTheme.colors.secondaryTextColor,
                            style = AppTheme.typography.heading4
                        )
                    FlowRow(Modifier.fillMaxSize()) {

                        IconWithText(
                            icon = R.drawable.ic_games_in_collection,
                            iconSize = 14,
                            text = "${profileData.profile.gamesInCollection}",
                            textColor = AppTheme.colors.secondaryTextColor
                        )

                        IconWithText(
                            icon = R.drawable.ic_experience,
                            iconSize = 14,
                            text = user.experience.toString(),
                            textColor = AppTheme.colors.secondaryTextColor
                        )

                        IconWithText(
                            icon = R.drawable.ic_tesera,
                            iconSize = 14,
                            iconTint = AppTheme.colors.darkTintColor,
                            text = "${user.rating}",
                            textColor = AppTheme.colors.secondaryTextColor
                        )

                        if (user.country.value.isNotEmpty() || user.city.value.isNotEmpty())
                            IconWithText(
                                icon = R.drawable.ic_location,
                                iconSize = 14,
                                text = listOf(
                                    user.country.value,
                                    user.city.value
                                ).filter { it.isNotEmpty() }.joinToString(", "),
                                textColor = AppTheme.colors.secondaryTextColor
                            )
                    }
                }
            }
        }
        Card(
            shape = RoundedCornerShape(
                topStart = AppTheme.padding.small,
                topEnd = AppTheme.padding.small,
                bottomStart = AppTheme.padding.medium,
                bottomEnd = AppTheme.padding.medium
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.padding.separator),
            colors = CardDefaults.cardColors(
                containerColor = AppTheme.colors.primaryBackground,
                contentColor = AppTheme.colors.primaryBackground
            )
        ) {
            if (profileData.profile.user.comment.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .padding(AppTheme.padding.medium),
                    shape = AppTheme.shapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = AppTheme.colors.interactiveBackground,
                        contentColor = AppTheme.colors.primaryBackground
                    )
                ) {
                    Text(
                        text = profileData.profile.user.comment,
                        color = AppTheme.colors.secondaryTextColor,
                        modifier = Modifier.padding(AppTheme.padding.small)
                    )
                }
            }
        }
        Card(
            shape = AppTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.padding.xSmall),
            colors = CardDefaults.cardColors(
                containerColor = AppTheme.colors.primaryBackground,
                contentColor = AppTheme.colors.primaryBackground
            )
        ) {
            HorizontalPhotoBlock(items = profileData.profile.photos)
        }
        Card(
            shape = AppTheme.shapes.mediumTop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.padding.xSmall),
            colors = CardDefaults.cardColors(
                containerColor = AppTheme.colors.primaryBackground,
                contentColor = AppTheme.colors.primaryBackground
            )
        ) {
            profileData.collections.collections.forEach { item ->
                ProfileListButton(
                    icon = R.drawable.ic_download,
                    text = item.collectionType.name,
                    helperText = item.gamesTotal.toString()
                ) {
                }
            }

            val density = LocalDensity.current

            val minimumHeightState = remember { MinimumHeightState() }
            val minimumHeightStateModifier = Modifier.minimumHeightModifier(
                minimumHeightState,
                density
            )

            if (profileData.profile.sales.isNotEmpty())
                MarketItemHorizontalBlock(
                    text = "",
                    modifier = minimumHeightStateModifier,
                    items = profileData.profile.sales
                ) {}

            if (profileData.profile.purchases.isNotEmpty())
                MarketItemHorizontalBlock(
                    text = "",
                    modifier = minimumHeightStateModifier,
                    items = profileData.profile.purchases
                ) {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    scroll: ScrollState,
    toolbarHeightPx: Float,
    login: String?,
    onBack: () -> Unit,
) {
    val toolbarBottom by remember { mutableStateOf(toolbarHeightPx) }
    val showToolbar by remember { derivedStateOf { scroll.value >= toolbarBottom } }

    TopAppBar(
        modifier = Modifier.background(
            color = if (showToolbar) AppTheme.colors.secondaryBackground else Color.Transparent
        ),
        title = {
            AnimatedVisibility(
                visible = showToolbar,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                Text(
                    login ?: "", style = AppTheme.typography.heading6,
                    color = AppTheme.colors.lightTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    tint = AppTheme.colors.lightTextColor
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
    )
}

@Composable
private fun CollapsingAvatar(
    modifier: Modifier,
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float,
    user: User?,
) {
    val maxImageSize = (AVATAR_RADIUS * 2)
    val savePoint = 0.5f
    val collapseRange: Float = (headerHeightPx + toolbarHeightPx)
    val scrollFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)
    val alphaFraction: Float = (scroll.value / (toolbarHeightPx)).coerceIn(0f, 1f)
    val avatarSize = when {
        scrollFraction > savePoint -> savePoint * maxImageSize
        else -> (1f - scrollFraction) * maxImageSize
    }

    val alpha = when {
        alphaFraction > savePoint -> minOf((1f - alphaFraction) * 2, 1f)
        else -> 1f
    }
    val elevation = when {
        alphaFraction > savePoint -> 0.dp
        else -> 10.dp
    }

    val avatarY = lerp(
        (headerHeight - AVATAR_RADIUS).dp,
        toolbarHeight,
        scrollFraction
    )

    val calculatedOffset =
        avatarY.value.toInt() - (AVATAR_RADIUS * 2).toInt() + (toolbarHeight.value.toInt() / 2)

    Box(modifier = Modifier
        .fillMaxWidth()
        .offset { IntOffset(0, calculatedOffset) }

        .zIndex(1f)) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size((avatarSize + 4).dp)
                .background(
                    AppTheme.colors.secondaryBackground.copy(alpha = alpha),
                    shape = CircleShape
                )
                .shadow(
                    elevation = elevation,
                    shape = CircleShape
                )
        )

        AsyncImage(
            model = user?.avatarUrl,
            contentDescription = user?.name,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(CircleShape)
                .align(Alignment.Center)
                .alpha(alpha)
                .size(avatarSize.dp),
            placeholder = avatarTextPainter(user?.name, user?.login, avatarSize),
            error = avatarTextPainter(user?.name, user?.login, avatarSize),
        )
//        Box(modifier = Modifier.zIndex(1f).align(Alignment.BottomCenter).size(width = (avatarSize + 4).dp, height = (avatarSize/2).dp).background(AppTheme.colors.primaryBackground.copy(alpha =1f- minOf(alpha*2, 1f)), shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)))
    }
}