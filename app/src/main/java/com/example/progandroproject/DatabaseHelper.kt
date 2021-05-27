package com.example.progandroproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(val context: Context): SQLiteOpenHelper(context, "SosialMedia", null , 2) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DatabaseContract.User.SQL_CREATE_TABLE)
        db?.execSQL(DatabaseContract.Gambar.SQL_CREATE_TABLE)
        db?.execSQL(DatabaseContract.Komentar.SQL_CREATE_TABLE)
        db?.execSQL(DatabaseContract.Like.SQL_CREATE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DatabaseContract.User.SQL_DELETE_TABLE)
        db?.execSQL(DatabaseContract.Gambar.SQL_DELETE_TABLE)
        db?.execSQL(DatabaseContract.Komentar.SQL_DELETE_TABLE)
        db?.execSQL(DatabaseContract.Like.SQL_DELETE_TABLE)

        onCreate(db)

    }
}