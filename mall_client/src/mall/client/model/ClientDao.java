package mall.client.model;

import java.sql.*;

import mall.client.commons.DBUtil;
import mall.client.vo.Client;

public class ClientDao {
	private DBUtil dbUtil;
	
	// 회원 비밀번호 업데이트 메소드
	public int updateClientPw(Client client) {
		// 변수 및 객체 초기화
		this.dbUtil = new DBUtil();
		int rowCnt = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.dbUtil.getConnection();
			String sql = "UPDATE client SET client_pw = PASSWORD(?) WHERE client_mail=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, client.getClientPw());
			stmt.setString(2, client.getClientMail());
			System.out.println(stmt+"<-- ClientDao updateClientPw의 stmt");
			stmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(conn, stmt, null);;
		}
		return rowCnt;
	}
	
	// clientOne (회원정보 메소드)
	public Client selectClientOne(String clientMail) {
		
		// 객체 초기화
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Client client = null;
		
		try {
			conn = this.dbUtil.getConnection();
			String sql = "SELECT client_mail clientMail, client_date clientDate FROM client WHERE client_mail = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, clientMail);
			System.out.println(stmt+"<-- ClientDao selectClientOne의 stmt");
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				client = new Client();
				client.setClientMail(rs.getString("clientMail"));
				client.setClientDate(rs.getString("clientDate"));
			}
		} catch (Exception e) { // 예외 처리
			e.printStackTrace();
		} finally { // dbUtil 사용 후 닫아줌
			this.dbUtil.close(conn, stmt, rs);
		}
		
		return client;
	}
	
	
	// 회원가입 이메일 중복 검사
	public String selectClientMail(String clientMail) {
		
		// 객체 초기화
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String returnClientMail = null;
		
		try {
			conn = this.dbUtil.getConnection();
			String sql = "SELECT client_mail FROM client WHERE client_mail = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, clientMail);
			System.out.println(stmt+"<-- ClientDao selectClientMail의 stmt");
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				returnClientMail = rs.getString("client_mail");
			}
		} catch (Exception e) { // 예외 처리
			e.printStackTrace();
		} finally {
			this.dbUtil.close(conn, stmt, rs);
		}
		
		return returnClientMail;
	}
	
	// 회원가입 (회원 정보 입력)
	public int insertClient(Client client) {
		
		// 변수 및 객체 초기화
		int rowCnt = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.dbUtil.getConnection();
			String sql = "INSERT INTO client(client_mail, client_pw, client_date) VALUES (?,PASSWORD(?),NOW())";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, client.getClientMail());
			stmt.setString(2, client.getClientPw());
			System.out.println(stmt+"<-- ClientDao insertClient의 stmt");
			stmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(conn, stmt, null);
		}
		return rowCnt;
	}
	
	// 로그인 정보 가져오는 메소드
	public Client login(Client client) {
		
		// 객체 초기화
		this.dbUtil = new DBUtil();
		Client returnClient = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = this.dbUtil.getConnection();
			String sql = "SELECT client_mail clientMail FROM client WHERE client_mail=? AND client_pw=PASSWORD(?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, client.getClientMail());
			stmt.setString(2, client.getClientPw());
			System.out.println(stmt+"<-- ClientDao login의 stmt");
			rs = stmt.executeQuery();
			if(rs.next()) {
				returnClient = new Client();
				returnClient.setClientMail(rs.getString("clientMail"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(conn, stmt, rs);
		}
		return returnClient;
	}
}
