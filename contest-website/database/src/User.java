public class User 
{
		protected String password;
	    protected String id;
	 
	    //constructors
	    public User() {
	    }
	 
	    public User(String id) 
	    {
	        this.id = id;
	    }
	    
	    public User(String id, String password) 
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