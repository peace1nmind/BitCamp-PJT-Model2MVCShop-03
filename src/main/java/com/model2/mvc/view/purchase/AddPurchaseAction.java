package com.model2.mvc.view.purchase;
// W D 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.Debug;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class AddPurchaseAction extends Action {

	// Field

	// Constructor
	public AddPurchaseAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Debug.startAction("AddPurchaseAction");
		
		UserService userService = new UserServiceImpl();
		ProductService productService = new ProductServiceImpl();
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		User user = userService.getUser(Debug.getParamStr(request, "buyerId"));
		Product product = productService.getProduct(Debug.getParamInt(request, "prodNo"));
		Purchase purchase = new Purchase(user, product);
		
		// tranNo = seq_transaction_tran_no.NEXTVAL
		// buyer = user
		// purchaseProd = product
		purchase.setPaymentOption(Debug.getParamStr(request, "paymentOption"));
		purchase.setReceiverName(Debug.getParamStr(request, "receiverName"));
		purchase.setReceiverPhone(Debug.getParamStr(request, "receiverPhone"));
		purchase.setDlvyAddr(Debug.getParamStr(request, "dlvyAddr"));
		purchase.setDlvyRequest(Debug.getParamStr(request, "dlvyRequest"));
		// tranCode = '2'
		// orderDate = SYSDATE
		purchase.setDlvyDate(Debug.getParamStr(request, "dlvyDate"));
		
		Debug.printDataT1("purchase", purchase);
		
		purchase = purchaseService.addPurchase(purchase);
		
		request.setAttribute("purchase", purchase);
		request.setAttribute("product", purchase.getPurchaseProd());
		request.setAttribute("buyer", purchase.getBuyer());
		
		Debug.endAction();
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
// class end
