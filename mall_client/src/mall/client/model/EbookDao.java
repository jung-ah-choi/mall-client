package mall.client.model;

import java.util.*;
import java.sql.*;

import mall.client.commons.DBUtil;
import mall.client.vo.Ebook;

public class EbookDao {
	private DBUtil dbutil;
	public List<Ebook> selectEbookListByPage(int beginRow, int rowPerPage) {
		List<Ebook> list = new ArrayList<>();
		this.dbutil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = this.dbutil.getConnection();
			String sql = "SELECT ebook_title ebookTitle, ebook_price ebookPrice FROM ebook ORDER BY ebook_date DESC LIMIT ?, ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Ebook ebook = new Ebook();
				ebook.setEbookTitle(rs.getString("ebookTitle"));
				ebook.setEbookPrice(rs.getInt("ebookPrice"));
				list.add(ebook);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			this.dbutil.close(conn, stmt, rs);
		}
		return list;
	}
}
