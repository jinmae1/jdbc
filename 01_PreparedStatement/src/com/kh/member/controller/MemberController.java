package com.kh.member.controller;

import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

// 
// controller class
// 프로그램 실행 제어권을 가진 클래스
// 
public class MemberController {

	private MemberDao memberDao = new MemberDao();

	public int insertMember(Member member) {
		// 1. dao 메소드 호출
		int result = memberDao.insertMember(member);

		// 2. 리턴값을 다시 view단에 전달
		return result;
	}

	public int deleteMember(String id) {
		int result = memberDao.deleteMember(id);

		return result;
	}

}
