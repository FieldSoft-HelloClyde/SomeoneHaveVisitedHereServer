package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import vo.ObjectMessage;

public class UserRegisterService extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try{
			String UserEmail = request.getParameter("UserEmail");
			String UserPassword = request.getParameter("UserPassword");
			if (UserEmail != null && UserPassword != null){
				ObjectMessage objectMessage = Account.Register(UserEmail, UserPassword);
				if (objectMessage.isResult()){
					out.println("success:注册成功");
				}
				else{
					throw new Exception(objectMessage.getMessageString());
				}
			}
			else{
				throw new Exception("参数不正确");
			}
		} catch(Exception e){
			out.println("error:" + e.getMessage());
		} finally{
			out.flush();
			out.close();
		}
	}

}
