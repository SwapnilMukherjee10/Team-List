package com.mustang.teamlist.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.mustang.teamlist.databinding.ActivityOtpactivityBinding

class OTPActivity : AppCompatActivity() {

    lateinit var otpBinding: ActivityOtpactivityBinding

    lateinit var verificationID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        otpBinding = ActivityOtpactivityBinding.inflate(layoutInflater)
        val view = otpBinding.root
        setContentView(view)

        verificationID = intent.getStringExtra("verificationId").toString()

        otpBinding.buttonVerify.setOnClickListener {

            if (otpBinding.editTextOTP.text.toString().trim().isEmpty()
            ) {

                Toast.makeText(applicationContext, "OTP is invalid", Toast.LENGTH_SHORT).show()

            } else if (verificationID != null) {

                val code: String = otpBinding.editTextOTP.text.toString().trim()

                val userPhone = intent.getStringExtra("phone")
                val credential = PhoneAuthProvider.getCredential(verificationID, code)
                FirebaseAuth.getInstance()
                    .signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@OTPActivity, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            Toast.makeText(applicationContext, "Welcome $userPhone", Toast.LENGTH_SHORT).show()
                            startActivity(intent)

                        } else {
                            Toast.makeText(
                                applicationContext,
                                "OTP is not valid",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

}
