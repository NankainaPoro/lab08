package g313.vakulenko.lab08;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB  extends SQLiteOpenHelper {
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE notes (id INT, txt TEXT);";
        db.execSQL(sql);
    }

    public int getMaxId(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT MAX(id) FROM notes;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()) return cur.getInt(0);
        return 0;
    }

    public void addNote(int id, String stxt){
        String sid = String.valueOf(id);
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO notes VALUES (" + sid + ", '" + stxt + "');";
        db.execSQL(sql);
    }

    public String getNote(int id){
        String sid = String.valueOf(id);
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT txt FROM notes WHERE id = " + sid + ";";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()) return cur.getString(0);
        return "";
    }

    public void getAllNotes(ArrayList<mynote> lst){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id, txt FROM notes;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()){
            do {
                mynote n = new mynote();
                n.id = cur.getInt(0);
                n.txt = cur.getString(1);
                lst.add(n);
            } while (cur.moveToNext());
        }
    }

    public void alterNote(int id, int newID, String stxt){
        String sid = String.valueOf(id);
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE notes SET txt = '" + stxt + "' WHERE id = '" + sid + "'; ";
        db.execSQL(sql);
        sql = "UPDATE notes SET id = '" + newID + "' WHERE id = " + sid + ";";
        db.execSQL(sql);
    }

    public void delete_note(int id){
        String sid = String.valueOf(id);
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM notes WHERE id = '" + sid + "';";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}