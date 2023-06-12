package com.ashutosh.ams

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminDBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table registration " + "(res_id integer primary key autoincrement, user_fname text,user_lname text,user_email text,user_id text,user_phone text,user_pass text)")
        db.execSQL("create table teacher_reg " + "(res_id integer primary key autoincrement, user_fname text,user_lname text,user_address text,user_phone text,user_id text,user_pass text)")
        db.execSQL("create table student_reg " + "(res_id integer primary key autoincrement, user_fname text,user_lname text,user_dept text,user_class text,user_address text,user_phone text,user_id text,user_pass text)")
        db.execSQL("create table attendance_sessiontable " + "(session_date text primary key,session_faculty_id text,session_dept text,session_class text,session_subject text)")
        db.execSQL("create table attendance_table" + "(session_date text ,session_faculty_id text, student_id text )")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS registration")
        db.execSQL("DROP TABLE IF EXISTS teacher_reg")
        db.execSQL("DROP TABLE IF EXISTS student_reg")
        db.execSQL("DROP TABLE IF EXISTS attendance_sessiontable")
        db.execSQL("DROP TABLE IF EXISTS attendance_table")
    }

    fun insert_userData(tablename: String?, contentValues: ContentValues?): Boolean {
        val db = this.writableDatabase
        db.insert(tablename, null, contentValues)
        return true
    }

    fun isPresent(id: String, stu_id: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("select * from attendance_table where session_date='$id' AND student_id = '$stu_id'", null)
        if (cursor.count <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }

    fun delete(id: String, stu_id: String) {
        val db = this.writableDatabase
        db.delete("attendance_table", "session_date='$id' AND student_id='$stu_id'", null)
    }

    fun get_userdata(tablename: String): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("select * from '$tablename'", null)
    }

    fun check_login(tablename: String, username: String, userpass: String): Cursor {
        val cursor: Cursor
        val db = this.readableDatabase
        cursor = db.rawQuery("select * from '$tablename' where user_id='$username'and user_pass='$userpass'", null)
        return cursor
    }

    fun DataDelete(tablename: String?, id: Int) {
        val database = writableDatabase
        database.delete(tablename, "res_id='$id'", null)
    }

    fun getstudentdata(class_student: String, dept: String): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM student_reg WHERE user_dept ='$dept' AND user_class='$class_student'", null)
    }

    fun showattendance(): Cursor {
        val database = this.readableDatabase
        return database.rawQuery("select * from attendance_table a inner join attendance_sessiontable b ON a.session_date=b.session_date", null)
    }

    fun showattendance(date: String): Cursor {
        val database = this.readableDatabase
        return database.rawQuery("select * from attendance_table a inner join attendance_sessiontable b ON a.session_date=b.session_date where a.session_date='$date'", null)
    }

    companion object {
        const val DATABASE_NAME = "Admin.db"
    }
}