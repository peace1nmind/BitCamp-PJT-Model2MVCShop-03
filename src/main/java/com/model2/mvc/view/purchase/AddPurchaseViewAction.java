package com.model2.mvc.view.purchase;
// W D 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.Debug;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddPurchaseViewAction extends Action {

	// Field

	// Constructor
	public AddPurchaseViewAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Debug.startAction("AddPurchaseViewAction");
		
		ProductService productService = new ProductServiceImpl();
		Product product = productService.getProduct(Debug.getParamInt(request, "prodNo"));
		Debug.printDataT1("product", product);
		
		request.setAttribute("product", product);
		
		Debug.endAction();
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
}
// class end
