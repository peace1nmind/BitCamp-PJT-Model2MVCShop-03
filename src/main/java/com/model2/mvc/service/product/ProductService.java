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
	// ��ǰ���
	public Product addProduct(Product product);
	
	// ��ǰ����ȸ
	public Product getProduct(int prodNo);
	
	// �Ǹ��ϴ� ��ǰ�� ����Ʈ
	public Map<String, Object> getProductList(Search search);
	
	// �ǸŻ�ǰ�� tranCode���� �̻�, ���� ��ǰ ����Ʈ
	public Map<String, Object> getProductList(Search search, String tranCode, boolean over);
	
	// ��ǰ���� ����
	public Product updateProduct(Product product);
	
	// TODO �Ǹſ� ��ǰ ���̺� �ѹ��� �����ڵ带 �����ϴ� ��� ����ؾ���
	// �����ڵ� ���� (1:�Ǹ���, 2:���ſϷ�, 3:�����, 4:��ۿϷ�)
	public void updateTranCode(int prodNo, String proTranCode);
	
}
// class end
