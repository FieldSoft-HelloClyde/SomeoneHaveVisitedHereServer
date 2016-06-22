package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.ConnectionPool;
import vo.ObjectMessage;
import vo.UserAccount;

public class Account {
	static public ObjectMessage AccountValidate(String UserEmail,String Password){
		Connection conn = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement PreStatement;
			PreStatement = conn.prepareStatement("select * from user_info where email=? and password_md5=?;");
			PreStatement.setString(1, UserEmail);
			PreStatement.setString(2, Security.toMd5(Password));
			ResultSet rSet = PreStatement.executeQuery();
			if (rSet.next()){
				int t_UserId = rSet.getInt("user_id");
				int t_UserType = rSet.getInt("user_type");
				String t_Email = rSet.getString("email");
				String t_password_md5 = rSet.getString("password_md5");
				return new ObjectMessage(new UserAccount(t_UserId,t_UserType, t_Email, t_password_md5), true, "账号密码验证成功");
			}
			else{
				return new ObjectMessage(null, false, "账号密码错误");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ObjectMessage(null, false, "出现异常");
		} finally{
			ConnectionPool.getInstance().release(conn);
		}
	}
	
	static public String GetUserName(int UserId){
		Connection conn = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement PreStatement;
			PreStatement = conn.prepareStatement("select EMAIL from user_info where USER_ID=?;");
			PreStatement.setInt(1, UserId);
			ResultSet rSet = PreStatement.executeQuery();
			if (rSet.next()){
				return rSet.getString(1);
			}
			else{
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally{
			ConnectionPool.getInstance().release(conn);
		}
	}
	
	static public ObjectMessage EmailIsExisted(String UserEmail){
		Connection conn = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement PreStatement;
			PreStatement = conn.prepareStatement("select * from user_info where email=?;");
			PreStatement.setString(1, UserEmail);
			ResultSet rSet = PreStatement.executeQuery();
			if (rSet.next()){
				return new ObjectMessage(UserEmail, true, "邮箱已经存在");
			}
			else{
				return new ObjectMessage(null, false, "邮箱不存在");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ObjectMessage(null, false, "出现异常");
		} finally{
			ConnectionPool.getInstance().release(conn);
		}
	}
	
	static public ObjectMessage Register(String UserEmail,String UserPassword){
		//判断邮箱格式是否正确
		if (UserEmail.matches("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")){
			//判断邮箱是否存在
			ObjectMessage tempObjMessage = EmailIsExisted(UserEmail);
			if (tempObjMessage.isResult()){
				return new ObjectMessage(null, false, "邮箱已经存在");
			}
			else{
				if (UserPassword.trim().length() < 6){
					return new ObjectMessage(null, false, "密码过短");
				}
				else{
					Connection conn = ConnectionPool.getInstance().getConnection();
					try {
						PreparedStatement PreStatement;
						PreStatement = conn.prepareStatement("INSERT INTO user_info VALUES (DEFAULT,1,?,?,'');");
						PreStatement.setString(1, UserEmail);
						PreStatement.setString(2, Security.toMd5(UserPassword));
						PreStatement.executeUpdate();
						return new ObjectMessage(null, true, "注册成功");
					} catch (SQLException e) {
						e.printStackTrace();
						return new ObjectMessage(null, false, "出现异常");
					} finally{
						ConnectionPool.getInstance().release(conn);
					}
				}
			}
		}
		else{
			return new ObjectMessage(null, false, "邮箱格式不正确");
		}
	}
}
