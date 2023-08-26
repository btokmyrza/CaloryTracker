package kz.btokmyrza.calorytracker.tracker_presentation.feature.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kz.btokmyrza.calorytracker.core.R
import kz.btokmyrza.calorytracker.core_ui.theme.CaloryTrackerTheme
import kz.btokmyrza.calorytracker.core_ui.theme.LocalSpacing
import kz.btokmyrza.calorytracker.tracker_presentation.common.components.NutrientInfo
import kz.btokmyrza.calorytracker.tracker_presentation.feature.search.TrackerSearchUiState
import kz.btokmyrza.calorytracker.tracker_presentation.model.TrackableFoodDvo

@Composable
internal fun TrackableFoodItem(
    modifier: Modifier = Modifier,
    trackableFoodUiState: TrackerSearchUiState.TrackableFoodItem,
    onAmountChange: (String) -> Unit,
    onClick: () -> Unit,
    onTrack: () -> Unit,
) {
    val food = trackableFoodUiState.food
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .padding(spacing.spaceExtraSmall)
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(5.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = onClick)
            .padding(end = spacing.spaceMedium),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(topStart = 5.dp)),
                    painter = if (food.imageUrl.isNullOrBlank()) {
                        painterResource(id = R.drawable.ic_burger)
                    } else {
                        rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(data = food.imageUrl)
                                .apply(
                                    block = {
                                        crossfade(true)
                                        error(R.drawable.ic_burger)
                                        fallback(R.drawable.ic_burger)
                                    },
                                )
                                .build(),
                        )
                    },
                    contentDescription = food.name,
                    contentScale = ContentScale.Fit,
                )
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                    Text(
                        text = food.name,
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    Text(
                        text = stringResource(
                            id = R.string.kcal_per_100g,
                            formatArgs = arrayOf(food.caloriesPer100g),
                        ),
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
            Row {
                NutrientInfo(
                    name = stringResource(id = R.string.carbs),
                    amount = food.carbsPer100g,
                    unit = stringResource(id = R.string.grams),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.body2,
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                NutrientInfo(
                    name = stringResource(id = R.string.protein),
                    amount = food.proteinPer100g,
                    unit = stringResource(id = R.string.grams),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.body2,
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                NutrientInfo(
                    name = stringResource(id = R.string.fat),
                    amount = food.fatPer100g,
                    unit = stringResource(id = R.string.grams),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.body2,
                )
            }
        }
        AnimatedVisibility(visible = trackableFoodUiState.isExpanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row {
                    BasicTextField(
                        modifier = Modifier
                            .border(
                                shape = RoundedCornerShape(5.dp),
                                width = 0.5.dp,
                                color = MaterialTheme.colors.onSurface,
                            )
                            .alignBy(LastBaseline)
                            .padding(spacing.spaceMedium)
                            .semantics { contentDescription = "Amount" },
                        value = trackableFoodUiState.amount,
                        onValueChange = onAmountChange,
                        keyboardOptions = KeyboardOptions(
                            imeAction = trackableFoodUiState.getKeyboardAction(),
                            keyboardType = KeyboardType.Number,
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                onTrack()
                                defaultKeyboardAction(ImeAction.Done)
                            },
                        ),
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
                    Text(
                        text = stringResource(id = R.string.grams),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.alignBy(LastBaseline),
                    )
                }
                IconButton(
                    onClick = onTrack,
                    enabled = trackableFoodUiState.amount.isNotBlank(),
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.track),
                    )
                }
            }
        }
    }
}

internal val stubTrackableFood = TrackerSearchUiState.TrackableFoodItem(
    food = TrackableFoodDvo(
        name = "Burger",
        imageUrl = null,
        caloriesPer100g = 40,
        carbsPer100g = 30,
        proteinPer100g = 30,
        fatPer100g = 30,
    ),
    isExpanded = false,
)

@Preview
@Composable
private fun TrackableFoodItemPreview() {
    CaloryTrackerTheme {
        TrackableFoodItem(
            trackableFoodUiState = stubTrackableFood,
            onAmountChange = {},
            onClick = {},
            onTrack = {},
        )
    }
}

@Preview
@Composable
private fun TrackableFoodItemExpandedPreview() {
    CaloryTrackerTheme {
        TrackableFoodItem(
            trackableFoodUiState = stubTrackableFood.copy(isExpanded = true),
            onAmountChange = {},
            onClick = {},
            onTrack = {},
        )
    }
}