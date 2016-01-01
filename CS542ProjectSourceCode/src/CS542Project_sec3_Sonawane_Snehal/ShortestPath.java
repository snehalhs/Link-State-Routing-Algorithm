/* This File takes the shortest distance as input calculated 
 * after providing source and destination from path.jsp File and prints the Router names for shortest path. */

package CS542Project_sec3_Sonawane_Snehal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ShortestPath extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ShortestPath() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				response.setContentType("text/html;charset=UTF-8");
			    PrintWriter out = response.getWriter();
			    Topology.ClearScreen();
			    int i,k,j=0,dest,x;
			    int [][]a = null;
				int [][]wt= null;
				String lines;
			    int routernumber[] = new int[50];
				String routername[] = new String[50];
				int[] path = new int[50];
				int[] pred = new int[50];
				int[] cost=new int[50];
				try{
					
					    String s1 = (String) request.getSession().getAttribute("filename");
					    int router = (int) request.getSession().getAttribute("router1");
					    String s11 = (String) request.getSession().getAttribute("source");
					    //String s11= request.getParameter("source");
						int source= Integer.parseInt(s11);
						String s12= request.getParameter("destination");
						int destination= Integer.parseInt(s12);
						request.getSession().setAttribute("destination", s12);
						if(destination>router)
						{
							out.println("<script type=\"text/javascript\">");
						   out.println("alert('Enter The Correct Router Number');");
						   out.println("location='path.jsp';");
						   out.println("</script>");
						} 
						String[] line = new String[router];
						a=new int[router][router];
						wt=new int[router][router];
						BufferedReader br1 = new BufferedReader(new FileReader(s1));
						while ((lines = br1.readLine()) != null)
						{
								line=lines.split(" ");
								for(i=0;i<router;i++)
								{
										a[j][i]= Integer.parseInt(line[i]);
						
								} 
								j=j+1;
						}
						out.println("<body style='background-color:CEF6F5;'>");
						out.println("<center>");
						for(i=0;i<router;i++)
						{
							for(j=0;j<router;j++)
							{
							
								if(a[i][j]<0)
									wt[i][j]=999;
								else
									wt[i][j]=a[i][j];
							}
							
						}
						out.println("</center>");
					   int[][] result=Topology.dijkstra(wt, router, source);
					    for(i=0;i<router;i++)
						{
							pred[i]=result[0][i];
							cost[i]=result[1][i];
						}	
					    for(i=0;i<router;i++)
						{
							routernumber[i]=i;
							routername[i]="R"+(i+1);
						}
					    
					    //Printing The Cost for shortest Path
					    for(i=0;i<router;i++)
					    {
					    	out.println("<center>");
					    	if(i==(destination-1))
					    		
					    		out.println("</br> THE TOTAL COST FROM "+" "+ routername[source-1]+" " + "TO" +" "+ routername[destination-1] +" "+"IS"+" "+cost[i]); 	
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
						 out.println(" &nbsp;&nbsp;&nbsp;&nbsp;<button type=button onclick=window.location.href='index.jsp'>" + "Return To Menu"+"</button>");
						 out.println("</center>");
						 
				}catch(NullPointerException e){
					
					 // File Not Selected
					   out.println("<script type=\"text/javascript\">");
					   out.println("alert('Pls Upload File First');");
					   out.println("location='input.jsp';");
					   out.println("</script>");
					   
				}catch(NumberFormatException e){
					
					// Router Number Entered Is not Digit
					   out.println("<script type=\"text/javascript\">");
					   out.println("alert('Pls Enter Number');");
					   out.println("location='path.jsp';");
					   out.println("</script>");
					
				}catch(ArrayIndexOutOfBoundsException e){
					// Router Number Entered Wrong
					   out.println("<script type=\"text/javascript\">");
					   out.println("alert('Enter The Correct Router Number');");
					   out.println("location='path.jsp';");
					   out.println("</script>");
				}
				
	}
}
