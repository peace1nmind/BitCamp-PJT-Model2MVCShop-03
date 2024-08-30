package com.model2.mvc.view.product;
// W D 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.Debug;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductAction extends Action {

	// Field

	// Constructor
	public UpdateProductAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Debug.startAction("UpdateProductActionAA");
		
		ProductService productService = new ProductServiceImpl();
		Product product = productService.getProduct(Debug.getParamInt(request, "prodNo"));
		
		product.setProdName(Debug.getParamStr(request, "prodName"));
		product.setProdDetail(Debug.getParamStr(request, "prodDetail"));
		product.setManuDate(Debug.getParamStr(request, "manuDate"));
		product.setPrice(Debug.getParamInt(request, "price"));
		product.setFileName(Debug.getParamStr(request, "fileName"));
		
		Debug.printDataT1("updated product", product);
		Product prod = productService.updateProduct(product);
		request.setAttribute("product", prod);
		
		Debug.endAction();
		
		return "forward:/product/updateProduct.jsp";
	}

}
// class end
