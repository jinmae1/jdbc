package com.kh.member.view;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import com.kh.member.controller.MemberController;
import com.kh.member.model.vo.Member;

public class MemberMenu {

	private Scanner sc = new Scanner(System.in);
	private MemberController memberController = new MemberController();

	public void mainMenu() {
		String menu = "***** 회원 관리 프로그램 *****\n" + "1. 전체 조회\n" + "2. 아이디 조회\n" + "3. 이름 검색\n" + "4. 회원 가입\n"
				+ "5. 회원 정보 변경\n" + "6. 회원 탈퇴\n" + "0. 프로그램 종료\n" + "*****************************\n" + "선택: ";

		while (true) {
			System.out.print(menu);
			String choice = sc.next();

			Member member = null;
			int result = 0;

			switch (choice) {
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				case "4":
					member = inputMember();
					System.out.println("member@menu = " + member);
					result = memberController.insertMember(member); // 모든 dml요청은 정수값이 반환됨
					// result는 실행된 행의 개수
					System.out.println(result > 0 ? "회원 가입 성공!" : "회원 가입 실패!");
					break;
				case "5":
					break;
				case "6":
					member = deleteMember();
					System.out.println("member@menu = " + member);
					result = memberController.deleteMember(member);
					System.out.println(result > 0 ? "회원 삭제 성공!" : "회원 삭제 실패!");
					break;
				case "0":
					return;

				default:
					System.out.println("잘못 입력하셨습니다.");
					break;
			}

		}
	}

	// 사용자 입력 정보를 Member객체로 반환
	// return
	private Member inputMember() {
		System.out.println("새 회원정보를 입력하세요");

		System.out.print("아이디: ");
		String id = sc.next();

		System.out.print("이름: ");
		String name = sc.next();

		System.out.print("성별(M/F): ");
		String gender = String.valueOf(sc.next().toUpperCase().charAt(0));

		// 사용자 입력값 java.util.Calendar -> java.sql.Date
		System.out.print("생년월일(예: 19900909): ");
		String temp = sc.next();
		int year = Integer.valueOf(temp.substring(0, 4));
		int month = Integer.valueOf(temp.substring(4, 6)) - 1; // 0 ~ 11월
		int day = Integer.valueOf(temp.substring(6, 8));
		Calendar cal = new GregorianCalendar(year, month, day);
		Date birthday = new Date(cal.getTimeInMillis());

		System.out.print("이메일: ");
		String email = sc.next();

		sc.nextLine();
		System.out.print("주소: ");
		String address = sc.nextLine();

		return new Member(id, name, gender, birthday, email, address, null);
	}

	private Member deleteMember() {
		System.out.println("삭제할 회원정보를 입력하세요");

		System.out.print("아이디: ");
		String id = sc.next();

		System.out.print("이름: ");
		String name = sc.next();

		return new Member(id, name, null, null, null, null, null);
	}
}
