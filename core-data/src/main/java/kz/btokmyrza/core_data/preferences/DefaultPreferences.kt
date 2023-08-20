package kz.btokmyrza.core_data.preferences

import android.content.SharedPreferences
import kz.btokmyrza.calorytracker.core.model.ActivityLevel
import kz.btokmyrza.calorytracker.core.model.Gender
import kz.btokmyrza.calorytracker.core.model.GoalType
import kz.btokmyrza.calorytracker.core.model.UserInfo
import kz.btokmyrza.calorytracker.core.preferences.Preferences

class DefaultPreferences(
    private val sharedPreferences: SharedPreferences,
) : Preferences {

    override fun saveGender(gender: Gender) {
        sharedPreferences.edit()
            .putString(Preferences.KEY_GENDER, gender.name)
            .apply()
    }

    override fun saveAge(age: Int) {
        sharedPreferences.edit()
            .putInt(Preferences.KEY_AGE, age)
            .apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPreferences.edit()
            .putFloat(Preferences.KEY_WEIGHT, weight)
            .apply()
    }

    override fun saveHeight(height: Int) {
        sharedPreferences.edit()
            .putInt(Preferences.KEY_HEIGHT, height)
            .apply()
    }

    override fun saveActivityLevel(activityLevel: ActivityLevel) {
        sharedPreferences.edit()
            .putString(Preferences.KEY_ACTIVITY_LEVEL, activityLevel.name)
            .apply()
    }

    override fun saveGoalType(goalType: GoalType) {
        sharedPreferences.edit()
            .putString(Preferences.KEY_GOAL_TYPE, goalType.name)
            .apply()
    }

    override fun saveCarbRatio(carbRatio: Float) {
        sharedPreferences.edit()
            .putFloat(Preferences.KEY_CARB_RATIO, carbRatio)
            .apply()
    }

    override fun saveProteinRatio(proteinRatio: Float) {
        sharedPreferences.edit()
            .putFloat(Preferences.KEY_PROTEIN_RATIO, proteinRatio)
            .apply()
    }

    override fun saveFatRatio(fatRatio: Float) {
        sharedPreferences.edit()
            .putFloat(Preferences.KEY_FAT_RATIO, fatRatio)
            .apply()
    }

    override fun loadUserInfo(): UserInfo = UserInfo(
        gender = getGender(),
        age = getAge(),
        weight = getWeight(),
        height = getHeight(),
        activityLevel = getActivityLevel(),
        goalType = getGoalType(),
        carbRatio = getCarbRatio(),
        proteinRatio = getProteinRatio(),
        fatRatio = getFatRatio(),
    )

    private fun getGender(): Gender =
        sharedPreferences
            .getString(Preferences.KEY_GENDER, null)
            .orEmpty()
            .let(Gender::fromString)

    private fun getAge(): Int = sharedPreferences.getInt(Preferences.KEY_AGE, -1)

    private fun getWeight(): Float = sharedPreferences.getFloat(Preferences.KEY_WEIGHT, -1f)

    private fun getHeight(): Int = sharedPreferences.getInt(Preferences.KEY_HEIGHT, -1)

    private fun getActivityLevel(): ActivityLevel =
        sharedPreferences
            .getString(Preferences.KEY_ACTIVITY_LEVEL, null)
            .orEmpty()
            .let(ActivityLevel::fromString)

    private fun getGoalType(): GoalType =
        sharedPreferences
            .getString(Preferences.KEY_GOAL_TYPE, null)
            .orEmpty()
            .let(GoalType::fromString)

    private fun getCarbRatio(): Float = sharedPreferences.getFloat(Preferences.KEY_CARB_RATIO, -1f)

    private fun getProteinRatio(): Float =
        sharedPreferences.getFloat(Preferences.KEY_PROTEIN_RATIO, -1f)

    private fun getFatRatio(): Float = sharedPreferences.getFloat(Preferences.KEY_FAT_RATIO, -1f)
}