package com.model2.mvc.service.purchase.impl;
// W D 

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDao;
import com.model2.mvc.service.purchase.PurchaseService;

public class PurchaseServiceImpl implements PurchaseService {

	// Field

	// Constructor
	public PurchaseServiceImpl() {
	}

	@Override
	public Purchase addPurchase(Purchase purchase) {
		
		int tranNo = purchaseDao.insertPurchase(purchase);
		new ProductDao().updateProTranCode(purchase.getPurchaseProd().getProdNo(), "2");
		
		return purchaseDao.findPurchase(tranNo);
	}

	@Override
	public Purchase getPurchase(int tranNo) {

		return purchaseDao.findPurchase(tranNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) {

		return null;
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId, String tranCode, boolean over) {

		return null;
	}

	@Override
	public Map<String, Object> getSaleList(Search search) {

		return null;
	}

	@Override
	public Map<String, Object> getDlvyList(Search search) {

		return null;
	}

	@Override
	public Purchase updatePurchase(Purchase purchase) {

		return null;
	}

	@Override
	public void updateTranCode(Purchase purchase) {
	}

	// Method
	//public  () {
	//	
	//}

}
// class end
