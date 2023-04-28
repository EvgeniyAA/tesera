package com.tesera.designsystem.theme.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.StickyHeader(
    @DrawableRes icon: Int? = null,
    @StringRes text: Int? = null,
    onClick: () -> Unit = {},
    showMoreButton: Boolean = false,
    onMoreButtonClick: () -> Unit = {}
) {
    stickyHeader {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = AppTheme.colors.primaryBackground)
                .clickable { onClick() },

            ) {
            if (icon != null) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(16.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            if (text != null) {
                Text(
                    text = stringResource(id = text),
                    style = AppTheme.typography.body1,
                    modifier = Modifier
                        .padding(start = 4.dp, top = 8.dp, bottom = 8.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            if (showMoreButton) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                ) {
                    TeseraLinkButton(
                        text = stringResource(id = R.string.more),
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp),
                        onClick = { onMoreButtonClick() }
                    )
                }
            }
        }
    }
}