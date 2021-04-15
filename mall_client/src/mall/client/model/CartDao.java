package mall.client.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mall.client.commons.DBUtil;
import mall.client.vo.Cart;

public class CartDao {
	private DBUtil dbUtil;
	
	//
	public boolean selectClientMail(Cart cart) {
		// false가 리턴될 경우는 중복되는 데이터가 있는 경우
		boolean flag = true;
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// DB 처리
		try {
			conn = this.dbUtil.getConnection();
			String sql = "SELECT * FROM cart WHERE client_mail = ? AND ebook_no = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, cart.getClientMail());
			stmt.setInt(2, cart.getEbookNo());
			System.out.println(stmt+"<-- CartDao.selectClientMail의 stmt"); // 디버깅
			rs = stmt.executeQuery();
		} catch(Exception e) { // 예외 처리
			e.printStackTrace();
		} finally {
			this.dbUtil.close(conn, stmt, rs);
		}
		return flag;
	}
	
	// 장바구니 추가 메소드
	public int insertCart(Cart cart) {
		int rowCnt = 0;
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.dbUtil.getConnection();
			String sql = "INSERT INTO cart(client_mail, ebook_no, cart_date) VALUES(?, ?, NOW())";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, cart.getClientMail());
			stmt.setInt(2, cart.getEbookNo());
			System.out.println(stmt+"<-- CartDao insertCart의 stmt");
			rowCnt = stmt.executeUpdate();
		} catch(Exception e) { // 예외 처리
			e.printStackTrace();
		} finally {
			this.dbUtil.close(conn, stmt, null);
		}
		return rowCnt;
	}
	
	
	// 로그인 한 계정의 장바구니 목록 가져오기
	public List<Map<String, Object>> selectCartList(String clientMail) {
		// 배열 선언 및 변수 초기화
		List<Map<String, Object>> list = new ArrayList<>();
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// DB 처리
		try {
			conn = this.dbUtil.getConnection();
			String sql = "SELECT c.cart_no cartNo, e.ebook_no ebookNo, e.ebook_title ebookTitle, c.cart_date cartDate FROM cart c INNER JOIN ebook e ON c.ebook_no = e.ebook_no WHERE c.client_mail = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, clientMail);
			System.out.println(stmt+"<-- CartDao.selectCartList의 stmt"); // 디버깅
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("cartNo", rs.getInt("cartNo"));
				map.put("ebookNo", rs.getInt("ebookNo"));
				map.put("ebookTitle", rs.getString("ebookTitle"));
				map.put("cartDate", rs.getString("cartDate"));
				list.add(map);
			}
			
		} catch(Exception e) { // 예외 처리
			e.printStackTrace();
		} finally {
			this.dbUtil.close(conn, stmt, rs);
		}
		return list; // list로 return 처리
	}
}
