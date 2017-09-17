package nakarin.birdssco.com.easyform.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by opticman on 17-Sep-17.
 */

public class MyManager {
    private Context context;
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MyManager(Context context) {
        this.context = context;
        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();
    }


    public long addNameToSQLite(String stringName,
                                String stringGender,
                                String stringProvince) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",stringName);
        contentValues.put("Gender",stringGender);
        contentValues.put("Province",stringProvince);
        return sqLiteDatabase.insert("nameTABLE",null,contentValues);
    }


} //Main Class
