package mall.client.model;

import java.util.*;
import java.sql.*;

import mall.client.commons.DBUtil;
import mall.client.vo.Ebook;

public class EbookDao {
	private DBUtil dbUtil;
	
	// 캘린더 신간 표시 메소드
	public List<Map<String, Object>> selectEbookListByMonth(int year, int month) {
		// 변수 초기화
		this.dbUtil = new DBUtil();
		List<Map<String, Object>> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// DB 구현
		try {
			conn = this.dbUtil.getConnection();
			String sql = "SELECT ebook_no ebookNo, ebook_title ebookTitle, DAY(ebook_date) d FROM ebook WHERE YEAR(ebook_date) = ? AND MONTH(ebook_date) = ? ORDER BY DAY(ebook_date) ASC";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, year);
			stmt.setInt(2, month);
			System.out.println(stmt+"<-- EbookDao.selectEbookListByMonth의 stmt");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("ebookNo", rs.getInt("ebookNo"));
				map.put("ebookTitle", rs.getString("ebookTitle"));
				map.put("d", rs.getInt("d"));
				list.add(map);
			}
		} catch (Exception e) {
			// 예외 발생 시 시스템을 멈추고, 함수(메소드)호출스택구조를 그대로 콘솔에 출력함
			e.printStackTrace();
		} finally {
			this.dbUtil.close(conn, stmt, rs);
		}
		return list;
	}
	
	// ebook 목록 + 검색 기능
	public List<Ebook> selectEbookListByPageAndSearchWord(int beginRow, int rowPerPage, String searchWord) {
		// 변수 초기화
		List<Ebook> list = new ArrayList<>();
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// DB 구현
		try {
			conn = this.dbUtil.getConnection();
			if(searchWord == null) {
				String sql = "SELECT ebook_no ebookNo, ebook_title ebookTitle, ebook_price ebookPrice FROM ebook ORDER BY ebook_date DESC LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, beginRow);
				stmt.setInt(2, rowPerPage);
			} else {
				String sql = "SELECT ebook_no ebookNo, ebook_title ebookTitle, ebook_price ebookPrice FROM ebook WHERE ebook_title LIKE ? ORDER BY ebook_date DESC LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+searchWord+"%");
				stmt.setInt(2, beginRow);
				stmt.setInt(3, rowPerPage);
			}
			System.out.println(stmt+"<-- EbookDao selectEbookListByPageAndSearchWord의 stmt");
			rs = stmt.executeQuery();
			while(rs.next()) {
				Ebook ebook = new Ebook();
				ebook.setEbookNo(rs.getInt("ebookNo"));
				ebook.setEbookTitle(rs.getString("ebookTitle"));
				ebook.setEbookPrice(rs.getInt("ebookPrice"));
				list.add(ebook);
			}
		} catch(Exception e) {
			// 예외 발생 시 시스템을 멈추고, 함수(메소드)호출스택구조를 그대로 콘솔에 출력함
			e.printStackTrace();
		} finally {
			this.dbUtil.close(conn, stmt, rs);
		}
		return list;
	}
	
	// ebook 목록 메소드 + 카테고리 목록
	public List<Ebook> selectEbookListByPageAndCategoryName(int beginRow, int rowPerPage, String categoryName) {
		// 변수 초기화
		List<Ebook> list = new ArrayList<>();
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// DB 구현
		try {
			conn = this.dbUtil.getConnection();
			// 카테고리 값이 없다면
			if(categoryName == null) {
				String sql = "SELECT ebook_no ebookNo, ebook_title ebookTitle, ebook_price ebookPrice FROM ebook ORDER BY ebook_date DESC LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, beginRow);
				stmt.setInt(2, rowPerPage);
			// 카테고리 값이 있다면
			} else {
				String sql = "SELECT ebook_no ebookNo, ebook_title ebookTitle, ebook_price ebookPrice FROM ebook WHERE category_name = ? ORDER BY ebook_date DESC LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, categoryName);
				stmt.setInt(2, beginRow);
				stmt.setInt(3, rowPerPage);
			}
			System.out.println(stmt+"<-- EbookDao selectEbookListByPageAndCategoryName의 stmt");
			rs = stmt.executeQuery();
			while(rs.next()) {
				Ebook ebook = new Ebook();
				ebook.setEbookNo(rs.getInt("ebookNo"));
				ebook.setEbookTitle(rs.getString("ebookTitle"));
				ebook.setEbookPrice(rs.getInt("ebookPrice"));
				list.add(ebook);
			}
		} catch(Exception e) {
			// 예외 발생 시 시스템을 멈추고, 함수(메소드)호출스택구조를 그대로 콘솔에 출력함
			e.printStackTrace();
		} finally {
			this.dbUtil.close(conn, stmt, rs);
		}
		return list;
	}
	
	// ebook 카테고리 메소드
	public List<String> categoryList() {
		// 변수 초기화
		this.dbUtil = new DBUtil();
		List<String> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// DB 구현
		try {
			conn = this.dbUtil.getConnection();
			String sql = "SELECT category_name categoryName FROM category ORDER BY category_weight DESC";
			stmt = conn.prepareStatement(sql);
			System.out.println(stmt+"<-- EbookDao.categoryList의 stmt");
			rs = stmt.executeQuery();
			while (rs.next()) {
				String cn = rs.getString("categoryName");
				list.add(cn);
			}
		} catch (Exception e) {
			// 예외 발생 시 시스템을 멈추고, 함수(메소드)호출스택구조를 그대로 콘솔에 출력함
			e.printStackTrace();
		} finally {
			this.dbUtil.close(conn, stmt, rs);
		}
		return list;
	}
	
	
	// 전체 행의 수 세는 메소드 + 카테고리 + 검색어
	public int totalCount(String categoryName, String searchWord) {
		// 변수 초기화
		this.dbUtil = new DBUtil();
		int totalRow = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// DB 구현
		try {
			conn = this.dbUtil.getConnection();
			// categoryName과 검색어 없는 경우 (searchWord)
			if(categoryName == null && searchWord == null) {
				String sql = "SELECT COUNT(*) cnt FROM ebook";
				stmt = conn.prepareStatement(sql);
			
			// categoryName이 있을 때
			} else if(categoryName != null) {
				String sql = "SELECT COUNT(*) cnt FROM ebook WHERE category_name = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, categoryName);
			
			// 검색어가 있을 때 (searchWord)
			} else if(searchWord != null) {
				String sql = "SELECT COUNT(*) cnt FROM ebook WHERE ebook_title LIKE ? ";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+searchWord+"%");
			}
			System.out.println(stmt+"<-- EbookDao.totalCount의 stmt");
			rs = stmt.executeQuery();
			while(rs.next()) { // ebook 데이터의 총 개수
				totalRow = rs.getInt("cnt");
			}
		} catch(Exception e) {
			// 예외 발생 시 시스템을 멈추고, 함수(메소드)호출스택구조를 그대로 콘솔에 출력함
			e.printStackTrace();
		} finally {
			this.dbUtil.close(conn, stmt, rs);
		}
		return totalRow;
	}
	
	// ebookOne 메소드
	public Ebook selectEbookOne(int ebookNo) {
		// 변수 초기화
		Ebook ebook = null;
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// DB 구현
		try {
			conn = this.dbUtil.getConnection();
			String sql = "SELECT ebook_no ebookNo, ebook_isbn ebookISBN, category_name categoryName, ebook_title ebookTitle, ebook_author ebookAuthor, ebook_company ebookCompany, ebook_page_count ebookPageCount, ebook_price ebookPrice, ebook_img ebookImg, ebook_summary ebookSummary, ebook_date ebookDate,ebook_state ebookState FROM ebook WHERE ebook_no = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ebookNo);
			System.out.println(stmt+"<-- EbookDao selectEbookOne의 stmt");
			rs = stmt.executeQuery();
			if(rs.next()) {
				ebook = new Ebook();
				ebook.setEbookNo(rs.getInt("ebookNo"));
				ebook.setCategoryName(rs.getString("categoryName"));
				ebook.setEbookISBN(rs.getString("ebookISBN"));
				ebook.setEbookTitle(rs.getString("ebookTitle"));
				ebook.setEbookAuthor(rs.getString("ebookAuthor"));
				ebook.setEbookCompany(rs.getString("ebookCompany"));
				ebook.setEbookPageCount(rs.getInt("ebookPageCount"));
				ebook.setEbookPrice(rs.getInt("ebookPrice"));
				ebook.setEbookSummary(rs.getString("ebookSummary"));
				ebook.setEbookImg(rs.getString("ebookImg"));
				ebook.setEbookDate(rs.getString("ebookDate"));
				ebook.setEbookState(rs.getString("ebookState"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(conn, stmt, rs);
		}
		return ebook;
	}
}
