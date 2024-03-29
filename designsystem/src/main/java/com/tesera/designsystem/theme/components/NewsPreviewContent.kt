package com.tesera.designsystem.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tesera.core.utils.niceDateStr
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.TeseraTheme
import com.tesera.domain.model.Author
import com.tesera.domain.model.NewsPreview
import com.tesera.domain.model.NewsType
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsPreviewContent(
    news: NewsPreview,
    onClick: (NewsPreview) -> Unit,
    onUserClicked: (String) -> Unit,
) {
    val type = when (news.objectType) {
        NewsType.News -> R.string.news
        NewsType.Article -> R.string.article
        NewsType.Thought -> R.string.thought
        NewsType.Journal -> R.string.journal
        NewsType.None -> null
    }

    Card(
        modifier = Modifier
            .padding(horizontal = AppTheme.padding.medium, vertical = AppTheme.padding.small)
            .fillMaxSize(),
        shape = AppTheme.shapes.medium,
        onClick = { onClick(news) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.interactiveBackground)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = AppTheme.padding.small,
                        top = AppTheme.padding.small,
                        bottom = AppTheme.padding.small
                    )
            ) {
                AsyncImage(
                    model = news.photoUrl,
                    contentDescription = news.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(AppTheme.shapes.medium)
                )
                Column(
                    modifier = Modifier.padding(
                        start = AppTheme.padding.small,
                        end = AppTheme.padding.small
                    )
                ) {
                    if (type != null) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            LabelWithBackground(
                                text = stringResource(id = type),
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }
                    }
                    Text(
                        text = news.title,
                        style = AppTheme.typography.body1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (news.author.name.isNotEmpty()) {
                        Row(modifier = Modifier.padding(top = AppTheme.padding.xSmall)) {

                            Avatar(
                                author = news.author,
                                avatarSize = 20F,
                                onClick = { onUserClicked(news.author.login) }
                            )
                            Text(
                                text = news.author.name,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = AppTheme.typography.body2,
                                modifier = Modifier
                                    .padding(start = AppTheme.padding.xxSmall)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomEnd)
                    .padding(
                        top = AppTheme.padding.small,
                        end = AppTheme.padding.small,
                        bottom = AppTheme.padding.small
                    )
            ) {
                Row(modifier = Modifier.align(Alignment.BottomEnd)) {
                    IconWithText(
                        icon = R.drawable.ic_star,
                        text = news.rating.toString()
                    )

                    IconWithText(
                        icon = R.drawable.ic_people,
                        text = news.numVotes.toString()
                    )

                    if (news.commentsTotal > 0) {
                        IconWithText(
                            icon = R.drawable.ic_comments,
                            iconSize = 14,
                            text = news.commentsTotal.toString()
                        )
                    }
                    Text(
                        text = news.creationDateUtc.niceDateStr(),
                        style = AppTheme.typography.body2,
                        modifier = Modifier.padding(start = AppTheme.padding.small)
                    )
                }
            }
        }
    }
}

@Preview("NewsPreview")
@Composable
fun NewsContent_Preview() {
    TeseraTheme {
        NewsPreviewContent(
            NewsPreview(
                0,
                NewsType.News,
                "All bgg top 100 games are localized!",
                "",
                "",
                "",
                Date(),
                "",
                8.89,
                1,
                12,
                Author(1, 1, "AB", "Ivan", "", 1, "")
            ), {}
        ) {}
    }
}