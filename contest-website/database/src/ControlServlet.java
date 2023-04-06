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
        		rootPage(request,response);
        		break;
        	case "/root":
        		rootPage(request,response);
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
        	case "/submitpage":
        		submitpage(request, response);
        		break;
        	case "/submitContest":
        		submitContest(request, response);
        		break;
        	case "/contestDetailsSponsor":
        		contestDetailsSponsor(request, response);
        		break;
        	case "/contestDetailsJudge":
        		contestDetailsJudge(request, response);
        		break;
        	case "/submitScore":
        		submitScore(request, response);
        		break;
        	case "/score":
        		score(request, response);
        		break;
        	case "/distributeSubmission":
        		distributeSubmission(request, response);
        		break;
        	case "/distributeBonus":
        		distributeRewards(request, response);
        		break;
        	case "/sponsorIndex":
        		sponsorIndex(request, response);
        		break;
        	case "/judgeIndex":
        		judgeIndex(request, response);
        		break;
        	case "/contestantIndex":
        		contestantIndex(request, response);
        		break;
        	case "/sponsorReturn":
        		sponsorReturn(request, response);
        		break;
        	case "/contestantReturn":
        		contestantReturn(request, response);
        		break;
        	case "/judgeReturn":
        		judgeReturn(request, response);
        		break;
        	case "/reviewJudge":
        		reviewJudge(request, response);
        		break;
        	case "/submitReview":
        		submitReview(request, response);
        		break;
        	case "/leaderboard":
        		leaderboard(request, response);
        		break;
        	case "/bigsponsors":
        		bigSponsors(request, response);
        		break;
        	case "/topjudges":
        		topJudges(request, response);
        		break;
        	case "/bestcontestants":
        		bestContestants(request, response);
        		break;
        	case "/commoncontestantspage":
        		commonContestantsPage(request, response);
        		break;
        	case "/commonContest":
        		commonContest(request, response);
        		break;
        	case "/sleepycontestants":
        		sleepyContestants(request, response);
        		break;
        	case "/busyjudges":
        		busyJudges(request, response);
        		break;
        	case "/toughcontests":
        		toughContests(request, response);
        		break;
        	case "/copycats":
        		copycats(request, response);
        		break;
        	case "/copycatspage":
        		copyCatsPage(request, response);
        		break;
        	
	    	}
	    }
	    catch(Exception ex) {
        	System.out.println(ex.getMessage());
	    	}
	    }
	    	        
	    private void rootPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	request.setAttribute("tips","You just initialized the database.");
	   	 	request.getRequestDispatcher("rootReturn.jsp").forward(request, response);
	    }
	    
	    protected void copyCatsPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	List<Contestant> contestants = userDAO.rankContestants();
	    	request.setAttribute("contestants", contestants);
	    	request.getRequestDispatcher("copyCatsPage.jsp").forward(request, response);
	    	
	    }
	    
	    protected void copycats(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String contestant1 = request.getParameter("contestant1");
	    	List<Contestant> contestants = userDAO.findYs(contestant1);
	    	request.setAttribute("contestants", contestants);
	    	request.setAttribute("queryName", "Copy Cats for "+ contestant1);
	    	request.getRequestDispatcher("sqlContestant.jsp").forward(request, response);
	    }
	    
	    protected void toughContests(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	List<Contest> contests = userDAO.findToughContests();
	    	request.setAttribute("queryName", "Tough Contests");
	    	request.setAttribute("contests", contests);
	    	request.getRequestDispatcher("sqlContest.jsp").forward(request, response);
	    }
	    
	    protected void busyJudges(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	List<Judge> judges = userDAO.busyJudges();
	    	request.setAttribute("queryName", "Busy Judges");
	    	request.setAttribute("judges", judges);
	    	request.getRequestDispatcher("sqlJudge.jsp").forward(request, response);
	    }
	    
	    protected void sleepyContestants(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	List<Contestant> contestants = userDAO.sleepyContestants();
	    	request.setAttribute("queryName", "Sleepy Contestants");
	    	request.setAttribute("contestants", contestants);
	    	request.getRequestDispatcher("sqlContestant.jsp").forward(request, response);
	    }
	    
	    protected void commonContest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String contestant1 = request.getParameter("contestant1");
	    	String contestant2 = request.getParameter("contestant2");
	    	List<Contest> contests = userDAO.commonContests(contestant1, contestant2);
	    	request.setAttribute("queryName", "Common Contests for two selected contestants");
	    	request.setAttribute("contests", contests);
	    	request.getRequestDispatcher("sqlContest.jsp").forward(request, response);
	    }
	    
	    protected void commonContestantsPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	List<Contestant> contestants = userDAO.rankContestants();
	    	request.setAttribute("contestants", contestants);
	    	request.getRequestDispatcher("commonContestants.jsp").forward(request, response);
	    	
	    }
	    
	    protected void bestContestants(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	List contestants = userDAO.bestContestants();
	    	request.setAttribute("queryName", "Best Contestants");
	    	request.setAttribute("contestants", contestants);
	    	request.getRequestDispatcher("sqlContestant.jsp").forward(request, response);
	    }
	    
	    protected void topJudges(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	List<Judge> judges = userDAO.topJudges();
	    	request.setAttribute("queryName", "Top Judges");
	    	request.setAttribute("judges", judges);
	    	System.out.println(judges.get(0).getLoginID());
	    	request.getRequestDispatcher("sqlJudge.jsp").forward(request, response);
	    			
	    }
	    
	    protected void bigSponsors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	List<Sponsor> sponsors = userDAO.listBigSponsors();
	    	request.setAttribute("sponsors", sponsors);
	    	request.setAttribute("queryName", "Big Sponsor(s)");
	    	request.getRequestDispatcher("sqlSponsor.jsp").forward(request, response);
	    }
	    
	    
	    protected void leaderboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	List<Contestant> contestants = userDAO.allContestants();
	  
	    	request.setAttribute("contestants", contestants);
	    	request.getRequestDispatcher("leaderboard.jsp").forward(request, response);
	    }
	    
	    protected void reviewJudge(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	String judgeID = request.getParameter("id");
	    	String sponsorID = request.getParameter("userID");
	    	Judge judge = userDAO.getjudgeByID(judgeID);
	    	String judgeLoginID = judge.getLoginID();
	    	List<Object> review = userDAO.getReview(judgeID, sponsorID);
	    	if (!review.isEmpty()) {
	    		request.setAttribute("review", review.get(0));
	    		request.setAttribute("score", review.get(1));
	    	}
	    	request.setAttribute("judgeLoginID", judgeLoginID);
	    	request.setAttribute("sponsorID", sponsorID);
	    	request.setAttribute("judgeID", judgeID);
	    	request.getRequestDispatcher("review.jsp").forward(request, response);
	    	
	    }
	    
	    protected void submitReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	String judgeID = request.getParameter("judgeID");
	    	String sponsorID = request.getParameter("sponsorID");
	    	String comment = request.getParameter("context");
	    	String scoreStr = request.getParameter("score");
	    	int score = Integer.parseInt(scoreStr);
	    	if (userDAO.insertReview(judgeID, sponsorID, comment, score)) {
	    		response.sendRedirect("/sponsorReturn?tips=s3&sponsorID="+sponsorID);
	    	}
	    	
	    }

	    protected void score(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	String contestID = request.getParameter("id");
	    	String judgeID = request.getParameter("judgeID");
	    	String contestantID = request.getParameter("contestantID");
	    	String submission = userDAO.getSubmission(contestID, contestantID);
	    	
	    	request.setAttribute("submission", submission);
	    	request.setAttribute("userID", judgeID);
	    	request.setAttribute("contestantID", contestantID);
	    	request.setAttribute("contestID", contestID);
	    	
	    	request.getRequestDispatcher("score.jsp").forward(request, response);
	    	
	    }
	    
	    protected void submitScore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	System.out.println("Enter here.");
	    	String contestID = request.getParameter("contestID");
	    	String judgeID = request.getParameter("userID");
	    	String contestantID = request.getParameter("contestantID");
	    	String scoreStr = request.getParameter("score");
	    	int score = Integer.parseInt(scoreStr);
	    	userDAO.updateGrade(contestID, contestantID, score);
