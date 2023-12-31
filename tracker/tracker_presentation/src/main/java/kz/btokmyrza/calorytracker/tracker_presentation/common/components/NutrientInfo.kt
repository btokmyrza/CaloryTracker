package kz.btokmyrza.calorytracker.tracker_presentation.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme

@Composable
internal fun NutrientInfo(
    modifier: Modifier = Modifier,
    name: String,
    amount: Int,
    unit: String,
    amountTextSize: TextUnit = 20.sp,
    amountColor: Color = MaterialTheme.colors.onBackground,
    unitTextSize: TextUnit = 14.sp,
    unitColor: Color = MaterialTheme.colors.onBackground,
    nameTextStyle: TextStyle = MaterialTheme.typography.body1
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UnitDisplay(
            amount = amount,
            unit = unit,
            amountTextSize = amountTextSize,
            amountColor = amountColor,
            unitTextSize = unitTextSize,
            unitColor = unitColor,
        )
        Text(
            text = name,
            color = MaterialTheme.colors.onBackground,
            style = nameTextStyle,
            fontWeight = FontWeight.Light,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NutrientInfoPreview() {
    CaloryTrackerTheme {
        NutrientInfo(
            modifier = Modifier.padding(8.dp),
            name = "Fat",
            amount = 40,
            unit = "g",
        )
    }
}