package com.model2.mvc.service.product.impl;
// W D 

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

public class ProductServiceImpl implements ProductService {

	// Field

	// Constructor
	public ProductServiceImpl() {
	}

	@Override
	public Product addProduct(Product product) {

		return null;
	}

	@Override
	public Product getProduct(int prodNo) {

		return null;
	}

	@Override
	public Map<String, Object> getProductList(Search search) {

		return productDao.getProductList(search);
	}
	
	public Map<String, Object> getProductList(Search search, String tranCode, boolean over) {

		return productDao.getProductList(search, tranCode, over);
	}

	@Override
	public Product updateProduct(Product product) {

		return null;
	}

	@Override
	public void updateTranCode(int prodNo, String proTranCode) {
	}

	// Method
	//public  () {
	//	
	//}

}
// class end
