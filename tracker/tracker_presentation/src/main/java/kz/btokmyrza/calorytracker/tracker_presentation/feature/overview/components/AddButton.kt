package kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.btokmyrza.calorytracker.core.R
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing

@Composable
internal fun AddButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(100f))
            .clickable(onClick = onClick)
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(100f),
            )
            .padding(spacing.spaceMedium),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.add),
            tint = color,
        )
        Spacer(modifier = Modifier.width(spacing.spaceMedium))
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            color = color,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddButtonPreview() {
    CaloryTrackerTheme {
        AddButton(
            modifier = Modifier.padding(8.dp),
            text = "Add",
            onClick = {},
        )
    }
}