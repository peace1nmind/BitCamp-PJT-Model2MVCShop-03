package com.model2.mvc.service.purchase;
// W D 

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDao;
import com.model2.mvc.service.purchase.dao.PurchaseDao;

public interface PurchaseService {
	
	// Field
	PurchaseDao purchaseDao = new PurchaseDao();
	ProductDao productDao = new ProductDao();

	// Method
	public Purchase addPurchase(Purchase purchase);
	
	public Purchase getPurchase(int tranNo);
	
	public Map<String, Object> getPurchaseList(Search search, String buyerId);
	
	public Map<String, Object> getPurchaseList(Search search, String buyerId, String tranCode, boolean over);
	
	public Map<String, Object> getSaleList(Search search);
	
	public Map<String, Object> getDlvyList(Search search, String buyerId);
	
	public Purchase updatePurchase(Purchase purchase);
	
	public void updateTranCode(Purchase purchase, String tranCode);
	
	public void updateTranCodeByProd(int prodNo, String tranCode);

}
// class end
