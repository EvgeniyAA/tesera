package com.tesera.designsystem.theme.components

import android.text.Html
import androidx.annotation.ColorInt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import coil.compose.AsyncImage
import com.tesera.core.utils.niceDateStr
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.TeseraTheme
import com.tesera.domain.model.AuthorModel
import com.tesera.domain.model.CommentModel
import com.tesera.domain.model.TeseraObjectModel
import java.util.*
import kotlin.math.absoluteValue

@OptIn(ExperimentalTextApi::class)
@Composable
fun Comment(
    commentModel: CommentModel,
    modifier: Modifier = Modifier,
    onExpandedClicked: (Int) -> Unit,
    onLikeClicked: (Int) -> Unit,
) {
    Row(modifier = modifier) {
        val iconSize = if (commentModel.parentId == null) 40f else 20f
        AsyncImage(
            model = commentModel.author.avatarUrl,
            contentDescription = commentModel.author.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(iconSize.dp)
                .clip(CircleShape),
            placeholder = TextPainter(
                Color((commentModel.author.name + commentModel.author.login).toHslColor()),
                circleSize = iconSize,
                textMeasurer = rememberTextMeasurer(),
                commentModel.author.name.substring(0, 2).uppercase()
            ),
            error = TextPainter(
                Color((commentModel.author.name + commentModel.author.login).toHslColor()),
                circleSize = iconSize,
                textMeasurer = rememberTextMeasurer(),
                commentModel.author.name.substring(0, 2).uppercase()
            ),
        )
        Column(modifier = Modifier.padding(horizontal = 4.dp)) {

            Text(text = commentModel.author.name, style = AppTheme.typography.body1)
            if (!commentModel.title.isNullOrEmpty())
                Text(
                    text = commentModel.title.toString(),
                    style = AppTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
            ExpandableText(
                text = Html.fromHtml(
                    commentModel.content,
                    Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH
                ).toString(), minimizedMaxLines = 11,
                isExpanded = commentModel.isExpanded,
                onExpandedClicked = { onExpandedClicked(commentModel.id) }
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = commentModel.creationDateUtc.niceDateStr(),
                    style = AppTheme.typography.body2,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = R.drawable.ic_like_not_filled,
                        contentDescription = "",
                        modifier = Modifier.size(12.dp).clickable { onLikeClicked(commentModel.id) },
                        colorFilter = ColorFilter.tint(color = AppTheme.colors.secondaryTextColor),
                    )
                    if (commentModel.rating > 0)
                        Text(
                            text = commentModel.rating.toString(),
                            style = AppTheme.typography.caption,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { onLikeClicked(commentModel.id) }
                        )
                }
            }
        }
    }
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

@Preview
@Composable
fun Comment_Preview() {
    TeseraTheme {
        Comment(
            commentModel = CommentModel(
                0,
                0,
                null,
                "About game",
                "Boardgames are awesome. \n\n",
                15,
                Date(),
                AuthorModel(0, 0, "User123", "Ivan", "", 13, ""),
                TeseraObjectModel(
                    0, 0, 0, "", "", "", ""
                )
            ),
            onExpandedClicked = { },
            onLikeClicked = {}
        )
    }
}

