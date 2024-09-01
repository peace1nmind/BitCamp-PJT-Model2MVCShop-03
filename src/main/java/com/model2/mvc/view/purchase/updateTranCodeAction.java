package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.Debug;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeAction extends Action {

	public UpdateTranCodeAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 배송완료
		Debug.startAction("UpdateTranCodeAction");
		
		int tranNo = Debug.getParamInt(request, "tranNo");
		int tranCode = Debug.getParamInt(request, "tranCoode");
		int currentPage = Debug.getPage(request, "page");
		
		ProductService productService = new ProductServiceImpl();
		PurchaseService purchaseService = new PurchaseServiceImpl();

		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		productService.updateTranCode(purchase.getPurchaseProd().getProdNo(), (tranCode+1)+"");
		purchaseService.updateTranCode(purchase, (tranCode+1)+"");
		
		Debug.endAction();
		
		return "redirect:/listPurchase.do?page=";
	}

}
