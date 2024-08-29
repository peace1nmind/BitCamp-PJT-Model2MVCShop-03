package com.model2.mvc.service.product.dao;
// W D 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.Debug;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.dao.AbstractDao;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;

public class ProductDao extends AbstractDao {

	// Field

	// Constructor
	public ProductDao() {
		Debug.daoConstruct("ProductDao");
	}

	// Method
	// 판매하는 상품 리스트를 얻는 메서드 (전체 ROW)
	public Map<String, Object> getProductList(Search search) {
		
		Debug.startDaoMethod("getProductList", "search");
		Debug.printDataInDao("search", search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * "
					+"FROM product ";
		
		String searchCondition = search.getSearchCondition();
		Debug.printDataInDao("searchCondition", searchCondition);
		String searchKeyword = search.getSearchKeyword();
		Debug.printDataInDao("searchKeyword", searchKeyword);
		String condition = "prod_no";
		
		if (!CommonUtil.null2str(searchKeyword).equals("")) {
			
			switch ((searchCondition==null)? "0" : searchCondition) {
			case "0":	// 상품번호로 검색
				sql += String.format("WHERE %s LIKE \'%%%s%%\' ", condition, searchKeyword);
				break;
				
			case "1":	// 상품명으로 검색
				condition = "prod_name";
				sql += String.format("WHERE %s LIKE \'%%%s%%\' ", condition, searchKeyword);
				break;
				
			case "2":	// 상품가격으로 검색
				condition = "price";
				sql += String.format("WHERE %s >= %s ", condition, searchKeyword);
				break;
				
			}
		}
		
		sql += "ORDER BY reg_date DESC NULLS LAST";
		
		int total = getTotalCount(sql);
		Debug.printDataInDao("total", total);
		map.put("count", total);
		
		try {
			sql = makeCurrentPageSql(sql, search);
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			List<Product> list = new ArrayList<Product>();
			
			while (rs.next()) {
				Product product = new Product();
				product.setProdNo(rs.getInt(1));
				product.setProdName(rs.getString(2));
				product.setProdDetail(rs.getString(3));
				product.setManuDate(rs.getString(4));
				product.setPrice(rs.getInt(5));
				product.setFileName(rs.getString(6));
				product.setRegDate(rs.getDate(7));
				product.setProTranCode(rs.getString(8));
				
				list.add(product);
			}
			
			map.put("list", list);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return map;
	}
	
	
	// 판매하는 상품 리스트를 얻는 메서드 (전체 ROW)
	// tranCode를 기준으로 이상, 이하
	public Map<String, Object> getProductList(Search search, String tranCode, boolean over) {
		
		Debug.startDaoMethod("getProductList", "search");
		Debug.printDataInDao("search", search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * "
					+"FROM product ";
		
		sql += "WHERE pro_tran_code "+((over)? ">=" : "<=")+"'"+tranCode+"'" ;
		
		String searchCondition = search.getSearchCondition();
		Debug.printDataInDao("searchCondition", searchCondition);
		String searchKeyword = search.getSearchKeyword();
		Debug.printDataInDao("searchKeyword", searchKeyword);
		String condition = "prod_no";
		
		if (!CommonUtil.null2str(searchKeyword).equals("")) {
			
			switch ((searchCondition==null)? "0" : searchCondition) {
			case "0":	// 상품번호로 검색
				sql += String.format("AND %s LIKE \'%%%s%%\' ", condition, searchKeyword);
				break;
				
			case "1":	// 상품명으로 검색
				condition = "prod_name";
				sql += String.format("AND %s LIKE \'%%%s%%\' ", condition, searchKeyword);
				break;
				
			case "2":	// 상품가격으로 검색
				condition = "price";
				sql += String.format("AND %s >= %s ", condition, searchKeyword);
				break;
				
			}
		}
		
		sql += "ORDER BY reg_date DESC NULLS LAST";
		
		int total = getTotalCount(sql);
		Debug.printDataInDao("total", total);
		map.put("count", total);
		
		try {
			sql = makeCurrentPageSql(sql, search);
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			List<Product> list = new ArrayList<Product>();
			
			while (rs.next()) {
				Product product = new Product();
				product.setProdNo(rs.getInt(1));
				product.setProdName(rs.getString(2));
				product.setProdDetail(rs.getString(3));
				product.setManuDate(rs.getString(4));
				product.setPrice(rs.getInt(5));
				product.setFileName(rs.getString(6));
				product.setRegDate(rs.getDate(7));
				product.setProTranCode(rs.getString(8));
				
				list.add(product);
			}
			
			map.put("list", list);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return map;
	}

}
// class end
