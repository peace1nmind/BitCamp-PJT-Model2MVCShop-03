package com.model2.mvc.view.product;
// W D 

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.Debug;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action {

	// Field

	// Constructor
	public GetProductAction() {
	}
	
	// �Ϲ�ȸ���� ��ǰ���� ��ȸ
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Debug.startAction("GetProductAction");
		
		// prodNo�� ���� �ش� ��ǰ�� ������ ã�� ����
		String prodNo = Debug.getParamStr(request, "prodNo");
		
		ProductService productService = new ProductServiceImpl();
		Product product = productService.getProduct(Integer.parseInt(prodNo));
		
		request.setAttribute("product", product);
		
		
		// TODO ������ �״�� ���� ������Ʈ �ʿ�
		// �ֱ� �� ��ǰ ����Ʈ�� ���õ� ����
		Cookie[] cookies = request.getCookies();
		Cookie history = new Cookie("history", null);
		
		if (cookies != null && cookies.length > 0) {
			
			for (Cookie cookie : cookies) {
				history = (cookie.getName().equals("history"))? cookie : history;
			}
			
		}
		
		String historyValue = history.getValue();
		String value = "";
		
		if (historyValue == null) {
			value = prodNo;
		}
		
		else {
			if (!historyValue.contains(prodNo)) {
				value = prodNo + "&" + historyValue;
				
			} else {
				value = historyValue.replace(prodNo, "");
				
				String[] splittedValue = value.split("&");
				value = "";
				
				for (int i = 0; i < splittedValue.length; i++) {
					
					if (!(splittedValue[i]==null || splittedValue[i].equals(""))) {
						value += splittedValue[i] + ((i < splittedValue.length -1)? "&" : "");
						
					}
				}
				
				value = prodNo + "&" + value;
				
			}
	
		}
		
		Debug.printDataT1("value", value);
		history.setValue(value);
		response.addCookie(history);
		
		Debug.endAction();
		
		return "forward:/product/getProduct.jsp";
	}

}
// class end
