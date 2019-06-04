package com.example.a10.go_bang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    DatabaseHelper(Context context) {
        super(context, "angkotku", null, 1);
        sqLiteDatabase = getWritableDatabase();
    }
    //method createTable untuk membuat table kamus
    private void createTable(SQLiteDatabase db){
        //db.execSQL("DROP TABLE IF EXISTS kamus");

        String createQuery = "create table if not exists " + Variabelglobal.driver + "(" +
                Variabelglobal.idDriver + " int(20), " +
                Variabelglobal.username + " char(100), " +
                Variabelglobal.statusLogin + " int(1));";

        db.execSQL(createQuery);
    }

    void insertRecord(String namaTabel, String nullColumnHack, ContentValues contentValues) {
        sqLiteDatabase.insert(namaTabel, nullColumnHack, contentValues);
    }

    void updateRecord(String namaTabel, ContentValues contentValues) {
        sqLiteDatabase.update(namaTabel, contentValues, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
        upgradeTable(db);
    }

    private void upgradeTable(SQLiteDatabase db) {
        //Gk bisa makek after di SQL
        String query = "update " + Variabelglobal.driver + " set " + Variabelglobal.statusLogin + " =0;";
        db.execSQL(query);

        /*query = "alter table " + Variabelglobal.tblAkun + " add column " + Variabelglobal.pemeriksaPrestasiKerja + " int(1);";
        db.execSQL(query);*/
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //super.onDowngrade(db, oldVersion, newVersion);
        downgradeTable(db);
    }

    private void downgradeTable(SQLiteDatabase db) {
        //Gk bisa makek after di SQL
        /*String query = "drop table " + Variabelglobal.tblAkun + ";";
        db.execSQL(query);
        createTable(db);*/

        /*query = "alter table " + Variabelglobal.tblAkun + " add column " + Variabelglobal.pemeriksaPrestasiKerja + " int(1);";
        db.execSQL(query);*/

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub
        createTable(db);
    }

    void selectTable(String namaTabel) {
        cursor = sqLiteDatabase.rawQuery("select * from " + namaTabel, null);
    }

    void updateTable(String namaTabel, ContentValues contentValues) {
        sqLiteDatabase.update(namaTabel, contentValues, null, null);
    }

    Boolean hasRow() {
        return cursor.moveToFirst();
    }

    String getIdDriver() {
        return cursor.getString(cursor.getColumnIndex(Variabelglobal.idDriver));
    }

    Boolean isLogin() {
        return cursor.getInt(cursor.getColumnIndex(Variabelglobal.statusLogin)) != 0;
    }

    String getUsername() {
        return cursor.getString(cursor.getColumnIndex(Variabelglobal.username));
    }

    /*String getNama(){
        return cursor.getString(cursor.getColumnIndex(Variabelglobal.nama));
    }

    String getKodeRumus() {
        return cursor.getString(cursor.getColumnIndex(Variabelglobal.kode_rumus));
    }

    String getLevelAkun () {
        return cursor.getString(cursor.getColumnIndex(Variabelglobal.levelAkun));
    }

    String getStatusPegawai () {
        return cursor.getString(cursor.getColumnIndex(Variabelglobal.statusPegawai));
    }

    Integer getIdLog() {
        return cursor.getInt(cursor.getColumnIndex(Variabelglobal.id));
    }

    Boolean isLogin() {
        return cursor.getInt(cursor.getColumnIndex(Variabelglobal.smartphone)) != 0;
    }

    Boolean isPemeriksa() {
        return cursor.getInt(cursor.getColumnIndex(Variabelglobal.pemeriksaPrestasiKerja)) == 1;
    }*/
}