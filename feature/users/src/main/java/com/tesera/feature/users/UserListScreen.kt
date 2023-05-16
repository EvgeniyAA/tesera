package com.tesera.feature.users

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.tesera.designsystem.theme.components.OwnerItem
import com.tesera.designsystem.theme.components.TeseraToolbar

@Composable
fun UserListScreen(
    onBack: () -> Unit,
    onAuthorClicked: () -> Unit,
    viewModel: UserListViewModel = hiltViewModel(),
) {
    val owners = viewModel.owners.collectAsLazyPagingItems()
    val title = stringResource(id = R.string.has_game)
    Scaffold(modifier = Modifier.fillMaxHeight(),
        topBar = { TeseraToolbar(titleText = title, navAction = onBack) }
    ) {
        LazyColumn(Modifier.padding(it)) {
            items(owners, key = { key -> key.teseraId }) {
                it?.let { owner -> OwnerItem(owner = owner, onAuthorClicked) }
            }
        }
    }
}