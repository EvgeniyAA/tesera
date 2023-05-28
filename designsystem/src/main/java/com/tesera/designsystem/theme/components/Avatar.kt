package com.tesera.designsystem.theme.components

import androidx.annotation.ColorInt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import coil.compose.AsyncImage
import com.tesera.domain.model.Author
import com.tesera.domain.model.GameOwner
import kotlin.math.absoluteValue

@OptIn(ExperimentalTextApi::class)
@Composable
fun Avatar(author: Author, avatarSize: Float, onClick: () -> Unit = {}) {
    AsyncImage(
        model = author.avatarUrl,
        contentDescription = author.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(avatarSize.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        placeholder = TextPainter(
            Color((author.name + author.login).toHslColor()),
            circleSize = avatarSize,
            textMeasurer = rememberTextMeasurer(),
            author.name.substring(0, minOf(2, author.name.length)).uppercase()
        ),
        error = TextPainter(
            Color((author.name + author.login).toHslColor()),
            circleSize = avatarSize,
            textMeasurer = rememberTextMeasurer(),
            author.name.substring(0, minOf(2, author.name.length)).uppercase()
        ),
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun Avatar(owner: GameOwner, avatarSize: Float) {
    AsyncImage(
        model = owner.avatarUrl,
        contentDescription = owner.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(avatarSize.dp)
            .clip(CircleShape),
        placeholder = TextPainter(
            Color((owner.name + owner.login).toHslColor()),
            circleSize = avatarSize,
            textMeasurer = rememberTextMeasurer(),
            owner.name.substring(0, minOf(2, owner.name.length)).uppercase()
        ),
        error = TextPainter(
            Color((owner.name + owner.login).toHslColor()),
            circleSize = avatarSize,
            textMeasurer = rememberTextMeasurer(),
            owner.name.substring(0, minOf(2, owner.name.length)).uppercase()
        ),
    )
}


@ColorInt
fun String.toHslColor(saturation: Float = 0.5f, lightness: Float = 0.4f): Int {
    val hue = fold(0) { acc, char -> char.code + acc * 37 } % 360
    return ColorUtils.HSLToColor(floatArrayOf(hue.absoluteValue.toFloat(), saturation, lightness))
}

class TextPainter @OptIn(ExperimentalTextApi::class) constructor(
    val circleColor: Color,
    val circleSize: Float,
    val textMeasurer: TextMeasurer,
    val text: String,
) : Painter() {

    @OptIn(ExperimentalTextApi::class)
    val textLayoutResult: TextLayoutResult =
        textMeasurer.measure(
            text = AnnotatedString(text),
            style = TextStyle(color = Color.White, fontSize = (circleSize / 2).sp)
        )

    override val intrinsicSize: Size get() = Size(circleSize, circleSize)

    @OptIn(ExperimentalTextApi::class)
    override fun DrawScope.onDraw() {
        //the circle background
        drawCircle(
            color = circleColor,
            radius = size.maxDimension / 2
        )

        val textSize = textLayoutResult.size
        //The text
        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(
                (this.size.width - textSize.width) / 2f,
                (this.size.height - textSize.height) / 2f
            )
        )
    }
}