package kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.components.expandable.meal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.btokmyrza.calorytracker.core.R
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing
import kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.components.AddButton
import kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.components.TrackedFoodItem
import kz.btokmyrza.calorytracker.tracker_presentation.model.MealDvo
import kz.btokmyrza.calorytracker.tracker_presentation.model.TrackedFoodDvo

@Composable
internal fun ExpandableMealItem(
    modifier: Modifier = Modifier,
    trackedFoods: List<TrackedFoodDvo>,
    meal: MealDvo,
    onDeleteTrackedFoodClick: (TrackedFoodDvo) -> Unit,
    onAddFoodClick: (MealDvo) -> Unit,
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.spaceSmall),
    ) {
        trackedFoods.forEach { food ->
            TrackedFoodItem(
                trackedFood = food,
                onDeleteClick = { onDeleteTrackedFoodClick(food) },
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }
        AddButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(
                id = R.string.add_meal,
                formatArgs = arrayOf(meal.name.asString(context)),
            ),
            onClick = { onAddFoodClick(meal) },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpandableMealItemPreview() {
    CaloryTrackerTheme {
        ExpandableMealItem(
            modifier = Modifier.padding(8.dp),
            trackedFoods = stubTrackedFoods,
            meal = stubMealBreakfast,
            onDeleteTrackedFoodClick = {},
            onAddFoodClick = {},
        )
    }
}