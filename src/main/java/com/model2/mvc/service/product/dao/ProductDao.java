package com.model2.mvc.service.product.dao;
// W D 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		Debug.printDataT2("search", search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * "
					+"FROM product ";
		
		String searchCondition = search.getSearchCondition();
		Debug.printDataT2("searchCondition", searchCondition);
		String searchKeyword = search.getSearchKeyword();
		Debug.printDataT2("searchKeyword", searchKeyword);
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
		
		sql += "ORDER BY reg_date DESC NULLS LAST, prod_no ";
		
		int total = getTotalCount(sql);
		Debug.printDataT2("total", total);
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
			
		} finally {
			
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
				
			} catch (Exception e2) {
				e2.printStackTrace();
				
			}
		}
		
		Debug.endDaoMethod();
		
		return map;
	}
	
	
	// 판매하는 상품 리스트를 얻는 메서드 (전체 ROW)
	// tranCode를 기준으로 이상, 이하
	public Map<String, Object> getProductList(Search search, String tranCode, boolean over) {
		
		Debug.startDaoMethod("getProductList", "search");
		Debug.printDataT2("search", search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * "
					+"FROM product ";
		
		sql += "WHERE pro_tran_code "+((over)? ">=" : "<=")+"'"+tranCode+"'" ;
		
		String searchCondition = search.getSearchCondition();
		Debug.printDataT2("searchCondition", searchCondition);
		String searchKeyword = search.getSearchKeyword();
		Debug.printDataT2("searchKeyword", searchKeyword);
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
		
		sql += "ORDER BY reg_date DESC NULLS LAST, prod_no ";
		
		int total = getTotalCount(sql);
		Debug.printDataT2("total", total);
		map.put("count", total);
		
		try {
			sql = makeCurrentPageSql(sql, search);
			Debug.printSQL(sql);
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
			
		} finally {
			
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
				
			} catch (Exception e2) {
				e2.printStackTrace();
				
			}
		}
		
		Debug.endDaoMethod();
		
		return map;
	}
	
	
	// 상품번호로 상품정보를 조회하는 DBMS
	public Product findProduct(int prodNo) {
		
		Debug.startDaoMethod("findProduct", "prodNo");
		Debug.printDataT2("prodNo", prodNo);
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Product product = new Product();
		
		String sql = "SELECT * " + 
					 "FROM product " + 
					 "WHERE prod_no = ? ";
		
		try {
			Debug.printSQL(sql);
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, prodNo);
			
			rs = stmt.executeQuery();
			
			rs.next();
			product.setProdNo(rs.getInt(1));
			product.setProdName(rs.getString(2));
			product.setProdDetail(rs.getString(3));
			product.setManuDate(rs.getString(4));
			product.setPrice(rs.getInt(5));
			product.setFileName(rs.getString(6));
			product.setRegDate(rs.getDate(7));
			product.setProTranCode(rs.getString("pro_tran_code"));
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
				
			} catch (Exception e2) {
				e2.printStackTrace();
				
			}
			
		}
		
		Debug.endDaoMethod();
		
		return product;
	}
	
	
	// 제일 최근에 추가된 상품내용 조회
	public Product findProduct() {
		
		Debug.startDaoMethod("findProduct", null);
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = makeLastRowSQL("product", "prod_no");
		
		Product product = new Product();
		
		try {
			Debug.printSQL(sql);
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			
			rs.next();
			product.setProdNo(rs.getInt(1));
			product.setProdName(rs.getString(2));
			product.setProdDetail(rs.getString(3));
			product.setManuDate(rs.getString(4));
			product.setPrice(rs.getInt(5));
			product.setFileName(rs.getString(6));
			product.setRegDate(rs.getDate(7));
			product.setProTranCode(rs.getString(8));
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
				
			} catch (Exception e2) {
				e2.printStackTrace();
				
			}
			
		}
		
		Debug.printDataT2("product", product);
		Debug.endDaoMethod();
		
		return product;
	}
	
	
	// 상품등록
	public int insertProduct(Product product) {
		
		Debug.startDaoMethod("insertProduct", "product");
		Debug.printDataT2("product", product);
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		PreparedStatement currStmt = null;
		int rs = -1;
		ResultSet rss = null;
		int prodNo = 0;
		
		String sql = "INSERT INTO product " + 
					 "VALUES (seq_product_prod_no.NEXTVAL, " + 
					 "?, ?, ?, ?, ?, SYSDATE, ?) ";
		
		String prodNoSql = "SELECT seq_product_prod_no.CURRVAL "
						 + "FROM DUAL";
		
		try {
			currStmt = con.prepareStatement(prodNoSql);
			Debug.printSQL(sql);
			stmt = con.prepareStatement(sql);
			stmt.setString(1, product.getProdName());
			stmt.setString(2, product.getProdDetail());
			stmt.setString(3, product.getManuDate());
			stmt.setInt(4, product.getPrice());
			stmt.setString(5, product.getFileName());
			stmt.setString(6, product.getProTranCode());
			
			rs = stmt.executeUpdate();
			
			Debug.printDataT2("rs", rs);
			
			if (rs > 0) {
				rss = currStmt.executeQuery();
				
				if (rss.next()) {
					prodNo = rss.getInt(1);
					Debug.printDataT2("prodNo", prodNo);
					
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if (rss != null) rss.close();
				if (stmt != null) stmt.close();
				if (currStmt != null) currStmt.close();
				if (con != null) con.close();
				
			} catch	(Exception e2) {
				e2.printStackTrace();
				
			}
		}
		
		Debug.endDaoMethod();
		
		return prodNo;
	}
	
	
	// 상품정보 수정
	// 상품명, 상품상세정보, 제조일자, 가격, 상품이미지
	public void updateProduct(Product product) {
		
		Debug.startDaoMethod("updateProduct", "product");
		Debug.printDataT2("product", product);
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		int rs = -1;
		
		String sql = "UPDATE product "
					+"SET prod_name=?, "
					+ 	 "prod_detail=?, "
					+ 	 "manufacture_day=?, "
					+ 	 "price=?, "
					+ 	 "image_file=? "
					+"WHERE prod_no=? ";
		
		try {
			Debug.printSQL(sql);
			stmt = con.prepareStatement(sql);
			stmt.setString(1, product.getProdName());
			stmt.setString(2, product.getProdDetail());
			stmt.setString(3, product.getManuDate());
			stmt.setInt(4, product.getPrice());
			stmt.setString(5, product.getFileName());
			stmt.setInt(6, product.getProdNo());
			
			rs = stmt.executeUpdate();
			
			Debug.printDataT2("rs", rs);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if (stmt != null) stmt.close();
				if (con != null) con.close();
				
			} catch	(Exception e2) {
				e2.printStackTrace();
				
			}
		}
		
		Debug.endDaoMethod();
		
	}
	
	
	

}
// class end
