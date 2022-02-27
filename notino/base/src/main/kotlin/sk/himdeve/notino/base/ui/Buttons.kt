package sk.himdeve.notino.base.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import sk.himdeve.notino.base.theme.Black_a30
import sk.himdeve.notino.base.theme.ButtonPadding
import sk.himdeve.notino.base.theme.Transparent

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
@Composable
fun ContouredButton(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    @DrawableRes iconRes: Int? = null,
    fontSize: TextUnit = MaterialTheme.typography.button.fontSize,
    contentPadding: Dp = ButtonPadding,
    onClick: (() -> Unit)?
) = ContouredButton(
    text = stringResource(id = textRes).uppercase(),
    modifier = modifier,
    isEnabled = isEnabled,
    iconRes = iconRes,
    fontSize = fontSize,
    contentPadding = contentPadding,
    onClick = onClick
)

@Composable
fun ContouredButton(
    text: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    @DrawableRes iconRes: Int? = null,
    fontSize: TextUnit = MaterialTheme.typography.button.fontSize,
    contentPadding: Dp = ButtonPadding,
    onClick: (() -> Unit)?
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick ?: {},
        enabled = isEnabled,
        contentPadding = PaddingValues(contentPadding),
        border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Transparent,
            contentColor = MaterialTheme.colors.onPrimary,
            disabledContentColor = Black_a30
        )
    ) {
        if (iconRes != null) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.padding(end = 12.dp)
            )
        }

        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            textAlign = TextAlign.Center
        )
    }
}