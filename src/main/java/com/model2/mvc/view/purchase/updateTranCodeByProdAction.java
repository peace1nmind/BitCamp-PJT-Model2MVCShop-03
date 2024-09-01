package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.Debug;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {

	public UpdateTranCodeByProdAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 배송하기
		Debug.startAction("UpdateTranCodeByProdAction");
		
		int prodNo = Debug.getParamInt(request, "prodNo");
		int currentPage = Debug.getPage(request, "page");
		int tranCode = Debug.getParamInt(request, "tranCode");
		
		ProductService productService = new ProductServiceImpl();
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		productService.updateTranCode(prodNo, (tranCode+1)+"");
		purchaseService.updateTranCodeByProd(prodNo, (tranCode+1)+"");
		
		Debug.endAction();
		
		return "redirect:/listProduct.do?menu=manage&salePage="+currentPage;
	}

}
