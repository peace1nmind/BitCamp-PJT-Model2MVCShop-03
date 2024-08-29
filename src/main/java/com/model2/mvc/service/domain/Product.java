package com.model2.mvc.service.domain;
// W D 

import java.sql.Date;

import com.model2.mvc.Debug;

public class Product {

	private int prodNo;
	private String prodName;
	private String prodDetail;
	private String manuDate;
	private int price;
	private String fileName;					// ��ǰ �̹��� �����̸�
	private Date regDate;
	private String proTranCode = "1";			// ��ǰ �����ڵ�
	//											  (1:�Ǹ���, 2:���ſϷ�, 3:�����, 4:��ۿϷ�)
	
	public Product(){
	}
	
	public String getProTranCode() {
		return proTranCode;
	}
	
	public void setProTranCode(String proTranCode) {
		this.proTranCode = proTranCode.trim();
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getManuDate() {
		return manuDate;
	}
	
	public void setManuDate(String manuDate) {
		manuDate = Debug.str2date(manuDate);
		Debug.printDataT1("@@manuDate", manuDate);
		this.manuDate = manuDate;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getProdDetail() {
		return prodDetail;
	}
	
	public void setProdDetail(String prodDetail) {
		this.prodDetail = prodDetail;
	}
	
	public String getProdName() {
		return prodName;
	}
	
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	
	public int getProdNo() {
		return prodNo;
	}
	
	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}
	
	public Date getRegDate() {
		return regDate;
	}
	
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	// Override
	public String toString() {
		
		return String.format("ProductVO : [prodNo] %d [prodName] %s [prdoDetail] %s "
				+ "\n\t [manuDate] %s [price] %d [fileName] %s  "
				+ "\n\t [regDate] %s [proTranCode] %s ", 
						prodNo, prodName, prodDetail, manuDate,price, fileName, regDate,proTranCode);
	}

}
// class end
