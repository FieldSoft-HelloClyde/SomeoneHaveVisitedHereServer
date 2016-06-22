package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.PublishNote;
import vo.ObjectMessage;
import vo.UserAccount;

public class DealWithPulishedNoteService extends HttpServlet {

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println(request.getLocalAddr() + "发布note");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			String UserEmail = request.getParameter("UserEmail");
			String UserPassword = request.getParameter("UserPassword");
			String Content = request.getParameter("Content");
			String ImageFilePath = request.getParameter("ImageFilePath");
			String Date = request.getParameter("Date");
			String LocationLatitudeString = request
					.getParameter("LocationLatitude");
			String LocationLongitudeString = request
					.getParameter("LocationLongitude");
			double LocationLatitude;
			double LocationLongitude;
			if (UserEmail != null && UserPassword != null && Content != null
					&& ImageFilePath != null && Date != null
					&& LocationLatitudeString != null
					&& LocationLongitudeString != null) {
				ObjectMessage objectMessage = Account.AccountValidate(
						UserEmail, UserPassword);
				if (objectMessage.isResult()) {
					try {
						LocationLatitude = Double
								.valueOf(LocationLatitudeString);
						LocationLongitude = Double
								.valueOf(LocationLongitudeString);
					} catch (Exception e) {
						throw new Exception("参数不正确");
					}
					ObjectMessage commitMessage = PublishNote.Commit(
							(UserAccount) objectMessage.getObject(), Content,
							ImageFilePath, Date, LocationLatitude,
							LocationLongitude);
					if (commitMessage.isResult()) {
						out.println("success:提交成功");
					} else {
						throw new Exception(commitMessage.getMessageString());
					}
				} else {
					throw new Exception(objectMessage.getMessageString());
				}
			} else {
				throw new Exception("参数不正确");
			}
		} catch (Exception e) {
			out.println("error:" + e.getMessage());
		} finally {
			out.flush();
			out.close();
		}
	}

}
