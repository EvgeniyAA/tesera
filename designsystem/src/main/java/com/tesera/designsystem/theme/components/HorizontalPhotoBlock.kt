package com.tesera.designsystem.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.TeseraTheme
import com.tesera.domain.model.Photo

@Composable
fun HorizontalPhotoBlock(items: List<Photo>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            shape = RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppTheme.colors.secondaryBackground.copy(alpha = 0.2f),
                contentColor = AppTheme.colors.secondaryBackground.copy(alpha = 0.2f)
            )
        ) {
            Text(
                text = stringResource(R.string.user_photo),
                modifier = Modifier.padding(8.dp),
                color = AppTheme.colors.secondaryTextColor
            )
        }
        if (items.isNotEmpty()) {
            LazyRow(modifier = Modifier.padding(top = 8.dp)) {
                items(items) { item ->
                    AsyncImage(
                        model = item.photoUrl,
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(80.dp)
                            .clickable {  }
                    )
                }
            }
        } else {
            Text(
                text = stringResource(id = R.string.empty_block),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                style = AppTheme.typography.body1,
                color = AppTheme.colors.primaryTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview("HorizontalPhotoBlock_Preview")
@Composable
fun HorizontalPhotoBlock_Preview() {
    TeseraTheme {
        HorizontalPhotoBlock(emptyList())
    }
}