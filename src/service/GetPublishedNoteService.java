package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Account;
import model.PublishNote;
import vo.ObjectMessage;
import vo.UserAccount;

public class GetPublishedNoteService extends HttpServlet {

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			String UserEmail = request.getParameter("UserEmail");
			String UserPassword = request.getParameter("UserPassword");
			if (UserEmail != null && UserPassword != null) {
				ObjectMessage objectMessage = Account.AccountValidate(
						UserEmail, UserPassword);
				if (objectMessage.isResult()) {
					//out.println("success:验证成功");
					String LocationLatitudeString = request
							.getParameter("LocationLatitude");
					String LocationLongitudeString = request
							.getParameter("LocationLongitude");
					if (LocationLatitudeString != null && LocationLongitudeString != null){
						double LocationLatitude;
						double LocationLongitude;
						try {
							LocationLatitude = Double
									.valueOf(LocationLatitudeString);
							LocationLongitude = Double
									.valueOf(LocationLongitudeString);
							ObjectMessage PublishedNoteMessage = PublishNote.GetByLocation(LocationLatitude, LocationLongitude);
							if (PublishedNoteMessage.isResult()){
								out.println(new Gson().toJson(PublishedNoteMessage.getObject()));
							}
							else{
								throw new Exception(PublishedNoteMessage.getMessageString());
							}
						} catch (Exception e) {
							throw new Exception("参数不正确");
						}
					}
					else{
						ObjectMessage PublishedNoteMessage = PublishNote.GetByAccount((UserAccount)objectMessage.getObject());
						if (PublishedNoteMessage.isResult()){
							out.println(new Gson().toJson(PublishedNoteMessage.getObject()));
						}
						else{
							throw new Exception(PublishedNoteMessage.getMessageString());
						}
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
