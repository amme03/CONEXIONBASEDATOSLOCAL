package com.cosmo.arquitecturamvpbase.views.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cosmo.arquitecturamvpbase.R;
import com.cosmo.arquitecturamvpbase.model.Product;
import com.cosmo.arquitecturamvpbase.presenter.BasePresenter;
import com.cosmo.arquitecturamvpbase.presenter.CreateProductPresenter;
import com.cosmo.arquitecturamvpbase.presenter.ProductPresenter;
import com.cosmo.arquitecturamvpbase.repository.ProductRepository;
import com.cosmo.arquitecturamvpbase.views.BaseActivity;

import static com.cosmo.arquitecturamvpbase.helper.Constants.PRODUCT_NOT_SYNCHRONIZED;

/**
 * Created by jersonsuaza on 9/19/17.
 */

public class CreateProductActivity  extends BaseActivity<CreateProductPresenter> implements ICreateProductView, TextWatcher {


    private EditText product_etName, product_etDescription, product_etPrice, product_etQuantity;
    private Button product_btnCreate;
    private ContentLoadingProgressBar progress;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        setPresenter(new CreateProductPresenter(new ProductRepository()));
        getPresenter().inject(this, getValidateInternet());
        progress = (ContentLoadingProgressBar) findViewById(R.id.progress);
        progress.hide();
        loadViews();
        loadEvents();
    }

    private void loadViews() {
        product_etName = (EditText) findViewById(R.id.product_etName);
        product_etName.addTextChangedListener(this);
        product_etDescription = (EditText) findViewById(R.id.product_etDescription);
        product_etDescription.addTextChangedListener(this);
        product_etPrice = (EditText) findViewById(R.id.product_etPrice);
        product_etPrice.addTextChangedListener(this);
        product_etQuantity = (EditText) findViewById(R.id.product_etQuantity);
        product_etQuantity.addTextChangedListener(this);
        product_btnCreate = (Button) findViewById(R.id.product_btnCreate);
    }

    private void loadEvents(){
        progress.show();
        product_btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Product product = new Product();
                product.setName(product_etName.getText().toString());
                product.setDescription(product_etDescription.getText().toString());
                product.setPrice(product_etPrice.getText().toString());
                product.setQuantity(product_etQuantity.getText().toString());*/
                getPresenter().createNewProduct(product_etName.getText().toString(), product_etDescription.getText().toString(),
                        product_etPrice.getText().toString(),product_etQuantity.getText().toString(), PRODUCT_NOT_SYNCHRONIZED);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!product_etName.getText().toString().trim().isEmpty() && !product_etDescription.getText().toString().trim().isEmpty() &&
                !product_etPrice.getText().toString().trim().isEmpty() && !product_etQuantity.getText().toString().trim().isEmpty()) {
            product_btnCreate.setBackgroundResource(R.color.colorPrimary);
            product_btnCreate.setEnabled(true);
        }else{
            product_btnCreate.setBackgroundResource(R.color.colorGray);
            product_btnCreate.setEnabled(false);
        }
    }

    @Override
    public void showResultCreateNewProduct(final boolean isCreated) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.hide();
                if(isCreated){
                    Toast.makeText(CreateProductActivity.this, getResources().getString(R.string.okResponseCreateProduct), Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(CreateProductActivity.this, getResources().getString(R.string.errResponseCreateProduct), Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void showAlertInternet(final int title, final int message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CreateProductActivity.this, R.string.validate_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
