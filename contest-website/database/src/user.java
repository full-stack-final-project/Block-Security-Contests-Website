public class user 
{
		protected String password;
	    protected String id;
	 
	    //constructors
	    public user() {
	    }
	 
	    public user(String id) 
	    {
	        this.id = id;
	    }
	    
	    public user(String id, String password) 
	    {
	        this.id = id;
	    	this.password = password;
	    }
	    
	   //getter and setter methods
	    public String getId() {
	        return id;
	    }
	    public void setId(String id) {
	        this.id = id;
	    }
	    
	    public String getPassword() {
	        return password;
	    }
	    public void setPassword(String password) {
	        this.password = password;
	    }
	}