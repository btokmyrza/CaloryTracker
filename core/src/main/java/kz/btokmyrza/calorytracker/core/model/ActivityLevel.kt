package kz.btokmyrza.calorytracker.core.model

private const val LOW = "LOW"
private const val MEDIUM = "MEDIUM"
private const val HIGH = "HIGH"

sealed class ActivityLevel(
    val name: String,
) {
    data object Low : ActivityLevel(LOW)
    data object Medium : ActivityLevel(MEDIUM)
    data object High : ActivityLevel(HIGH)

    companion object {
        fun fromString(name: String): ActivityLevel = when (name) {
            LOW -> Low
            MEDIUM -> Medium
            HIGH -> High
            else -> Medium
        }
    }
}
