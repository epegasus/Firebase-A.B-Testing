package dev.epegasus.firebase_ab_testing

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.installations.FirebaseInstallations
import dev.epegasus.firebase_ab_testing.databinding.ActivityMainBinding
import dev.epegasus.firebase_ab_testing.helpers.RemoteConfiguration
import dev.epegasus.firebase_ab_testing.helpers.TAG

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val remoteConfiguration by lazy { RemoteConfiguration(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getDeviceToken()

        remoteConfiguration.checkRemoteConfig {
            binding.tvTitleMain.text = it
            Toast.makeText(this, "Remote Configuration fetched", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDeviceToken() {
        // Add this 'id' in firebase AB testing console as a testing device
        FirebaseInstallations.getInstance().getToken(true)
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    Log.d(TAG, "Installation auth token: " + task.result.token)
                } else {
                    Log.e(TAG, "Unable to get Installation auth token")
                }
            }
    }
}