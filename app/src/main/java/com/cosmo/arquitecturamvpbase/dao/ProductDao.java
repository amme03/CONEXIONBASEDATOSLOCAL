package com.cosmo.arquitecturamvpbase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cosmo.arquitecturamvpbase.model.Product;
import com.cosmo.arquitecturamvpbase.providers.DbContentProvider;
import com.cosmo.arquitecturamvpbase.schemes.IProductScheme;

import java.util.ArrayList;

/**
 * Created by jersonsuaza on 9/30/17.
 */

public class ProductDao extends DbContentProvider implements IProductScheme, IProductDao {



    private Cursor cursor;
    private ContentValues initialValues;

    public ProductDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public ArrayList<Product> fetchAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        cursor = super.query(PRODUCT_TABLE, PRODUCT_COLUMNS, null, null, COLUMN_PRODUCT_NAME);
        if(cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Product product = cursorToEntity(cursor);
                productList.add(product);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return productList;
    }

    @Override
    public Boolean createProduct(Product product) {
        setContentValueProduct(product);
        try{
            return super.insert(PRODUCT_TABLE, getContentValue()) >= 0;
            /*
            if(totalInserted == -1){
                return false;
            }
            return true;
            */
        }catch (SQLiteConstraintException ex){
            Log.e("DbErrorCreateProduct", ex.getMessage());
            return false;
        }
    }

    @Override
    public int updateProduct(Product product) {
        setContentValueProduct(product);
        try{

            String[] where=new String[]{product.getName()};
            String select=COLUMN_PRODUCT_NAME+"=?";
            int i=super.update(PRODUCT_TABLE,getContentValue(),select, where);

            return i;
        }catch (SQLiteConstraintException ex){
            Log.e("DbErrorUpdateProduct", ex.getMessage());

           return 0;
        }

    }
    private void setContentValueProduct(Product product) {
        initialValues = new ContentValues();
        initialValues.put(COLUMN_ID, product.getId());
        initialValues.put(COLUMN_PRODUCT_NAME, product.getName());
        initialValues.put(COLUMN_PRODUT_DESCRIPTION, product.getDescription());
        initialValues.put(COLUMN_PRODUCT_QUANTITY, product.getQuantity());
        initialValues.put(COLUMN_PRODUCT_PRICE, product.getPrice());
        initialValues.put(COLUMN_PRODUCT_ISSYNE, product.getIsSync());

    }



    private ContentValues getContentValue() {
        return initialValues;
    }

    @Override
    protected Product cursorToEntity(Cursor cursor) {
        Product product = new Product();
        int idIndex;
        int nameIndex;
        int descriptionIndex;
        int quantityIndex;
        int priceIndex;

        if(cursor.getColumnIndex(COLUMN_ID) != -1){
            idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
            product.setId(cursor.getString(idIndex));
        }
        if(cursor.getColumnIndex(COLUMN_PRODUCT_NAME) != -1){
            nameIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME);
            product.setName(cursor.getString(nameIndex));
        }
        if(cursor.getColumnIndex(COLUMN_PRODUT_DESCRIPTION) != -1){
            descriptionIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUT_DESCRIPTION);
            product.setDescription(cursor.getString(descriptionIndex));
        }
        if(cursor.getColumnIndex(COLUMN_PRODUCT_QUANTITY) != -1){
            quantityIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_QUANTITY);
            product.setQuantity(cursor.getString(quantityIndex));
        }
        if(cursor.getColumnIndex(COLUMN_PRODUCT_PRICE) != -1){
            priceIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_PRICE);
            product.setPrice(cursor.getString(priceIndex));
        }

        if(cursor.getColumnIndex(COLUMN_PRODUCT_ISSYNE) != -1){
            priceIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ISSYNE);
            product.setIsSync(cursor.getInt(priceIndex));
        }
        return product;
    }
}
