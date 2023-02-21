

public class judge extends user{
	protected float reward_balance;

	public judge() {
	}
 
    public judge(float reward_balance) 
    {
        this.reward_balance = reward_balance;
    }
    
    public judge(String id, String password, float reward_balance) 
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
