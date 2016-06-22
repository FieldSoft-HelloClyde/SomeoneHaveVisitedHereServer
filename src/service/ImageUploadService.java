package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageUploadService extends HttpServlet {

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean IsSucceed = false;
		try{
			String fileName = request.getHeaders("FileName").nextElement();
			InputStream inputStream = request.getInputStream();
			String path = this.getServletContext().getRealPath("/");
			File imageFile = new File(path + "/Image/" + fileName + ".jpg");
			System.out.println(imageFile);
			if (!imageFile.exists()){
				FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
				byte[] buffer = new byte[1024];
				int length = -1;
				while ((length = inputStream.read(buffer)) != -1){
					fileOutputStream.write(buffer, 0, length);
				}
				fileOutputStream.close();
				inputStream.close();
			}
			IsSucceed = true;
		}catch(Exception e){
			e.printStackTrace();
			IsSucceed = false;
		}
		ServletOutputStream outputStream = response.getOutputStream();
		if (IsSucceed)
			outputStream.print("success");
		else
			outputStream.print("error");
		outputStream.flush();
		outputStream.close();
	}

}
