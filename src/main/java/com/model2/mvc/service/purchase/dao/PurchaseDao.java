package com.model2.mvc.service.purchase.dao;
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
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDao extends AbstractDao {

	// Field
	UserService userService = new UserServiceImpl();
	ProductService productService = new ProductServiceImpl();

	// Constructor
	public PurchaseDao() {
		Debug.daoConstruct("PurchaseDao");
	}

	// Method
	// 구매이력 한개 검색
	public Purchase findPurchase(int tranNo) {
		
		Debug.startDaoMethod("findPurchase", "tranNo");
		Debug.printDataT2("tranNo", tranNo);
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Purchase purchase = new Purchase();
		
		String sql = "SELECT * " + 
				"FROM transaction " + 
				"WHERE tran_no=? ";
		
		try {
			Debug.printSQL(sql);
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, tranNo);
			
			rs = stmt.executeQuery();
			
			rs.next();
			purchase.setTranNo(rs.getInt(1));
			purchase.setPurchaseProd(productService.getProduct(rs.getInt(2)));
			purchase.setBuyer(userService.getUser(rs.getString(3)));
			purchase.setPaymentOption(rs.getString(4));
			purchase.setReceiverName(rs.getString(5));
			purchase.setReceiverPhone(rs.getString(6));
			purchase.setDlvyAddr(rs.getString(7));
			purchase.setDlvyRequest(rs.getString(8));
			purchase.setTranCode(rs.getString(9));
			purchase.setOrderDate(rs.getDate(10));
			purchase.setDlvyDate(rs.getString(11));
			
			Debug.printDataT2("purchase", purchase);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
				
			} catch	(Exception e2) {
				e2.printStackTrace();
				
			}
		}
		
		Debug.endDaoMethod();
		
		return purchase;
	}
	
	
	// 구매이력 한개 검색
	public Purchase findPurchaseByProd(int prodNo) {
		
		Debug.startDaoMethod("findPurchase", "prodNo");
		Debug.printDataT2("prodNo", prodNo);
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Purchase purchase = new Purchase();
		
		String sql = "SELECT * " + 
				"FROM transaction " + 
				"WHERE prod_no=? ";
		
		try {
			Debug.printSQL(sql);
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, prodNo);
			
			rs = stmt.executeQuery();
			
			rs.next();
			purchase.setTranNo(rs.getInt(1));
			purchase.setPurchaseProd(productService.getProduct(rs.getInt(2)));
			purchase.setBuyer(userService.getUser(rs.getString(3)));
			purchase.setPaymentOption(rs.getString(4));
			purchase.setReceiverName(rs.getString(5));
			purchase.setReceiverPhone(rs.getString(6));
			purchase.setDlvyAddr(rs.getString(7));
			purchase.setDlvyRequest(rs.getString(8));
			purchase.setTranCode(rs.getString(9));
			purchase.setOrderDate(rs.getDate(10));
			purchase.setDlvyDate(rs.getString(11));
			
			Debug.printDataT2("purchase", purchase);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
				
			} catch	(Exception e2) {
				e2.printStackTrace();
				
			}
		}
		
		Debug.endDaoMethod();
		
		return purchase;
	}

	
	// 구매
	public int insertPurchase(Purchase purchase) {
		
		Debug.startDaoMethod("insertPurchase", "purchase");
		Debug.printDataT2("purchase", purchase);
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		PreparedStatement currStmt = null;
		int rs = -1;
		ResultSet rss = null;
		int tranNo = 0;
		
		String sql = "INSERT INTO transaction " + 
					 "VALUES (seq_transaction_tran_no.NEXTVAL, " + 
					 "?, ?, ?, ?, ?, ?, ?, '2', SYSDATE, ?) ";
		
		String tranNoSql = "SELECT seq_transaction_tran_no.CURRVAL "
						 + "FROM DUAL ";
		
		try {
			currStmt = con.prepareStatement(tranNoSql);
			Debug.printSQL(sql);
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
			stmt.setString(2, purchase.getBuyer().getUserId());
			stmt.setString(3, purchase.getPaymentOption());
			stmt.setString(4, purchase.getReceiverName());
			stmt.setString(5, purchase.getReceiverPhone());
			stmt.setString(6, purchase.getDlvyAddr());
			stmt.setString(7, purchase.getDlvyRequest());
			stmt.setString(8, purchase.getDlvyDate());
			
			rs = stmt.executeUpdate();
			
			Debug.printDataT2("rs", rs);
			
			if (rs > 0) {
				rss = currStmt.executeQuery();
				
				if (rss.next()) {
					tranNo = rss.getInt(1);
					Debug.printDataT2("tranNo", tranNo);
							
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
		
		return tranNo;
	}
	
	
	// 구매자의 구매이력 조회 (전체 ROW)
	public Map<String, Object> getPurchaseList(Search search, String buyerId) {
		
		Debug.startDaoMethod("getPurchaseList", "search");
		Debug.printDataT2("search", search);
		Debug.printDataT2("buyerId", buyerId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * " + 
				"FROM transaction " + 
				"WHERE buyer_id="+ buyerId + " " + 
				"ORDER BY order_date DESC ";
		
		try {
			int total = getTotalCount(sql);
			Debug.printDataT2("total", total);
			map.put("count", total);
			
			sql = makeCurrentPageSql(sql, search);
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			List<Purchase> list = new ArrayList<Purchase>();
			
			while (rs.next()) {
				Purchase purchase = new Purchase();
				purchase.setTranNo(rs.getInt(1));
				purchase.setPurchaseProd(productService.getProduct(rs.getInt(2)));
				purchase.setBuyer(userService.getUser(rs.getString(3)));
				purchase.setPaymentOption(rs.getNString(4));
				purchase.setReceiverName(rs.getString(5));
				purchase.setReceiverPhone(rs.getString(6));
				purchase.setDlvyAddr(rs.getString(7));
				purchase.setDlvyRequest(rs.getString(8));
				purchase.setTranCode(rs.getString(9));
				purchase.setOrderDate(rs.getDate(10));
				purchase.setDlvyDate(rs.getString(11));
				
				Debug.printDataT2("tranNo", purchase.getTranNo());
				Debug.printDataT2("prodNo", purchase.getPurchaseProd().getProdNo());
				Debug.printDataT2("tranCode", purchase.getTranCode());
				System.out.println();
				
				list.add(purchase);
			}
			
			map.put("list", list);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
				
			} catch	(Exception e2) {
				e2.printStackTrace();
				
			}
		}
		
		Debug.endDaoMethod();
		
		return map;

	}
	
	
	// 구매자의 구매이력 조회 (tranCode기준 이상, 이하)
	public Map<String, Object> getPurchaseList(Search search, String buyerId, String tranCode, boolean over) {
		
		Debug.startDaoMethod("getPurchaseList", "search");
		Debug.printDataT2("search", search);
		Debug.printDataT2("buyerId", buyerId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * " + 
				"FROM transaction " + 
				"WHERE buyer_id='"+ buyerId + "' " + 
				"AND tran_status_code" + ((over)? " >= " : " <= ") + tranCode + " " +
				"ORDER BY order_date DESC ";
		
		try {
			int total = getTotalCount(sql);
			Debug.printDataT2("total", total);
			map.put("count", total);
			
			sql = makeCurrentPageSql(sql, search);
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			List<Purchase> list = new ArrayList<Purchase>();
			
			while (rs.next()) {
				Purchase purchase = new Purchase();
				purchase.setTranNo(rs.getInt(1));
				purchase.setPurchaseProd(productService.getProduct(rs.getInt(2)));
				purchase.setBuyer(userService.getUser(rs.getString(3)));
				purchase.setPaymentOption(rs.getString(4));
				purchase.setReceiverName(rs.getString(5));
				purchase.setReceiverPhone(rs.getString(6));
				purchase.setDlvyAddr(rs.getString(7));
				purchase.setDlvyRequest(rs.getString(8));
				purchase.setTranCode(rs.getString(9));
				purchase.setOrderDate(rs.getDate(10));
				purchase.setDlvyDate(rs.getString(11));
				
				Debug.printDataT2("tranNo", purchase.getTranNo());
				Debug.printDataT2("prodNo", purchase.getPurchaseProd().getProdNo());
				Debug.printDataT2("tranCode", purchase.getTranCode());
				System.out.println();
				
				list.add(purchase);
			}
			
			map.put("list", list);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
				
			} catch	(Exception e2) {
				e2.printStackTrace();
				
			}
		}
		
		Debug.endDaoMethod();
		
		return map;

	}
	
	
	// 구매자의 구매이력 조회 (특정 tranCode)
	public Map<String, Object> getPurchaseList(Search search, String buyerId, String tranCode) {
		
		Debug.startDaoMethod("getPurchaseList", "search");
		Debug.printDataT2("search", search);
		Debug.printDataT2("buyerId", buyerId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * " + 
				"FROM transaction " + 
				"WHERE buyer_id='"+ buyerId + "' " + 
				"AND tran_status_code='" + tranCode + "' " +
				"ORDER BY order_date DESC ";
		
		try {
			int total = getTotalCount(sql);
			Debug.printDataT2("total", total);
			map.put("count", total);
			
			sql = makeCurrentPageSql(sql, search);
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			List<Purchase> list = new ArrayList<Purchase>();
			
			while (rs.next()) {
				Purchase purchase = new Purchase();
				purchase.setTranNo(rs.getInt(1));
				purchase.setPurchaseProd(productService.getProduct(rs.getInt(2)));
				purchase.setBuyer(userService.getUser(rs.getString(3)));
				purchase.setPaymentOption(rs.getString(4));
				purchase.setReceiverName(rs.getString(5));
				purchase.setReceiverPhone(rs.getString(6));
				purchase.setDlvyAddr(rs.getString(7));
				purchase.setDlvyRequest(rs.getString(8));
				purchase.setTranCode(rs.getString(9));
				purchase.setOrderDate(rs.getDate(10));
				purchase.setDlvyDate(rs.getString(11));
				
				Debug.printDataT2("tranNo", purchase.getTranNo());
				Debug.printDataT2("prodNo", purchase.getPurchaseProd().getProdNo());
				Debug.printDataT2("tranCode", purchase.getTranCode());
				System.out.println();
				
				list.add(purchase);
			}
			
			map.put("list", list);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
				
			} catch	(Exception e2) {
				e2.printStackTrace();
				
			}
		}
		
		Debug.endDaoMethod();
		
		return map;

	}
	
	
	// 구매완료된 상품리스트
	public Map<String, Object> getSaleList(Search search) {
		
		Debug.startDaoMethod("getPurchaseList", "search");
		Debug.printDataT2("search", search);

		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * " + 
				"FROM transaction " + 
				"WHERE tran_status_code = '2' " + 
				"ORDER BY order_date DESC ";
		
		try {
			int total = getTotalCount(sql);
			Debug.printDataT2("total", total);
			map.put("count", total);
			
			sql = makeCurrentPageSql(sql, search);
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			List<Purchase> list = new ArrayList<Purchase>();
			
			while (rs.next()) {
				Purchase purchase = new Purchase();
				purchase.setTranNo(rs.getInt(1));
				purchase.setPurchaseProd(productService.getProduct(rs.getInt(2)));
				purchase.setBuyer(userService.getUser(rs.getString(3)));
				purchase.setPaymentOption(rs.getString(4));
				purchase.setReceiverName(rs.getString(5));
				purchase.setReceiverPhone(rs.getString(6));
				purchase.setDlvyAddr(rs.getString(7));
				purchase.setDlvyRequest(rs.getString(8));
				purchase.setTranCode(rs.getString(9));
				purchase.setOrderDate(rs.getDate(10));
				purchase.setDlvyDate(rs.getString(11));
				
				Debug.printDataT2("tranNo", purchase.getTranNo());
				Debug.printDataT2("prodNo", purchase.getPurchaseProd().getProdNo());
				Debug.printDataT2("tranCode", purchase.getTranCode());
				System.out.println();
				
				list.add(purchase);
			}
			
			map.put("list", list);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
				
			} catch	(Exception e2) {
				e2.printStackTrace();
				
			}
		}
		
		Debug.endDaoMethod();
		
		return map;

	}
	
	
	// 구매정보 수정
	public void updatePurchase(Purchase purchase) {
		
		Debug.startDaoMethod("updatePurchase", "purchase");
		Debug.printDataT2("purchase", purchase);
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		int rs = -1;
		
		String sql = "UPDATE transaction " + 
					 "SET payment_option=?, " + 
					 	 "receiver_name=?, " + 
					 	 "receiver_phone=?, " + 
					 	 "dlvy_addr=?, " + 
					 	 "dlvy_request=?, " + 
					 	 "dlvy_date=? " + 
					 "WHERE tran_no=? ";
		
		try {
			Debug.printSQL(sql);
			stmt = con.prepareStatement(sql);
			stmt.setString(1, purchase.getPaymentOption());
			stmt.setString(2, purchase.getReceiverName());
			stmt.setString(3, purchase.getReceiverPhone());
			stmt.setString(4, purchase.getDlvyAddr());
			stmt.setString(5, purchase.getDlvyRequest());
			stmt.setDate(6, purchase.getOrderDate());
			stmt.setInt(7, purchase.getTranNo());
			
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
	
	
	public void updateTranStatusCode(Purchase purchase, String tranCode) {
		
		Debug.startDaoMethod("updateTranCode", "purchase");
		Debug.printDataT2("purchase", purchase);
		Debug.printDataT2("tranCode", tranCode);
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		int rs = -1;
		
		String sql = "UPDATE transaction " + 
					 "SET tran_status_code=? " + 
					 "WHERE tran_no=? ";
		
		try {
			Debug.printSQL(sql);
			stmt = con.prepareStatement(sql);
			stmt.setString(1, tranCode);
			stmt.setInt(2, purchase.getTranNo());
			
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
