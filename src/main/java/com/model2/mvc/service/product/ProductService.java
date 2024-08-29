package com.model2.mvc.service.product;
// W D 

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.dao.ProductDao;

public interface ProductService {

	// Field
	ProductDao productDao = new ProductDao();

	// Method
	// 상품등록
	public Product addProduct(Product product);
	
	// 상품상세조회
	public Product getProduct(int prodNo);
	
	// 판매하는 상품들 리스트
	public Map<String, Object> getProductList(Search search);
	
	// 판매상품들 tranCode기준 이상, 이하 상품 리스트
	public Map<String, Object> getProductList(Search search, String tranCode, boolean over);
	
	// 상품정보 수정
	public Product updateProduct(Product product);
	
	// TODO 판매와 상품 테이블 한번에 상태코드를 변경하는 방법 고안해야함
	// 상태코드 변경 (1:판매중, 2:구매완료, 3:배송중, 4:배송완료)
	public void updateTranCode(int prodNo, String proTranCode);
	
}
// class end
