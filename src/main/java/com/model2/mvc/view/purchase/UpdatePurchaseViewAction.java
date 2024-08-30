package com.model2.mvc.view.purchase;
// W D 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.Debug;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseViewAction extends Action {

	// Field

	// Constructor
	public UpdatePurchaseViewAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Debug.startAction("UpdatePurchaseViewAction");
		
		int tranNo = Debug.getParamInt(request, "tranNo");
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		request.setAttribute("purchase", purchaseService.getPurchase(tranNo));
		
		Debug.endAction();
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}

}
// class end
