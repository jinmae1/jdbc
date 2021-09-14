package com.kh.member.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import com.kh.member.model.vo.Member;

//
// Data Access Object Class
// Databases에 접근, 쿼리 실행 및 결과를 담당하는 클래스
//
// jdbc api 사용
// ojdbc8.jar 필수

// dml: Connection, PreparedStatement 사용, 결과 값이 int
// dql: Connection, PreparedStatement 사용, 결과 값이 resultSet

// ddl은 일반적으로 안 한다.
public class MemberDao {

	private String driverClass;
	private String url;
	private String user;
	private String password;

	public MemberDao() {
		HashMap<String, String> info = new Info().readInfo();
		driverClass = info.get("driverClass");
		url = info.get("url");
		user = info.get("user");
		password = info.get("password");
	}

	public int insertMember(Member member) {

		// 1. driver class 등록(프로그램 실행 시 1회)

		// 2. Connection 객체를 생성(url, user, password) & auto-commit여부 설정

		// 3. (쿼리를 실행하기 위한) PreparedStatement 객체 생성(미완성쿼리 & 값 대입)

		// 4. 쿼리실행(DML: executeUpdate) & 결과 값 처리

		// 5. 트랜잭션 처리(commit, rollback)

		// 6. 자원반납: 생성의 역순으로 반납

		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into member values(?, ?, ?, ?, ?, ?, default)";
		int result = 0;

		try {
			// 1.
			Class.forName(driverClass);

			// 2.
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false); // 기본값이 true이고, 명시적으로 commit하기 위함

			// 3.
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getGender());
			pstmt.setDate(4, member.getBirthday());
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getAddress());

			// 4.
			result = pstmt.executeUpdate();

			// 5.
			if (result > 0)
				conn.commit();
			else
				conn.rollback();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			// 6.
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return result;
	}

	public int deleteMember(Member member) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete from member where id = ? and name = ?";
		int result = 0;

		try {
			Class.forName(driverClass);

			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());

			result = pstmt.executeUpdate();

			if (result > 0)
				conn.commit();
			else
				conn.rollback();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
