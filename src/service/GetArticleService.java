package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.enterprise.inject.New;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Account;
import model.PublishedArticle;
import vo.ObjectMessage;

public class GetArticleService extends HttpServlet {

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			//�ù���������֤
			String pageSizeString = request.getParameter("PageSizeString");
			String pageString = request.getParameter("PageString");
			if (pageSizeString == null || pageString == null)
				throw new Exception("��������ȷ");
			int PageSize;
			int Page;
			try{
				PageSize = Integer.valueOf(pageSizeString);
				Page = Integer.valueOf(pageString);
			}catch(Exception e){
				throw new Exception("��������ȷ");
			}
			ObjectMessage articleMessage = PublishedArticle.getArticle(PageSize,Page);
			if (articleMessage.isResult()){
				out.println(new Gson().toJson(articleMessage.getObject()));
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
