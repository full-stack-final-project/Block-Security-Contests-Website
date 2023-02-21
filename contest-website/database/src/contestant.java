
public class contestant extends user{
	protected float reward_balance;

	public contestant() {
	}
 
    public contestant(float reward_balance) 
    {
        this.reward_balance = reward_balance;
    }
    
    public contestant(String id, String password, float reward_balance) 
    {
        this.id = id;
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

}
