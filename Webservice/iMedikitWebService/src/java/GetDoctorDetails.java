/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hasmath
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(name="GetDoctorDetails", urlPatterns={"/GetDoctorDetails"})
public class GetDoctorDetails extends HttpServlet {
    
    
     Connection conn = null;
    Statement statement=null;
    ResultSet resultset=null;
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Register</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Register at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    boolean connector()
    {
                  String url = "jdbc:mysql://localhost:3306/";
		  String dbName = "imedikit";
		  String driver = "com.mysql.jdbc.Driver";
		  String userName = "root";
		  String password = "";

                  try {
		  Class.forName(driver).newInstance();
		  conn = DriverManager.getConnection(url+dbName,userName,password);
		 // conn.close();
		  }
                  catch (Exception e) {
		  e.printStackTrace();
                  return false;
		  }

                  if(conn!=null) return true;
                  else return false;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            //  processRequest(request, response);
        String doctorCode=request.getParameter("doctor_code");
     // String doctorCode="1";
      
        
        
        ArrayList<String> info=new ArrayList<String>();
        // ArrayList<String>doctor_code=new ArrayList<String>();
       
        
        
     
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            JSONObject json = new JSONObject();
     /*   try {
            json.put("user_name", user);
            json.put("password", pass);
            json.put("mobile_no", mobile);
        } catch (JSONException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }  */
            try {
                boolean connection = connector();
                if(connection)
                {
                     try{
                          statement=conn.createStatement();
                           String sql = "select * from doctor_info WHERE id = "+doctorCode+" ORDER BY id";
                           ResultSet rs =  statement.executeQuery(sql);
 
                           
                           
                           while(rs.next()){
         //Retrieve by column name
        
        info.add(rs.getString("name"));
          info.add(rs.getString("qualification"));
        info.add(rs.getString("chember"));
       
         info.add(rs.getString("speciality"));
        info.add(rs.getString("visit"));
        info.add(rs.getString("working_day"));
        info.add(rs.getString("lat"));
        info.add(rs.getString("lon"));
     
       


         //Display values
         
                               }
                     }
                     catch(Exception e){
                      json.put("success", 0);
                    }

                    json.put("success", 1);
                    json.put("name", info);
                     
                    
        
            try{
                statement.close();
                conn.close();
            }
            catch(Exception e)
            {
            }

                }
                else json.put("success", 0);
            } catch (JSONException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }     

            out.println(json);

        }
        


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


}

