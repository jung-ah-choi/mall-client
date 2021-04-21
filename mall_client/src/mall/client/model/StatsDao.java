package mall.client.model;

import mall.client.commons.DBUtil;
import mall.client.vo.Stats;

import java.sql.*;

public class StatsDao {
	private DBUtil dbUtil;
	
	// 접속한 세션의 오늘 카운트가 있는지 정보를 가져옴 (오늘 접속자 수)
	public Stats selectStatsByToday() {
		// 변수 초기화
		Stats stats = null;
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// DB 처리
		try {
			conn = this.dbUtil.getConnection();
			String sql = "SELECT stats_day statsDay, stats_count statsCount FROM stats WHERE stats_day = DATE(NOW())";
			stmt = conn.prepareStatement(sql);
			System.out.println(stmt+"<-- StatsDao.selectStatsByToday의 stmt");
			rs = stmt.executeQuery();
			if(rs.next()) {
				stats = new Stats();
				stats.setStatsDay(rs.getString("statsDay"));
				stats.setStatsCount(rs.getLong("statsCount"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			this.dbUtil.close(conn, stmt, rs);
		}
		
		return stats;
	}
	
	// 오늘 카운트가 없다면 1 값을 입력함
	public void insertStats() {
		// 변수 초기화
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		
		// DB 처리
		try {
			conn = this.dbUtil.getConnection();
			String sql = "INSERT INTO stats(stats_day, stats_count) VALUES (DATE(NOW()),1)";
			stmt = conn.prepareStatement(sql);
			System.out.println(stmt + "<--StatsDao.insertStats의 stmt");
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			this.dbUtil.close(conn, stmt, null);
		}
	}
	
	// 오늘 카운트가 있다면 +1 값을 업데이트하는 메소드
	public void updateStats() {
		// 변수 초기화
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		
		// DB 처리
		try {
			conn = this.dbUtil.getConnection();
			String sql = "UPDATE stats SET stats_count = stats_count+1 WHERE stats_day = DATE(NOW())";
			stmt = conn.prepareStatement(sql);
			System.out.println(stmt + "<--StatsDao.updateStats의 stmt");
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			this.dbUtil.close(conn, stmt, null);
		}
	}
	
	// 전체 세션 카운트를 출력 (전체 접속자 수)
	public long selectStatsTotal() {
		// 변수 초기화
		long total = 0;
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// DB 처리
		try {
			conn = this.dbUtil.getConnection();
			String sql = "SELECT COUNT(stats_count) total FROM stats";
			stmt = conn.prepareStatement(sql);
			System.out.println(stmt + "<--StatsDao.selectStatsTotal의 stmt");
			rs = stmt.executeQuery();
			if(rs.next()) {
				total = rs.getLong("total");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			this.dbUtil.close(conn, stmt, rs);
		}
		return total;
	}
}
