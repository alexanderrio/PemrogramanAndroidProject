package com.example.progandroproject

import android.provider.BaseColumns

class DatabaseContract {
    object User: BaseColumns{
        var TABLE_NAME = "user"
        var COLUMN_NAME_USERNAME = "username"
        var COLUMN_NAME_PASSWORD = "password"

        var SQL_CREATE_TABLE = "CREATE TABLE ${TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY NOT NULL," +
                "${COLUMN_NAME_USERNAME} TEXT," +
                "${COLUMN_NAME_PASSWORD} TEXT)"

        var SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${TABLE_NAME}"
    }

    object Gambar: BaseColumns{
        var TABLE_NAME = "gambar"
        var COLUMN_NAME_GAMBAR = "gambar"
        var COLUMN_NAME_PEMILIK = "id_pemilik"
        var COLUMN_NAME_JUMLAHLIKE = "jumlah_like"

        var SQL_CREATE_TABLE = "CREATE TABLE ${TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY NOT NULL,"+
                "${COLUMN_NAME_GAMBAR} blob,"+
                "${COLUMN_NAME_PEMILIK} INTEGER,"+
                "${COLUMN_NAME_JUMLAHLIKE} INTEGER)"
        var SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${TABLE_NAME}"
    }

    object Komentar: BaseColumns{
        var TABLE_NAME = "komentar"
        var COLUMN_NAME_KOMENTAR = "komentar_orang"
        var COLUMN_NAME_IDGAMBAR = "id_gambar"

        var SQL_CREATE_TABLE = "CREATE TABLE ${TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY NOT NULL,"+
                "${COLUMN_NAME_KOMENTAR} TEXT,"+
                "${COLUMN_NAME_IDGAMBAR} INTEGER)"
        var SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${TABLE_NAME}"
    }

    object Like: BaseColumns{
        var TABLE_NAME = "like"
        var COLUMN_NAME_ISLIKE = "like"
        var COLUMN_NAME_IDGAMBAR = "id_gambar"
        var COLUMN_NAME_IDUSER = "id_user"
        var COLUMN_NAME_IDKOMENTAR = "id_komentar"

        var SQL_CREATE_TABLE = "CREATE TABLE ${TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY NOT NULL,"+
                "${COLUMN_NAME_ISLIKE} BOOLEAN,"+
                "${COLUMN_NAME_IDGAMBAR} INTEGER,"+
                "${COLUMN_NAME_IDUSER} INTEGER,"+
                "${COLUMN_NAME_IDKOMENTAR} INTEGER)"
        var SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${TABLE_NAME}"
    }

}