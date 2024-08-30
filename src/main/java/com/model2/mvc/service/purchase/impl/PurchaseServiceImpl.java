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

		return purchaseDao.getPurchaseList(search, buyerId);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId, String tranCode, boolean over) {

		return purchaseDao.getPurchaseList(search, buyerId, tranCode, over);
	}

	@Override
	public Map<String, Object> getSaleList(Search search) {

		return null;
	}

	@Override
	public Map<String, Object> getDlvyList(Search search, String buyerId) {

		return purchaseDao.getPurchaseList(search, buyerId, "3");
	}

	@Override
	public Purchase updatePurchase(Purchase purchase) {
		
		purchaseDao.updatePurchase(purchase);
		
		return purchaseDao.findPurchase(purchase.getTranNo());
	}

	@Override
	public void updateTranCode(Purchase purchase) {
	}


}
// class end
