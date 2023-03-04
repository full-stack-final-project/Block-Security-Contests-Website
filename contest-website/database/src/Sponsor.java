public class Sponsor extends User{
	protected String login_id;
	protected String company_name;
	protected String email;
	protected String address;
	protected long balance;

	public Sponsor() {
	}
 
    public Sponsor(String id, String login_id, String company_name, String email, String address, String password, long balance) 
    {
        this.id = id;
        this.login_id = login_id;
        this.company_name = company_name;
        this.email = email;
        this.address = address;
        this.password = password;
        this.balance = balance;
    }
    
   //getter and setter methods
    public String getCompanyName() {
        return this.company_name;
    }
    public void setCompanyName(String company_name) {
    	this.company_name = company_name;
    }
    
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
    	this.address = address;
    }
    
    public String getLoginID() {
    	return this.login_id;
    }
    public void setLoginID(String login_id) {
    	this.login_id = login_id;
    }
    
    public long getBalance() {
    	return this.balance;
    }
    public void setBalance(long balance) {
    	this.balance = balance;
    }
    
    

}