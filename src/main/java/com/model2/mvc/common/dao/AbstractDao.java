package com.model2.mvc.common.dao;
// W D 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.model2.mvc.Debug;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;

public abstract class AbstractDao {

	// Field

	// Constructor
	public AbstractDao() {
		Debug.setDaoName("AbstractDao");
		Debug.daoConstruct();
	}
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	public int getTotalCount(String sql) {
		
		Debug.startDaoMethod("getTotalCount", "sql");
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int totalCount = 0;
		
		sql = "SELECT COUNT(*) "+
		      "FROM ( " +sql+ ") countTable";
		
		Debug.printSQL(sql);
		
		try {
			con = DBUtil.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			if(rs.next() ){
				totalCount = rs.getInt(1);
			}
			
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
		
		return totalCount;
	}
	
	// 게시판 currentPage Row 만  return 
	// sql문을 넣어서 리스트를 줄때 해당 page에 필요한 내용을 줄 수 있는 sql문을 만들어줌
	public String makeCurrentPageSql(String sql , Search search){
		
		Debug.startDaoMethod("makeCurrentPageSql", "sql, search");
		Debug.printDataInDao("sql", sql);
		Debug.printDataInDao("search", search);
		
		sql = 	"SELECT * "+ 
				"FROM (	SELECT inner_table.* , ROWNUM AS row_seq " +
						"FROM (	"+sql+" ) inner_table "+
						"WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("\t\tUserDAO :: make SQL :: "+ sql);	
		
		Debug.endDaoMethod();
		
		return sql;
	}

}
// class end
