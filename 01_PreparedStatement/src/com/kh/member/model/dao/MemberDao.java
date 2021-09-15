package com.kh.member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

	public List<Member> selectAllMember() {

		// 1. driverClass 등록: 클래스 인스턴스 생성(reflection api)
		// 2. Connection객체 생성
		// 3. PreparedStatement객체 생성(쿼리 전달 & 값 대입)
		// 4. 쿼리 실행 (ResultSet객체 반환)
		// 5. Result객체 -> List<Member> 변환
		// 0. 트랜잭션 처리는 없다.(단순 조회만 하는 것이므로)
		// 6. 자원반납 (생성의 역순 ResultSet -> PreparedStatement -> Connection)

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member order by reg_date";
		List<Member> list = new ArrayList<>(); // 결과집합이 0행이어도 null이 아닌 빈 list객체가 반한되게 하기 위함

		try {
			// 1.
			Class.forName(driverClass);

			// 2.
			conn = DriverManager.getConnection(url, user, password);

			// 3.
			pstmt = conn.prepareStatement(sql);

			// 4.
			// 결과집합이 0행이어도 rset이 null이 아니다.
			rset = pstmt.executeQuery();

			// 5.
			// rset에 한 행씩 접근해서 Member객체 변환 -> list추가
			while (rset.next()) {
				// 한 행(record) -> Member객체
				String id = rset.getString("id"); // 현재 행의 id컬럼(문자형)
				String name = rset.getString("name");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String address = rset.getString("address");
				Timestamp reg_date = rset.getTimestamp("reg_date");

				Member member = new Member(id, name, gender, birthday, email, address, reg_date);
				list.add(member);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			// 6.
			try {
				rset.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// System.out.println("list@dao = " + list);
		return list;
	}

	public Member selectOneMember(String id) {
		String sql = "select * from member where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member member = null; // 조회된 결과가 없으면 null을 리턴

		// 1. driverClass 등록
		// 2. Connection객체 생성
		// 3. PreparedStatement객체 생성
		// 4. 쿼리 실행
		// 5. ResultSet -> Member변환
		// 6. 자원 반납

		try {
			// 1.
			Class.forName(driverClass);

			// 2.
			conn = DriverManager.getConnection(url, user, password);

			// 3.
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			// 4.
			rset = pstmt.executeQuery();

			// 5.
			if (rset.next()) {
				member = new Member();
				member.setId(rset.getString(1)); // 컬럼 인덱스로 접근 가능
				member.setName(rset.getString(2));
				member.setGender(rset.getString(3));
				member.setBirthday(rset.getDate(4));
				member.setEmail(rset.getString(5));
				member.setAddress(rset.getString(6));
				member.setRegDate(rset.getTimestamp(7));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6.
			try {
				rset.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return member;
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

	public int deleteMember(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete from member where id = ?";
		int result = 0;

		try {
			Class.forName(driverClass);

			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

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
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
