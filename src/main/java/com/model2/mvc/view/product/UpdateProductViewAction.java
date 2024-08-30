package com.model2.mvc.view.product;
// W D 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.Debug;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductViewAction extends Action {

	// Field

	// Constructor
	public UpdateProductViewAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Debug.startAction("UpdateProductViewAction");
		
		int prodNo = Debug.getParamInt(request, "prodNo");
		
		ProductService productService = new ProductServiceImpl();
		
		request.setAttribute("product", productService.getProduct(prodNo));
		
		Debug.endAction();
		
		return "forward:/product/updateProductView.jsp";
	}

}
// class end
