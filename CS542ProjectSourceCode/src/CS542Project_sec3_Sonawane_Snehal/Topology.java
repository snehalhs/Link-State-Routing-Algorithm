package CS542Project_sec3_Sonawane_Snehal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Used to generate the connection table on selection of a source router
 * @author snehal
 *
 */
public class Topology extends HttpServlet {
	private static final long serialVersionUID = 1L; 

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				ClearScreen();
				String lines;
				int j = 0, i, s;
				int[][] a = null;
				int[][] wt = null;
				int routernum[] = new int[50];
				int[] pred = new int[50];
				int[] cost = new int[50];
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				try {
						String s1 = (String) request.getSession().getAttribute("filename");
						int router = (int) request.getSession().getAttribute("router1");
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
					
					//Taking Value of Source from JSP Page
					String s11 = request.getParameter("source");
					s = Integer.parseInt(s11);
					request.getSession().setAttribute("source", s11);
					int[][] result = dijkstra(wt, router, s);
					for (i = 0; i < router; i++)
					{
						pred[i] = result[0][i];
						cost[i] = result[1][i];
					}
					for (i = 0; i < router; i++) 
					{
						routernum[i] = i + 1;
					}
					s = s - 1;
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
						if(i==s)
						{
							out.print("<tr width=100px>");
							out.println("<td width=70px,align=center>" + routernum[i] + "</td> "
									+"<td width=70px,align=center>" + "-" + "</td>");
							out.print("</tr>");
						}
						if ((pred[i] == s || pred[i] == 0) && i!=s)
						{
							out.print("<tr width=100px>");
							out.println("<td width=70px,align=center>" + routernum[i] + "</td>"+ "<td width=70px,align=center>"
									+ routernum[i] + "</td>");
							out.print("</tr>");
						}
						else
							if(i!=s)
							{
								out.print("<tr width=100px>");
								out.println("<td width=70px,align=center>" + routernum[i] + "</td> " + "<td width=70px,align=center>"
										+ (pred[i] + 1) + "</td>");
								out.print("</tr>");
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
					   
				} catch (NumberFormatException e) {
					
					 // Router Number Entered Is not Digit
					   out.println("<script type=\"text/javascript\">");
					   out.println("alert('Please Enter Number');");
					   out.println("location='connection.jsp';");
					   out.println("</script>");
				}
	}

	public static void ClearScreen() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}

	// Taking cost matrix and number of routers as input and calculating shortest path 
	public static int[][] dijkstra(int[][] wt, int router, int source) {
		int visit[] = new int[20];
		int cost[] = new int[20];
		int pred[] = new int[20];
		int k, x, dest;
		int routernumber[] = new int[20];
		String routername[] = new String[20];
		int minwt, next = 0;
		int i, j;
		source = source - 1;
		visit[source] = 1;
		cost[source] = 0;
		for (i = 0; i < router; i++)
		{
			routernumber[i] = i;
			routername[i] = "R" + (i + 1);
		}
		
		// Visiting each node
		for (i = 0; i < router; i++) 
		{
			if (i != source) 
			{
				visit[i] = 0;
				cost[i] = wt[source][i];
				pred[i] = source;
			}
		}
		
		// Checking cost for each node and comparing with minimum cost
		for (j = 0; j < router; j++) 
		{
			minwt = 999;
			for (i = 0; i < router; i++) 
			{
				if (cost[i] < minwt && visit[i] == 0) 
				{
					minwt = cost[i];
					next = i;
				}
			}
			visit[next] = 1;
			for (i = 0; i < router; i++) {
				if (visit[i] == 0 && (minwt + wt[next][i]) < cost[i]) 
				{
					cost[i] = minwt + wt[next][i];
					pred[i] = next;
				}
			}
		}

		int[][] result = new int[][] { pred, cost };
		return result;
	}
}
