package com.model2.mvc.view.product;
// W D 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.Debug;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddProductAction extends Action {

	// Field

	// Constructor
	public AddProductAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Debug.startAction("AddProductAction");
		
		Product product = new Product();
		// prodNo = seq_product_prod_no.NEXTVAL
		product.setProdName(Debug.getParamStr(request, "prodName"));
		product.setProdDetail(Debug.getParamStr(request, "prodDetail"));
		product.setManuDate(Debug.getParamStr(request, "manuDate"));
		product.setPrice(Debug.getParamInt(request, "price"));
		product.setFileName(Debug.getParamStr(request, "fileName"));
		// regDate = SYSDATE
		product.setProTranCode("1");
		
		Debug.printDataT1("product", product);
		
		ProductService productService = new ProductServiceImpl();
		
		request.setAttribute("product", productService.addProduct(product));
		
		Debug.endAction();
		
		return "forward:/product/addProduct.jsp";
	}

}
// class end
