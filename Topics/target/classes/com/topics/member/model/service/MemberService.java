package com.topics.member.model.service;

import java.util.List;

import org.hibernate.Session;

import com.topics.member.model.bean.MemberBean;
import com.topics.member.model.dao.MemberDao;

public class MemberService {

	private MemberDao memberDao;
	
	public MemberService(Session session) {
		memberDao = new MemberDao(session);
	}
	
	public void insertMember(MemberBean member) {
		memberDao.insertMember(member);
	}
	
//	public void deleteMemberById(int id) {
//		memberDao.deleteMemberById(id);
//	}
//	
//	public void updateMemberById(MemberBean member) {
//		memberDao.updateMemberById(member);
//	}
//	
//	public MemberBean selectMemberById(int id) {
//		return memberDao.selectMemberById(id);
//	}
//	
//	public MemberBean selectMemberByAccount(String account) {
//		return memberDao.selectMemberByAccount(account);
//	}
//	
//	public MemberBean selectMemberByIdno(String idno) {
//		return memberDao.selectMemberByAccount(idno);
//	}
//	
	public List<MemberBean> selectMember() {
		return memberDao.selectMember();
	}
}
