package com.tesera.designsystem.theme.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.TeseraTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileListButton(
    @DrawableRes icon: Int,
    iconTint: Color? = null,
    iconSize: Dp = 24.dp,
    text: String,
    textColor: Color = AppTheme.colors.secondaryTextColor,
    helperText: String? = null,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier,
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.primaryBackground),
        shape = RoundedCornerShape(corner = CornerSize(0.dp)),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
                AsyncImage(
                    model = icon,
                    colorFilter = iconTint?.let { ColorFilter.tint(color = it) },
                    contentDescription = text,
                    modifier = Modifier
                        .size(iconSize)
                )
            Column(modifier =
            Modifier.padding(start = 8.dp).fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    color = textColor,
                    style = AppTheme.typography.body1,
                    modifier = Modifier
                )

                helperText?.let {
                    LabelWithBackground(text = it, backgroundColor = AppTheme.colors.secondaryBackground)
                }
            }
        }
    }
}

@Preview("ProfileListButton_Preview")
@Composable
fun ProfileListButton_Preview() {
    TeseraTheme {
        ProfileListButton(icon = R.drawable.ic_hotness, text = "Games owned", helperText = "123") {}
    }
}