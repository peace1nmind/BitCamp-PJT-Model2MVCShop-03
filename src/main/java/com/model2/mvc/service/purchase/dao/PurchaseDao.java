package com.model2.mvc.service.purchase.dao;
// W D 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model2.mvc.Debug;
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

}
// class end
