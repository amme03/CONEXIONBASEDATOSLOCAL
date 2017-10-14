package com.cosmo.arquitecturamvpbase.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cosmo.arquitecturamvpbase.schemes.IProductScheme;

/**
 * Created by jersonsuaza on 9/30/17.
 */

class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(IProductScheme.PRODUCT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Constants.DATABASE_NAME, " actualizando de base de datos "+ oldVersion + " a: " + newVersion);
        /*
        for (int i = oldVersion+1; i <=newVersion ; i++) {

            if(2==i)
            {

            }
        }*/
        if(newVersion == 1){
            db.execSQL(IProductScheme.PRODUCT_TABLE_CREATE);
        }
        if(newVersion == 2){
            //db.execSQL(" ALTER TABLE " + IProductScheme.PRODUCT_TABLE +" ADD COLUMN_PRODUCT_ISSYNE NUMBER)");
        }
    }
}
