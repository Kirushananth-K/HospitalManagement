package com;

import com.doctor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/doctorAPI")
public class doctotAPI extends HttpServlet {

	private static final long serialVersionUID = 1L;

	doctor doc = new doctor();
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String output = doc.insertDoctor(request.getParameter("DoctorID"), request.getParameter("DoctorName"),
				request.getParameter("Specialization"), request.getParameter("Contact"),
				request.getParameter("Address"));
		response.getWriter().write(output);
	}

	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			for (String param : params) {
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		} catch (Exception e) {
		}
		return map;
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException
			{
			 Map paras = getParasMap(request);
			 String output = doc.updateDoctor(paras.get("hidDIDSave").toString(),
			 paras.get("DoctorID").toString(),
			 paras.get("DoctorName").toString(),
			paras.get("Specialization").toString(),
			paras.get("Contact").toString(),
			paras.get("Address").toString());
			response.getWriter().write(output);
			}
			protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException
			{
			 Map paras = getParasMap(request);
			 String output = doc.deleteDoctor(paras.get("DID").toString());
			response.getWriter().write(output);
			}
}
