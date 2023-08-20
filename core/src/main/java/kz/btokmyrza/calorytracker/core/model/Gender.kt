package kz.btokmyrza.calorytracker.core.model

private const val MALE = "MALE"
private const val FEMALE = "FEMALE"

sealed class Gender(
    val name: String,
) {
    data object Male : Gender(MALE)
    data object Female : Gender(FEMALE)

    companion object {
        fun fromString(name: String): Gender = when (name) {
            MALE -> Male
            FEMALE -> Female
            else -> Male
        }
    }
}
