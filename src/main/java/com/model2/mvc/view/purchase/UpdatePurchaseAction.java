package com.model2.mvc.view.purchase;
// W D 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.Debug;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseAction extends Action {

	// Field

	// Constructor
	public UpdatePurchaseAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Debug.startAction("UpdatePurchaseAction");
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		Purchase purchase = purchaseService.getPurchase(Debug.getParamInt(request, "tranNo"));
		
		purchase.setPaymentOption(Debug.getParamStr(request, "paymentOption"));
		purchase.setReceiverName(Debug.getParamStr(request, "receiverName"));
		purchase.setReceiverPhone(Debug.getParamStr(request, "receiverPhone"));
		purchase.setDlvyAddr(Debug.getParamStr(request, "dlvyAddr"));
		purchase.setDlvyRequest(Debug.getParamStr(request, "dlvyRequest"));
		purchase.setDlvyDate(Debug.getParamStr(request, "dlvyDate"));
		
		purchase = purchaseService.updatePurchase(purchase);
		Debug.printDataT1("updated purchase", purchase);
		
		request.setAttribute("purchase", purchase);
		
		Debug.endAction();
		
		return "forward:/purchase/updatePurchase.jsp";
	}
	
}
// class end
