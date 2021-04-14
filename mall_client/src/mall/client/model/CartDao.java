package mall.client.model;

import mall.client.commons.DBUtil;
import mall.client.vo.*;

import java.util.*;
import java.sql.*;

public class CartDao {
	private DBUtil dbUtil;
	
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
			String sql = "SELECT c.cart_no, e.ebook_no, e.ebook_title, c.cart_date FROM cart c INNER JOIN ebook e ON c.ebook_no = e.ebook_no WHERE c.client_mail = ?";
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
