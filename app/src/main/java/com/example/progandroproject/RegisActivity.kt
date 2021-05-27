package com.example.progandroproject

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisActivity: AppCompatActivity() {
    lateinit var db: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis)

        db = DatabaseHelper(this).writableDatabase
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        val btnSubmit = findViewById<Button>(R.id.btnApa)

        val btnBack = findViewById<Button>(R.id.btnBack)

        btnSubmit.setOnClickListener {
            Toast.makeText(this,etUsername.text.toString(), Toast.LENGTH_LONG).show()
            val values = ContentValues().apply {
                put(DatabaseContract.User.COLUMN_NAME_USERNAME, etUsername.text.toString())
                put(DatabaseContract.User.COLUMN_NAME_PASSWORD, etPassword.text.toString())
            }
            //Toast.makeText(this,"test", Toast.LENGTH_LONG).show()
            db.insert(DatabaseContract.User.TABLE_NAME,null,values)
           // Toast.makeText(this,"test2", Toast.LENGTH_LONG).show()

            val intent2 = Intent(this,LoginActivity::class.java)
            startActivity(intent2)
        }

        btnBack.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}