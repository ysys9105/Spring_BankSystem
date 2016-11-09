package com.cjon.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBTemplate {
	
	private Connection con;
	
	public DBTemplate(){
		try {
			// 1. JDBC Driver Loading(MYSQL)
			Class.forName("com.mysql.jdbc.Driver");
			// 2. DB 접속
			String url ="jdbc:mysql://localhost:3306/library";
			String id = "jquery";
			String pw = "jquery";
			con = DriverManager.getConnection(url,id,pw);
			
			//Transaction 시작을 위해 autocommit : false로 설정
			con.setAutoCommit(false); //Transaction의 시작
			//con에 대해서 commit 되거나, rollback되거나 정상적으로, close될 때 transaction이 종료됨
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void commit() {
		try {
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void rollback() {
		try {
			con.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public Connection getCon() {
		return con;
	}
	public void setCon(Connection con) {
		this.con = con;
	}

	
}
