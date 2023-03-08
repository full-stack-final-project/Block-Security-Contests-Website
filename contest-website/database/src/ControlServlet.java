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
import java.time.LocalDateTime;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        	case "/checkUserID":
        		checkUserID(request, response);
        		break;
        	case "/create":
        		createPage(request, response);
        		break;
        	case "/createContest":
        		createContest(request, response);
        		break;
        	case "/openContest":
        		openContest(request, response);
        		break;
        	case "/contestDetails":
        		contestDetails(request, response);
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
//	    	request.setAttribute("resStr","You just initialized the database. You can review the whole database through Workbench now");
	   	 	 request.getRequestDispatcher("rootView.jsp").forward(request, response);
	    }
	    
	    protected void contestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String contestID = request.getParameter("id");
	    	String contestantID = request.getParameter("userID");
	    	System.out.println(contestID);
	    	Contest contest = userDAO.getContestbyID(contestID);
	    	request.setAttribute("contestName", contest.getTitle());
	    	request.setAttribute("beginTime", contest.getBeginTime());
	    	request.setAttribute("endTime", contest.getEndTime());
	    	request.setAttribute("requirements", contest.getRequirementList());
	    	request.setAttribute("userID", contestantID);
	    	request.setAttribute("contestID", contest.getContestID());
	    	RequestDispatcher rd = request.getRequestDispatcher("contest.jsp");
	    	rd.forward(request, response);
	    	
	    }
	    
	    protected void openContest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String contestantID = request.getParameter("id");
	    	List<Contest> open_contests = userDAO.getOpenContests();
	    	request.setAttribute("contests", open_contests);
	    	request.setAttribute("contestantID", contestantID);
	    	RequestDispatcher rd = request.getRequestDispatcher("openContest.jsp");
	    	rd.forward(request, response);
	    }
	    
	    protected void createPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String sponsor_id = request.getParameter("sponsor_id");
	    	List<String> judgesName = userDAO.listJudgesName();
	    	request.setAttribute("judgesList", judgesName);
	    	request.setAttribute("sponsor_id", sponsor_id);
	    	RequestDispatcher rd = request.getRequestDispatcher("createContest.jsp");
	    	rd.forward(request, response);
	    	
	    }
	    
	    protected void createContest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String contestName = request.getParameter("title");
	    	String walletAddress = request.getParameter("wallet");
	    	String funding = request.getParameter("funding");
	    	long fundingContest = Long.parseLong(funding);
	    	String begin_time_str = request.getParameter("begin");
	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	    	LocalDateTime begin_time = LocalDateTime.parse(begin_time_str, formatter);
	    	String end_time_str = request.getParameter("end");
	    	LocalDateTime end_time = LocalDateTime.parse(end_time_str, formatter);
	    	String[] selectJudges = request.getParameterValues("judges");
	    	String sponsor_id = request.getParameter("sponsorID");
	    	
	    	String requirment = request.getParameter("requirment");
	    	Contest contest = new Contest(walletAddress, sponsor_id, contestName, begin_time, end_time, "created", requirment, fundingContest);
	    	
	    	userDAO.createContest(contest, selectJudges);
	    	userDAO.insertJudgeby(contest, selectJudges);
	    	
	    	Contest[] contest_list = new Contest[100];
   		 	request.setAttribute("userID", sponsor_id);
   		 	request.setAttribute("contestList", contest_list);
	   	 	request.getRequestDispatcher("sponsorIndex.jsp").forward(request, response);
	    	
	    		    	
	    }
	    
	    protected void checkUserID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	PrintWriter out = response.getWriter();
	    	String outMessage = "";
	    	if (userDAO.checkUserID(currentUser, currentUser)) {
	    		outMessage = "Good UserID";
	    	}
	    	else {
	    		outMessage = "This UserID has been used";
	    	}
	    	out.print(outMessage);
	    	out.flush();
	    	
	    }
	    
	    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	 String userID = request.getParameter("userID");
	    	 String password = request.getParameter("password");
	    	 String role = request.getParameter("role");
	    	 
	    	 if (userID.equals("root") && password.equals("pass1234")) {
//	    		 request.setAttribute("resStr","Logging in successfully as the root user");
		   	 	 request.getRequestDispatcher("rootView.jsp").forward(request, response);
	    	 }
	    	 else if (role.equals("sponsor") && userDAO.isValid(userID, password, role)) {
	    		 //request.setAttribute("resStr","Logging in successfully as the sponsor user");
	    		 Contest[] contest_list = new Contest[100];
	    		 String sponsor_id = userDAO.getSponsorIDByLoginID(userID);
	    		 request.setAttribute("userID", sponsor_id);
	    		 request.setAttribute("contestList", contest_list);
		   	 	 request.getRequestDispatcher("sponsorIndex.jsp").forward(request, response);
	    		 
	    	 }
	    	 
	    	 else if (role.equals("contestant") && userDAO.isValid(userID, password, role)) {
//	    		 request.setAttribute("resStr","Logging in successfully as the contestant user");
//		   	 	 request.getRequestDispatcher("tempRes.jsp").forward(request, response);
	    		 Contestant contestant = userDAO.getContestantByLoginID(userID);
	    		 request.setAttribute("walletAddress", contestant.id);
	    		 request.setAttribute("balance", contestant.reward_balance);
	    		 List<Contest> contests = userDAO.getContestsParticipated(contestant.id);
	    		 request.setAttribute("contests", contests);
	    		 
	    		 request.getRequestDispatcher("contestantIndex.jsp").forward(request, response);	    		 
	    	 }
	    	 
	    	 else if (role.equals("judge") && userDAO.isValid(userID, password, role)) {
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
	   	 	String login_id = request.getParameter("userID");
	   	 	if (userDAO.checkWalletAddress(wallet_address, role)) {
	   	 		if (role.equals("sponsor")) {
	   	 			Sponsor sponsors = new Sponsor(wallet_address,login_id, "", "", "", password, 1000000);
	   	 			userDAO.insert(sponsors, "sponsor");
	   	 		}
	   	 		if (role.equals("judge")) {
	   	 			Judge judges = new Judge(wallet_address, login_id, password, 0, 0, 0);
	   	 			userDAO.insert(judges, "judge");
	   	 		}
	   	 		if (role.equals("contestant")) {
	   	 			Contestant contestants = new Contestant(wallet_address, login_id, 0, password);
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
        		response.sendRedirect("index.jsp");
        	}
	
	    

	     
        
	    
	    
	    
	    
	    
}
	        
	        
	    
	        
	        
	        
	    


