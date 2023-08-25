package kz.btokmyrza.calorytracker.tracker_presentation.model

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import kz.btokmyrza.calorytracker.core.R
import kz.btokmyrza.calorytracker.core.util.UiText
import kz.btokmyrza.calorytracker.tracker_domain.model.MealType

data class MealDvo(
    val name: UiText,
    @DrawableRes
    val drawableRes: Int,
    val mealType: MealType,
    val carbs: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val calories: Int = 0,
    val isExpanded: Boolean = false,
) {

    fun getMealTypeName(): String = mealType.name

    fun getArrowDirectionImage(): ImageVector = Icons.Default.run {
        if (isExpanded) KeyboardArrowUp else KeyboardArrowDown
    }

    @Composable
    fun getArrowDirectionContentDescription(): String = if (isExpanded) {
        stringResource(id = R.string.collapse)
    } else {
        stringResource(id = R.string.extend)
    }
}

val defaultMeals = listOf(
    MealDvo(
        name = UiText.StringResource(R.string.breakfast),
        drawableRes = R.drawable.ic_breakfast,
        mealType = MealType.Breakfast,
    ),
    MealDvo(
        name = UiText.StringResource(R.string.lunch),
        drawableRes = R.drawable.ic_lunch,
        mealType = MealType.Lunch,
    ),
    MealDvo(
        name = UiText.StringResource(R.string.dinner),
        drawableRes = R.drawable.ic_dinner,
        mealType = MealType.Dinner,
    ),
    MealDvo(
        name = UiText.StringResource(R.string.snacks),
        drawableRes = R.drawable.ic_snack,
        mealType = MealType.Snack,
    ),
)