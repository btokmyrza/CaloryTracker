package kz.btokmyrza.calorytracker.onboarding_presentation.common.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing

@Composable
internal fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.button,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        enabled = isEnabled,
        shape = RoundedCornerShape(100.dp),
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier.padding(LocalSpacing.current.spaceSmall),
            text = text,
            style = textStyle,
            color = MaterialTheme.colors.onPrimary,
        )
    }
}

@Preview
@Composable
private fun ActionButtonEnabledPreview() {
    CaloryTrackerTheme {
        ActionButton(
            modifier = Modifier.padding(16.dp),
            text = "Action Button Text",
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun ActionButtonDisabledPreview() {
    CaloryTrackerTheme {
        ActionButton(
            modifier = Modifier.padding(16.dp),
            text = "Action Button Text",
            isEnabled = false,
            onClick = {},
        )
    }
}