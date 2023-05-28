package com.tesera.feature.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.TeseraTheme
import com.tesera.designsystem.theme.components.TextPainter
import com.tesera.designsystem.theme.components.toHslColor
import com.tesera.domain.model.User
import com.tesera.feature.profile.models.ProfileData

const val AVATAR_RADIUS = 40f

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val unauthorized = remember { state.unauthorized }

    val scroll: ScrollState = rememberScrollState(0)
    val headerHeightPx = with(LocalDensity.current) { headerHeight.dp.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }


    when {
        unauthorized -> Column(modifier = Modifier.fillMaxSize()) { Text(text = "unauthorized") }
        else -> Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.secondaryBackground)
        ) {
            Header(scroll = scroll, headerHeightPx)
            Body(scroll = scroll, state.data)
            Toolbar(
                scroll = scroll,
                headerHeightPx = headerHeightPx,
                toolbarHeightPx = toolbarHeightPx,
                state.data?.profile?.user?.login
            )
            CollapsingAvatar(
                modifier = Modifier,
                scroll = scroll,
                headerHeightPx = headerHeightPx,
                toolbarHeightPx = toolbarHeightPx,
                user = state.data?.profile?.user
            )
        }
    }
}

private val headerHeight = 200
private val toolbarHeight = 56.dp


@Composable
private fun Header(scroll: ScrollState, headerHeightPx: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight.dp)
            .background(color = AppTheme.colors.secondaryBackground)
            .graphicsLayer {
                translationY = -scroll.value.toFloat() / 2f
                alpha = (-1f / headerHeightPx) * scroll.value + 1
            }
    )
}

@Composable
private fun Body(scroll: ScrollState, profileData: ProfileData?) {
    Column(
        modifier = Modifier
            .verticalScroll(state = scroll)
            .fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .height(headerHeight.dp)
                .background(color = AppTheme.colors.primaryBackground)
        )
        Card(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = AppTheme.colors.primaryBackground,
                contentColor = AppTheme.colors.primaryBackground
            ),
        ) {
            Text(text = profileData.toString(), color = AppTheme.colors.secondaryTextColor)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float,
    login: String?,
) {
    val toolbarBottom by remember { mutableStateOf(headerHeightPx - toolbarHeightPx) }
    val showToolbar by remember { derivedStateOf { scroll.value >= toolbarBottom } }

    TopAppBar(
        modifier = Modifier.background(color = AppTheme.colors.secondaryBackground),
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
            IconButton(onClick = {}) {
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

@OptIn(ExperimentalTextApi::class)
@Composable
private fun CollapsingAvatar(
    modifier: Modifier,
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float,
    user: User?,
) {

    val maxImageSize = (AVATAR_RADIUS * 2)
    val collapseRange: Float = (headerHeightPx - toolbarHeightPx)
    val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)
    val avatarSize = if (collapseFraction > 0.5) 0f else (1f - collapseFraction) * maxImageSize

    val avatarY = lerp(
        (headerHeight - AVATAR_RADIUS).dp,
        toolbarHeight,
        collapseFraction
    )

    Box(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = user?.avatarUrl,
            contentDescription = user?.name,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .align(Alignment.TopCenter)
                .padding(top = avatarY)
                .size(avatarSize.dp)
                .clip(CircleShape),
            placeholder = TextPainter(
                Color((user?.name + user?.login).toHslColor()),
                circleSize = avatarSize,
                textMeasurer = rememberTextMeasurer(),
                user?.name?.substring(0, minOf(2, user.name.length))?.uppercase() ?: ""
            ),
            error = TextPainter(
                Color((user?.name + user?.login).toHslColor()),
                circleSize = avatarSize,
                textMeasurer = rememberTextMeasurer(),
                user?.name?.substring(0, minOf(2, user.name.length))?.uppercase() ?: ""
            ),
        )
    }
}

@Composable
@Preview("ProfilePreview")
fun ProfileScreen_Preview() {
    TeseraTheme {
        ProfileScreen()
    }
}