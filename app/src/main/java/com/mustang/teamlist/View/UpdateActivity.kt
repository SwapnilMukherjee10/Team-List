package com.mustang.teamlist.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.mustang.teamlist.R

class UpdateActivity : AppCompatActivity() {

    lateinit var editTextName: EditText
    lateinit var editTextNumber: EditText
    lateinit var editTextEmail: EditText
    lateinit var editTextAddress: EditText
    lateinit var buttonCancel: Button
    lateinit var buttonUpdate: Button

    var currentId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        supportActionBar?.title = "Update Details"

        editTextName = findViewById(R.id.editTextNameUpdate)
        editTextNumber = findViewById(R.id.editTextNumberUpdate)
        editTextEmail = findViewById(R.id.editTextEmailUpdate)
        editTextAddress = findViewById(R.id.editTextAddressUpdate)
        buttonCancel = findViewById(R.id.buttonCancelUpdate)
        buttonUpdate = findViewById(R.id.buttonSaveUpdate)

        getAndSetData()

        buttonCancel.setOnClickListener {

            Toast.makeText(applicationContext, "Nothing updated", Toast.LENGTH_SHORT).show()
            finish()

        }

        buttonUpdate.setOnClickListener {

            updateDetails()

        }

    }

    private fun updateDetails() {

        val updatedName = editTextName.text.toString()
        val updatedNumber = editTextNumber.text.toString()
        val updatedEmail = editTextEmail.text.toString()
        val updatedAddress = editTextAddress.text.toString()

        val intent = Intent()
        intent.putExtra("updatedName",updatedName)
        intent.putExtra("updatedNumber",updatedNumber)
        intent.putExtra("updatedEmail",updatedEmail)
        intent.putExtra("updatedAddress",updatedAddress)
        if (currentId != -1) {
            intent.putExtra("memberId",currentId)
            setResult(RESULT_OK,intent)
            Toast.makeText(applicationContext, "Details updated", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun getAndSetData() {

        //get
        val currentName = intent.getStringExtra("currentName")
        val currentNumber = intent.getStringExtra("currentNumber")
        val currentEmail = intent.getStringExtra("currentEmail")
        val currentAddress = intent.getStringExtra("currentAddress")
        currentId = intent.getIntExtra("currentId",-1)

        //set
        editTextName.setText(currentName)
        editTextNumber.setText(currentNumber)
        editTextEmail.setText(currentEmail)
        editTextAddress.setText(currentAddress)

    }

}