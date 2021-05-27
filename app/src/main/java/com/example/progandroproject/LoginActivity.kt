package com.example.progandroproject

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.BaseColumns
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity: AppCompatActivity() {
    lateinit var db: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        db = DatabaseHelper(this).writableDatabase

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegis = findViewById<Button>(R.id.btnRegis)
        var pointer = 0

       btnLogin.setOnClickListener {
           val columns = arrayOf(
                   BaseColumns._ID,
                   DatabaseContract.User.COLUMN_NAME_USERNAME,
                   DatabaseContract.User.COLUMN_NAME_PASSWORD
           )
           val selectionArgs = arrayOf(
                   etUsername.text.toString(),etPassword.text.toString()
           )

           val cursor: Cursor = db.query(
                   DatabaseContract.User.TABLE_NAME,
                   columns,
                   "${DatabaseContract.User.COLUMN_NAME_USERNAME} LIKE ?"+
                           "AND ${DatabaseContract.User.COLUMN_NAME_PASSWORD} LIKE ?",
                    selectionArgs,
                   null,
                   null,
                   null
           )


           val cursorCount = cursor.count


           if( cursorCount > 0 ){
                cursor.moveToNext()
               val intent = Intent(this,MainActivity::class.java)
               intent.putExtra("id",cursor.getInt(0).toString())
               startActivity(intent)
           }else{
               pesan("Username atau Password salah")
           }
           cursor.close()
       }

       btnRegis.setOnClickListener {
           val intent2 = Intent(this,RegisActivity::class.java)
           startActivity(intent2)
       }

    }
    fun pesan(message: String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }
}