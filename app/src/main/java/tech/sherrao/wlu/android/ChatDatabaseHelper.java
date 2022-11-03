package tech.sherrao.wlu.android;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "messages";
    public static final int VERSION_NUM = 3;
    public static final String ID_COLUMN = "Id";
    public static final String MESSAGE_COLUMN = "Message";

    private SQLiteDatabase readDb;
    private SQLiteDatabase writeDb;

    public ChatDatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, VERSION_NUM);
        this.readDb = super.getReadableDatabase();
        this.writeDb = super.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(this.getClass().getSimpleName(), "Calling onCreate()");

        String query = String.format("CREATE TABLE %s (%s INT AUTO_INCREMENT, %s VARCHAR(255))", TABLE_NAME, ID_COLUMN, MESSAGE_COLUMN);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(this.getClass().getSimpleName(), "Calling onUpgrade(), oldVersion=" + oldVersion + ", newVersion=" + newVersion);

        String query = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(query);
        this.onCreate(db);
    }

    public void saveMessage(String message) {
        ContentValues values = new ContentValues();
        values.put(MESSAGE_COLUMN, message);

        this.writeDb.insert(TABLE_NAME, null, values);
    }

    @SuppressLint("Range")
    public List<String> getStoredMessages() {
        List<String> result = new ArrayList<>();
        Cursor cursor = this.readDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//                this.readDb.query(TABLE_NAME, new String[] {MESSAGE_COLUMN}, null, null, null, null, null, null );

        if(cursor.getColumnIndex(MESSAGE_COLUMN) == -1)
            return result;

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String message = cursor.getString( cursor.getColumnIndex(MESSAGE_COLUMN) );
            result.add(message);
            cursor.moveToNext();

            Log.i(this.getClass().getSimpleName(), "Retrieving message: \"" + message + "\"");
        }

        cursor.close();
        return result;
    }

    public void onDestroy() {
        Log.i(this.getClass().getSimpleName(), "Calling onDestroy()");
        super.close();
    }
}
