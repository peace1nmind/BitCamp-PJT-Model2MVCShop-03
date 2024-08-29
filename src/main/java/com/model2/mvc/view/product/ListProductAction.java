package com.model2.mvc.view.product;
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
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {

	// Field

	// Constructor
	public ListProductAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Debug.startAction("ListProductAction");
		
		// 상품 목록 / 상품 관리 구분 로직
		// menu: search , manage
		String menu = Debug.getParamStr(request, "menu");
		
		request.setAttribute("menu", menu);
		request.setAttribute("title", (menu!=null && menu.equals("search"))? "상품 목록조회" : "상품관리");
		request.setAttribute("navi", (menu!=null && menu.equals("search"))? "getProduct.do" : "updateProductView.do");
		
		
		// 검색조건을 다루는 로직
		Search search = new Search(getServletContext());
		search.setCurrentPage(Debug.getParamInt(request, "page"));
		search.setSearchCondition(Debug.getParamStr(request, "searchCondition"));
		search.setSearchKeyword(Debug.getParamStr(request, "searchKeyword"));
		
		request.setAttribute("search", search);
		
		
		// 검색한 리스트값들을 다루는 로직
		ProductService productService = new ProductServiceImpl();
		
		
		
		
		Map<String, Object> map = (menu.equals("search")) ? 
									productService.getProductList(search) : 
									productService.getProductList(search);
		request.setAttribute("map", map);
		request.setAttribute("list", map.get("list"));
		
		
		// 페이징을 다루는 로직
		Paging paging = new Paging(getServletContext());
		System.out.println(map.get("count"));
		paging.calculatePage((int) map.get("count"), search.getCurrentPage());
		request.setAttribute("paging", paging);
		
		int no = ((List<Product>) map.get("list")).size();
		request.setAttribute("no", no);
		
		Map<String, String> tranCodeMap = TranCodeMapper.getInstance().getMap();
		request.setAttribute("tranCodeMap", tranCodeMap);
		
		Debug.endAction();
		
		return "forward:/product/listProduct.jsp?menu="+menu;
	}

}
// class end
