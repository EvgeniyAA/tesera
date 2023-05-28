package com.tesera.designsystem.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tesera.designsystem.theme.AppTheme
import com.tesera.domain.model.SearchItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResult(
    item: SearchItem,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.primaryBackground),
        shape = RoundedCornerShape(corner = CornerSize(0.dp)),
        onClick = onClick
    ) {
        Box {
            Row(modifier = Modifier.padding(8.dp)) {
                AsyncImage(
                    model = item.photoUrl,
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .size(48.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(16.dp)))

                )
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = item.title, style = AppTheme.typography.body1,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = item.title2, style = AppTheme.typography.body2)
                }
            }
        }
    }
}