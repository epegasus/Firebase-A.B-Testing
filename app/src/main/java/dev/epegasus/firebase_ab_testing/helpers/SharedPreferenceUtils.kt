package dev.epegasus.firebase_ab_testing.helpers

import android.content.SharedPreferences

class SharedPreferenceUtils(private val sharedPreferences: SharedPreferences) {


    // Remote Config
    private val abTest = "ab_test"

    companion object {
        const val PREF_NAME = "ab_testing_preference"
    }

    /* ------------------------------------------------------ Remote Config ------------------------------------------------------ */

    var rcAbTest: String
        get() = sharedPreferences.getString(abTest, "") ?: ""
        set(value) {
            sharedPreferences.edit().apply {
                putString(abTest, value)
                apply()
            }
        }
}