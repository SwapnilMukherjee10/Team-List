package com.mustang.teamlist.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.mustang.teamlist.R

class AddMemberActivity : AppCompatActivity() {

    lateinit var editTextName: EditText
    lateinit var editTextNumber: EditText
    lateinit var editTextEmail: EditText
    lateinit var editTextAddress: EditText
    lateinit var buttonCancel: Button
    lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)

        supportActionBar?.title = "Add Details"

        editTextName = findViewById(R.id.editTextName)
        editTextNumber = findViewById(R.id.editTextNumber)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextAddress = findViewById(R.id.editTextAddress)
        buttonCancel = findViewById(R.id.buttonCancel)
        buttonSave = findViewById(R.id.buttonSave)

        buttonCancel.setOnClickListener {

            Toast.makeText(applicationContext, "Nothing submitted", Toast.LENGTH_SHORT).show()
            finish()

        }

        buttonSave.setOnClickListener {

            saveDetails()

        }

    }

    private fun saveDetails() {

        val memberName: String = editTextName.text.toString()
        val memberNumber: String = editTextNumber.text.toString()
        val memberEmail: String = editTextEmail.text.toString()
        val memberAddress: String = editTextAddress.text.toString()

        if (memberName == "" || memberNumber == "" || memberEmail == "" || memberAddress == "") {

            Toast.makeText(applicationContext, "Fill all details before saving", Toast.LENGTH_SHORT).show()

        } else {
            val intent = Intent()
            intent.putExtra("memberName", memberName)
            intent.putExtra("memberNumber", memberNumber)
            intent.putExtra("memberEmail", memberEmail)
            intent.putExtra("memberAddress", memberAddress)
            setResult(RESULT_OK, intent)
            Toast.makeText(applicationContext, "Member added", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

}