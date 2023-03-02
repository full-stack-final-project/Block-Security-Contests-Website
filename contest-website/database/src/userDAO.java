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
    protected void connect_func() throws SQLException {
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
    		connect_func("root","root");
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
    public void connect_func(String username, String password) throws SQLException {
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
    
    public List<sponsor> listBigSponsors() throws SQLException{
    	List<sponsor> bigSponsors = new ArrayList<sponsor>();
    	
    	
    }
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
    public void insert(user users, String role) throws SQLException {
    	connect_func("root","pass1234");         
		String sql = "insert into " + role + "(" + role + "_id, password) values (?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, users.getId());
		preparedStatement.setString(2, users.getPassword());
		System.out.print(sql);
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
        connect_func();
         
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
    	connect_func();
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
    	connect_func();
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
    	connect_func();
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
    
    
    
    public boolean isValid(String wallet_address, String password, String role) throws SQLException
    {
    	String sql = "SELECT * FROM " + role;
    	connect_func();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	
    	resultSet.last();
    	
    	int setSize = resultSet.getRow();
    	resultSet.beforeFirst();
    	
    	for(int i = 0; i < setSize; i++)
    	{
    		resultSet.next();
    		if(resultSet.getString(role + "_id").equals(wallet_address) && resultSet.getString("password").equals(password)) {
    			return true;
    		}		
    	}
    	return false;
    }
    
    
    public void init() throws SQLException, FileNotFoundException, IOException{
    	connect_func();
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
        		
        		("insert into contestant(contestant_id, login_id, reward_balance, password) "+
            			"values ('0xFFA48B515340C430BA8BD38E739715449A9A98C2', 'DJA8', '500', '1122'),"+
    			    		 	"('0x35A85E2BAD55C300CE6275B15CAB8CA31D84C599', '5HpCn', '500', '2211'),"+
    			    	 	 	"('0xFCD3B620543DD7F60E242DF58682B868E85736AD', 'W0Zq', '1000', '1313'),"+
    			    		 	"('0x680ABFEC8CB5FA2DAEC5C1390B5A215107A4B395', 'WbGY', '2000', '1414'),"+
    			    		 	"('0x07C753B5DC16CF9A08C0FB9E1ED2DF7BFF8D2183', 'wuWk2', '4000', '1515'),"+
    			    		 	"('0x2E1F392C012084AFEE3488950F3EDB976BA58E24', 'qjgA', '500', '1616'),"+
    			    			"('0x803B1734224C73B9FFC5F08B5EF197695F002EF1', 'zH9TjKF', '500', '1717'),"+
    			    			"('0x3CFBCB6D1980E895BFE47C09677A2D6D130CC7F4', 'b27AQ54y', '1000', '1818'),"+
    			    			"('0x3F3568E46909FB7AAE17B1C31F7D2F334CF1C6E5', 'eVtMDD5u', '2000', '1919'),"+
    			    			"('0xB7E05DE3DCB4D73810F4F8A25DB1D32A900D89A0', 'i1AiG', '4000', '1010');"),
        		
        		("insert into judge(judge_id, login_id, reward_balance, password, avg_score, review_number) "+
            			"values ('0x7028B6789BBEE245564032790282FD27B8042476', 'UPnNuu', '400', '1231', '8', '1'),"+
    			    		 	"('0x4967CFB5FC27C098230CFE8B8985234D91D52886', 'r6vPbtH', '400', '1232', '8', '1'),"+
    			    	 	 	"('0xA115319467FB68EDD5DA513152B1158A2EF14CBF', '8uSPO2x', '400', '1233', '8', '1'),"+
    			    		 	"('0xA542FE379E6E11749D19F1AB9514E0202D6E64AC', 'ckoC4v2', '400', '1234', '8', '1'),"+
    			    		 	"('0x85C797CAF9D2FEDBA5C49AF91F4FEAB7366EA6AB', 'Q1Mbbjz', '400', '1235', '8', '1'),"+
    			    		 	"('0x239BFD2748D6D220AF5B93E88F44A9C4FCC36F3C', 'nGv5', '400', '1236', '8', '1'),"+
    			    			"('0x987263D76E9C6674D9330A3B6F1A3E6FB4801691', 'AmKrgjq0', '400', '1237', '8', '1'),"+
    			    			"('0xBDCE1E9D1D7A2DF132C54B1F15A1AF386DF95EE8', '7L9is', '400', '1238', '8', '1'),"+
    			    			"('0x28B62CECE61DF2E4656A66DE2929E02DA90B8E83', 'aC2BS', '400', '1239', '8', '1'),"+
    			    			"('0x307431DB1BAE5134190DA6352D0D294FA69ECEC4', 'VCjLuqf', '400', '1230', '8', '1');"),
        		
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
        
        //for loop to put these in database
        for (int i = 0; i < INITIAL.length; i++)
        	statement.execute(INITIAL[i]);
        for (int i = 0; i < TUPLES.length; i++)	
        	statement.execute(TUPLES[i]);
        disconnect();
    }
    
    
   
    
    
    
    
    
	
	

}
