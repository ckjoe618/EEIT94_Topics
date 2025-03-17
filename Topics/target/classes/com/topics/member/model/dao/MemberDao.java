package com.topics.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.topics.member.model.bean.MemberBean;
//import com.topics.util.ConnectUtils;

public class MemberDao {
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter DATETIME_FORMATTER = new DateTimeFormatterBuilder()
		    .appendPattern("yyyy-MM-dd HH:mm:ss")
		    .optionalStart()
		    .appendFraction(ChronoField.MILLI_OF_SECOND, 1, 3, true)
		    .optionalEnd()
		    .toFormatter();
	private final static String INSERT_MEMBER = "INSERT INTO member(member_name, gender, idno, email, phone, birthday_date, account, password) "
										   + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String DELETE_MEMBER = "UPDATE member SET active_status=0 WHERE member_id=?";
	private final static String UPDATE_MEMBER = "UPDATE member SET member_name=?, gender=?, idno=?, email=?, phone=?, "
										   + "birthday_date=?, account=?, password=?, active_status=? WHERE member_id=?";
	private final static String SELECT_MEMBER_BY_ID = "SELECT * FROM member WHERE member_id=?";
	private final static String SELECT_MEMBER_BY_ACCOUNT = "SELECT * FROM member WHERE account=?";
	private final static String SELECT_MEMBER_BY_IDNO = "SELECT * FROM member WHERE idno=?";
	private final static String SELECT_MEMBER = "SELECT * FROM member WHERE active_status=1";
	
	private Session session;
	
	public MemberDao(Session session) {
		this.session = session;
	}
	
	public Session getSession() {
		return session;
	}
	
