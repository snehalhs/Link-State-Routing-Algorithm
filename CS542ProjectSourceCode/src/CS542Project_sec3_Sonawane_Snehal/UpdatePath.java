/* This File generates Cost matrix and calculates shortest path. 
 * Connection Table Gets Printed according to the value taken of source from connection.jsp Page. */

package CS542Project_sec3_Sonawane_Snehal;

import java.io.BufferedReader;


import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.catalina.loader.Reloader;

import javax.servlet.RequestDispatcher;


public class UpdatePath extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 public UpdatePath() {
	        super();
	        // TODO Auto-generated constructor stub
	    }

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				Topology.ClearScreen();
				String lines;
				int j = 0, i, source,k,l,x,dest,s,destination;
				int[][] a = null;
				int[][] wt = null;
				int routernum[] = new int[50];
				String routername[] = new String[50];
				int[] pred = new int[50];
				int[] cost = new int[50];
				int[] path = new int[50];
				String in=request.getParameter("routerNumber");
				int input=Integer.parseInt(in);
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				try {
						String s1 = (String) request.getSession().getAttribute("filename");
						int router = (int) request.getSession().getAttribute("router1");
						String s11 = (String) request.getSession().getAttribute("source");
					    //String s11= request.getParameter("source");
						source= Integer.parseInt(s11);
/*						String s11 = request.getParameter("source");
						source = Integer.parseInt(s11);
						String s12= request.getParameter("destination");
						int destination= Integer.parseInt(s12);*/
						String s12= (String) request.getSession().getAttribute("destination");
						destination=Integer.parseInt(s12);
						if(destination>router)
						{
							out.println("<script type=\"text/javascript\">");
						   out.println("alert('Enter The Correct Router Number');");
						   out.println("location='path.jsp';");
						   out.println("</script>");
						} 
						String[] line = new String[router];
						a = new int[router][router];
						wt = new int[router][router];
						BufferedReader br1 = new BufferedReader(new FileReader(s1));
						while ((lines = br1.readLine()) != null)
						{
							line = lines.split(" ");
							for (i = 0; i < router; i++) 
							{
									a[j][i] = Integer.parseInt(line[i]);
							}
							j = j + 1;
						}
						
						for (k = 0; k < router; k++) 
						{
						    for (l = 0; l < router; l++) 
							{
								if (k==(input-1) || l==(input-1))
									a[k][l] = -1;
							}
						    
					}
					out.println("<center>");	
					out.println("<body style='background-color:CEF6F5;'>");
					
					
					//Initializing Cost Matrix
					
					for (i = 0; i < router; i++) 
					{
						
					    for (j = 0; j < router; j++) 
						{
							
							if (a[i][j] <0)
								wt[i][j] = 999;
							else
								wt[i][j] = a[i][j];
								
						}
					   
					}
					out.println("</center>");
					
					int[][] result = Topology.dijkstra(wt, router, source);
					for (i = 0; i < router; i++)
					{
						pred[i] = result[0][i];
						cost[i] = result[1][i];
					}
					for (i = 0; i < router; i++) 
					{
						routernum[i] = i + 1;
						routername[i]="R"+(i+1);
					}
					
					
					for(i=0;i<router;i++)
				    {
				    	out.println("<center>");
				    	if(i==(destination-1))
				    		
				    		out.println("</br> THE TOTAL COST FROM"+" "+ routername[source-1]+" " + "TO" +" "+ routername[destination-1] +" "+"IS"+" "+cost[i]); 	
				    }
				   
				     k=0;
					 out.println("</br>");
					 
					 // Printing the Shortest Path
				     out.println("</br>SHORTEST PATH IS:");
					 dest=pred[destination-1];
					 path[k]=dest;
					 do
						{ 
							 k++;
							 dest=pred[dest];
							 path[k]=dest;
						} while(dest!=(source-1));
					     
					 out.print(routername[source-1]+" "+"->");
					 for(x=k;x>=0;x--)
					  {
							 if(routername[path[x+1]]!=routername[path[x]])
							    out.print(routername[path[x]]+ " "+"->");
				       }
					 out.println(routername[destination-1]);
					 out.println("</br>");
					 out.println("</br>");
					 out.println("</center>");
					
					 source = source - 1;
						out.println("<html>");
						out.println("<center>");
						
						// Printing Connection Table
						out.println("<h1><u>Connection Table:</u> </h1>");
						out.println("==============================</br>");
						out.println("<b><u>Destination</u> &nbsp;<u>Interface</b></u></br>");
						out.println("</br>");
						out.print("<table border=1>");
						for (i = 0; i < router; i++) 
						{
							if(i!=input-1){
							if(i==source)
							{
								out.print("<tr width=100px>");
								//out.print("<td width=100px>");
								out.println("<td width=70px,align=center>" + routernum[i] + "</td> "
										+"<td width=70px,align=center>" + "-" + "</td>");
								//out.print("</td>");
								out.print("</tr>");
							}
							if ((pred[i] == source || pred[i] == 0) && i!=source)
							{
								out.print("<tr width=100px>");
								out.println("<td width=70px,align=center>" + routernum[i] + "</td>"+ "<td width=70px,align=center>"
										+ routernum[i] + "</td>");
								out.print("</tr>");
							}
							else
								if(i!=source)
								{
									out.print("<tr width=100px>");
									out.println("<td width=70px,align=center>" + routernum[i] + "</td> " + "<td width=70px,align=center>"
											+ (pred[i] + 1) + "</td>");
									out.print("</tr>");
								}
							}
						}
						out.print("</table>");
						request.getSession().setAttribute("router1", router);
						request.getSession().setAttribute("filename", s1);
						out.println( "</br>" + "<button type=button onclick=window.location.href='index.jsp'>"
								+ "Return To Menu" + "</button>");
						out.println("</center>");
		
				} catch (ArrayIndexOutOfBoundsException e) {
	
					 // Router Number Entered Wrong
					   out.println("<script type=\"text/javascript\">");
					   out.println("alert('Enter The Correct Router Number');");
					   out.println("location='connection.jsp';");
					   out.println("</script>");
					   
				}catch (NullPointerException e) {
					
					   // File Not Selected
					   out.println("<script type=\"text/javascript\">");
					   out.println("alert('Pls Upload File First');");
					   out.println("location='input.jsp';");
					   out.println("</script>");
					
				} catch (NumberFormatException e) {
					
					 // Router Number Entered Is not Digit
					   out.println("<script type=\"text/javascript\">");
					   out.println("alert('Pls Enter Number');");
					   out.println("location='connection.jsp';");
					   out.println("</script>");
				}
	}
}
