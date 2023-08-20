package kz.btokmyrza.calorytracker.core.preferences

import kz.btokmyrza.calorytracker.core.model.ActivityLevel
import kz.btokmyrza.calorytracker.core.model.Gender
import kz.btokmyrza.calorytracker.core.model.GoalType
import kz.btokmyrza.calorytracker.core.model.UserInfo

interface Preferences {

    fun saveGender(gender: Gender)

    fun saveAge(age: Int)

    fun saveWeight(weight: Float)

    fun saveHeight(height: Int)

    fun saveActivityLevel(activityLevel: ActivityLevel)

    fun saveGoalType(goalType: GoalType)

    fun saveCarbRatio(carbRatio: Float)

    fun saveProteinRatio(proteinRatio: Float)

    fun saveFatRatio(fatRatio: Float)

    fun loadUserInfo(): UserInfo

    companion object {
        const val KEY_GENDER = "KEY_GENDER"
        const val KEY_AGE = "KEY_AGE"
        const val KEY_WEIGHT = "KEY_WEIGHT"
        const val KEY_HEIGHT = "KEY_HEIGHT"
        const val KEY_ACTIVITY_LEVEL = "KEY_ACTIVITY_LEVEL"
        const val KEY_GOAL_TYPE = "KEY_GOAL_TYPE"
        const val KEY_CARB_RATIO = "KEY_CARB_RATIO"
        const val KEY_PROTEIN_RATIO = "KEY_PROTEIN_RATIO"
        const val KEY_FAT_RATIO = "KEY_FAT_RATIO"
    }
}