	public void insertMember(MemberBean member) {
		
		MemberBean memberBean = getSession().get(MemberBean.class, member.getMemberId());
		
		if(memberBean == null) {
			this.getSession().persist(memberBean);
		}
		
		
//		Connection connection = ConnectUtils.getConnection();
//		PreparedStatement stmt = null;
//		
//		try{
//			stmt = connection.prepareStatement(INSERT_MEMBER);
//			stmt.setString(1, member.getName());
//			stmt.setString(2, member.getGender());
//			stmt.setString(3, member.getIdno());
//			stmt.setString(4, member.getEmail());
//			stmt.setString(5, member.getPhone());
//			stmt.setString(7, member.getAccount());
//			stmt.setString(8, member.getPassword());
//			
//			String formattedDate = member.getBirthday().format(DATE_FORMATTER);
//			stmt.setString(6, formattedDate);
//			
//			stmt.execute();
//						
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			ConnectUtils.closeResource(connection, stmt);
//		}
	}
	
//	public void deleteMemberById(int id) {
//		Connection connection = ConnectUtils.getConnection();
//		PreparedStatement stmt = null;
//		
//		try{			
//			stmt = connection.prepareStatement(DELETE_MEMBER);
//			stmt.setInt(1, id);
//			stmt.execute();
//						
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			ConnectUtils.closeResource(connection, stmt);
//		}
//	}
//	
//	public void updateMemberById(MemberBean member) {
//		Connection connection = ConnectUtils.getConnection();
//		PreparedStatement stmt = null;
//		
//		try{
//			
//			stmt = connection.prepareStatement(UPDATE_MEMBER);
//			stmt.setInt(10, member.getMemberId());
//			stmt.setString(1, member.getName());
//			stmt.setString(2, member.getGender());
//			stmt.setString(3, member.getIdno());
//			stmt.setString(4, member.getEmail());
//			stmt.setString(5, member.getPhone());
//			stmt.setString(7, member.getAccount());
//			stmt.setString(8, member.getPassword());
//			stmt.setBoolean(9, member.isActive());
//			
//			String formattedDate = member.getBirthday().format(DATE_FORMATTER);
//			stmt.setString(6, formattedDate);
//			
//			stmt.execute();
//						
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			ConnectUtils.closeResource(connection, stmt);
//		}
//	}
//	
//	public MemberBean selectMemberById(int id) {	
//		Connection connection = ConnectUtils.getConnection();
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		MemberBean member = new MemberBean();
//		
//		try{
//			stmt = connection.prepareStatement(SELECT_MEMBER_BY_ID);
//			stmt.setInt(1, id);	
//			rs = stmt.executeQuery();
//			
//			if(rs.next()) {
//				member.setMemberId(rs.getInt("member_id"));
//				member.setName(rs.getString("member_name"));
//				member.setGender(rs.getString("gender"));
//				member.setIdno(rs.getString("idno"));
//				member.setEmail(rs.getString("email"));
//				member.setPhone(rs.getString("phone"));			
//				member.setAccount(rs.getString("account"));
//				member.setPassword(rs.getString("password"));
//				member.setActive(rs.getBoolean("active_status"));
//				
//				LocalDate date = LocalDate.parse(rs.getString("birthday_date"));
//				member.setBirthday(date);	
//				LocalDateTime dateTime = LocalDateTime.parse(rs.getString("create_account_date"), DATETIME_FORMATTER);
//				member.setCreateAccount(dateTime);
//			}
//						
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			ConnectUtils.closeResource(connection, stmt, rs);
//		}
//		return member;
//	}
//	
//	public MemberBean selectMemberByAccount(String account) {
//		Connection connection = ConnectUtils.getConnection();
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		MemberBean member = new MemberBean();
//		
//		try{
//			stmt = connection.prepareStatement(SELECT_MEMBER_BY_ACCOUNT);
//			stmt.setString(1, account);	
//			rs = stmt.executeQuery();
//			
//			if(rs.next()) {
//				member.setMemberId(rs.getInt("member_id"));
//				member.setName(rs.getString("member_name"));
//				member.setGender(rs.getString("gender"));
//				member.setIdno(rs.getString("idno"));
//				member.setEmail(rs.getString("email"));
//				member.setPhone(rs.getString("phone"));			
//				member.setAccount(rs.getString("account"));
//				member.setPassword(rs.getString("password"));
//				member.setActive(rs.getBoolean("active_status"));
//				
//				LocalDate date = LocalDate.parse(rs.getString("birthday_date"));
//				member.setBirthday(date);	
//				LocalDateTime dateTime = LocalDateTime.parse(rs.getString("create_account_date"), DATETIME_FORMATTER);
//				member.setCreateAccount(dateTime);
//			}
//						
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			ConnectUtils.closeResource(connection, stmt, rs);
//		}
//		return member;
//	}
//	
//	public MemberBean selectMemberByIdno(String idno) {
//		Connection connection = ConnectUtils.getConnection();
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		MemberBean member = new MemberBean();
//		
//		try{
//			stmt = connection.prepareStatement(SELECT_MEMBER_BY_IDNO);
//			stmt.setString(1, idno);	
//			rs = stmt.executeQuery();
//			
//			if(rs.next()) {
//				member.setMemberId(rs.getInt("member_id"));
//				member.setName(rs.getString("member_name"));
//				member.setGender(rs.getString("gender"));
//				member.setIdno(rs.getString("idno"));
//				member.setEmail(rs.getString("email"));
//				member.setPhone(rs.getString("phone"));			
//				member.setAccount(rs.getString("account"));
//				member.setPassword(rs.getString("password"));
//				member.setActive(rs.getBoolean("active_status"));
//				
//				LocalDate date = LocalDate.parse(rs.getString("birthday_date"));
//				member.setBirthday(date);	
//				LocalDateTime dateTime = LocalDateTime.parse(rs.getString("create_account_date"), DATETIME_FORMATTER);
//				member.setCreateAccount(dateTime);
//			}
//						
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			ConnectUtils.closeResource(connection, stmt, rs);
//		}
//		return member;
//	}
//	
	public List<MemberBean> selectMember() {
		
		Query query = getSession().createQuery("from MemberBean", MemberBean.class);
		List<MemberBean> list = query.list();
		return list;
		
		
//		Connection connection = ConnectUtils.getConnection();
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		List<MemberBean> list = new ArrayList<>();
//		
//		try{
//			stmt = connection.prepareStatement(SELECT_MEMBER);
//			rs = stmt.executeQuery();
//			
//			while(rs.next()) {
//				MemberBean member = new MemberBean();
//				member.setMemberId(rs.getInt("member_id"));
//				member.setName(rs.getString("member_name"));
//				member.setGender(rs.getString("gender"));
//				member.setIdno(rs.getString("idno"));
//				member.setEmail(rs.getString("email"));
//				member.setPhone(rs.getString("phone"));	
//				member.setAccount(rs.getString("account"));
//				member.setPassword(rs.getString("password"));
//				member.setActive(rs.getBoolean("active_status"));
//				
//				LocalDate date = LocalDate.parse(rs.getString("birthday_date"));
//				member.setBirthday(date);	
//				LocalDateTime dateTime = LocalDateTime.parse(rs.getString("create_account_date"), DATETIME_FORMATTER);
//				member.setCreateAccount(dateTime);
//				
//				list.add(member);
//			}
//						
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			ConnectUtils.closeResource(connection, stmt, rs);
//		}
//		return list;
	}
}
