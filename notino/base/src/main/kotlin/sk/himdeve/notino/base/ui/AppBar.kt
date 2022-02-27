package sk.himdeve.notino.base.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import sk.himdeve.notino.base.R
import sk.himdeve.notino.base.theme.*

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
@Composable
fun AppBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = DefaultPadding,
                end = DefaultPadding,
                top = DefaultPadding
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h1
        )

        Divider(
            color = Grey_a10,
            thickness = 1.dp,
            modifier = Modifier.padding(top = DefaultPadding * 2)
        )
    }
}