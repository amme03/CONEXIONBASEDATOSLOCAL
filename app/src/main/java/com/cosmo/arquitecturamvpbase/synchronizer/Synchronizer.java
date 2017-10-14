package com.cosmo.arquitecturamvpbase.synchronizer;

import android.util.Log;

import com.cosmo.arquitecturamvpbase.helper.Database;
import com.cosmo.arquitecturamvpbase.model.Product;
import com.cosmo.arquitecturamvpbase.repository.IProductRepository;
import com.cosmo.arquitecturamvpbase.repository.ProductRepository;

import java.util.ArrayList;

import static com.cosmo.arquitecturamvpbase.helper.Constants.PRODUCT_NOT_SYNCHRONIZED;
import static com.cosmo.arquitecturamvpbase.helper.Constants.PRODUCT_SYNCHRONIZED;


/**
 * Created by ana.marrugo on 12/10/2017.
 */

public class Synchronizer {


    private static final String TAG_CLASS ="com.cosmo.arquitecturamvpbase.synchronizer.Synchronizer" ;
    public  static  Synchronizer instance;

    public static  Synchronizer getIntance(){
        if(instance==null){
            instance =new Synchronizer();
        }
        return instance;
    }

    public void executeSynlocalToServer(Boolean isConnected) {

        ArrayList<Product> productArrayList= Database.productDao.fetchAllProducts();

        if (productArrayList .size()>0 && isConnected ){
            IProductRepository productRepositor= new ProductRepository();
            for (Product product:productArrayList){
                if(product.getIsSync()==PRODUCT_NOT_SYNCHRONIZED) {
                    productRepositor.createProduct(product);
                    product.setIsSync(PRODUCT_SYNCHRONIZED);
                    Database.productDao.updateProduct(product);

                }
             }
        }
    }
}
