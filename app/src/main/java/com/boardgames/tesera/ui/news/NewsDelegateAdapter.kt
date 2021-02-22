package com.boardgames.tesera.ui.news

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.boardgames.tesera.R

import com.boardgames.tesera.data.model.BoardgameItem
import com.boardgames.tesera.data.model.HotnessGameItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_hotness_game.*

class NewsDelegateAdapter {
    fun createAdapter(click: (BoardgameItem) -> Unit) = AsyncListDifferDelegationAdapter(
        DiffBoardgameItem,
        AdapterDelegatesManager<List<BoardgameItem>>()
            .addDelegate(hotnessBoardgamesAdapter(click))
    )

    private fun hotnessBoardgamesAdapter(click: (BoardgameItem) -> Unit) =
        adapterDelegateLayoutContainer<HotnessGameItem, BoardgameItem>(R.layout.item_hotness_game) {
            bind {
                hotnessGameTitle.text = item.title
            }
        }

    private object DiffBoardgameItem : DiffUtil.ItemCallback<BoardgameItem>() {
        override fun areItemsTheSame(oldItem: BoardgameItem, newItem: BoardgameItem): Boolean =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: BoardgameItem, newItem: BoardgameItem): Boolean =
            oldItem == newItem

    }
}