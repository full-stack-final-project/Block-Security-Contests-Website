
public class contestant extends user{
	protected float reward_balance;
	protected String login_id;

	public contestant() {
	}
 

    public contestant(String id, String login_id, float reward_balance, String password) 
    {
        this.id = id;
        this.login_id = login_id;
    	this.password = password;
    	this.reward_balance = reward_balance;
    }
    
   //getter and setter methods
    public float getRewardBalance() {
        return this.reward_balance;
    }
    public void setRewardBalance(float reward_balance) {
    	this.reward_balance = reward_balance;
    }
    
    public String getLoginID() {
        return this.login_id;
    }
    public void setLoginID(String login_id) {
    	this.login_id = login_id;
    }

}
