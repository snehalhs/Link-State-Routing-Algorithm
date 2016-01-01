

package CS542Project_sec3_Sonawane_Snehal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Take input file from the user and read it and display the matrix table
 */
public class Dijkstra extends HttpServlet {
	private static final long serialVersionUID = 102831973239L;
	private BufferedReader br;

	public int inputfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int router = 0;
		int i;
		String lines;
		String next = "";
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try 
		{		    
				String s1="C:/try/" + "dij.txt";
				File file = new File("C:/try/" + "dij.txt");
				//Reading the input file
				br = new BufferedReader(new FileReader(file));
				out.println("<center>");
				out.println("<body style='background-color:CEF6F5;'>");
				out.println("<b><u>Review original topology matrix:</u> </b></br> ");
				out.println("</br>");
				
				//Printing the File
				while ((lines = br.readLine()) != null) {
				String[] line = lines.split(" ");
				for(i=0;i<line.length;i++)
				{
					next+=line[i]+",";
					if(Character.isAlphabetic(line[i].charAt(0)))
					{
						response.sendRedirect("input.jsp"); 
					}
				}
	
				out.println(lines+" "+" ");
				router = router + 1;
				out.println("</br>");
					
			    
			}
				String array[]= next.split(",");
				//Error handling checking if the input file is correct
				if(array.length<(router*router) || array.length>(router*router))
				{
					  out.println("<script type=\"text/javascript\">");
					   out.println("alert('File opened is not correct Becuase Number of rows are not equal to number of columns. Please Upload Correct File');");
					   out.println("location='input.jsp';");
					   out.println("</script>");
				}
			out.println("</br>");
			request.getSession().setAttribute("router1", router);
			request.getSession().setAttribute("filename", s1);
			out.println("<button type=button onclick=window.location.href='index.jsp'>"
					+ "Return To Menu" + "</button>");
			out.println("</center>");
			
		} catch (FileNotFoundException e) {
			   
			// Error handling to check if file is not selected correctly
			   out.println("<script type=\"text/javascript\">");
			   out.println("alert('Select File First');");
			   out.println("location='input.jsp';");
			   out.println("</script>");
		}      

		return (router);

	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		try 
		{
			//String s1 = request.getRealPath("dij.txt");
			//String s1=request.getParameter("file");
			// inputfile = "C:/temp/";
				//String filename1 = "C:/try/";
				//File file = new File("C:/try/" + "file");
			String s1="C:/try/dij.txt";
			request.getSession().setAttribute("filename", s1);
			int router = inputfile(request, response);

		} catch (FileNotFoundException e) 
		{     
			   // Error handling to check if file is not selected correctly
			   out.println("<script type=\"text/javascript\">");
			   out.println("alert('Select File First');");
			   out.println("location='input.jsp';");
			   out.println("</script>");
		}

	}

}
