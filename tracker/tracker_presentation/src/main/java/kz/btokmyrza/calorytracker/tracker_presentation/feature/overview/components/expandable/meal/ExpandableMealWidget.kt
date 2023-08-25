package kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.components.expandable.meal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.btokmyrza.calorytracker.core.R
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing
import kz.btokmyrza.calorytracker.tracker_presentation.common.components.NutrientInfo
import kz.btokmyrza.calorytracker.tracker_presentation.common.components.UnitDisplay
import kz.btokmyrza.calorytracker.tracker_presentation.model.MealDvo

@Composable
internal fun ExpandableMealWidget(
    modifier: Modifier = Modifier,
    meal: MealDvo,
    onToggleClick: () -> Unit,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onToggleClick)
                .padding(spacing.spaceMedium),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = meal.drawableRes),
                contentDescription = meal.name.asString(context),
            )
            Spacer(modifier = Modifier.width(spacing.spaceMedium))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = meal.name.asString(context),
                        style = MaterialTheme.typography.h3,
                    )
                    Icon(
                        imageVector = meal.getArrowDirectionImage(),
                        contentDescription = meal.getArrowDirectionContentDescription(),
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    UnitDisplay(
                        amount = meal.calories,
                        unit = stringResource(id = R.string.kcal),
                        amountTextSize = 30.sp,
                    )
                    Row {
                        NutrientInfo(
                            name = stringResource(id = R.string.carbs),
                            amount = meal.carbs,
                            unit = stringResource(id = R.string.grams),
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        NutrientInfo(
                            name = stringResource(id = R.string.protein),
                            amount = meal.protein,
                            unit = stringResource(id = R.string.grams),
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        NutrientInfo(
                            name = stringResource(id = R.string.fat),
                            amount = meal.fat,
                            unit = stringResource(id = R.string.grams),
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        AnimatedVisibility(
            visible = meal.isExpanded,
            content = content,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpandableMealPreview() {
    CaloryTrackerTheme {
        ExpandableMealWidget(
            modifier = Modifier.padding(8.dp),
            meal = stubMealBreakfast,
            onToggleClick = {},
            content = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpandableMealExpandedPreview() {
    CaloryTrackerTheme {
        ExpandableMealWidget(
            modifier = Modifier.padding(8.dp),
            meal = stubMealBreakfast.copy(isExpanded = true),
            onToggleClick = {},
            content = {
                ExpandableMealItem(
                    trackedFoods = stubTrackedFoods,
                    meal = stubMealBreakfast,
                    onDeleteTrackedFoodClick = {},
                    onAddFoodClick = {},
                )
            },
        )
    }
}