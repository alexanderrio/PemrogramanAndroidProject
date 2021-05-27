package com.example.progandroproject

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.os.Bundle
import android.provider.BaseColumns
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {
    lateinit var db: SQLiteDatabase
    var SELECT_PICTURE = 100


    lateinit var ivGambarAdd: ImageView
    lateinit var ivGambarPost: ImageView

    var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        db = DatabaseHelper(this).writableDatabase
        id = intent.getStringExtra("id").toString()


        ivGambarAdd = findViewById<ImageView>(R.id.gambaradd)
        ivGambarPost = findViewById<ImageView>(R.id.gambarpost)

        var gambar = ArrayList<Gambar>()
        val columns = arrayOf(
                BaseColumns._ID,
                DatabaseContract.Gambar.COLUMN_NAME_GAMBAR,
                DatabaseContract.Gambar.COLUMN_NAME_PEMILIK,
                DatabaseContract.Gambar.COLUMN_NAME_JUMLAHLIKE
        )


        val cursor: Cursor = db.query(
                DatabaseContract.Gambar.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        )

        val cursorCount = cursor.count


        if( cursorCount > 0 ){
            while (cursor.moveToNext()){
                gambar.add(Gambar(cursor.getInt(0), cursor.getBlob(1), cursor.getInt(2),cursor.getInt(3),id.toInt()))
            }




        }else{
           // pesan("Username atau Password salah")
        }

        val feed_recycler = findViewById<RecyclerView>(R.id.rvGambar)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = false
        layoutManager.reverseLayout = false
        feed_recycler.layoutManager = layoutManager
        val adapter = FeedAdapter(gambar)
        feed_recycler.adapter = adapter
        ivGambarAdd.setOnClickListener(View.OnClickListener {
            checkPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE,101)
            imageChooser()

        })

        adapter.notifyDataSetChanged()



    }


    fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray? {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(CompressFormat.PNG, 0, outputStream)
        return outputStream.toByteArray()
    }

    fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
           // Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String?>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults)
        if (requestCode == 101) {
            if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // Toast.makeText(this@MainActivity, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                //Toast.makeText(this@MainActivity, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun imageChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, SELECT_PICTURE)
    }

    fun pesan(message: String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    fun resizeBitmap(source: Bitmap, maxLength: Int): Bitmap {
        try {
            if (source.height >= source.width) {
                if (source.height <= maxLength) { // if image height already smaller than the required height
                    return source
                }

                val aspectRatio = source.width.toDouble() / source.height.toDouble()
                val targetWidth = (maxLength * aspectRatio).toInt()
                val result = Bitmap.createScaledBitmap(source, targetWidth, maxLength, false)
                return result
            } else {
                if (source.width <= maxLength) { // if image width already smaller than the required width
                    return source
                }

                val aspectRatio = source.height.toDouble() / source.width.toDouble()
                val targetHeight = (maxLength * aspectRatio).toInt()

                val result = Bitmap.createScaledBitmap(source, maxLength, targetHeight, false)
                return result
            }
        } catch (e: Exception) {
            return source
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PICTURE){

            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, data?.data)
//            ivGambarPost.setImageURI(data?.data) // handle chosen image
//            var bitmap = (ivGambarPost.drawable as BitmapDrawable).bitmap

            //ivGambarPost.setImageBitmap(bitmap)
            val values = ContentValues().apply {
                put(DatabaseContract.Gambar.COLUMN_NAME_GAMBAR,getBitmapAsByteArray(resizeBitmap(bitmap,600)))
                put(DatabaseContract.Gambar.COLUMN_NAME_PEMILIK, id.toInt())
                put(DatabaseContract.Gambar.COLUMN_NAME_JUMLAHLIKE,0)
            }
            db.insert(DatabaseContract.Gambar.TABLE_NAME,null,values)

                val intent1 = Intent(this,MainActivity::class.java)
                intent1.putExtra("id",id)
                startActivity(intent)


        }
    }

}

