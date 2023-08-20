package kz.btokmyrza.calorytracker.core.model

private const val LOSE_WEIGHT = "LOSE_WEIGHT"
private const val KEEP_WEIGHT = "KEEP_WEIGHT"
private const val GAIN_WEIGHT = "GAIN_WEIGHT"

sealed class GoalType(
    val name: String,
) {
    data object LoseWeight : GoalType(LOSE_WEIGHT)
    data object KeepWeight : GoalType(KEEP_WEIGHT)
    data object GainWeight : GoalType(GAIN_WEIGHT)

    companion object {
        fun fromString(name: String): GoalType = when (name) {
            LOSE_WEIGHT -> LoseWeight
            KEEP_WEIGHT -> KeepWeight
            GAIN_WEIGHT -> GainWeight
            else -> KeepWeight
        }
    }
}
