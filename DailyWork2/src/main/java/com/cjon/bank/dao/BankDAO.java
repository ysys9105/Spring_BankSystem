package com.cjon.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.util.DBTemplate;

public class BankDAO {
	
	private DBTemplate template;
	
	public BankDAO() {
		// TODO Auto-generated constructor stub
	}
	public BankDAO(DBTemplate template) {
		this.template = template;
	}
	
	public DBTemplate getTemplate() {
		return template;
	}
	public void setTemplate(DBTemplate template) {
		this.template = template;
	}
	public BankDTO update(BankDTO dto){
		//데이터베이스 처리를 해야함!
		//일반 JDBC처리 코드가 나오면 된다
		Connection con = template.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 3. 사용할 SQL 작성하고 PreparedStatement 생성
			String sql ="update bank set balance = balance+? where userid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBalance());
			pstmt.setString(2, dto.getUserid());
			// 4. 실행
			int count = pstmt.executeUpdate();
			// 5. 결과처리
			if(count==1){
				//정상처리되면
				String sql1 = "select userid,balance from bank where userid=?";
				PreparedStatement pstmt1 = con.prepareStatement(sql1);
				pstmt1.setString(1, dto.getUserid());  //?는 in parameter라고 한다
				rs = pstmt1.executeQuery();
				if(rs.next()){
					dto.setBalance(rs.getInt("balance"));
				}
				dto.setResult(true);		//정상처리되었다는 표시
				try {
					rs.close();
					pstmt1.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				dto.setResult(false);		//비정상처리되었다는 표시
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;
	}

	public BankDTO updateWithdraw(BankDTO dto) {
		Connection con = template.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
						
			// 3. 사용할 SQL 작성하고 PreparedStatement 생성
			String sql ="update bank set balance = balance-? where userid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBalance());
			pstmt.setString(2, dto.getUserid());
			// 4. 실행
			int count = pstmt.executeUpdate();
			// 5. 결과처리
			if(count==1){
				//정상처리되면
				String sql1 = "select userid,balance from bank where userid=?";
				PreparedStatement pstmt1 = con.prepareStatement(sql1);
				pstmt1.setString(1, dto.getUserid());  //?는 in parameter라고 한다
				rs = pstmt1.executeQuery();
				if(rs.next()){
					dto.setBalance(rs.getInt("balance"));
				}
				if(dto.getBalance()<0){
					System.out.println("예금금액이 작아서 출금할 수 없다");
					dto.setResult(false);		//비정상처리되었다는 표시
				}
				else{
					dto.setResult(true);		//정상처리되었다는 표시
				}
				try {
					rs.close();
					pstmt1.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;
	}

}
