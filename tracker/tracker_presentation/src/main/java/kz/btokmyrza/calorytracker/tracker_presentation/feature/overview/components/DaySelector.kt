package kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.btokmyrza.calorytracker.core.R
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme

@Composable
internal fun DaySelector(
    modifier: Modifier = Modifier,
    date: String,
    onPreviousDayClick: () -> Unit,
    onNextDayClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onPreviousDayClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.previous_day),
            )
        }
        Text(
            text = date,
            style = MaterialTheme.typography.h2,
        )
        IconButton(onClick = onNextDayClick) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = stringResource(id = R.string.next_day),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DaySelectorPreview() {
    CaloryTrackerTheme {
        DaySelector(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            date = "25.08.2023",
            onPreviousDayClick = {},
            onNextDayClick = {},
        )
    }
}