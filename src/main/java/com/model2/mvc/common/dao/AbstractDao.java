package com.model2.mvc.common.dao;
// W D 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import com.model2.mvc.Debug;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.TranCodeMapper;

public abstract class AbstractDao {

	// Field

	// Constructor
	public AbstractDao() {
		Debug.setDaoName("AbstractDao");
		Debug.daoConstruct();
	}
	
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
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
	
	// �Խ��� currentPage Row ��  return 
	// sql���� �־ ����Ʈ�� �ٶ� �ش� page�� �ʿ��� ������ �� �� �ִ� sql���� �������
	public String makeCurrentPageSql(String sql , Search search){
		
		Debug.startDaoMethod("makeCurrentPageSql", "sql, search");
		Debug.printDataT2("sql", sql);
		Debug.printDataT2("search", search);
		
		sql = 	"SELECT * "+ 
				"FROM (	SELECT inner_table.* , ROWNUM AS row_seq " +
						"FROM (	"+sql+" ) inner_table "+
						"WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("\t\tUserDAO :: make SQL :: "+ sql);	
		
		Debug.endDaoMethod();
		
		return sql;
	}
	
	public boolean checkTranCode(String tranCode) {
		
		tranCode = tranCode.trim();
		Debug.startDaoMethod("checkTranCode", "tranCode");
		Debug.printDataT2("tranCode", tranCode);
		
		Map<String, String> tranCodeMap = TranCodeMapper.getInstance().getMap();
		
		boolean result = tranCodeMap.containsKey(tranCode);
		Debug.printDataT2("result", result);
		
		return result;
	}

}
// class end
