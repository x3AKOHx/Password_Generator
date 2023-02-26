package com.example.passwordgenerator

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.example.passwordgenerator.compose.MainScreen
import dagger.hilt.android.AndroidEntryPoint

private lateinit var biometricPrompt: BiometricPrompt
private lateinit var promptInfo: BiometricPrompt.PromptInfo

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }

    override fun onResume() {
        super.onResume()

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Авторизуйтесь для доступа к приложению")
            .setSubtitle("Используйте отпечаток пальца или пароль")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .build()

        val authenticationCallback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                this@MainActivity.finishAffinity()
            }
        }
        biometricPrompt = BiometricPrompt(this, mainExecutor, authenticationCallback)
        biometricPrompt.authenticate(promptInfo)
    }
}