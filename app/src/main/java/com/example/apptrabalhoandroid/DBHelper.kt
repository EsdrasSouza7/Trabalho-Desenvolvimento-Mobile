package com.example.apptrabalhoandroid

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "historico.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "historico"
        const val COLUMN_ID = "id"
        const val COLUMN_CALC = "calculo"
        const val COLUMN_RESP = "resposta"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CALC + " TEXT, "
                + COLUMN_RESP + " DOUBLE)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    private fun getRowCount(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME", null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count
    }

    private fun deleteOldestRecord() {
        val db = this.writableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID),
            null,
            null,
            null,
            null,
            "$COLUMN_ID ASC",
            "1"
        )

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))

        }
        cursor.close()
    }

    fun insertData(calculo: String, resposta: Double): Boolean {
        if (getRowCount() >= 10) {
            deleteOldestRecord()
        }
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_CALC, calculo)
        contentValues.put(COLUMN_RESP, resposta)
        val result = db.insert(TABLE_NAME, null, contentValues)
        return result != -1L
    }

    fun getAllData(): List<Historico> {
        val list = mutableListOf<Historico>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val calculo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CALC))
                val resposta = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_RESP))
                list.add(Historico(id, calculo, resposta))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
}
