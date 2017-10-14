package com.cosmo.arquitecturamvpbase.presenter;

import android.util.Log;

import com.cosmo.arquitecturamvpbase.R;
import com.cosmo.arquitecturamvpbase.helper.Database;
import com.cosmo.arquitecturamvpbase.model.Product;
import com.cosmo.arquitecturamvpbase.receivers.NetworkStateReceiver;
import com.cosmo.arquitecturamvpbase.repository.IProductRepository;
import com.cosmo.arquitecturamvpbase.repository.ProductRepository;
import com.cosmo.arquitecturamvpbase.views.activities.ICreateProductView;

import java.util.UUID;

import retrofit.RetrofitError;

/**
 * Created by jersonsuaza on 9/19/17.
 */

public class CreateProductPresenter extends BasePresenter<ICreateProductView> {

    private IProductRepository productRepository;

    public CreateProductPresenter(IProductRepository productRepository){
        this.productRepository = productRepository;
    }

    /*public void createNewProduct(Product product) {
        if (getValidateInternet().isConnected()){
            createThreadCreateProduct(product);
        }else{
            getView().showAlertInternet(R.string.error, R.string.validate_internet);
        }
    }*/

    public void createNewProduct(String name, String description, String price, String quantity, int isSync) {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString()); //WARNING
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setIsSync(isSync);

       if ( NetworkStateReceiver.isConnected()){
            createThreadCreateProduct(product);
       }else{
           createThreadCreateProductNotConnected(product);
        }
    }

    public void createThreadCreateProduct(final Product product) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
            createNewProductService(product);

            }
        });
        thread.start();
    }

    public void createThreadCreateProductNotConnected(final Product product) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
              createNewProductLocal(product);
            }
        });
        thread.start();
    }

    private void createNewProductLocal(Product product) {

        try{
            boolean isSaved = Database.productDao.createProduct(product);
            getView().showResultCreateNewProduct(isSaved);
        }catch (Exception ex){
            Log.e("LOCAL DATABASE", ex.getMessage());
            getView().showResultCreateNewProduct(false);
        }
    }

    private void createNewProductService(Product product){
        try{
            productRepository.createProduct(product);
            getView().showResultCreateNewProduct(true);
        }catch (RetrofitError retrofitError){
            getView().showResultCreateNewProduct(false);
        }
    }

}
