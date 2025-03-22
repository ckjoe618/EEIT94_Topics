package com.topics.member.model.dao;

import com.topics.member.model.bean.MemberBean;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class MemberDao {
	
	private Session session;
	private final static String SELECT_MEMBER_BY_ID = "FROM MemberBean WHERE memberId=:id";
	private final static String SELECT_MEMBER_BY_ACCOUNT = "FROM MemberBean WHERE account=:acc";
	private final static String SELECT_MEMBER_BY_IDNO = "FROM MemberBean WHERE idno=:idno";
	private final static String SELECT_MEMBER = "FROM MemberBean WHERE isActive=true";
	
	public MemberDao(Session session) {
		this.session = session;
	}
	
	public Session getSession() {
		return session;
	}
	
	public void insertMember(MemberBean member) {
		Query<MemberBean> query = getSession().createQuery(SELECT_MEMBER_BY_ACCOUNT, MemberBean.class);
		query.setParameter("acc", member.getAccount());
		MemberBean uniqueResult = query.uniqueResult();
		if (uniqueResult == null) {
			this.getSession().persist(member);
		}
	}
	
	public void deleteMemberById(int id) {
		MemberBean member = getSession().get(MemberBean.class, id);
		member.setActive(false);
		getSession().merge(member);
	}
	
	public void updateMemberById(MemberBean member) {
		MemberBean updateMember = getSession().get(MemberBean.class, member.getMemberId());
		updateMember.setName(member.getName());
		updateMember.setGender(member.getGender());
		updateMember.setIdno(member.getIdno());
		updateMember.setEmail(member.getEmail());
		updateMember.setPhone(member.getPhone());
		updateMember.setBirthday(member.getBirthday());
		updateMember.setAccount(member.getAccount());
		updateMember.setPassword(member.getPassword());
		updateMember.setActive(member.isActive());
		getSession().merge(updateMember);
	}
	
	public MemberBean selectMemberById(int id) {	
		Query<MemberBean> query = getSession().createQuery(SELECT_MEMBER_BY_ID, MemberBean.class);
		query.setParameter("id", id);
		MemberBean member = query.uniqueResult();
		return member;
	}
	
	public MemberBean selectMemberByAccount(String account) {
		Query<MemberBean> query = getSession().createQuery(SELECT_MEMBER_BY_ACCOUNT, MemberBean.class);
		query.setParameter("acc", account);
		MemberBean member = query.uniqueResult();
		return member;
	}
	
	public MemberBean selectMemberByIdno(String idno) {
		Query<MemberBean> query = getSession().createQuery(SELECT_MEMBER_BY_IDNO, MemberBean.class);
		query.setParameter("idno", idno);
		MemberBean member = query.uniqueResult();
		return member;
	}
	
	public List<MemberBean> selectMember() {
		Query<MemberBean> query = getSession().createQuery(SELECT_MEMBER, MemberBean.class);
		List<MemberBean> list = query.list();
		return list;
	}
	
}
