package com.cosmo.arquitecturamvpbase.dao;

import com.cosmo.arquitecturamvpbase.model.Product;

import java.util.ArrayList;

/**
 * Created by jersonsuaza on 9/30/17.
 */

public interface IProductDao {

    public ArrayList<Product> fetchAllProducts();
    public Boolean createProduct(Product product);
    public int updateProduct(Product product);

    }
