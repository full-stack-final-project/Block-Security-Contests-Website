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
    	System.out.println(sql);
    	connectFunc();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	resultSet.next();
    	
    	String contest_id = resultSet.getString("contest_id");
    	String sponsor_id = resultSet.getString("sponsor_id");
    	String title = resultSet.getString("title");
    	LocalDateTime begin_time = resultSet.getObject("begin_time", LocalDateTime.class);
    	System.out.println(begin_time);
    	LocalDateTime end_time = resultSet.getObject("end_time", LocalDateTime.class);
    	String status = resultSet.getString("status");
    	String requirement_list = resultSet.getString("requirement_list");
    	long sponsor_fee = resultSet.getLong("sponsor_fee");
    	Contest contest = new Contest(contest_id, sponsor_id, title, begin_time, end_time, status, requirement_list, sponsor_fee); 
    	
    	return contest;
    }
    
    
    
    public List<Object> sponsor_contests(String sponsor_ID) throws SQLException{
    	List<Object> list = new ArrayList<Object>();
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
    	list.add(sponsor);
    	
    	String sql_find_contests = "select * from contest\r\n"
    			+ "where sponsor_id = ? and status in ('opened', 'past')\r\n"
    			+ "order by begin_time desc;";
    	preparedStatement = connect.prepareStatement(sql_find_contests);
    	preparedStatement.setString(1, sponsor_ID);
    	resultSet = preparedStatement.executeQuery();
    	if (!resultSet.next()) {
    		System.out.println("This sponsor has not sponsored any contests yet.");
    	}else {
    		while (resultSet.next()) {
    			String contest_id = resultSet.getString("contest_id");
    			sponsor_id = resultSet.getString("sponsor_id");
    			String title = resultSet.getString("title");
    			LocalDateTime begin_time = resultSet.getObject("begin_time", LocalDateTime.class);
        		LocalDateTime end_time = resultSet.getObject("end_time", LocalDateTime.class);
        		String status = resultSet.getString("status");
        		String requirement_list = resultSet.getString("requirement_list");
        		long sponsor_fee = resultSet.getLong("sponsor_fee");
        		Contest sponsored_contest = new Contest(contest_id, sponsor_id, title, begin_time, end_time, status, requirement_list, sponsor_fee);
        		list.add(sponsored_contest);
    		}
    	}
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
    
    protected String getJudgeIDByLoginID(String loginID) throws SQLException {
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
    
    protected Contestant getContestantByLoginID(String loginID) throws SQLException {
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
    	String sql = "insert into contest (contest_id, sponsor_id, title, begin_time, end_time, status, requirement_list, sponsor_fee) "
    			+ "values (?, ? ,?, ?, ?, ?, ?, ?);" ;
    	connectFunc();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    	preparedStatement.setString(1, contest.contest_id);
    	preparedStatement.setString(2, contest.sponsor_id);
    	preparedStatement.setString(3, contest.title);
    	preparedStatement.setObject(4, contest.begin_time);
    	preparedStatement.setObject(5, contest.end_time);
    	preparedStatement.setString(6, contest.status);
    	preparedStatement.setString(7, contest.requirement_list);
    	preparedStatement.setLong(8, contest.sponsor_fee);
    	preparedStatement.executeUpdate();
        preparedStatement.close(); 
        
        System.out.println(sql);
    }
    
    public void insertJudgeby(Contest contest, String[] selectJudges) throws SQLException {
         
            	
        
        
        for (String judge_userID : selectJudges) {
        	String insert_judgeby_sql = "insert into judgeby (contest_id, judge_id) values";
        	String[] splited = judge_userID.split("\\s+");
        	String judge_id = getJudgeIDByLoginID(splited[0]);
        	
        	insert_judgeby_sql += " (" 
        	+ "'" + contest.contest_id +  "'" + ", " + "'" + judge_id + "'"+ ");";
//        	
        	System.out.println(insert_judgeby_sql);
        	statement = (Statement) connect.createStatement();
        	statement.executeUpdate(insert_judgeby_sql);
        	
        }
        
        
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
							    "avg_score float," + 
							    "review_number int default 0," + 
							    "PRIMARY KEY (judge_id) " +
							    ");"),
					        
					        ("create TABLE grade( " +
							    "contest_id varchar(42)," +
							    "contestant_id varchar(42)," +
							    "judge_id varchar(42)," +
							    "score integer," +
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
        		
        		("insert into sponsor(sponsor_id, login_id, company_name, email, address, password) "+
            			"values ('0x97947962D8E41604A639E45CF53ABF7D8908F3D2', 'vL7D', 'Hill Ltd ', 'dpowell@example.org', '76642 Sara Island', '1111'),"+
    			    		 	"('0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', 'FFuUm64J', 'Jacobs-Waters', 'sallybaker@example.org','West Laurenberg, GA 91052', '2222'),"+
    			    	 	 	"('0xAE68D751D3F446DE82E721D710A39699D75ED4E6', 'Y3WvlF', 'Roberts Inc', 'duncancheryl@example.org','6614 Atkins Port Apt. 038', '3333'),"+
    			    		 	"('0x852E70564680766F82DF05F73BE602C8C3365052', 'osHB', 'Foster Inc', 'emily72@example.com','Jonathanside, CO 22043', '4444'),"+
    			    		 	"('0x5178CD3E65527138457B67B681984A4CAEFE3639', 'aXhRd', 'Woods, Winters and Duncan', 'william12@example.org','62948 Ashley Unions Apt. 579', '5555'),"+
    			    		 	"('0x9DB671E5B81081ECB3B6BBB0B3BD6CF3DBC22FD9', 'zcsi', 'Hinton Group', 'wtorres@example.net','Karaberg, WI 67321', '6666'),"+
    			    			"('0x529F3FC6C7F7B7D02B4894DF3F2289E2D507851C', 'JFWHpFJl', 'Rodriguez Inc', 'dvalenzuela@example.net','92335 Rivas Circles Apt. 345', '7777'),"+
    			    			"('0x0CB12A11D9A621DEAC12FCC3175086E32A15034C', 'IfGNZtt', 'Rodriguez-Guerrero','riceallen@example.org', 'Port Johnfort, GA 54293', '8888'),"+
    			    			"('0x8D07672797426DBA4F6E9CAC7D8CDC8DFA420C11', '8NVQNmE', 'Johnson-Warren', 'morrisonkyle@example.net','160 Ward Forges', '9999'),"+
    			    			"('0x360F3E8F837793A7E8F205EC58A3E0ADDE5C66AE', 'Q49zwPCC', 'Johnson, Morse and Chandler ', 'david97@example.com','New Christopher, DE 18612', '0000');"),
        		
        		("insert into contest(contest_id, sponsor_id, begin_time, end_time, status, requirement_list, sponsor_fee) "+
            			"values ('0x49A1E288EFF45984CEC8409406053B9B9A5500DA', '0x97947962D8E41604A639E45CF53ABF7D8908F3D2','1970-09-09 23:11:23', '1976-01-23 19:01:46', 'past', 'Executive serious challenge question. Instead money court city learn where. Common always key analysis show ball.', '10000'),"+
    			    		 	"('0x6E646856D2D15AD6FD8F7E823858685B5E5FEAE9', '0x97947962D8E41604A639E45CF53ABF7D8908F3D2', '1996-09-07 00:55:01', '2000-05-01 13:55:40', 'past', 'Computer institution fill sister. Challenge support save and.', '10000'),"+
    			    	 	 	"('0x3A16D903AF1805A69C52DC072AF509003766138A', '0x97947962D8E41604A639E45CF53ABF7D8908F3D2', '1985-08-18 22:22:30', '2008-11-15 12:04:11', 'past', 'Election military maintain experience state. Hundred right series actually child. Who time control.', '10000'),"+
    			    		 	"('0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '0x97947962D8E41604A639E45CF53ABF7D8908F3D2', '2018-05-02 09:07:19', '2019-11-02 13:31:09', 'past', 'Major case space ok remember.', '10000'),"+
    			    		 	"('0xFA42A5A7FF1A0DEFC7755A5E3B0005E990153533', '0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', '2007-05-07 13:25:04', '2012-01-31 18:02:48', 'past', 'Race whether mind situation lot my magazine. Follow add speak during.', '10000'),"+
    			    		 	"('0x498FAD17F26E914F1A034D960B724A3C05A54DD8', '0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', '1997-08-29 14:25:39', '2014-04-25 07:32:53', 'past', 'Close success class down. Which medical he tree social American.', '10000'),"+
    			    			"('0xADA51A737606643AADAB8D02D32185893CA0A9A8', '0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', '1970-10-12 03:27:02', '1973-01-25 22:11:43', 'past', 'Reach people idea public. Truth learn left marriage pretty player. Table hour design soldier.', '10000'),"+
    			    			"('0xC115EE698B2946DB9C29869D7CD643822DBE022C', '0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', '2012-04-04 00:16:11', '2019-02-25 09:48:38', 'past', 'Score eight newspaper kid work suffer exist visit. Say hit might own. Miss amount word least.', '10000'),"+
    			    			"('0x9AE248CF24DB9E1A5DB95128230DB6880EE958AC', '0xAE68D751D3F446DE82E721D710A39699D75ED4E6', '1983-12-11 17:07:42', '1988-03-16 16:37:25', 'past', 'Discover professional deep music. Now coach data black yard apply.', '10000'),"+
    			    			"('0x45D4F7663EEE2246299620F1B6D69EB8C1675635', '0xAE68D751D3F446DE82E721D710A39699D75ED4E6', '2005-04-10 23:24:54', '2011-08-04 03:00:23', 'past', 'Building make be we mother. Mission now clearly but according Mr wait.', '10000');"),
        		
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
        		
        		("insert into judge(judge_id, login_id, password, avg_score, review_number) "+
            			"values ('0x7028B6789BBEE245564032790282FD27B8042476', 'UPnNuu', '1231', '8', '1'),"+
    			    		 	"('0x4967CFB5FC27C098230CFE8B8985234D91D52886', 'r6vPbtH', '1232', '8', '1'),"+
    			    	 	 	"('0xA115319467FB68EDD5DA513152B1158A2EF14CBF', '8uSPO2x', '1233', '8', '1'),"+
    			    		 	"('0xA542FE379E6E11749D19F1AB9514E0202D6E64AC', 'ckoC4v2', '1234', '8', '1'),"+
    			    		 	"('0x85C797CAF9D2FEDBA5C49AF91F4FEAB7366EA6AB', 'Q1Mbbjz', '1235', '8', '1'),"+
    			    		 	"('0x239BFD2748D6D220AF5B93E88F44A9C4FCC36F3C', 'nGv5', '1236', '8', '1'),"+
    			    			"('0x987263D76E9C6674D9330A3B6F1A3E6FB4801691', 'AmKrgjq0', '1237', '8', '1'),"+
    			    			"('0xBDCE1E9D1D7A2DF132C54B1F15A1AF386DF95EE8', '7L9is', '1238', '8', '1'),"+
    			    			"('0x28B62CECE61DF2E4656A66DE2929E02DA90B8E83', 'aC2BS', '1239', '8', '1'),"+
    			    			"('0x307431DB1BAE5134190DA6352D0D294FA69ECEC4', 'VCjLuqf', '1230', '8', '1');"),
        		
        		("insert into participate(contestant_id, contest_id, contestant_reward) "+
            			"values ('0xFFA48B515340C430BA8BD38E739715449A9A98C2', '0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '500'),"+
    			    		 	"('0x35A85E2BAD55C300CE6275B15CAB8CA31D84C599', '0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '500'),"+
    			    	 	 	"('0xFCD3B620543DD7F60E242DF58682B868E85736AD', '0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '1000'),"+
    			    		 	"('0x680ABFEC8CB5FA2DAEC5C1390B5A215107A4B395', '0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '2000'),"+
    			    		 	"('0x07C753B5DC16CF9A08C0FB9E1ED2DF7BFF8D2183', '0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '4000'),"+
    			    		 	"('0x2E1F392C012084AFEE3488950F3EDB976BA58E24', '0xC115EE698B2946DB9C29869D7CD643822DBE022C', '500'),"+
    			    			"('0x803B1734224C73B9FFC5F08B5EF197695F002EF1', '0xC115EE698B2946DB9C29869D7CD643822DBE022C', '500'),"+
    			    			"('0x3CFBCB6D1980E895BFE47C09677A2D6D130CC7F4', '0xC115EE698B2946DB9C29869D7CD643822DBE022C', '1000'),"+
    			    			"('0x3F3568E46909FB7AAE17B1C31F7D2F334CF1C6E5', '0xC115EE698B2946DB9C29869D7CD643822DBE022C', '2000'),"+
    			    			"('0xB7E05DE3DCB4D73810F4F8A25DB1D32A900D89A0', '0xC115EE698B2946DB9C29869D7CD643822DBE022C', '4000');"),
        		
        		("insert into grade(contest_id, contestant_id, judge_id, score) "+
            			"values ('0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '0xFFA48B515340C430BA8BD38E739715449A9A98C2', '0x7028B6789BBEE245564032790282FD27B8042476', '5'),"+
    			    		 	"('0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '0x35A85E2BAD55C300CE6275B15CAB8CA31D84C599', '0x4967CFB5FC27C098230CFE8B8985234D91D52886', '5'),"+
    			    	 	 	"('0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '0xFCD3B620543DD7F60E242DF58682B868E85736AD', '0xA115319467FB68EDD5DA513152B1158A2EF14CBF', '10'),"+
    			    		 	"('0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '0x680ABFEC8CB5FA2DAEC5C1390B5A215107A4B395', '0xA542FE379E6E11749D19F1AB9514E0202D6E64AC', '20'),"+
    			    		 	"('0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '0x07C753B5DC16CF9A08C0FB9E1ED2DF7BFF8D2183', '0x85C797CAF9D2FEDBA5C49AF91F4FEAB7366EA6AB', '40'),"+
    			    		 	"('0xC115EE698B2946DB9C29869D7CD643822DBE022C', '0x2E1F392C012084AFEE3488950F3EDB976BA58E24', '0x239BFD2748D6D220AF5B93E88F44A9C4FCC36F3C', '5'),"+
    			    			"('0xC115EE698B2946DB9C29869D7CD643822DBE022C', '0x803B1734224C73B9FFC5F08B5EF197695F002EF1', '0x987263D76E9C6674D9330A3B6F1A3E6FB4801691', '5'),"+
    			    			"('0xC115EE698B2946DB9C29869D7CD643822DBE022C', '0x3CFBCB6D1980E895BFE47C09677A2D6D130CC7F4', '0xBDCE1E9D1D7A2DF132C54B1F15A1AF386DF95EE8', '10'),"+
    			    			"('0xC115EE698B2946DB9C29869D7CD643822DBE022C', '0x3F3568E46909FB7AAE17B1C31F7D2F334CF1C6E5', '0x28B62CECE61DF2E4656A66DE2929E02DA90B8E83', '20'),"+
    			    			"('0xC115EE698B2946DB9C29869D7CD643822DBE022C', '0xB7E05DE3DCB4D73810F4F8A25DB1D32A900D89A0', '0x307431DB1BAE5134190DA6352D0D294FA69ECEC4', '40');"),
        		
        		("insert into judgeby(contest_id, judge_id, judge_reward) "+
            			"values ('0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '0x7028B6789BBEE245564032790282FD27B8042476', '400'),"+
    			    		 	"('0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '0x4967CFB5FC27C098230CFE8B8985234D91D52886', '400'),"+
    			    	 	 	"('0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '0xA115319467FB68EDD5DA513152B1158A2EF14CBF', '400'),"+
    			    		 	"('0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '0xA542FE379E6E11749D19F1AB9514E0202D6E64AC', '400'),"+
    			    		 	"('0x8A3A72C9692503153B9AE06852BE307CA3BEE18F', '0x85C797CAF9D2FEDBA5C49AF91F4FEAB7366EA6AB', '400'),"+
    			    		 	"('0xC115EE698B2946DB9C29869D7CD643822DBE022C', '0x239BFD2748D6D220AF5B93E88F44A9C4FCC36F3C', '400'),"+
    			    			"('0xC115EE698B2946DB9C29869D7CD643822DBE022C', '0x987263D76E9C6674D9330A3B6F1A3E6FB4801691', '400'),"+
    			    			"('0xC115EE698B2946DB9C29869D7CD643822DBE022C', '0xBDCE1E9D1D7A2DF132C54B1F15A1AF386DF95EE8', '400'),"+
    			    			"('0xC115EE698B2946DB9C29869D7CD643822DBE022C', '0x28B62CECE61DF2E4656A66DE2929E02DA90B8E83', '400'),"+
    			    			"('0xC115EE698B2946DB9C29869D7CD643822DBE022C', '0x307431DB1BAE5134190DA6352D0D294FA69ECEC4', '400');"),
        		
        		("insert into review(judge_id, sponsor_id, review_score, comment) "+
            			"values ('0x7028B6789BBEE245564032790282FD27B8042476', '0x97947962D8E41604A639E45CF53ABF7D8908F3D2', '8', 'Those well check partner.'),"+
    			    		 	"('0x4967CFB5FC27C098230CFE8B8985234D91D52886', '0x97947962D8E41604A639E45CF53ABF7D8908F3D2', '8', 'Inside along PM own break. Play sit good able.'),"+
    			    	 	 	"('0xA115319467FB68EDD5DA513152B1158A2EF14CBF', '0x97947962D8E41604A639E45CF53ABF7D8908F3D2', '8', 'Full teacher why perhaps question.'),"+
    			    		 	"('0xA542FE379E6E11749D19F1AB9514E0202D6E64AC', '0x97947962D8E41604A639E45CF53ABF7D8908F3D2', '8', 'receive theory. President shoulder there history bad rest threat.'),"+
    			    		 	"('0x85C797CAF9D2FEDBA5C49AF91F4FEAB7366EA6AB', '0x97947962D8E41604A639E45CF53ABF7D8908F3D2', '8', 'Rock then think natural inside represent current long.'),"+
    			    		 	"('0x239BFD2748D6D220AF5B93E88F44A9C4FCC36F3C', '0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', '8', 'Talk enjoy weight form. Threat half money our wide. Nice during nor fill wait.'),"+
    			    			"('0x987263D76E9C6674D9330A3B6F1A3E6FB4801691', '0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', '8', 'Sing product memory. Social foreign treatment actually. Piece cut left else feeling environment.'),"+
    			    			"('0xBDCE1E9D1D7A2DF132C54B1F15A1AF386DF95EE8', '0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', '8', 'Similar together simply able. Image loss friend own father happy response door.'),"+
    			    			"('0x28B62CECE61DF2E4656A66DE2929E02DA90B8E83', '0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', '8', 'Use anything teacher instead low trial else. Report north beat like.'),"+
    			    			"('0x307431DB1BAE5134190DA6352D0D294FA69ECEC4', '0xB2A8F45FEC88A1825EB1ABA35F82C2A03A4C840A', '8', 'Inside along PM own break. Play sit good able.');")
			    			};
        
        String[] EVENT = {
        		("-- update status for a contest --\r\n"
        				+ "drop procedure if exists contest_start_status;\r\n"
        				+ "drop procedure if exists contest_close_status;"),
        		
        		("DELIMITER $$\r\n"
        				+ "CREATE DEFINER=`john`@`%` PROCEDURE `contest_start_status`()\r\n"
        				+ "BEGIN\r\n"
        				+ "	update contest set status = 'opened'\r\n"
        				+ "    where status = 'created' and (UNIX_TIMESTAMP(begin_time) <= unix_timestamp(now()));\r\n"
        				+ "END$$\r\n"
        				+ "DELIMITER ;"),
        		
        		("DELIMITER $$\r\n"
        				+ "CREATE DEFINER=`john`@`%` PROCEDURE `contest_close_status`()\r\n"
        				+ "BEGIN\r\n"
        				+ "	update contest set status = 'closed'\r\n"
        				+ "    where status = 'opened' and (UNIX_TIMESTAMP(end_time) <= unix_timestamp(now()));\r\n"
        				+ "END$$\r\n"
        				+ "DELIMITER ;"),
        		
        		("-- set scheduled task on --\r\n"
        				+ "set global event_scheduler = on;\r\n"
        				+ "\r\n"
        				+ "drop event if exists check_contest_status;\r\n"
        				+ "delimiter |\r\n"
        				+ "create DEFINER=`john`@'%' EVENT if not exists`check_contest_status`\r\n"
        				+ "on schedule\r\n"
        				+ "every '10' second starts '2023-03-02 08:00:00'\r\n"
        				+ "comment 'check if contest stauts needs to be updated'\r\n"
        				+ "do\r\n"
        				+ "	begin\r\n"
        				+ "		CALL `contestdb`.`contest_start_status`();\r\n"
        				+ "		CALL `contestdb`.`contest_close_status`();\r\n"
        				+ "	end |\r\n"
        				+ "    \r\n"
        				+ "delimiter ; ")
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
