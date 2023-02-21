import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class ControlServlet extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	    private userDAO userDAO = new userDAO();
	    private String currentUser;
	    private HttpSession session=null;
	    
	    public ControlServlet()
	    {
	    	
	    }
	    
	    public void init()
	    {
	    	userDAO = new userDAO();
	    	currentUser= "";
	    }
	    
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String action = request.getServletPath();
	        System.out.println(action);
	    
	    try {
        	switch(action) {  
        	case "/login":
        		login(request,response);
        		break;
        	case "/signup":
        		register(request, response);
        		break;
        	case "/initialize":
        		userDAO.init();
        		System.out.println("Database successfully initialized!");
        		rootPage(request,response,"");
        		break;
        	case "/root":
        		rootPage(request,response, "");
        		break;
        	case "/logout":
        		logout(request,response);
        		break;
        	 case "/list": 
                 System.out.println("The action is: list");
                 listUser(request, response);           	
                 break;
	    	}
	    }
	    catch(Exception ex) {
        	System.out.println(ex.getMessage());
	    	}
	    }
        	
//	    private void listUser(HttpServletRequest request, HttpServletResponse response)
//	            throws SQLException, IOException, ServletException {
//	        System.out.println("listUser started: 00000000000000000000000000000000000");
//
//	     
//	        List<user> listUser = userDAO.listAllUsers();
//	        request.setAttribute("listUser", listUser);       
//	        RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");       
//	        dispatcher.forward(request, response);
//	     
//	        System.out.println("listPeople finished: 111111111111111111111111111111111111");
//	    }
	    	        
	    private void rootPage(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException{
	    	System.out.println("root view");
			request.setAttribute("listUser", userDAO.listAllUsers());
	    	request.getRequestDispatcher("rootView.jsp").forward(request, response);
	    }
	    
	    
	    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	 String wallet_address = request.getParameter("walletAddress");
	    	 String password = request.getParameter("password");
	    	 String role = request.getParameter("role");
	    	 
	    	 if (wallet_address.equals("root") && password.equals("pass1234")) {
	    		 request.setAttribute("resStr","Logging in successfully as the root user");
		   	 	 request.getRequestDispatcher("tempRes.jsp").forward(request, response);
	    	 }
	    	 else if (role.equals("sponsor") && userDAO.isValid(wallet_address, password, role)) {
	    		 request.setAttribute("resStr","Logging in successfully as the sponsor user");
		   	 	 request.getRequestDispatcher("tempRes.jsp").forward(request, response);
	    		 
	    	 }
	    	 
	    	 else if (role.equals("contestant") && userDAO.isValid(wallet_address, password, role)) {
	    		 request.setAttribute("resStr","Logging in successfully as the contestant user");
		   	 	 request.getRequestDispatcher("tempRes.jsp").forward(request, response);
	    		 
	    	 }
	    	 
	    	 else if (role.equals("judge") && userDAO.isValid(wallet_address, password, role)) {
	    		 request.setAttribute("resStr","Logging in successfully as the judge user");
		   	 	 request.getRequestDispatcher("tempRes.jsp").forward(request, response);
	    		 
	    	 }
	    	 else {
	    		 request.setAttribute("resStr","Not able to log you in. please check if you putted role, wallet addreess or password right");
		   	 	 request.getRequestDispatcher("tempRes.jsp").forward(request, response);
	    	 }
	    	
//	    	 String email = request.getParameter("email");
//	    	 String password = request.getParameter("password");
//	    	 
//	    	 if (email.equals("root") && password.equals("pass1234")) {
//				 System.out.println("Login Successful! Redirecting to root");
//				 session = request.getSession();
//				 session.setAttribute("username", email);
//				 rootPage(request, response, "");
//	    	 }
//	    	 else if(userDAO.isValid(email, password)) 
//	    	 {
//			 	 
//			 	 currentUser = email;
//				 System.out.println("Login Successful! Redirecting");
//				 request.getRequestDispatcher("activitypage.jsp").forward(request, response);
//			 			 			 			 
//	    	 }
//	    	 else {
//	    		 request.setAttribute("loginStr","Login Failed: Please check your credentials.");
//	    		 request.getRequestDispatcher("login.jsp").forward(request, response);
//	    	 }
	    }
	           
	    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	
	   	 	String wallet_address = request.getParameter("walletAddress");
	   	 	String password = request.getParameter("password");
	   	 	String role = request.getParameter("role");
	   	 	System.out.print(role);
	   	 	if (userDAO.checkWalletAddress(wallet_address, role)) {
	   	 		if (role.equals("sponsor")) {
	   	 			sponsor sponsors = new sponsor(wallet_address, "", "", "", password);
	   	 			userDAO.insert(sponsors, "sponsor");
	   	 		}
	   	 		if (role.equals("judge")) {
	   	 			judge judges = new judge(wallet_address, password, 0);
	   	 			userDAO.insert(judges, "judge");
	   	 		}
	   	 		if (role.equals("contestant")) {
	   	 			contestant contestants = new contestant(wallet_address, password, 0);
	   	 			userDAO.insert(contestants, "contestant");
	   	 		}	
	   	 		request.setAttribute("resStr","Sign up successfully");
	   	 		request.getRequestDispatcher("tempRes.jsp").forward(request, response);
	   	 	}else {
	   	 		request.setAttribute("resStr","Please use another wallet address to sign up an account. Duplicated in our database");
	   	 		request.getRequestDispatcher("tempRes.jsp").forward(request, response);
	   	 	}
	   	 	
	    }    
	    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	currentUser = "";
        		response.sendRedirect("login.jsp");
        	}
	
	    

	     
        
	    
	    
	    
	    
	    
}
	        
	        
	    
	        
	        
	        
	    


