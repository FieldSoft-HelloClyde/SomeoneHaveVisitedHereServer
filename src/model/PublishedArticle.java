package model;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import database.ConnectionPool;
import vo.Article;
import vo.ObjectMessage;
import vo.UserAccount;

public class PublishedArticle {
	public static ObjectMessage getArticle(int PageSize,int Page){
		Connection conn = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement PreStatement;
			PreStatement = conn.prepareStatement("SELECT MAX(ARTICLE_ID) from article;");
			ResultSet rSet = PreStatement.executeQuery();
			rSet.next();
			int MaxId = rSet.getInt(1);
			int LeftN = PageSize * Page;
			int RightN = PageSize + (Page + 1);
			int Left = MaxId - RightN;
			int Right = MaxId - LeftN;
			PreStatement = conn.prepareStatement("SELECT * FROM article WHERE ARTICLE_ID >= ? AND ARTICLE_ID < ?;");
			PreStatement.setInt(1, Left);
			PreStatement.setInt(2, Right);
			rSet = PreStatement.executeQuery();
			ArrayList<Article> articles = new ArrayList<Article>();
			while (rSet.next()){
				articles.add(new Article(rSet.getInt(1), rSet.getString(2), rSet.getInt(3), rSet.getString(4)));
			}
			return new ObjectMessage(articles, true, "获取文章成功");
		} catch (SQLException e) {
			e.printStackTrace();
			return new ObjectMessage(null, false, "出现异常");
		} finally{
			ConnectionPool.getInstance().release(conn);
		}
	}
	
	public static ObjectMessage Commit(UserAccount userAccount,String Title,String HtmlContent){
		Connection conn = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement PreStatement;
			PreStatement = conn.prepareStatement("insert into article values (default,?,?,?);");
			PreStatement.setInt(1, userAccount.getM_UserId());
			PreStatement.setString(2, Title);
			PreStatement.setString(3, HtmlContent);
			PreStatement.executeUpdate();
			return new ObjectMessage(null, true, "发布成功");
		} catch (SQLException e) {
			e.printStackTrace();
			return new ObjectMessage(null, false, "出现异常");
		} finally{
			ConnectionPool.getInstance().release(conn);
		}
	}
}
