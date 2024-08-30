package com.model2.mvc.view.purchase;
// W D 

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.Debug;
import com.model2.mvc.common.Paging;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.TranCodeMapper;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListPurchaseAction extends Action {

	// Field

	// Constructor
	public ListPurchaseAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Debug.startAction("ListPurchaseAction");
		
		User buyer = (User) request.getSession().getAttribute("user");
		Search search = new Search(getServletContext());
		
		search.setCurrentPage(Debug.getPage(request, "page"));
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		Map<String, Object> map = purchaseService.getPurchaseList(search, buyer.getUserId(), "3", false);
		List<Purchase> list = (List<Purchase>) map.get("list");
		
		Paging paging = new Paging(getServletContext());
		paging.calculatePage((int) map.get("count"), search.getCurrentPage());
		
		Debug.printDataT1("list<Purchase>", list);
		
		request.setAttribute("map", map);
		request.setAttribute("list", list);
		request.setAttribute("paging", paging);
		request.setAttribute("tranCodeMap", TranCodeMapper.getInstance().getMap());
		
		Debug.endAction();
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
// class end
