package dev.epegasus.firebase_ab_testing.helpers

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dev.epegasus.firebase_ab_testing.R
import dev.epegasus.firebase_ab_testing.helpers.SharedPreferenceUtils.Companion.PREF_NAME

const val TAG = "MyTag"

class RemoteConfiguration(context: Context) {

    private val abTest = "ab_test"
    private val sharedPreferenceUtils = SharedPreferenceUtils(context.getSharedPreferences(PREF_NAME, MODE_PRIVATE))

    fun checkRemoteConfig(callback: (result: String) -> Unit) {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings { minimumFetchIntervalInSeconds = 2 }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        fetchRemoteValues(callback)
    }

    private fun fetchRemoteValues(callback: (result: String) -> Unit) {
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.fetchAndActivate().addOnCompleteListener { updateRemoteValues(callback) }
    }

    private fun updateRemoteValues(callback: (result: String) -> Unit) {
        val remoteConfig = Firebase.remoteConfig

        // Save this value anywhere
        sharedPreferenceUtils.apply {
            rcAbTest = remoteConfig[abTest].asString()
        }
        Log.d(TAG, "updateRemoteValues: abTest: ${sharedPreferenceUtils.rcAbTest}")
        callback.invoke(sharedPreferenceUtils.rcAbTest)
    }
}