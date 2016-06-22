package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.PublishedArticle;
import vo.ObjectMessage;
import vo.UserAccount;

import com.google.gson.Gson;

public class DealWithPublishedArticle extends HttpServlet {

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			//需要验证管理员身份
			String UserEmail = request.getParameter("UserEmail");
			String UserPassword = request.getParameter("UserPassword");
			if (UserEmail == null || UserPassword == null)
				throw new Exception("参数错误");
			ObjectMessage userMessage = Account.AccountValidate(UserEmail, UserPassword);
			if (!userMessage.isResult())
				throw new Exception(userMessage.getMessageString());
			UserAccount userAccount = (UserAccount)(userMessage.getObject());
			if (userAccount.getM_UserType() == 1)
				throw new Exception("权限不够");
			//获取参数
			String Title = request.getParameter("Title");
			String HtmlContent = request.getParameter("HtmlContent");
			if (Title == null || HtmlContent == null)
				throw new Exception("参数错误");
			if (Title.equals("") || HtmlContent.equals(""))
				throw new Exception("标题或者内容为空");
			if (Title.length() > 50)
				throw new Exception("标题过长");
			ObjectMessage articleMessage = PublishedArticle.Commit(userAccount, Title, HtmlContent);
			if (articleMessage.isResult()){
				out.println("success:" + articleMessage.getMessageString());
			}
			else{
				throw new Exception(articleMessage.getMessageString());
			}
		} catch (Exception e) {
			out.println("error:" + e.getMessage());
		} finally {
			out.flush();
			out.close();
		}
	}

}
