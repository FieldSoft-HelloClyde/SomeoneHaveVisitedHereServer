package model;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import database.ConnectionPool;
import vo.Note;
import vo.ObjectMessage;
import vo.UserAccount;

public class PublishNote {
	static public ObjectMessage Commit(UserAccount User,String Content,String ImageFilePath,String Date,double LocationLatitude,double LocationLongitude){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.parse(Date);
		}catch(Exception e){
			return new ObjectMessage(null, false, "日期格式不正确");
		}
		Connection conn = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement PreStatement;
			PreStatement = conn.prepareStatement("insert into publish_note values (default,?,?,?,?,?,?);");
			PreStatement.setInt(1, User.getM_UserId());
			PreStatement.setString(2, Content);
			PreStatement.setString(3, ImageFilePath);
			PreStatement.setString(4, Date);
			PreStatement.setDouble(5, LocationLatitude);
			PreStatement.setDouble(6, LocationLongitude);
			PreStatement.executeUpdate();
			return new ObjectMessage(null, true, "发布成功");
		} catch (SQLException e) {
			e.printStackTrace();
			return new ObjectMessage(null, false, "出现异常");
		} finally{
			ConnectionPool.getInstance().release(conn);
		}
	}
	
	static public ObjectMessage GetByLocation(double LocationLatitude,double LocationLongitude){
		Connection conn = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement PreStatement;
			PreStatement = conn.prepareStatement("SELECT * FROM publish_note WHERE SQRT( POW((LOCATION_LATITUDE - ?), 2) + POW((LOCATION_LONGITUDE - ?), 2) ) <= 3;");
			PreStatement.setDouble(1, LocationLatitude);
			PreStatement.setDouble(2, LocationLongitude);
			ResultSet rSet = PreStatement.executeQuery();
			ArrayList<Note> noteArrayList = new ArrayList<Note>();
			while (rSet.next()) {
				Note tempNote = new Note();
				tempNote.setNote_Id(rSet.getInt("NOTE_ID"));
				tempNote.setUser_Id(rSet.getInt("USER_ID"));
				tempNote.setContent(rSet.getString("CONTENT"));
				tempNote.setImage_File_Path(rSet.getString("IMAGE_FILE_PATH"));
				tempNote.setDate(rSet.getString("DATE"));
				tempNote.setLocation_Latitude(rSet.getDouble("LOCATION_LATITUDE"));
				tempNote.setLocation_Longitude(rSet.getDouble("LOCATION_LONGITUDE"));
				noteArrayList.add(tempNote);
			}
			return new ObjectMessage(noteArrayList, true, "获取成功");
		} catch (SQLException e) {
			e.printStackTrace();
			return new ObjectMessage(null, false, "出现异常");
		} finally{
			ConnectionPool.getInstance().release(conn);
		}
	}
	
	static public ObjectMessage GetByAccount(UserAccount userAccount){
		Connection conn = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement PreStatement;
			PreStatement = conn.prepareStatement("SELECT * FROM publish_note WHERE USER_ID = ?;");
			PreStatement.setInt(1, userAccount.getM_UserId());
			ResultSet rSet = PreStatement.executeQuery();
			ArrayList<Note> noteArrayList = new ArrayList<Note>();
			while (rSet.next()) {
				Note tempNote = new Note();
				tempNote.setNote_Id(rSet.getInt("NOTE_ID"));
				tempNote.setUser_Id(rSet.getInt("USER_ID"));
				tempNote.setContent(rSet.getString("CONTENT"));
				tempNote.setImage_File_Path(rSet.getString("IMAGE_FILE_PATH"));
				tempNote.setDate(rSet.getString("DATE"));
				tempNote.setLocation_Latitude(rSet.getDouble("LOCATION_LATITUDE"));
				tempNote.setLocation_Longitude(rSet.getDouble("LOCATION_LONGITUDE"));
				noteArrayList.add(tempNote);
			}
			return new ObjectMessage(noteArrayList, true, "获取成功");
		} catch (SQLException e) {
			e.printStackTrace();
			return new ObjectMessage(null, false, "出现异常");
		} finally{
			ConnectionPool.getInstance().release(conn);
		}
	}
	
	static public ObjectMessage GetById(int NoteId){
		Connection conn = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement PreStatement;
			PreStatement = conn.prepareStatement("SELECT * FROM publish_note WHERE NOTE_ID = ?;");
			PreStatement.setInt(1, NoteId);
			ResultSet rSet = PreStatement.executeQuery();
			Note tempNote = new Note();
			if (rSet.next()) {
				tempNote.setNote_Id(rSet.getInt("NOTE_ID"));
				tempNote.setUser_Id(rSet.getInt("USER_ID"));
				tempNote.setContent(rSet.getString("CONTENT"));
				tempNote.setImage_File_Path(rSet.getString("IMAGE_FILE_PATH"));
				tempNote.setDate(rSet.getString("DATE"));
				tempNote.setLocation_Latitude(rSet.getDouble("LOCATION_LATITUDE"));
				tempNote.setLocation_Longitude(rSet.getDouble("LOCATION_LONGITUDE"));
			}
			else{
				return new ObjectMessage(null, false, "该Note不存在");
			}
			return new ObjectMessage(tempNote, true, "获取成功");
		} catch (SQLException e) {
			e.printStackTrace();
			return new ObjectMessage(null, false, "出现异常");
		} finally{
			ConnectionPool.getInstance().release(conn);
		}
	}
}
