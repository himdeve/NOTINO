package sk.himdeve.notino.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import sk.himdeve.formatter.format
import sk.himdeve.notino.base.R
import sk.himdeve.notino.base.theme.*
import sk.himdeve.notino.base.ui.ComposeList
import sk.himdeve.notino.base.ui.ContouredButton
import sk.himdeve.notino.base.ui.NoItems
import sk.himdeve.notino.products.Product
import sk.himdeve.notino.products.ProductId
import com.mahmoudalim.compose_rating_bar.RatingBarView

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel
) {
    val state by viewModel.state.collectAsState(initial = DashboardViewModel.State())
    LoginContent(state.loading, state.items, viewModel::heartClick)
}

@Composable
private fun LoginContent(
    loading: Boolean,
    items: List<Product>?,
    heartClick: ((productId: ProductId, favourite: Boolean) -> Unit)?
) {
    when {
        loading -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(color = Black, modifier = Modifier.align(Alignment.Center))
            }
        }
        items.isNullOrEmpty() -> {
            NoItems(
                textRes = R.string.dashboard_no_items,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
        else -> {
            ComposeList(
                items = items
            ) {
                ProductListItem(item = it, onClick = heartClick)
            }
        }
    }
}

@Composable
fun ProductListItem(item: Product, onClick: ((productId: ProductId, favourite: Boolean) -> Unit)?) {
    Surface(
        color = MaterialTheme.colors.primarySurface,
        modifier = Modifier.padding(ListItemMargin)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(ListItemContentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val defaultTopPadding = DefaultPadding / 2
            Box(Modifier.fillMaxSize()) {
                Image(
                    painter = rememberImagePainter(
                        data = item.imageUrl,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .align(Alignment.Center)
                )
                IconButton(
                    modifier = Modifier
                        .padding(DefaultPadding)
                        .size(22.dp)
                        .align(Alignment.TopEnd),
                    onClick = { onClick?.invoke(item.id, !item.favourite) },
                ){
                    val iconRes = if (item.favourite) R.drawable.ic_heart_fill else R.drawable.ic_heart_empty
                    val iconTint = if (item.favourite) Pink else Black

                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        tint = iconTint
                    )
                }
            }
            Text(
                text = item.brand.name,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = defaultTopPadding)
            )
            Text(
                text = item.name,
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = defaultTopPadding)
            )
            Text(
                text = item.annotation,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = defaultTopPadding)
            )
            RatingBarView(
                rating = rememberSaveable { mutableStateOf(item.reviewSummary.score) },
                ratedStarsColor = Pink,
                starSize = 22.dp,
                modifier = Modifier.padding(top = DefaultPadding)
            )
            Text(
                text = "${item.price.value.format(true)} ${item.price.currency}",
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = defaultTopPadding)
            )
            ContouredButton(
                textRes = R.string.dashboard_add,
                onClick = null,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    NotinoTheme {
        LoginContent(loading = false, items = null, heartClick = null)
    }
}