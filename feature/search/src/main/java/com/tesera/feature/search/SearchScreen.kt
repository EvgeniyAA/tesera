package com.tesera.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.components.SearchResult
import com.tesera.designsystem.theme.components.TeseraInputField
import com.tesera.feature.search.models.SearchIntent.UiActions.SearchTextChanged

@Composable
fun SearchScreen(
    onGameDetails: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        TeseraInputField(
            text = state.query,
            onValueChange = { (viewModel::obtainIntent)(SearchTextChanged(it)) }, //(viewModel::obtainIntent)::SearchTextChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Search"
        )
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    strokeWidth = 2.dp,
                    color = AppTheme.colors.primaryTintColor
                )
            }
        }

        if (state.isEmpty) {
            Text(text = "Nothing found", modifier = Modifier.fillMaxWidth())
        }
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(state.history) {
                Text(text = it)
            }
            items(state.searchResult) {
                SearchResult(item = it) { onGameDetails(it.alias) }
            }
        }
    }

}