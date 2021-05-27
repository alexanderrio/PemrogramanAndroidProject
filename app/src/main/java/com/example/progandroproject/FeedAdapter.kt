package com.example.progandroproject

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.BaseColumns
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayInputStream

class FeedAdapter(val posts: ArrayList<Gambar>)
    : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    class ViewHolder(val v: View) : RecyclerView.ViewHolder(v){
        var db: SQLiteDatabase = DatabaseHelper(v.context).writableDatabase
        var test = 0
        fun bindView(gambar: Gambar){


            v.findViewById<TextView>(R.id.jumlahlike).text = gambar.jumlahLike.toString()
            //val jumlahLike = v.findViewById<TextView>(R.id.jumlahlike).text.toString().toInt()

            val arrayInputStream = ByteArrayInputStream(gambar.gambar)
            v.findViewById<ImageView>(R.id.gambarpost).setImageBitmap(BitmapFactory.decodeStream(arrayInputStream))

            val columns1 = arrayOf(
                    BaseColumns._ID

            )
            val selectionArgs1 = arrayOf(
                    gambar.id.toString(),"0",gambar.currentUser.toString()
            )

            val cursor1: Cursor = db.query(
                    DatabaseContract.Like.TABLE_NAME,
                    columns1,
                    "${DatabaseContract.Like.COLUMN_NAME_IDGAMBAR} = ?"+
                            " AND ${DatabaseContract.Like.COLUMN_NAME_IDKOMENTAR} = ?"+
                            " AND ${DatabaseContract.Like.COLUMN_NAME_IDUSER} = ?",
                    selectionArgs1,
                    null,
                    null,
                    null
            )
            val cursorCount1 = cursor1.count

            if(cursorCount1 > 0){
                v.findViewById<ImageView>(R.id.gambarlike).setImageResource(R.drawable.ic_like)
            }else{
                v.findViewById<ImageView>(R.id.gambarlike).setImageResource(R.drawable.ic_like_border)
            }





//            if( cursorCount > 0){
//                v.findViewById<ImageView>(R.id.gambarlike).setImageResource(R.drawable.ic_like)
//           }else{
//                v.findViewById<ImageView>(R.id.gambarlike).setImageResource(R.drawable.ic_like_border)
//            }


            v.findViewById<ImageView>(R.id.gambarlike).setOnClickListener(View.OnClickListener {
                val columns = arrayOf(
                        BaseColumns._ID

                )
                val selectionArgs = arrayOf(
                        gambar.id.toString(),"0",gambar.currentUser.toString()
                )

                val cursor: Cursor = db.query(
                        DatabaseContract.Like.TABLE_NAME,
                        columns,
                        "${DatabaseContract.Like.COLUMN_NAME_IDGAMBAR} = ?"+
                                " AND ${DatabaseContract.Like.COLUMN_NAME_IDKOMENTAR} = ?"+
                                " AND ${DatabaseContract.Like.COLUMN_NAME_IDUSER} = ?",
                        selectionArgs,
                        null,
                        null,
                        null
                )
                val cursorCount = cursor.count
                if( cursorCount > 0){

                    cursor.moveToNext()
                    gambar.jumlahLike -= 1
                    v.findViewById<ImageView>(R.id.gambarlike).setImageResource(R.drawable.ic_like_border)
                    val values = ContentValues().apply {
                        put(DatabaseContract.Gambar.COLUMN_NAME_JUMLAHLIKE, gambar.jumlahLike.toString())

                    }
                    val selectionArgs = arrayOf(
                            gambar.id.toString()
                    )
                    db.update(DatabaseContract.Gambar.TABLE_NAME,values,"_id = ?",selectionArgs)

                    val selectionArgs1 = arrayOf(
                            cursor.getInt(0).toString()
                    )
                    db.delete(DatabaseContract.Like.TABLE_NAME,"_id = ?",selectionArgs1)

                    v.findViewById<TextView>(R.id.jumlahlike).text = gambar.jumlahLike.toString()
                    test--
                }else{
                    gambar.jumlahLike += 1

                    v.findViewById<ImageView>(R.id.gambarlike).setImageResource(R.drawable.ic_like)

                    val values = ContentValues().apply {
                        put(DatabaseContract.Gambar.COLUMN_NAME_JUMLAHLIKE, gambar.jumlahLike)
                    }
                    val selectionArgs = arrayOf(
                            gambar.id.toString()
                    )
                    db.update(DatabaseContract.Gambar.TABLE_NAME,values,"_id = ?",selectionArgs)

                    val values1 = ContentValues().apply {
                        put(DatabaseContract.Like.COLUMN_NAME_IDGAMBAR, gambar.id)
                        put(DatabaseContract.Like.COLUMN_NAME_IDKOMENTAR, 0)
                        put(DatabaseContract.Like.COLUMN_NAME_IDUSER, gambar.currentUser)
                        put(DatabaseContract.Like.COLUMN_NAME_ISLIKE,true)
                    }

                    db.insert(DatabaseContract.Like.TABLE_NAME,null,values1)

                    v.findViewById<TextView>(R.id.jumlahlike).text = gambar.jumlahLike.toString()
                    test++
                }


            })

            v.findViewById<ImageView>(R.id.gambarkomentar).setOnClickListener(View.OnClickListener {
                val intent = Intent(v.context, KomenActivity::class.java)
                intent.putExtra("id",gambar.id.toString())
                intent.putExtra("id1",gambar.currentUser.toString())
                v.context.startActivity(intent)
            })


        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(posts[position])


    }

    override fun getItemCount(): Int {
        return posts.size
    }
}