package com.example.progandroproject

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.media.Image
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.BaseColumns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class KomenActivity : AppCompatActivity(){
    lateinit var db: SQLiteDatabase
    lateinit var btnSend: Button
    lateinit var btnBack: Button
    lateinit var etKomentar: EditText
    lateinit var ivLike : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.komentar)
        val id = intent.getStringExtra("id")
        val id1 = intent.getStringExtra("id1")
        db = DatabaseHelper(this).writableDatabase
        btnSend = findViewById(R.id.btnSend)
        btnBack = findViewById(R.id.btnBack)
        etKomentar = findViewById(R.id.etKomentar)



        //val text = etKomentar.text.toString()




        var komentar = ArrayList<Komentar>()
        val columns = arrayOf(
                BaseColumns._ID,
                DatabaseContract.Komentar.COLUMN_NAME_KOMENTAR,
                DatabaseContract.Komentar.COLUMN_NAME_IDGAMBAR
        )

        val selectionArgs = arrayOf(
                id
        )

        val cursor: Cursor = db.query(
                DatabaseContract.Komentar.TABLE_NAME,
                columns,
                "${DatabaseContract.Komentar.COLUMN_NAME_IDGAMBAR} = ?",
                selectionArgs,
                null,
                null,
                null
        )

        val cursorCount = cursor.count


        if( cursorCount > 0 ){
            while (cursor.moveToNext()){
                komentar.add(Komentar(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)))
            }

        }

        val komen_recycler = findViewById<RecyclerView>(R.id.rvKomentar)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        //layoutManager.reverseLayout = true
        komen_recycler.layoutManager = layoutManager
        val adapter = KomenAdapter(komentar)
        komen_recycler.adapter = adapter
        adapter.notifyDataSetChanged()





        btnSend.setOnClickListener(View.OnClickListener {
            val values = ContentValues().apply {
                put(DatabaseContract.Komentar.COLUMN_NAME_KOMENTAR, etKomentar.text.toString())
                put(DatabaseContract.Komentar.COLUMN_NAME_IDGAMBAR, id?.toInt())
            }
            //Toast.makeText(this,"test", Toast.LENGTH_LONG).show()
            db.insert(DatabaseContract.Komentar.TABLE_NAME,null,values)
            val intent = Intent(this,KomenActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)

             })

        btnBack.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("id",id1)
            startActivity(intent)

        })



    }
}