//	    	if (userDAO.updateGrade(contestID, contestantID, score)) {
//	    		System.out.println("Updated the grade.");
//	    	}
	    	response.sendRedirect("/judgeReturn?tips=s1&judgeID="+judgeID);
	    }

	    protected void submitContest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	String submission = request.getParameter("context");
	    	String contestantID = request.getParameter("userID");
	    	String contestID = request.getParameter("contestID");
	    	if (userDAO.insertSubmission(contestID, contestantID, submission)) {
//	    		Contestant contestant = userDAO.getContestantByID(contestantID);
//	    		 request.setAttribute("walletAddress", contestant.id);
//	    		 request.setAttribute("balance", contestant.reward_balance);
//	    		 List<Contest> contests = userDAO.getContestsParticipated(contestant.id);
//	    		 request.setAttribute("contests", contests);
//	    		 
//	    		 request.getRequestDispatcher("contestantIndex.jsp").forward(request, response);
	    		response.sendRedirect("/contestantReturn?tips=s1&contestantID="+contestantID);
	    	}
	    }
	    

	    protected void submitpage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String contestID = request.getParameter("contestID");
	    	String contestantID = request.getParameter("userID");
	    	Contest contest = userDAO.getContestbyID(contestID);
	    	request.setAttribute("contestName", contest.getTitle());
	    	request.setAttribute("userID", contestantID);
	    	request.setAttribute("contestID", contestID);
	    	RequestDispatcher rd = request.getRequestDispatcher("submit.jsp");
	    	rd.forward(request, response);
	    }
	    

	    protected void contestDetailsJudge(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String contestID = request.getParameter("id");
	    	String judgeID = request.getParameter("judgeID");
	    	Contest contest = userDAO.getContestbyID(contestID);
	    	List<Contestant> contestantList = userDAO.getContestantsJudge(contestID, judgeID);
	    	request.setAttribute("contestName", contest.getTitle());
	    	request.setAttribute("beginTime", contest.getBeginTime());
	    	request.setAttribute("endTime", contest.getEndTime());
	    	request.setAttribute("requirements", contest.getRequirementList());
	    	request.setAttribute("userID", judgeID);
	    	request.setAttribute("contestID", contest.getContestID());
	    	request.setAttribute("contestantList", contestantList);
	    	RequestDispatcher rd = request.getRequestDispatcher("contestJudge.jsp");
	    	rd.forward(request, response);
	    }
	    

	    protected void contestDetailsSponsor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String contestID = request.getParameter("id");
	    	String sponsorID = request.getParameter("userID");
	    	System.out.println(contestID);
	    	Contest contest = userDAO.getContestbyID(contestID);
	    	List<Judge> judges = userDAO.getJudgesContest(contestID);
	    	String complete = userDAO.checkContestComplete(contestID);
	    	request.setAttribute("contestName", contest.getTitle());
	    	request.setAttribute("beginTime", contest.getBeginTime());
	    	request.setAttribute("endTime", contest.getEndTime());
	    	request.setAttribute("requirements", contest.getRequirementList());
	    	request.setAttribute("userID", sponsorID);
	    	request.setAttribute("contestID", contest.getContestID());
	    	request.setAttribute("completed", complete);
	    	request.setAttribute("status", contest.getStatus());
	    	request.setAttribute("judgeList", judges);
	    	RequestDispatcher rd = request.getRequestDispatcher("contestSponsor.jsp");
	    	rd.forward(request, response);
	    }
	    
	    protected void sponsorReturn(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException, SQLException{
	    	String returnContextCode = request.getParameter("tips");
	    	String returnContext = "";
	    	String sponsorID = request.getParameter("sponsorID");
	    	if (returnContextCode.equals("s1")){
	    		returnContext = "Sucessfully distibuted Rewards to judges and contestants!";
	    		
	    	}
	    	else if (returnContextCode.equals("s2")) {
	    		returnContext = "Sucessfully distibuted Rewards to judges and contestants!";
	    	}
	    	else if (returnContextCode.equals("s2")) {
	    		returnContext = "Sucessfully Reviewed!";
	    	}
	    	request.setAttribute("tips", returnContext);
	    	request.setAttribute("sponsorID", sponsorID);
	    	RequestDispatcher rd = request.getRequestDispatcher("sponsorReturn.jsp");
	    	rd.forward(request, response);
	    }
	    
	    protected void contestantReturn(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException, SQLException{
	    	
	    	String returnContextCode = request.getParameter("tips");
	    	String returnContext = "";
	    	String contestantID = request.getParameter("contestantID");
	    	if (returnContextCode.equals("s1")) {
	    		returnContext = "Successfully submitted!";
	    	}
	    	
	    	request.setAttribute("tips", returnContext);
	    	request.setAttribute("sponsorID", contestantID);
	    	RequestDispatcher rd = request.getRequestDispatcher("contestantReturn.jsp");
	    	rd.forward(request, response);
	    }
	    
	    protected void judgeReturn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	String returnContextCode = request.getParameter("tips");
	    	String returnContext = "";
	    	String judgeID = request.getParameter("judgeID");
	    	if (returnContextCode.equals("s1")) {
	    		returnContext = "Successfully sumbitted the score!";
	    	}
	    	request.setAttribute("tips", returnContext);
	    	request.setAttribute("sponsorID", judgeID);
	    	RequestDispatcher rd = request.getRequestDispatcher("judgeReturn.jsp");
	    	rd.forward(request, response);
	    }

	    protected void distributeSubmission(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
	    	String sponsorID = request.getParameter("sponsorID");
	    	String contestID = request.getParameter("id");
	    	Contest contest = userDAO.getContestbyID(contestID);
	    	userDAO.assignSubmissionsToJudges(contest);
	    	response.sendRedirect("/sponsorReturn?tips=s1&sponsorID="+sponsorID);
	    }
	    
	    
	    protected void distributeRewards(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String sponsorID = request.getParameter("sponsorID");
	    	String contestID = request.getParameter("id");
	    	Contest contest = userDAO.getContestbyID(contestID);
	    	userDAO.distributedRewardsToJudges(contest);
	    	userDAO.distributedRewardsToContestants(contest);
	    	response.sendRedirect("/sponsorReturn?tips=s2&sponsorID="+sponsorID);
//	    	sponsorReturn(request, response, "Sucessfully distibuted Rewards to judges and contestants", sponsorID);
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
	    	request.setAttribute("status", contest.getStatus());
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
	    	
	    	String requirment = request.getParameter("requirement");
	    	
	    	Contest contest = new Contest(walletAddress, sponsor_id, contestName, begin_time, end_time, "created", requirment, fundingContest);
	    	
	    	userDAO.createContest(contest, selectJudges);
	    	userDAO.insertJudgeby(contest, selectJudges);
	    	
	    	 List<Contest> contest_list = userDAO.sponsor_contests(sponsor_id);
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
	    
	    protected void sponsorIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String sponsor_id = request.getParameter("id");
	    	List<Contest> contest_list = userDAO.sponsor_contests(sponsor_id);
	    	
   		 	request.setAttribute("userID", sponsor_id);
   		 	request.setAttribute("contestList", contest_list);
	   	 	request.getRequestDispatcher("sponsorIndex.jsp").forward(request, response);
	    }
	    
	    protected void contestantIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String contestant_id = request.getParameter("id");
	    	 Contestant contestant = userDAO.getContestantByID(contestant_id);
    		 request.setAttribute("walletAddress", contestant.id);
    		 request.setAttribute("balance", contestant.reward_balance);
    		 List<Contest> contests = userDAO.getContestsParticipated(contestant.id);
    		 request.setAttribute("contests", contests);
    		 
    		 request.getRequestDispatcher("contestantIndex.jsp").forward(request, response);
	    }
	    
	    protected void judgeIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String judgeID = request.getParameter("id");
	    	Judge judge = userDAO.getjudgeByID(judgeID);
   		 	List<Contest> contests = userDAO.getContestsJudge(judgeID);
   		 	List<Review> reviews = userDAO.getReviewOneJudge(judgeID);
   		 	request.setAttribute("walletAddress", judge.getId());
   		 	request.setAttribute("balance", judge.getRewardBalance());
   		 	request.setAttribute("reviewScore", judge.getAvgScore());
   		 	request.setAttribute("contests", contests);
   		 	request.setAttribute("reviews", reviews);
	   	 	request.getRequestDispatcher("judgeIndex.jsp").forward(request, response);
	    }
	    
	    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	 String userID = request.getParameter("userID");
	    	 String password = request.getParameter("password");
	    	 String role = request.getParameter("role");
	    	 
	    	 if (userID.equals("root") && password.equals("pass1234")) {
//	    		 request.setAttribute("resStr","Logging in successfully as the root user");
		   	 	 request.getRequestDispatcher("newRoot.jsp").forward(request, response);
	    	 }
	    	 else if (role.equals("sponsor") && userDAO.isValid(userID, password, role)) {
	    		 //request.setAttribute("resStr","Logging in successfully as the sponsor user");
	    		 
	    		 String sponsor_id = userDAO.getSponsorIDByLoginID(userID);
	    		 List<Contest> contest_list = userDAO.sponsor_contests(sponsor_id);
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
//	    		 request.setAttribute("resStr","Logging in successfully as the judge user");
	    		 String judgeID = userDAO.getJudgeIDByLoginID(userID);
//	    		 Judge judge = userDAO.getjudgeByLoginID(userID);
//	    		 List<Contest> contests = userDAO.getContestsJudge(judgeID);
//	    		 request.setAttribute("walletAddress", judge.getId());
//	    		 System.out.println(judge.getRewardBalance());
//	    		 request.setAttribute("balance", judge.getRewardBalance());
//	    		 request.setAttribute("contests", contests);
	    		 response.sendRedirect("judgeIndex?id=" + judgeID);
//		   	 	 request.getRequestDispatcher("judgeIndex.jsp").forward(request, response);
	    		 
	    	 }
	    	 else {
	    		 request.setAttribute("resStr","Not able to log you in. please check if you putted role, wallet addreess or password right");
		   	 	 request.getRequestDispatcher("tempRes.jsp").forward(request, response);
	    	 }
	    	
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
	        
	        
	    
	        
	        
	        
	    


