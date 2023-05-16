package com.tesera.designsystem.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.TeseraTheme
import com.tesera.domain.model.GameOwner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerItem(
    owner: GameOwner, onAuthorClicked: () -> Unit,
) {
    val showRating = rememberSaveable { mutableStateOf(owner.rating > 0) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.primaryBackground),
        shape = RoundedCornerShape(corner = CornerSize(0.dp)),
        onClick = onAuthorClicked
    ) {
        Box {
            Box(modifier = Modifier.zIndex(1f)) {
                if (showRating.value) {
                    Rating(rating = owner.rating, ratingBg = R.drawable.user_rating_bg)
                }
            }
            Row(modifier = Modifier.padding(16.dp)) {
                Avatar(owner = owner, avatarSize = 40f)
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(text = owner.name.takeIf { it.isNotEmpty() } ?: owner.login)
                }
            }
        }
    }
}

@Preview("OwnerPreview")
@Composable
fun Owner_Preview() {
    TeseraTheme {
        OwnerItem(owner = GameOwner(0, "Ivan", "Ivan Ivanov", 6.78, "nice", null, null)) {
        }
    }
}