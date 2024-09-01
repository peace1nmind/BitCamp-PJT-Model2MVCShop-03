package com.model2.mvc.view.product;
// W D 

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.Debug;
import com.model2.mvc.common.Paging;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.TranCodeMapper;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListProductAction extends Action {

	// Field

	// Constructor
	public ListProductAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Debug.startAction("ListProductAction");
		
		// ��ǰ ��� / ��ǰ ���� ���� ����
		// menu: search , manage
		String menu = Debug.getParamStr(request, "menu");
		
		request.setAttribute("menu", menu);
		request.setAttribute("title", (menu!=null && menu.equals("search"))? "��ǰ �����ȸ" : "��ǰ����");
		request.setAttribute("navi", (menu!=null && menu.equals("search"))? "getProduct.do" : "updateProductView.do");
		
		
		// �˻������� �ٷ�� ����
		Search search = new Search(getServletContext());
		search.setCurrentPage(Debug.getPage(request, "page"));
		search.setSearchCondition(Debug.getParamStr(request, "searchCondition"));
		search.setSearchKeyword(Debug.getParamStr(request, "searchKeyword"));
		
		request.setAttribute("search", search);
		
		
		/* �Ǹ����� ��ǰ�� */
		// �˻��� ����Ʈ������ �ٷ�� ����
		ProductService productService = new ProductServiceImpl();
		
		Map<String, Object> map = productService.getProductList(search, "1", false);
		request.setAttribute("map", map);
		request.setAttribute("list", map.get("list"));
		
		
		// ����¡�� �ٷ�� ����
		Paging paging = new Paging(getServletContext());
		paging.calculatePage((int) map.get("count"), search.getCurrentPage());
		request.setAttribute("paging", paging);
		
		Map<String, String> tranCodeMap = TranCodeMapper.getInstance().getMap();
		request.setAttribute("tranCodeMap", tranCodeMap);
		
		
		/* ���ſϷ� ��ǰ�� */
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		Search saleSearch = new Search(getServletContext());
		saleSearch.setCurrentPage(Debug.getPage(request, "salePage"));
		
		Map<String, Object> saleMap = purchaseService.getSaleList(saleSearch);
		request.setAttribute("saleMap", saleMap);
		
		Paging salePaging = new Paging(getServletContext());
		salePaging.calculatePage((int) saleMap.get("count"), saleSearch.getCurrentPage());
		
		request.setAttribute("saleMap", saleMap);
		request.setAttribute("salePaging", salePaging);
		
		Debug.endAction();
		
		return "forward:/product/listProduct.jsp?menu="+menu;
	}

}
// class end
