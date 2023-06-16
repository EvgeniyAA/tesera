package com.tesera.designsystem.theme.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tesera.designsystem.theme.AppTheme
import com.tesera.domain.model.GamePreview

@Composable
fun  GameOfferBlock(
    text: String,
    items: List<GamePreview>,
    modifier: Modifier,
    onClick: (GamePreview) -> Unit,
) {
    Text(
        text = text,
        style = AppTheme.typography.body1,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
    )
    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
        items(items) { gameModel ->
            HorizontalGamePreviewContent(
                modifier = modifier,
                game = gameModel
            ) { onClick(it) }
        }
    }
}

fun Modifier.minimumHeightModifier(state: MinimumHeightState, density: Density) =
    onSizeChanged { size ->
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