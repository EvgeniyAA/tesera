package com.tesera.designsystem.theme.components

import android.text.Html
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tesera.core.utils.niceDateStr
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.TeseraTheme
import com.tesera.domain.model.Author
import com.tesera.domain.model.CommentModel
import com.tesera.domain.model.TeseraObjectModel
import java.util.*

@Composable
fun Comment(
    commentModel: CommentModel,
    modifier: Modifier = Modifier,
    onExpandedClicked: (Int) -> Unit,
    onLikeClicked: (Int) -> Unit,
) {
    Row(modifier = modifier) {
        val iconSize = if (commentModel.parentId == null) 40f else 20f
        Avatar(author = commentModel.author, avatarSize = iconSize)
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
                        modifier = Modifier
                            .size(12.dp)
                            .clickable { onLikeClicked(commentModel.id) },
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
                Author(0, 0, "User123", "Ivan", "", 13, ""),
                TeseraObjectModel(
                    0, 0, 0, "", "", "", ""
                )
            ),
            onExpandedClicked = { },
            onLikeClicked = {}
        )
    }
}

