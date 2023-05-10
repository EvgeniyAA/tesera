package com.tesera.designsystem.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import com.tesera.domain.model.GamePreviewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalGamePreviewContent(
    modifier: Modifier = Modifier,
    game: GamePreviewModel,
    onClick: (GamePreviewModel) -> Unit = {},
) {
    val showRating = rememberSaveable { mutableStateOf(game.n10Rating > 0) }
    Card(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .width(100.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.interactiveBackground),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        onClick = { onClick(game) }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            val (rating, image, mainInfo) = createRefs()
            Box(
                modifier = Modifier
                    .zIndex(1f)
                    .constrainAs(rating) {
                        top.linkTo(parent.top)
                        start.linkTo(image.end, margin = (-35).dp)
                    }
            ) {
                if (showRating.value) {
                    Image(
                        painter = painterResource(id = R.drawable.user_rating_bg),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .padding(4.dp)
                    )
                    Text(
                        text = String.format("%.1f", game.n10Rating),
                        style = AppTheme.typography.hint,
                        color = AppTheme.colors.hintTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Center)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
                    .fillMaxWidth()
                    .constrainAs(mainInfo) {
                        top.linkTo(image.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(image.start)
                        end.linkTo(image.end)

                        width = Dimension.fillToConstraints
                    }
            ) {
                if (game.isAddition) LabelWithBackground(stringResource(id = R.string.addition))
                Text(
                    text = game.title, style = AppTheme.typography.body1, maxLines = 2,
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
                    .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp)
                    .size(84.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)

                        height = Dimension.fillToConstraints
                    }
            )
        }
    }
}

@Preview("HorizontalGamePreviewContent")
@Composable
fun HorizontalGamePreviewContent_Preview() {
    TeseraTheme {
        HorizontalGamePreviewContent(
            Modifier,
            GamePreviewModel(
                0,
                0,
                "Game extra1 extra2 extra3 extra4 extra5 extra6 long title",
                "",
                2023,
                "",
                144,
                45,
                8.89,
                "",
                true
            )
        )
    }
}