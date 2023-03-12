package com.mustang.teamlist.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.mustang.teamlist.databinding.ActivityLoginBinding
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    lateinit var loginBinding: ActivityLoginBinding


    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        loginBinding.buttonSubmit.setOnClickListener {

            if (loginBinding.editTextLoginNumber.text.toString().trim().isEmpty()) {
                Toast.makeText(applicationContext, "Invalid phone number", Toast.LENGTH_SHORT)
                    .show()
            } else if (loginBinding.editTextLoginNumber.text.toString().trim().length != 10) {
                Toast.makeText(applicationContext, "Type valid phone number", Toast.LENGTH_SHORT)
                    .show()
            } else {
                otpSend()
            }

        }

    }
    private fun otpSend() {

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                val intent = Intent(this@LoginActivity, OTPActivity::class.java)
                intent.putExtra("verificationId",verificationId)
                intent.putExtra("phone",loginBinding.editTextLoginNumber.text.toString().trim())
                startActivity(intent)
                Toast.makeText(applicationContext, "OTP sent to your number", Toast.LENGTH_LONG).show()

            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91"+ loginBinding.editTextLoginNumber.text.toString().trim())       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)


    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser
        if (user != null) {
            val userPhone = loginBinding.editTextLoginNumber.text.toString().trim()

            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            Toast.makeText(applicationContext, "Welcome $userPhone", Toast.LENGTH_SHORT).show()
            startActivity(intent)

        }
    }

}