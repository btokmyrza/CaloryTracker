package kz.btokmyrza.calorytracker.onboarding_presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing

@Composable
internal fun UnitTextField(
    modifier: Modifier = Modifier,
    value: String,
    unitName: String,
    textStyle: TextStyle = TextStyle(
        color = MaterialTheme.colors.primaryVariant,
        fontSize = 70.sp,
    ),
    onValueChange: (String) -> Unit,
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        BasicTextField(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .alignBy(LastBaseline),
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Text(
            modifier = Modifier.alignBy(LastBaseline),
            text = unitName,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UnitTextFieldPreview() {
    CaloryTrackerTheme {
        UnitTextField(
            modifier = Modifier.padding(8.dp),
            value = "180",
            unitName = "cm",
            onValueChange = {},
        )
    }
}