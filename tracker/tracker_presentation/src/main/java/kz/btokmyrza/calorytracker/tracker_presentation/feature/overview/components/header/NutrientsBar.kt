package kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.components.header

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.CarbColor
import kz.btokmyrza.calorytracker.core_ui.theme.FatColor
import kz.btokmyrza.calorytracker.core_ui.theme.ProteinColor

@Composable
internal fun NutrientsBar(
    modifier: Modifier = Modifier,
    carbs: Int,
    protein: Int,
    fat: Int,
    calories: Int,
    calorieGoal: Int,
) {
    val background = MaterialTheme.colors.background
    val caloriesExceededColor = MaterialTheme.colors.error
    val carbWidthRatio = remember { Animatable(0f) }
    val proteinWidthRatio = remember { Animatable(0f) }
    val fatWidthRatio = remember { Animatable(0f) }
    LaunchedEffect(key1 = carbs) {
        carbWidthRatio.animateTo(targetValue = ((carbs * 4f) / calorieGoal))
    }
    LaunchedEffect(key1 = protein) {
        proteinWidthRatio.animateTo(targetValue = ((protein * 4f) / calorieGoal))
    }
    LaunchedEffect(key1 = fat) {
        fatWidthRatio.animateTo(targetValue = ((fat * 9f) / calorieGoal))
    }
    Canvas(modifier = modifier) {
        if (calories <= calorieGoal) {
            NutrientsBarCaloriesIndicator(
                background = background,
                carbWidthRatio = carbWidthRatio.value,
                proteinWidthRatio = proteinWidthRatio.value,
                fatWidthRatio = fatWidthRatio.value,
            )
        } else {
            NutrientsBarCaloriesExceeded(caloriesExceededColor)
        }
    }
}

private fun DrawScope.NutrientsBarCaloriesIndicator(
    background: Color,
    carbWidthRatio: Float,
    proteinWidthRatio: Float,
    fatWidthRatio: Float,
) {
    val carbsWidth = carbWidthRatio * size.width
    val proteinWidth = proteinWidthRatio * size.width
    val fatWidth = fatWidthRatio * size.width
    drawRoundRect(
        color = background,
        size = size,
        cornerRadius = CornerRadius(100f),
    )
    drawRoundRect(
        color = FatColor,
        size = Size(
            width = carbsWidth + proteinWidth + fatWidth,
            height = size.height,
        ),
        cornerRadius = CornerRadius(100f),
    )
    drawRoundRect(
        color = ProteinColor,
        size = Size(
            width = carbsWidth + proteinWidth,
            height = size.height,
        ),
        cornerRadius = CornerRadius(100f),
    )
    drawRoundRect(
        color = CarbColor,
        size = Size(
            width = carbsWidth,
            height = size.height,
        ),
        cornerRadius = CornerRadius(100f),
    )
}

private fun DrawScope.NutrientsBarCaloriesExceeded(
    caloriesExceededColor: Color,
) {
    drawRoundRect(
        color = caloriesExceededColor,
        size = size,
        cornerRadius = CornerRadius(100f),
    )
}

@Preview
@Composable
private fun NutrientsBarPreview() {
    CaloryTrackerTheme {
        NutrientsBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            carbs = 40,
            protein = 30,
            fat = 30,
            calories = 70,
            calorieGoal = 100,
        )
    }
}

@Preview
@Composable
private fun NutrientsBarCaloriesExceededPreview() {
    CaloryTrackerTheme {
        NutrientsBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            carbs = 40,
            protein = 30,
            fat = 30,
            calories = 150,
            calorieGoal = 100,
        )
    }
}