package com.kh.member.controller;

import java.util.List;

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

	public Member selectOneMember(String id) {
		return memberDao.selectOneMember(id);
	}

	public int deleteMember(String id) {
		int result = memberDao.deleteMember(id);

		return result;
	}

	public List<Member> selectAllMember() {
		return memberDao.selectAllMember();
	}

	public int modifyMember(Member member, String field, String newValue) {
		return memberDao.modifyMember(member, field, newValue);
	}

}
