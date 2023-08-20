package kz.btokmyrza.calorytracker.onboarding_presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing

@Composable
internal fun SelectableButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.button,
    isSelected: Boolean,
    color: Color,
    selectedTextColor: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(100.dp))
            .border(
                width = 2.dp,
                color = color,
                shape = RoundedCornerShape(100.dp),
            )
            .background(
                color = if (isSelected) color else Color.Transparent,
                shape = RoundedCornerShape(100.dp)
            )
            .clickable(onClick = onClick)
            .padding(LocalSpacing.current.spaceMedium),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = textStyle,
            color = if (isSelected) selectedTextColor else color,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectableButtonSelectedPreview() {
    CaloryTrackerTheme {
        SelectableButton(
            modifier = Modifier.padding(8.dp),
            text = "Selectable Button text",
            isSelected = true,
            color = MaterialTheme.colors.primary,
            selectedTextColor = MaterialTheme.colors.onPrimary,
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectableButtonDeselectedPreview() {
    CaloryTrackerTheme {
        SelectableButton(
            modifier = Modifier.padding(8.dp),
            text = "Selectable Button text",
            isSelected = false,
            color = MaterialTheme.colors.primary,
            selectedTextColor = MaterialTheme.colors.onPrimary,
            onClick = {},
        )
    }
}