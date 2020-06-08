package com.example.administrator.weatherdemo.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.weatherdemo.PersonSQLiteOpenHelper;
import com.example.administrator.weatherdemo.bean.BookStore;

import java.util.ArrayList;
import java.util.List;

public class BookStoreDao {
    private PersonSQLiteOpenHelper dbHelper;

    public BookStoreDao(Context context){
        dbHelper = new PersonSQLiteOpenHelper(context,"BookStore.db",null,2);
    }

    public List<BookStore> findAll(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<BookStore> bookStores = new ArrayList<BookStore>();
        Cursor cursor = db.query("Book",new String[]{"id","author","price","pages","name"},null,null,null,null,null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String author = cursor.getString(cursor.getColumnIndex("author"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int pages = cursor.getInt(cursor.getColumnIndex("pages"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));
            BookStore bs = new BookStore(id,author,price,pages,name);
            bookStores.add(bs);
        }
        cursor.close();
        db.close();
        return bookStores;
    }
}
