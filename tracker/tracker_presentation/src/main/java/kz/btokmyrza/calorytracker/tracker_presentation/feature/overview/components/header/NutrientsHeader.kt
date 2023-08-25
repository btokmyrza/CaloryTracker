package kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.components.header

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.btokmyrza.calorytracker.core.R
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.CarbColor
import kz.btokmyrza.calorytracker.core_ui.theme.FatColor
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing
import kz.btokmyrza.calorytracker.core_ui.theme.ProteinColor
import kz.btokmyrza.calorytracker.tracker_presentation.common.components.UnitDisplay
import kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.TrackerOverviewUiState

@Composable
internal fun NutrientsHeader(
    modifier: Modifier = Modifier,
    uiState: TrackerOverviewUiState
) {
    val spacing = LocalSpacing.current
    val animatedCurrentCalorieCount = animateIntAsState(
        targetValue = uiState.totalCalories,
        label = "animated Calorie Count",
    )
    val animatedCalorieGoalCount = animateIntAsState(
        targetValue = uiState.caloriesGoal,
        label = "animated Calorie Count",
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
            .background(MaterialTheme.colors.primary)
            .padding(horizontal = spacing.spaceLarge)
            .padding(vertical = spacing.spaceExtraLarge),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            UnitDisplay(
                amount = animatedCurrentCalorieCount.value,
                unit = stringResource(id = R.string.kcal),
                amountColor = MaterialTheme.colors.onPrimary,
                amountTextSize = 40.sp,
                unitColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier.align(Alignment.Bottom),
            )
            Column {
                Text(
                    text = stringResource(id = R.string.your_goal),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onPrimary,
                )
                UnitDisplay(
                    amount = animatedCalorieGoalCount.value,
                    unit = stringResource(id = R.string.kcal),
                    amountColor = MaterialTheme.colors.onPrimary,
                    amountTextSize = 40.sp,
                    unitColor = MaterialTheme.colors.onPrimary,
                )
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        NutrientsBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            carbs = uiState.totalCarbs,
            protein = uiState.totalProtein,
            fat = uiState.totalFat,
            calories = uiState.totalCalories,
            calorieGoal = uiState.caloriesGoal,
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            NutrientCircleWidget(
                modifier = Modifier.size(90.dp),
                value = uiState.totalCarbs,
                goal = uiState.carbsGoal,
                name = stringResource(id = R.string.carbs),
                color = CarbColor,
            )
            NutrientCircleWidget(
                modifier = Modifier.size(90.dp),
                value = uiState.totalProtein,
                goal = uiState.proteinGoal,
                name = stringResource(id = R.string.protein),
                color = ProteinColor,
            )
            NutrientCircleWidget(
                modifier = Modifier.size(90.dp),
                value = uiState.totalFat,
                goal = uiState.fatGoal,
                name = stringResource(id = R.string.fat),
                color = FatColor,
            )
        }
    }
}

@Preview
@Composable
private fun NutrientsHeaderPreview() {
    CaloryTrackerTheme {
        NutrientsHeader(
            modifier = Modifier.padding(8.dp),
            uiState = TrackerOverviewUiState(
                totalCarbs = 40,
                totalProtein = 30,
                totalFat = 30,
                totalCalories = 70,
                carbsGoal = 100,
                proteinGoal = 100,
                fatGoal = 100,
                caloriesGoal = 100,
            ),
        )
    }
}