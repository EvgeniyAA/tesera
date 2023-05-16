package com.tesera.designsystem.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.TeseraTheme
import com.tesera.domain.model.GamePreview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamePreviewContent(
    game: GamePreview,
    onClick: () -> Unit,
) {
    val showRating = rememberSaveable { mutableStateOf(game.n10Rating > 0) }
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        onClick = onClick
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = AppTheme.colors.interactiveBackground)
        ) {
            val (rating, image, mainInfo, comments, addition) = createRefs()
            Box(
                modifier = Modifier
                    .zIndex(1f)
                    .constrainAs(rating) {
                        top.linkTo(parent.top)
                        start.linkTo(image.end, margin = (-26).dp)
                    }
            ) {
                if (showRating.value) {
                    Rating(rating = game.n10Rating, ratingBg = R.drawable.game_rating_bg)
                }
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp, bottom = 4.dp)
                    .fillMaxWidth()
                    .constrainAs(mainInfo) {
                        top.linkTo(image.top)
                        bottom.linkTo(comments.top)
                        start.linkTo(image.end)
                        end.linkTo(parent.end)

                        width = Dimension.fillToConstraints
                    }
            ) {
                Text(
                    text = game.title,
                    style = AppTheme.typography.body1,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                if (game.year > 0)
                    Text(text = game.year.toString(), style = AppTheme.typography.body2)
            }
            AsyncImage(
                model = game.photoUrl,
                contentDescription = game.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp)
                    .size(84.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)

                        height = Dimension.fillToConstraints
                    }
            )
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .constrainAs(comments) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_comments),
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = game.commentsTotal.toString(),
                    style = AppTheme.typography.body1,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Text(
                    text = " +${game.commentsTotalNew}",
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.increaseTextColor,
                    modifier = Modifier.align(Alignment.Bottom)
                )
            }

            if (game.isAddition) LabelWithBackground(
                stringResource(id = R.string.addition),
                Modifier
                    .constrainAs(addition) {
                        start.linkTo(image.end)
                        bottom.linkTo(comments.bottom)
                    }
                    .padding(8.dp)
            )
        }
    }
}

@Preview("GamePreview")
@Composable
fun GameContent_Preview() {
    TeseraTheme {
        GamePreviewContent(
            GamePreview(
                0,
                0,
                "Game extra1 extra2 extra3 extra4 extra5 extra6 long title extra4 extra5 extra6 long title",
                "",
                2023,
                "",
                144,
                45,
                8.89,
                "",
                true
            ), {}
        )
    }
}