public class sponsor extends user{
	protected String company_name;
	protected String email;
	protected String address;

	public sponsor() {
	}
 
    public sponsor(String id, String company_name, String email, String address, String password) 
    {
        this.id = id;
        this.company_name = company_name;
        this.email = email;
        this.address = address;
        this.password = password;
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
    public void setRewardBalance(String address) {
    	this.address = address;
    }

}