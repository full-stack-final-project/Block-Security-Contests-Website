import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/userDAO")
public class userDAO 
{
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public userDAO(){}
	
	/** 
	 * @see HttpServlet#HttpServlet()
     */
    protected void connectFunc() throws SQLException {
    	//uses default connection to the database
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/contestdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=john");
            System.out.println(connect);
        }
    }
    
    public boolean database_login(String email, String password) throws SQLException{
    	try {
    		connectFunc("root","root");
    		String sql = "select * from user where email = ?";
    		preparedStatement = connect.prepareStatement(sql);
    		preparedStatement.setString(1, email);
    		ResultSet rs = preparedStatement.executeQuery();
    		return rs.next();
    	}
    	catch(SQLException e) {
    		System.out.println("failed login");
    		return false;
    	}
    }
	//connect to the database 
    public void connectFunc(String username, String password) throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/contestdb?"
  			          + "useSSL=false&user=" + username + "&password=" + password);
            System.out.println(connect);
        }
    }
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
//    public List<user> listAllUsers() throws SQLException {
//        List<user> listUser = new ArrayList<user>();        
//        String sql = "SELECT * FROM User";      
//        connect_func();      
//        statement = (Statement) connect.createStatement();
//        ResultSet resultSet = statement.executeQuery(sql);
//         
//        while (resultSet.next()) {
//            String email = resultSet.getString("email");
//            String firstName = resultSet.getString("firstName");
//            String lastName = resultSet.getString("lastName");
//            String password = resultSet.getString("password");
//            String birthday = resultSet.getString("birthday");
//            String adress_street_num = resultSet.getString("adress_street_num"); 
//            String adress_street = resultSet.getString("adress_street"); 
//            String adress_city = resultSet.getString("adress_city"); 
//            String adress_state = resultSet.getString("adress_state"); 
//            String adress_zip_code = resultSet.getString("adress_zip_code"); 
//            int cash_bal = resultSet.getInt("cash_bal");
//            int PPS_bal = resultSet.getInt("PPS_bal");
//
//             
//            user users = new user(email,firstName, lastName, password, birthday, adress_street_num,  adress_street,  adress_city,  adress_state,  adress_zip_code, cash_bal,PPS_bal);
//            listUser.add(users);
//        }        
//        resultSet.close();
//        disconnect();        
//        return listUser;
//    }
    
    public List<Judge> getJudgesContest(String contestID) throws SQLException {
    	List<Judge> judgesForContest = new ArrayList<Judge>();
    	
    	String sql = "Select j.* from judge j\r\n"
    			+ "	join     			\r\n"
    			+ "(select * from judgeby jb where contest_id = \""+ contestID + "\"\r\n"
    			+ "	) as jb_contest on j.judge_id = jb_contest.judge_id;";
    	
    	connectFunc();
    	
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	while (resultSet.next()) {
    		String judgeID = resultSet.getString("judge_id");
    		String loginID = resultSet.getString("login_id");
    		float balance = resultSet.getFloat("reward_balance");
    		float avg_score = resultSet.getFloat("avg_score");
    		int review_num = resultSet.getInt("review_number");
    		String password = resultSet.getString("password");
    		
    		Judge judge = new Judge(judgeID, loginID, password, balance, avg_score, review_num);
    		judgesForContest.add(judge);
    	}
    	
    	return judgesForContest;
    }
    
    public List<Contestant> findYs(String contestantLoginID) throws SQLException {
    	
    	Contestant contestant = getContestantByLoginID(contestantLoginID);
    	
    	List<Contestant> Ys = new ArrayList<Contestant>();
    	
    	String sql = "Select c2.*\r\n"
    			+ "from participate pp1\r\n"
    			+ "join participate pp2 on pp1.contest_id = pp2.contest_id and pp1.contestant_id <> pp2.contestant_id\r\n"
    			+ "join contestant c1 on c1.contestant_id = pp1.contestant_id and c1.contestant_id = '" + contestant.id + "'\r\n"
    			+ "join contestant c2 on pp2.contestant_id = c2.contestant_id\r\n"
    			+ "group by pp2.contestant_id\r\n"
    			+ "having count(*) = (select count(*) from participate p1 where p1.contestant_id = '" + contestant.id + "');";
    	
    	connectFunc();
    	
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	while (resultSet.next()) {
    		String contestantID = resultSet.getString("contestant_id");
    		String loginID = resultSet.getString("login_id");
    		float balance = resultSet.getFloat("reward_balance");
    		String password = resultSet.getString("password");
    		contestant = new Contestant(contestantID, loginID, balance, password);
    		Ys.add(contestant);
    	}
    	
    	return Ys;
    }
    
    public List<Contest> findToughContests() throws SQLException {
    	List<Contest> toughContests = new ArrayList<Contest>();
    	
    	String sql = "select * from contest\r\n"
    			+ "where contest_id in (\r\n"
    			+ "select contest.contest_id from participate \r\n"
    			+ "right join contest \r\n"
    			+ "on participate.contest_id = contest.contest_id\r\n"
    			+ "group by contest.contest_id\r\n"
    			+ "having count(*) < 10)\r\n"
    			+ "and contest.status = 'past';";

    	connectFunc();
    	
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	while (resultSet.next()) {
    		String contestID = resultSet.getString("contest_id");
    		String sponsorID = resultSet.getString("sponsor_id");
    		String title = resultSet.getString("title");
    		LocalDateTime beginTime = resultSet.getObject("begin_time", LocalDateTime.class);
    		LocalDateTime endTime = resultSet.getObject("end_time", LocalDateTime.class);
    		String status = resultSet.getString("status");
    		String requirementList = resultSet.getString("requirement_list");
    		long sponsorFee = resultSet.getLong("sponsor_fee");
    		Contest toughContest = new Contest(contestID, sponsorID, title, beginTime, endTime, status, requirementList, sponsorFee);
    		toughContests.add(toughContest);
    	}
    	
    	return toughContests;
    }
    
    public List<Judge> findBusyJudges() throws SQLException {
    	List<Judge> busyJudges = new ArrayList<Judge>();
    	String sql = "select judge.judge_id \r\n"
    			+ "from judge \r\n"
    			+ "join judgeby on judge.judge_id = judgeby.judge_id\r\n"
    			+ "join contest on judgeby.contest_id = contest.contest_id and contest.status = \"past\"\r\n"
    			+ "group by judge.judge_id\r\n"
    			+ "having count(*) = (select count(*) from contest where status = \"past\");";
    	connectFunc();
    	
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	
    	while (resultSet.next()) {
    		String judgeID = resultSet.getString("judge_id");
    		String loginID = resultSet.getString("login_id");
    		float balance = resultSet.getFloat("reward_balance");
    		float avg_score = resultSet.getFloat("avg_score");
    		int review_num = resultSet.getInt("review_number");
    		String password = resultSet.getString("password");
    		
    		Judge judge = new Judge(judgeID, loginID, password, balance, avg_score, review_num);
    		busyJudges.add(judge);
    	}
    	return busyJudges;
    }
    
    public List<Contestant> findSleepyContestants() throws SQLException {
    	List<Contestant> sleepyContestants = new ArrayList<Contestant>();
    	
    	String sql = "select * from contestant where contestant_id not in(\r\n"
    			+ "select distinct(contestant_id) from \r\n"
    			+ "participate\r\n"
    			+ ");";
    	connectFunc();
    	
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	while (resultSet.next()) {
    		String contestantID = resultSet.getString("contestant_id");
    		String loginID = resultSet.getString("login_id");
    		float balance = resultSet.getFloat("reward_balance");
    		String password = resultSet.getString("password");
    		Contestant contestant = new Contestant(contestantID, loginID, balance, password);
    		sleepyContestants.add(contestant);
    	}
    	
    	return sleepyContestants;
    }
    
    
    public List<String> listJudgesName() throws SQLException{
    	List<String> judgesName = new ArrayList<String>();
    	String sql = "Select login_id, avg_score from judge;";
    	connectFunc();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	while (resultSet.next()) {
    		String judgeID = resultSet.getString("login_id");
    		String judgeScore = resultSet.getString("avg_score");
    		String judgeName = judgeID + " (" + judgeScore + "/10)";
    		judgesName.add(judgeName);
    	}
    	
    	return judgesName;
    	
    }
    
    public List<Contest> getOpenContests() throws SQLException{
    	List<Contest> openContests = new ArrayList<Contest>();
    	String sql = "Select * from contest where status = 'opened';";
    	connectFunc();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	Contest openedContest;
    	
    	while (resultSet.next()) {
    		String contestID = resultSet.getString("contest_id");
    		String sponsorID = resultSet.getString("sponsor_id");
    		String title = resultSet.getString("title");
    		LocalDateTime beginTime = resultSet.getObject("begin_time", LocalDateTime.class);
    		LocalDateTime endTime = resultSet.getObject("end_time", LocalDateTime.class);
    		String status = resultSet.getString("status");
    		String requirementList = resultSet.getString("requirement_list");
    		long sponsorFee = resultSet.getLong("sponsor_fee");
    		openedContest = new Contest(contestID, sponsorID, title, beginTime, endTime, status, requirementList, sponsorFee);
    		openContests.add(openedContest);
    	}
    	
    	for (Contest c : openContests) {
    		System.out.println(c.begin_time);
    	}
    	
    	return openContests;
    }
    
    public List<Contestant> rankContestants() throws SQLException{
    	List<Contestant> contestant_list = new ArrayList<Contestant>();
    	String sql = "select * from contestant order by reward_balance desc;";
    	connectFunc();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	while (resultSet.next()) {
    		String contestant_id = resultSet.getString("contestant_id");
    		String login_id = resultSet.getString("login_id");
    		float reward_balance = resultSet.getFloat("reward_balance");
    		String password = resultSet.getString("password");
    		Contestant contestant = new Contestant(contestant_id, login_id, reward_balance, password);
    		contestant_list.add(contestant);
    	}
    	return contestant_list;
    }
    
    public Contest getContestbyID(String contestID) throws SQLException {
    	String sql = "Select * from contest where contest_id = '" + contestID + "';";
    	connectFunc();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	resultSet.next();
    	
    	String contest_id = resultSet.getString("contest_id");
    	String sponsor_id = resultSet.getString("sponsor_id");
    	String title = resultSet.getString("title");
    	LocalDateTime begin_time = resultSet.getObject("begin_time", LocalDateTime.class);
    	LocalDateTime end_time = resultSet.getObject("end_time", LocalDateTime.class);
    	String status = resultSet.getString("status");
    	String requirement_list = resultSet.getString("requirement_list");
    	long sponsor_fee = resultSet.getLong("sponsor_fee");
    	Contest contest = new Contest(contest_id, sponsor_id, title, begin_time, end_time, status, requirement_list, sponsor_fee); 
    	
    	return contest;
    }
    
    public boolean insertSubmission(String contestID, String contestantID, String submission) throws SQLException {
    	String sql = "Insert into participate (contestant_id, contest_id, submission) " 
    			+ "VALUE ( '" + contestantID + "', '" + contestID + "', '" + submission + "') " 
    			+ "ON DUPLICATE KEY UPDATE "
    			+ "submission = '"+ submission + "';";
    	System.out.println(sql);
    	connectFunc();
    	statement = (Statement) connect.createStatement();
    	boolean resultSet = statement.executeUpdate(sql) > 0;
    	return resultSet;
    }
    
    public boolean insertReview(String judgeID, String sponsorID, String review, int review_score) throws SQLException {
    	String sql = "Insert into review (judge_id, sponsor_id, comment, review_score) " 
    			+ "VALUE ( '" + judgeID + "', '" + sponsorID + "', '" + review + "', ' " + review_score +" ') " 
    			+ "ON DUPLICATE KEY UPDATE "
    			+ "review = '"+ review + "', review_score = '" + review_score + "';";
    	System.out.println(sql);
    	connectFunc();
    	statement = (Statement) connect.createStatement();
    	boolean resultSet = statement.executeUpdate(sql) > 0;
    	return resultSet;
    }
    
    public List<Object> getReview(String judgeID, String sponsorID) throws SQLException {
    	
    	List<Object> review = new ArrayList<Object>();
    	
    	String sql = "Select exists ("
    			+ "select * from review where judge_id = ' " + judgeID +"' and "
    					+ "sponsor_id = '" + sponsorID + "');";
    	
    	connectFunc();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	
    	if (resultSet.next()) {
    		String comment = resultSet.getString("comment");
    		int score = resultSet.getInt("review_score");
    		review.add(comment);
    		review.add(score);
    	}
    	
    	return review;
    	
    }
    
    public Sponsor getSponsorbyID(String sponsor_ID) throws SQLException {
    	connectFunc();
    	String sql_find_sponsor = "select * from sponsor where sponsor_id = ?";
    	preparedStatement = connect.prepareStatement(sql_find_sponsor);
    	preparedStatement.setString(1, sponsor_ID);
    	ResultSet resultSet = preparedStatement.executeQuery();
    	resultSet.next();
		String sponsor_id = resultSet.getString("sponsor_id");
		String login_id = resultSet.getString("login_id");
		String company_name = resultSet.getString("company_name");
		String email = resultSet.getString("email");
		String address = resultSet.getString("address");
		String password = resultSet.getString("password");
		long balance = resultSet.getLong("address");
		Sponsor sponsor = new Sponsor(sponsor_id, login_id, company_name, email, address, password, balance);
		return sponsor;
    }

    
    public List<Contest> sponsor_contests(String sponsor_ID) throws SQLException{
    	List<Contest> list = new ArrayList<Contest>();
    	String sql_find_contests = "select * from contest\r\n"
    			+ "where sponsor_id = ?\r\n"
    			+ "order by begin_time desc;";
    	preparedStatement = connect.prepareStatement(sql_find_contests);
    	preparedStatement.setString(1, sponsor_ID);
    	resultSet = preparedStatement.executeQuery();
//    	if (!resultSet.next()) {
//    		System.out.println("This sponsor has not sponsored any contests yet.");
//    	}else {
    		while (resultSet.next()) {
    			String contest_id = resultSet.getString("contest_id");
    			String sponsor_id = resultSet.getString("sponsor_id");
    			String title = resultSet.getString("title");
    			LocalDateTime begin_time = resultSet.getObject("begin_time", LocalDateTime.class);
        		LocalDateTime end_time = resultSet.getObject("end_time", LocalDateTime.class);
        		String status = resultSet.getString("status");
        		String requirement_list = resultSet.getString("requirement_list");
        		long sponsor_fee = resultSet.getLong("sponsor_fee");
        		Contest sponsored_contest = new Contest(contest_id, sponsor_id, title, begin_time, end_time, status, requirement_list, sponsor_fee);
        		list.add(sponsored_contest);
    		}
//    	}
    	return list;
    }
    
    public List<Sponsor> listBigSponsors() throws SQLException{
    	List<Sponsor> bigSponsors = new ArrayList<Sponsor>();
    	String sql = "select * from sponsor where sponsor_id in (\r\n"
    			+ "select sponsor_id as selected_sponsor\r\n"
    			+ "from contest group by sponsor_id\r\n"
    			+ "having count(sponsor_id)=(\r\n"
    			+ "	select max(count_num)\r\n"
    			+ "	from (\r\n"
    			+ "		select sponsor_id, count(sponsor_id) count_num\r\n"
    			+ "		from contest\r\n"
    			+ "		group by sponsor_id) groupby_sponsor \r\n"
    			+ ") ) ;";
    	connectFunc();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
 
    	while (resultSet.next()) {
    		String sponsor_id = resultSet.getString("sponsor_id");
    		String login_id = resultSet.getString("login_id");
    		String company_name = resultSet.getString("company_name");
    		String email = resultSet.getString("email");
    		String address = resultSet.getString("address");
    		String password = resultSet.getString("password");
    		long balance = resultSet.getLong("balance"); 
    		Sponsor sponsor = new Sponsor(sponsor_id, login_id, company_name, email, address, password, balance);
    		bigSponsors.add(sponsor);
    	}        
	    resultSet.close();
	    disconnect();        
	    return bigSponsors;
    }
    
    public String getJudgeIDByLoginID(String loginID) throws SQLException {
    	String sql = "select judge_id from judge where login_id = ?";
    	String judgeID = "";
    	connectFunc();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1, loginID);
    	ResultSet resultSet = preparedStatement.executeQuery();
    	
    	while (resultSet.next()) {
    		judgeID = resultSet.getString("judge_id");
    	}
    	
    	return judgeID;
    }
    
    protected String getSponsorIDByLoginID(String loginID) throws SQLException {
    	String sql = "select sponsor_id from sponsor where login_id = ?";
    	String sponsorID = "";
    	connectFunc();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1, loginID);
    	ResultSet resultSet = preparedStatement.executeQuery();
    	
    	while (resultSet.next()) {
    		sponsorID = resultSet.getString("sponsor_id");
    	}
    	
    	return sponsorID;
    }
    
    public Contestant getContestantByLoginID(String loginID) throws SQLException {
    	String sql = "select * from contestant where login_id = ?";
    	String contestantID = "";
    	connectFunc();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1, loginID);
    	ResultSet resultSet = preparedStatement.executeQuery();
    	Contestant contestant = new Contestant();
    	while (resultSet.next()) {
    		contestantID = resultSet.getString("contestant_id");
    		float balance = resultSet.getFloat("reward_balance");
    		String password = resultSet.getString("password");
    		contestant = new Contestant(contestantID, loginID, balance, password);
    	}
    	
    	return contestant;
    }
    
    public Judge getjudgeByLoginID(String loginID) throws SQLException {
    	String sql = "select * from judge where login_id = ?";
    	String judgeID = "";
    	connectFunc();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1, loginID);
    	ResultSet resultSet = preparedStatement.executeQuery();
    	Judge judge = new Judge();
    	while (resultSet.next()) {
    		judgeID = resultSet.getString("judge_id");
    		float balance = resultSet.getFloat("reward_balance");
    		float avg_score = resultSet.getFloat("avg_score");
    		int review_num = resultSet.getInt("review_number");
    		String password = resultSet.getString("password");
    		
    		judge = new Judge(judgeID, loginID, password, balance, avg_score, review_num);
    	}
    	
    	return judge;
    }
    
    public Judge getjudgeByID(String judgeID) throws SQLException {
    	String sql = "select * from judge where judge_id = '" + judgeID + "';";
    	connectFunc();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	Judge judge = new Judge();
    	while (resultSet.next()) {
    		String loginID = resultSet.getString("login_id");
    		float balance = resultSet.getFloat("reward_balance");
    		float avg_score = resultSet.getFloat("avg_score");
    		int review_num = resultSet.getInt("review_number");
    		String password = resultSet.getString("password");
    		judge = new Judge(judgeID, loginID, password, balance, avg_score, review_num);
    	}
    	return judge;
    }
    
    
    protected Contestant getContestantByID(String contestantID) throws SQLException {
    	String sql = "select * from contestant where contestant_id = ?";
    	
    	connectFunc();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1, contestantID);
    	ResultSet resultSet = preparedStatement.executeQuery();
    	Contestant contestant = new Contestant();
    	while (resultSet.next()) {
    		String loginID = resultSet.getString("login_id");
    		float balance = resultSet.getFloat("reward_balance");
    		String password = resultSet.getString("password");
    		contestant = new Contestant(contestantID, loginID, balance, password);
    	}
    	
    	return contestant;
    }

    public List<Contestant> getContestantsJudge(String contestID, String judgeID) throws SQLException {
    	List<Contestant> contestantLoginIDs = new ArrayList<Contestant>();
    	String sql = "select contestant.* "
    			+ "from"
    			+ "(select contestant_id from grade where judge_id = ? and contest_id = ?) t1 "
    			+ "left join contestant "
    			+ "on contestant.contestant_id = t1.contestant_id;";
    	connectFunc();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1, judgeID);
    	preparedStatement.setString(2, contestID);
    	
    	ResultSet resultSet = preparedStatement.executeQuery();
    	Contestant contestant = new Contestant();
    	while (resultSet.next()) {
    		String contestantID = resultSet.getString("contestant_id");
    		String loginID = resultSet.getString("login_id");
    		float balance = resultSet.getFloat("reward_balance");
    		String password = resultSet.getString("password");
    		contestant = new Contestant(contestantID, loginID, balance, password);
    		contestantLoginIDs.add(contestant);
    	}
    	System.out.println(contestantLoginIDs.get(0).getId());
    	return contestantLoginIDs;
    }
    
    public void updateGrade(String contestID, String contestantID, int grade) throws SQLException  {
    	String sql = "Update grade set score = '" + grade + "', complete = '1' \r\n"
    			+ "where contest_id = '" + contestID + "' and contestant_id = '" + contestantID + "';";
    	connectFunc();
    	
    	statement = (Statement) connect.createStatement();
    	statement.executeUpdate(sql);
    	System.out.println(sql);
//    	boolean resultSet = preparedStatement.executeUpdate() > 0;
//    	return resultSet;
    }
    
    public String getSubmission(String contestID, String contestantID) throws SQLException {
    	String submission;
    	
    	String sql = "select submission from participate where contest_id = ? and contestant_id = ?;";
    	
    	connectFunc();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1, contestID);
    	preparedStatement.setString(2, contestantID);
    	ResultSet resultSet = preparedStatement.executeQuery();
    	resultSet.next();
    	submission = resultSet.getString("submission");
    	return submission;
    }
    
    public List<Contest> getContestsJudge(String judgeID) throws SQLException {
    	String sql = "select contest.* "
    			+ "from "
    			+ "(select contest_id from judgeby where judge_id = ?) t1 "
    			+ "left join contest "
    			+ "on contest.contest_id = t1.contest_id;";
    	connectFunc();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1, judgeID);
    	ResultSet resultSet = preparedStatement.executeQuery();
    	List<Contest> contests = new ArrayList<Contest>();
    	Contest contestObj;
    	while (resultSet.next()) {
    		String contestID = resultSet.getString("contest_id");
    		String sponsorID = resultSet.getString("sponsor_id");
    		String title = resultSet.getString("title");
    		LocalDateTime beginTime = resultSet.getObject("begin_time", LocalDateTime.class);
    		LocalDateTime endTime = resultSet.getObject("end_time", LocalDateTime.class);
    		String status = resultSet.getString("status");
    		String requirementList = resultSet.getString("requirement_list");
    		long sponsorFee = resultSet.getLong("sponsor_fee");
    		contestObj = new Contest(contestID, sponsorID, title, beginTime, endTime, status, requirementList, sponsorFee); 
    		System.out.println(contestObj.getTitle());
    		contests.add(contestObj);
    	}
    	
    	return contests; 
    	
    	
    }

    protected List<Contest> getContestsParticipated(String contestantID) throws SQLException {
    	String sql = "select contest.*\r\n"
    			+ "from (\r\n"
    			+ "	(select contest_id from participate where contestant_id = ?) t1\r\n"
    			+ "    left join contest\r\n"
    			+ "    on contest.contest_id = t1.contest_id\r\n"
    			+ ");";
    	connectFunc();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1, contestantID);
    	ResultSet resultSet = preparedStatement.executeQuery();
    	List<Contest> contests = new ArrayList<Contest>();
    	Contest contestObj;
    	while (resultSet.next()) {
    		String contestID = resultSet.getString("contest_id");
    		String sponsorID = resultSet.getString("sponsor_id");
    		String title = resultSet.getString("title");
    		LocalDateTime beginTime = resultSet.getObject("begin_time", LocalDateTime.class);
    		LocalDateTime endTime = resultSet.getObject("end_time", LocalDateTime.class);
    		String status = resultSet.getString("status");
    		String requirementList = resultSet.getString("requirement_list");
    		long sponsorFee = resultSet.getLong("sponsor_fee");
    		contestObj = new Contest(contestID, sponsorID, title, beginTime, endTime, status, requirementList, sponsorFee); 
    		System.out.println(contestObj.getTitle());
    		contests.add(contestObj);
    	}
    	
    	return contests;
    			
    }
    
    public void createContest(Contest contest, String[] selectJudges) throws SQLException {
    	String insertIntoContest = "insert into contest (contest_id, sponsor_id, title, begin_time, end_time, status, requirement_list, sponsor_fee) "
    			+ "values (?, ? ,?, ?, ?, ?, ?, ?);" ;
    	connectFunc();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(insertIntoContest);
    	preparedStatement.setString(1, contest.getContestID());
    	preparedStatement.setString(2, contest.getSponsorID());
    	preparedStatement.setString(3, contest.getTitle());
    	preparedStatement.setObject(4, contest.getBeginTime());
    	preparedStatement.setObject(5, contest.getEndTime());
    	preparedStatement.setString(6, contest.getStatus());
    	preparedStatement.setString(7, contest.getRequirementList());
    	preparedStatement.setLong(8, contest.getSponsorFee());
    	preparedStatement.executeUpdate();
    	preparedStatement.close(); 
    	
    	String getSponsorBalance = "select balance from sponsor where sponsor_id = '" + contest.getSponsorID() + "';";
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(getSponsorBalance);
    	resultSet.next();
    	long sponsorBalance = resultSet.getLong("balance");
    	long updatedBalance = sponsorBalance - contest.getSponsorFee();
    	
    	String updateSponsorInfo = "update sponsor set balance = '" + updatedBalance + "' where sponsor_id = '" + contest.getSponsorID() + "';";
    	statement.executeUpdate(updateSponsorInfo);
    }
    
    public void insertJudgeby(Contest contest, String[] selectJudges) throws SQLException {
        for (String judge_userID : selectJudges) {
        	String insert_judgeby_sql = "insert into judgeby (contest_id, judge_id) values";
        	String[] splited = judge_userID.split("\\s+");
        	String judge_id = getJudgeIDByLoginID(splited[0]);
        	
        	insert_judgeby_sql += " (" 
        	+ "'" + contest.contest_id +  "'" + ", " + "'" + judge_id + "'"+ ");";
        	System.out.println(insert_judgeby_sql);
        	statement = (Statement) connect.createStatement();
        	statement.executeUpdate(insert_judgeby_sql);
        }
    }
    
    // Assign submissions to judges
    public void assignSubmissionsToJudges(Contest contest) throws SQLException{
    	connectFunc();
    	String getJudgesList = "select judge_id from judgeby where contest_id = '"+ contest.getContestID() + "';"; 
    	String getJudgesCount = "select count(*) as judgesCount from judgeby where contest_id = '"+ contest.getContestID() + "';"; 
    	String getContestantsList = "select contestant_id from participate where contest_id = '"+ contest.getContestID() + "';"; 
    	String getContestantsCount = "select count(*) as contestantsCount from participate where contest_id = '"+ contest.getContestID() + "';"; 
    	
    	// get judges count and contestants count.
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(getJudgesCount);
    	resultSet.next();
    	int judgesCount = resultSet.getInt("judgesCount");
    	resultSet = statement.executeQuery(getContestantsCount);
    	resultSet.next();
    	int contestantsCount = resultSet.getInt("contestantsCount");
    	
    	// get judges ID and contestants ID
    	resultSet = statement.executeQuery(getJudgesList);
    	String[] judgesID = new String[judgesCount];
    	int curIndex = 0;
    	while (resultSet.next()) {
    		judgesID[curIndex] = resultSet.getString("judge_id");
    		curIndex += 1;
    	}
    	
    	resultSet = statement.executeQuery(getContestantsList);
    	String[] contestantsID = new String[contestantsCount];
    	curIndex = 0;
    	while (resultSet.next()) {
    		contestantsID[curIndex] = resultSet.getString("contestant_id");
    		curIndex += 1;
    	}
    	
    	// assign submissions
    	String insertIntoGrade = "INSERT INTO grade (contest_id, contestant_id, judge_id) VALUES (?, ?, ?);";
    	preparedStatement = (PreparedStatement) connect.prepareStatement(insertIntoGrade);
    	for (int i = 0; i < contestantsCount; i ++) {
    		preparedStatement.setString(1, contest.getContestID());
    		preparedStatement.setString(2, contestantsID[i]);
    		preparedStatement.setString(3,  judgesID[i % judgesCount]);
    		preparedStatement.executeUpdate();
    	}
    	preparedStatement.close();
    	System.out.println("Assigned submissions to judges successfully.");
    	
    }
    
    // Distributed rewards.
    public void distributeContestRewards(Contest contest) throws SQLException{
    	connectFunc();
    	distributedRewardsToJudges(contest);
    	distributedRewardsToContestants(contest);
    	String updateStatus = "Update contest set status = 'past' where contest_id = '" + contest.getContestID() + "';";
    	statement = (Statement) connect.createStatement();
    	statement.executeQuery(updateStatus);
    }
    
    
    protected void distributedRewardsToJudges(Contest contest) throws SQLException {
    	connectFunc();
    	String getJudgesCount = "select count(*) as judgeCount from judgeby where contest_id = '" + contest.getContestID() + "';"; 
    	String getJudgesInfo = "select judge.* from\r\n"
    			+ "((select judge_id from judgeby where contest_id = '" + contest.getContestID() + "') filtered_judges\r\n"
    			+ "join judge \r\n"
    			+ "on filtered_judges.judge_id = judge.judge_id);";
    	long sponsor_fee = contest.getSponsorFee();
    	double judgeTotalRewards = sponsor_fee * 0.2;
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(getJudgesCount);
    	resultSet.next();
    	int judgeCount = resultSet.getInt("judgeCount");
    	float perJudgeReward = (float)(judgeTotalRewards / judgeCount);
    	
    	String[] insertJudgeByStatements = new String[judgeCount];
    	String[] updateJudgeStatements = new String[judgeCount];
    	int curIndex = 0;
    	
    	resultSet = statement.executeQuery(getJudgesInfo);
    	String curJudgeID;
    	float curRewardBalance;
    	while (resultSet.next()) {
    		curJudgeID = resultSet.getString("judge_id");
    		curRewardBalance = resultSet.getFloat("reward_balance");
    		
    		insertJudgeByStatements[curIndex] = "UPDATE judgeby\r\n"
    				+ "SET judge_reward = '" + perJudgeReward + "'\r\n"
    				+ "WHERE contest_id = '" + contest.getContestID() + "' AND judge_id = '" + curJudgeID + "';";
    		updateJudgeStatements[curIndex] = "Update judge set reward_balance = '" + (curRewardBalance + perJudgeReward) + "'\r\n"
    				+ "where judge_id = '" + curJudgeID + "';";	
    		curIndex += 1;
    	}        
    	
    	
    	for (int i = 0; i < judgeCount; i++) {
    		statement.execute(insertJudgeByStatements[i]);
    		statement.execute(updateJudgeStatements[i]);
    	}	
    	
    	System.out.println("Assigned");
    	statement.close();
    }
    
    protected void distributedRewardsToContestants(Contest contest) throws SQLException {
    	connectFunc();
    	String getCountAndScores = "select count(*) as contestantsCount, sum(score) as totalScore from grade where contest_id = '" + contest.getContestID() + "';"; 
    	String getContestantsInfo = "select contestant.*, filtered_contestants.score as score from "
    			+ "((select contestant_id, score from grade where contest_id = '" + contest.getContestID() + "') filtered_contestants\r\n"
    			+ "join contestant \r\n"
    			+ "on filtered_contestants.contestant_id = contestant.contestant_id);";
    	long sponsor_fee = contest.getSponsorFee();
    	float judgeTotalRewards = (float)(sponsor_fee * 0.8);
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(getCountAndScores);
    	resultSet.next();
    	int contestantsCount = resultSet.getInt("contestantsCount");
    	int contestTotalScore = resultSet.getInt("totalScore");
    	
    	String[] insertParticipateStatements = new String[contestantsCount];
    	String[] updateContestantStatements = new String[contestantsCount];
    	int curIndex = 0;
    	
    	resultSet = statement.executeQuery(getContestantsInfo);
    	String curContestantID;
    	float curRewardBalance, distributedReward;
    	int score;
    	while (resultSet.next()) {
    		curContestantID = resultSet.getString("contestant_id");
    		curRewardBalance = resultSet.getFloat("reward_balance");
    		score = resultSet.getInt("score");
    		distributedReward = judgeTotalRewards * score / contestTotalScore;
    		
    		insertParticipateStatements[curIndex] = "UPDATE participate\r\n"
    				+ "SET contestant_reward = '" + distributedReward + "'\r\n"
    				+ "WHERE contest_id = '" + contest.getContestID() + "' AND contestant_id = '" + curContestantID + "';";
    		updateContestantStatements[curIndex] = "Update contestant set reward_balance = '" + (curRewardBalance + distributedReward) + "'\r\n"
    				+ "where contestant_id = '" + curContestantID + "';";	
    		curIndex += 1;
    	}        
    	
    	
    	for (int i = 0; i < contestantsCount; i++) {
    		statement.execute(insertParticipateStatements[i]);
    		statement.execute(updateContestantStatements[i]);
    	}
    	statement.close();
    }
    
    // check if all grading for a contest are done or not
    public String checkContestComplete(String contestID) throws SQLException{
    	connectFunc();
    	String result;
    	String checkComplete = "select * from grade where contest_id = '" + contestID + "' and complete = 0;";
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(checkComplete);
    	if (resultSet.next()){
    		result = "false";
    	}else {
    		result = "true";
    	}
    	return result;
    }
    
    
    public void insert(User users, String role) throws SQLException {
    	connectFunc();         
		String sql = "insert into " + role + "(" + role + "_id, password) values (?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, users.getId());
		preparedStatement.setString(2, users.getPassword());
//			preparedStatement.setString(1, users.getEmail());
//			preparedStatement.setString(2, users.getFirstName());
//			preparedStatement.setString(3, users.getLastName());
//			preparedStatement.setString(4, users.getPassword());
//			preparedStatement.setString(5, users.getBirthday());
//			preparedStatement.setString(6, users.getAdress_street_num());		
//			preparedStatement.setString(7, users.getAdress_street());		
//			preparedStatement.setString(8, users.getAdress_city());		
//			preparedStatement.setString(9, users.getAdress_state());		
//			preparedStatement.setString(10, users.getAdress_zip_code());		
//			preparedStatement.setInt(11, users.getCash_bal());		
//			preparedStatement.setInt(12, users.getPPS_bal());		

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public boolean delete(String email) throws SQLException {
        String sql = "DELETE FROM User WHERE email = ?";        
        connectFunc();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowDeleted;     
    }
     
//    public boolean update(user users) throws SQLException {
//        String sql = "update User set firstName=?, lastName =?,password = ?,birthday=?,adress_street_num =?, adress_street=?,adress_city=?,adress_state=?,adress_zip_code=?, cash_bal=?, PPS_bal =? where email = ?";
//        connect_func();
//        
//        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
//        preparedStatement.setString(1, users.getEmail());
//		preparedStatement.setString(2, users.getFirstName());
//		preparedStatement.setString(3, users.getLastName());
//		preparedStatement.setString(4, users.getPassword());
//		preparedStatement.setString(5, users.getBirthday());
//		preparedStatement.setString(6, users.getAdress_street_num());		
//		preparedStatement.setString(7, users.getAdress_street());		
//		preparedStatement.setString(8, users.getAdress_city());		
//		preparedStatement.setString(9, users.getAdress_state());		
//		preparedStatement.setString(10, users.getAdress_zip_code());		
//		preparedStatement.setInt(11, users.getCash_bal());		
//		preparedStatement.setInt(12, users.getPPS_bal());
//         
//        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
//        preparedStatement.close();
//        return rowUpdated;     
//    }
//    
//    public user getUser(String email) throws SQLException {
//    	user user = null;
//        String sql = "SELECT * FROM User WHERE email = ?";
//         
//        connect_func();
//         
//        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
//        preparedStatement.setString(1, email);
//         
//        ResultSet resultSet = preparedStatement.executeQuery();
//         
//        if (resultSet.next()) {
//            String firstName = resultSet.getString("firstName");
//            String lastName = resultSet.getString("lastName");
//            String password = resultSet.getString("password");
//            String birthday = resultSet.getString("birthday");
//            String adress_street_num = resultSet.getString("adress_street_num"); 
//            String adress_street = resultSet.getString("adress_street"); 
//            String adress_city = resultSet.getString("adress_city"); 
//            String adress_state = resultSet.getString("adress_state"); 
//            String adress_zip_code = resultSet.getString("adress_zip_code"); 
//            int cash_bal = resultSet.getInt("cash_bal");
//            int PPS_bal = resultSet.getInt("PPS_bal");
//            user = new user(email, firstName, lastName, password, birthday, adress_street_num,  adress_street,  adress_city,  adress_state,  adress_zip_code,cash_bal,PPS_bal);
//        }
//         
//        resultSet.close();
//        statement.close();
//         
//        return user;
//    }
    public boolean checkWalletAddress(String wallet_address, String role) throws SQLException {
    	boolean checks = true;
    	String sql = "SELECT * From " + role + "  WHERE " +  role + "_id = ?";
    	System.out.print(sql);
    	connectFunc();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, wallet_address);
        System.out.print(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = false;
        }

        
        System.out.println(checks);
    	return checks;
    	
    }
    
    
    public boolean checkUserID(String userID, String role) throws SQLException {
    	boolean checks = true;
    	String sql = "SELECT * From " + role + "  WHERE " +  "login_id = ?";
    	System.out.print(sql);
    	connectFunc();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, userID);
        System.out.print(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = false;
        }
        System.out.println(checks);
    	return checks;
    	
    }

    public boolean checkPassword(String password) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM User WHERE password = ?";
    	connectFunc();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = true;
        }
        
        System.out.println(checks);
       	return checks;
    }
    
    
    
    public boolean isValid(String user_id, String password, String role) throws SQLException
    {
    	String sql = "SELECT * FROM " + role;
    	connectFunc();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	
    	resultSet.last();
    	
    	int setSize = resultSet.getRow();
    	resultSet.beforeFirst();
    	
    	for(int i = 0; i < setSize; i++)
    	{
    		resultSet.next();
    		if(resultSet.getString("login_id").equals(user_id) && resultSet.getString("password").equals(password)) {
    			return true;
    		}		
    	}
    	return false;
    }
    
    
    public void init() throws SQLException, FileNotFoundException, IOException{
    	connectFunc();
        statement =  (Statement) connect.createStatement();
        
        String[] INITIAL = {"drop database if exists contestdb; ",
					        "create database contestdb; ",
					        "use contestdb; ",
					        ("CREATE TABLE sponsor ( " +
					        	"sponsor_id varchar(42)," +
					        	"login_id varchar(10)," + 
					        	"unique (login_id), " +
					        	"company_name varchar(100)," +
					        	"email varchar(30)," +
					        	"address varchar(100), " +
					        	"password varchar(30)," +
					        	"balance bigint default 1000000, " +
					        	"PRIMARY KEY (sponsor_id)" +
					        	");"),
					        
					        ("CREATE TABLE contest ( " +
						        "contest_id varchar(42)," +
						        "sponsor_id varchar(42)," +
						        "title varchar(100)," +
						        "begin_time datetime," +
						        "end_time datetime," +
						        "status char(10) DEFAULT 'created'," +
						        "requirement_list text," +
						        "sponsor_fee bigint default 0, " +
						        "PRIMARY KEY (contest_id), " +
						        "FOREIGN KEY (sponsor_id) REFERENCES sponsor (sponsor_id)," +
						        "CHECK ((status in ('created', 'opened', 'closed', 'past')))" +
						        ");"),
					        
					        ("CREATE TABLE contestant ( " +
						        "contestant_id varchar(42)," +
						        "login_id varchar(10)," + 
					        	"unique (login_id), " +
						        "reward_balance float default 0," +
						        "password varchar(30)," +
						        "PRIMARY KEY (contestant_id) " +
						        ");"),
					        
					        ("CREATE TABLE participate ( " +
						        "contestant_id varchar(42), " +
						        "contest_id varchar(42), " +
						        "contestant_reward float default 0, " +
						        "submission text, " +
						        "PRIMARY KEY (contestant_id,contest_id), " +
						        "FOREIGN KEY (contestant_id) REFERENCES contestant (contestant_id)," +
						        "FOREIGN KEY (contest_id) REFERENCES contest (contest_id)" +
						        ");"),
					        
					        ("CREATE TABLE judge ( " +
							    "judge_id varchar(42) NOT NULL," +
							    "login_id varchar(10)," + 
					        	"unique (login_id), " +
							    "reward_balance float default 0," +
							    "password varchar(30)," +
							    "avg_score float default 0," + 
							    "review_number int default 0," + 
							    "PRIMARY KEY (judge_id) " +
							    ");"),
					        
					        ("create TABLE grade( " +
							    "contest_id varchar(42)," +
							    "contestant_id varchar(42)," +
							    "judge_id varchar(42)," +
							    "score integer default 0," +
							    "complete bool default False," + 
							    "check (score >= 0 & score <= 100)," +
							    "primary key (contest_id, contestant_id, judge_id)," +
							    "foreign key (contest_id) references contest (contest_id)," +
							    "foreign key (contestant_id) references contestant (contestant_id)," +
							    "foreign key (judge_id) references judge (judge_id)" +
							    ");"),
					        
					        ("CREATE TABLE judgeby ( " +
							    "contest_id varchar(42), " +
							    "judge_id varchar(42), " +
							    "judge_reward float default 0," +
							    "PRIMARY KEY (contest_id,judge_id)," +
							    "FOREIGN KEY (contest_id) REFERENCES contest (contest_id)," +
							    "FOREIGN KEY (judge_id) REFERENCES judge (judge_id)" +
							    ");"),
					        
					        ("create TABLE review( " +
								"judge_id varchar(42)," +
								"sponsor_id varchar(42)," +
								"review_score integer," +
								"check (review_score >= 0 & review_score <= 10)," +
								"comment text," +
								"primary key (judge_id, sponsor_id)," +
								"foreign key (judge_id) references judge (judge_id)," +
								"foreign key (sponsor_id) references sponsor (sponsor_id)" +
								");"),
					        
					        
					        ("CREATE TABLE admin ( " +
								    "name varchar(20)," +
								    "password varchar(30)," +
								    "primary key (name)" +
								    ");")
        					};
        String[] TUPLES = {("INSERT INTO admin(name, password) values ('root', 'pass1234');"),
        		
        		("insert into sponsor(sponsor_id, login_id, company_name, email, address, password, balance) "+
            			"values ('0x97947962D8E41604A639E45CF53ABF7D8908F3D2', 'vL7D', 'Hill Ltd ', 'dpowell@example.org', '76642 Sara Island', '1111', '990000'),"+
    			    		 	"('0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', 'FFuUm64J', 'Jacobs-Waters', 'sallybaker@example.org','West Laurenberg, GA 91052', '2222', '990000'),"+
    			    	 	 	"('0xAE68D751D3F446DE82E721D710A39699D75ED4E6', 'Y3WvlF', 'Roberts Inc', 'duncancheryl@example.org','6614 Atkins Port Apt. 038', '3333', '990000'),"+
    			    		 	"('0x852E70564680766F82DF05F73BE602C8C3365052', 'osHB', 'Foster Inc', 'emily72@example.com','Jonathanside, CO 22043', '4444', '1000000'),"+
    			    		 	"('0x5178CD3E65527138457B67B681984A4CAEFE3639', 'aXhRd', 'Woods, Winters and Duncan', 'william12@example.org','62948 Ashley Unions Apt. 579', '5555', '1000000'),"+
    			    		 	"('0x9DB671E5B81081ECB3B6BBB0B3BD6CF3DBC22FD9', 'zcsi', 'Hinton Group', 'wtorres@example.net','Karaberg, WI 67321', '6666', '1000000'),"+
    			    			"('0x529F3FC6C7F7B7D02B4894DF3F2289E2D507851C', 'JFWHpFJl', 'Rodriguez Inc', 'dvalenzuela@example.net','92335 Rivas Circles Apt. 345', '7777', '1000000'),"+
    			    			"('0x0CB12A11D9A621DEAC12FCC3175086E32A15034C', 'IfGNZtt', 'Rodriguez-Guerrero','riceallen@example.org', 'Port Johnfort, GA 54293', '8888', '1000000'),"+
    			    			"('0x8D07672797426DBA4F6E9CAC7D8CDC8DFA420C11', '8NVQNmE', 'Johnson-Warren', 'morrisonkyle@example.net','160 Ward Forges', '9999', '1000000'),"+
    			    			"('0x360F3E8F837793A7E8F205EC58A3E0ADDE5C66AE', 'Q49zwPCC', 'Johnson, Morse and Chandler ', 'david97@example.com','New Christopher, DE 18612', '0000', '1000000');"),
        		
        		("insert into contest(contest_id, sponsor_id, begin_time, end_time, title, status, requirement_list, sponsor_fee) "+
            			"values ('0x49A1E288EFF45984CEC8409406053B9B9A5500DA', '0x97947962D8E41604A639E45CF53ABF7D8908F3D2','2023-03-08 23:11:23', '2023-03-28 19:01:46', 'contest_1', 'opened', 'Executive serious challenge question. Instead money court city learn where. Common always key analysis show ball.', '10000'),"+
    			    		 	"('0x498FAD17F26E914F1A034D960B724A3C05A54DD8', '0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', '2023-03-09 14:25:39', '2023-03-10 18:32:53', 'contest_2', 'opened', 'Close success class down. Which medical he tree social American.', '10000'),"+
    			    			"('0x45D4F7663EEE2246299620F1B6D69EB8C1675635', '0xAE68D751D3F446DE82E721D710A39699D75ED4E6', '2023-03-19 23:24:54', '2023-04-01 03:00:23', 'contest_3', 'created',  'Building make be we mother. Mission now clearly but according Mr wait.', '10000');"),
        		
        		("insert into contestant(contestant_id, login_id, password) "+
            			"values ('0xFFA48B515340C430BA8BD38E739715449A9A98C2', 'DJA8', '1122'),"+
    			    		 	"('0x35A85E2BAD55C300CE6275B15CAB8CA31D84C599', '5HpCn', '2211'),"+
    			    	 	 	"('0xFCD3B620543DD7F60E242DF58682B868E85736AD', 'W0Zq', '1313'),"+
    			    		 	"('0x680ABFEC8CB5FA2DAEC5C1390B5A215107A4B395', 'WbGY', '1414'),"+
    			    		 	"('0x07C753B5DC16CF9A08C0FB9E1ED2DF7BFF8D2183', 'wuWk2', '1515'),"+
    			    		 	"('0x2E1F392C012084AFEE3488950F3EDB976BA58E24', 'qjgA', '1616'),"+
    			    			"('0x803B1734224C73B9FFC5F08B5EF197695F002EF1', 'zH9TjKF', '1717'),"+
    			    			"('0x3CFBCB6D1980E895BFE47C09677A2D6D130CC7F4', 'b27AQ54y', '1818'),"+
    			    			"('0x3F3568E46909FB7AAE17B1C31F7D2F334CF1C6E5', 'eVtMDD5u', '1919'),"+
    			    			"('0xB7E05DE3DCB4D73810F4F8A25DB1D32A900D89A0', 'i1AiG', '1010');"),
        		
        		("insert into judge(judge_id, login_id, password) "+
            			"values ('0x7028B6789BBEE245564032790282FD27B8042476', 'UPnNuu', '1231'),"+
    			    		 	"('0x4967CFB5FC27C098230CFE8B8985234D91D52886', 'r6vPbtH', '1232'),"+
    			    	 	 	"('0xA115319467FB68EDD5DA513152B1158A2EF14CBF', '8uSPO2x', '1233'),"+
    			    		 	"('0xA542FE379E6E11749D19F1AB9514E0202D6E64AC', 'ckoC4v2', '1234'),"+
    			    		 	"('0x85C797CAF9D2FEDBA5C49AF91F4FEAB7366EA6AB', 'Q1Mbbjz', '1235'),"+
    			    		 	"('0x239BFD2748D6D220AF5B93E88F44A9C4FCC36F3C', 'nGv5', '1236'),"+
    			    			"('0x987263D76E9C6674D9330A3B6F1A3E6FB4801691', 'AmKrgjq0', '1237'),"+
    			    			"('0xBDCE1E9D1D7A2DF132C54B1F15A1AF386DF95EE8', '7L9is', '1238'),"+
    			    			"('0x28B62CECE61DF2E4656A66DE2929E02DA90B8E83', 'aC2BS', '1239'),"+
    			    			"('0x307431DB1BAE5134190DA6352D0D294FA69ECEC4', 'VCjLuqf', '1230');"),
        		
        		("insert into participate(contestant_id, contest_id) "+
            			"values ('0xFFA48B515340C430BA8BD38E739715449A9A98C2', '0x49A1E288EFF45984CEC8409406053B9B9A5500DA'),"+
    			    		 	"('0x35A85E2BAD55C300CE6275B15CAB8CA31D84C599', '0x49A1E288EFF45984CEC8409406053B9B9A5500DA'),"+
    			    	 	 	"('0xFCD3B620543DD7F60E242DF58682B868E85736AD', '0x49A1E288EFF45984CEC8409406053B9B9A5500DA'),"+
    			    		 	"('0x680ABFEC8CB5FA2DAEC5C1390B5A215107A4B395', '0x49A1E288EFF45984CEC8409406053B9B9A5500DA'),"+
    			    		 	"('0x07C753B5DC16CF9A08C0FB9E1ED2DF7BFF8D2183', '0x49A1E288EFF45984CEC8409406053B9B9A5500DA'),"+
    			    		 	"('0x2E1F392C012084AFEE3488950F3EDB976BA58E24', '0x498FAD17F26E914F1A034D960B724A3C05A54DD8'),"+
    			    			"('0x803B1734224C73B9FFC5F08B5EF197695F002EF1', '0x498FAD17F26E914F1A034D960B724A3C05A54DD8'),"+
    			    			"('0x3CFBCB6D1980E895BFE47C09677A2D6D130CC7F4', '0x498FAD17F26E914F1A034D960B724A3C05A54DD8'),"+
    			    			"('0x3F3568E46909FB7AAE17B1C31F7D2F334CF1C6E5', '0x498FAD17F26E914F1A034D960B724A3C05A54DD8'),"+
    			    			"('0xB7E05DE3DCB4D73810F4F8A25DB1D32A900D89A0', '0x498FAD17F26E914F1A034D960B724A3C05A54DD8');"),
        		
        		("insert into grade(contest_id, contestant_id, judge_id) "+
            			"values ('0x49A1E288EFF45984CEC8409406053B9B9A5500DA', '0xFFA48B515340C430BA8BD38E739715449A9A98C2', '0x7028B6789BBEE245564032790282FD27B8042476'),"+
    			    		 	"('0x49A1E288EFF45984CEC8409406053B9B9A5500DA', '0x35A85E2BAD55C300CE6275B15CAB8CA31D84C599', '0x4967CFB5FC27C098230CFE8B8985234D91D52886'),"+
    			    	 	 	"('0x49A1E288EFF45984CEC8409406053B9B9A5500DA', '0xFCD3B620543DD7F60E242DF58682B868E85736AD', '0xA115319467FB68EDD5DA513152B1158A2EF14CBF'),"+
    			    		 	"('0x49A1E288EFF45984CEC8409406053B9B9A5500DA', '0x680ABFEC8CB5FA2DAEC5C1390B5A215107A4B395', '0xA542FE379E6E11749D19F1AB9514E0202D6E64AC'),"+
    			    		 	"('0x49A1E288EFF45984CEC8409406053B9B9A5500DA', '0x07C753B5DC16CF9A08C0FB9E1ED2DF7BFF8D2183', '0x85C797CAF9D2FEDBA5C49AF91F4FEAB7366EA6AB'),"+
    			    		 	"('0x498FAD17F26E914F1A034D960B724A3C05A54DD8', '0x2E1F392C012084AFEE3488950F3EDB976BA58E24', '0x239BFD2748D6D220AF5B93E88F44A9C4FCC36F3C'),"+
    			    			"('0x498FAD17F26E914F1A034D960B724A3C05A54DD8', '0x803B1734224C73B9FFC5F08B5EF197695F002EF1', '0x987263D76E9C6674D9330A3B6F1A3E6FB4801691'),"+
    			    			"('0x498FAD17F26E914F1A034D960B724A3C05A54DD8', '0x3CFBCB6D1980E895BFE47C09677A2D6D130CC7F4', '0xBDCE1E9D1D7A2DF132C54B1F15A1AF386DF95EE8'),"+
    			    			"('0x498FAD17F26E914F1A034D960B724A3C05A54DD8', '0x3F3568E46909FB7AAE17B1C31F7D2F334CF1C6E5', '0x28B62CECE61DF2E4656A66DE2929E02DA90B8E83'),"+
    			    			"('0x498FAD17F26E914F1A034D960B724A3C05A54DD8', '0xB7E05DE3DCB4D73810F4F8A25DB1D32A900D89A0', '0x307431DB1BAE5134190DA6352D0D294FA69ECEC4');"),
        		
        		("insert into judgeby(contest_id, judge_id) "+
            			"values ('0x49A1E288EFF45984CEC8409406053B9B9A5500DA', '0x7028B6789BBEE245564032790282FD27B8042476'),"+
    			    		 	"('0x49A1E288EFF45984CEC8409406053B9B9A5500DA', '0x4967CFB5FC27C098230CFE8B8985234D91D52886'),"+
    			    	 	 	"('0x49A1E288EFF45984CEC8409406053B9B9A5500DA', '0xA115319467FB68EDD5DA513152B1158A2EF14CBF'),"+
    			    		 	"('0x49A1E288EFF45984CEC8409406053B9B9A5500DA', '0xA542FE379E6E11749D19F1AB9514E0202D6E64AC'),"+
    			    		 	"('0x49A1E288EFF45984CEC8409406053B9B9A5500DA', '0x85C797CAF9D2FEDBA5C49AF91F4FEAB7366EA6AB'),"+
    			    		 	"('0x498FAD17F26E914F1A034D960B724A3C05A54DD8', '0x239BFD2748D6D220AF5B93E88F44A9C4FCC36F3C'),"+
    			    			"('0x498FAD17F26E914F1A034D960B724A3C05A54DD8', '0x987263D76E9C6674D9330A3B6F1A3E6FB4801691'),"+
    			    			"('0x498FAD17F26E914F1A034D960B724A3C05A54DD8', '0xBDCE1E9D1D7A2DF132C54B1F15A1AF386DF95EE8'),"+
    			    			"('0x498FAD17F26E914F1A034D960B724A3C05A54DD8', '0x28B62CECE61DF2E4656A66DE2929E02DA90B8E83'),"+
    			    			"('0x498FAD17F26E914F1A034D960B724A3C05A54DD8', '0x307431DB1BAE5134190DA6352D0D294FA69ECEC4');")};
//        		("insert into review(judge_id, sponsor_id, review_score, comment) "+
//            			"values ('0x7028B6789BBEE245564032790282FD27B8042476', '0x97947962D8E41604A639E45CF53ABF7D8908F3D2', '8', 'Those well check partner.'),"+
//    			    		 	"('0x4967CFB5FC27C098230CFE8B8985234D91D52886', '0x97947962D8E41604A639E45CF53ABF7D8908F3D2', '8', 'Inside along PM own break. Play sit good able.'),"+
//    			    	 	 	"('0xA115319467FB68EDD5DA513152B1158A2EF14CBF', '0x97947962D8E41604A639E45CF53ABF7D8908F3D2', '8', 'Full teacher why perhaps question.'),"+
//    			    		 	"('0xA542FE379E6E11749D19F1AB9514E0202D6E64AC', '0x97947962D8E41604A639E45CF53ABF7D8908F3D2', '8', 'receive theory. President shoulder there history bad rest threat.'),"+
//    			    		 	"('0x85C797CAF9D2FEDBA5C49AF91F4FEAB7366EA6AB', '0x97947962D8E41604A639E45CF53ABF7D8908F3D2', '8', 'Rock then think natural inside represent current long.'),"+
//    			    		 	"('0x239BFD2748D6D220AF5B93E88F44A9C4FCC36F3C', '0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', '8', 'Talk enjoy weight form. Threat half money our wide. Nice during nor fill wait.'),"+
//    			    			"('0x987263D76E9C6674D9330A3B6F1A3E6FB4801691', '0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', '8', 'Sing product memory. Social foreign treatment actually. Piece cut left else feeling environment.'),"+
//    			    			"('0xBDCE1E9D1D7A2DF132C54B1F15A1AF386DF95EE8', '0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', '8', 'Similar together simply able. Image loss friend own father happy response door.'),"+
//    			    			"('0x28B62CECE61DF2E4656A66DE2929E02DA90B8E83', '0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', '8', 'Use anything teacher instead low trial else. Report north beat like.'),"+
//    			    			"('0x307431DB1BAE5134190DA6352D0D294FA69ECEC4', '0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', '8', 'Inside along PM own break. Play sit good able.');")
			    			
        String[] EVENT = {
        		("CREATE DEFINER=`john`@`%` PROCEDURE `contest_start_status`()\r\n"
        				+ "BEGIN\r\n"
        				+ "update contest set status = 'opened'\r\n"
        				+ "where status = 'created' and (UNIX_TIMESTAMP(begin_time) <= unix_timestamp(now()));\r\n"
        				+ "END;\r\n"
        		),
        		("CREATE DEFINER=`john`@`%` PROCEDURE `contest_close_status`()\r\n"
        				+ "BEGIN\r\n"
        				+ "	update contest set status = 'closed'\r\n"
        				+ "    where status = 'opened' and (UNIX_TIMESTAMP(end_time) <= unix_timestamp(now()));\r\n"
        				+ "END;"),
        		
        		("create DEFINER=`john`@`%` EVENT `check_contest_status` on schedule every 10 second "
        				+ "do\r\n"
        				+ "	begin\r\n"
        				+ "		CALL `contestdb`.`contest_start_status`();\r\n"
        				+ "		CALL `contestdb`.`contest_close_status`();\r\n"
        				+ "	end;\r\n"),
        		
        		("set global event_scheduler = on;\r\n")
        };
        
        //for loop to put these in database
        for (int i = 0; i < INITIAL.length; i++)
        	statement.execute(INITIAL[i]);
        for (int i = 0; i < TUPLES.length; i++)	
        	statement.execute(TUPLES[i]);
        for (int i = 0; i < EVENT.length; i ++)
        	statement.execute(EVENT[i]);
        disconnect();
    }
    
    
   
    
    
    
    
    
	
	

}
