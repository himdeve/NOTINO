package sk.himdeve.notino.base.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> ComposeList(
    items: List<T>,
    modifier: Modifier = Modifier,
    listItem: @Composable LazyItemScope.(item: T) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Fixed(2)
    ) {
        items(
            items = items,
            itemContent = listItem
        )
    }
}