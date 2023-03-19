

public class Judge extends User{
	protected float reward_balance;
	protected float avg_score;
	protected int review_number;
	protected String login_id;

	public Judge() {
	}
    
    public Judge(String id, String login_id, String password, float reward_balance, float avg_score, int review_number) 
    {
        this.id = id;
    	this.password = password;
    	this.reward_balance = reward_balance;
    	this.avg_score = avg_score;
        this.review_number = review_number;
    }
    
   //getter and setter methods
    public String getLoginID() {
    	return this.login_id;
    }
    public float getRewardBalance() {
        return this.reward_balance;
    }
    public void setRewardBalance(float reward_balance) {
    	this.reward_balance = reward_balance;
    }
    
    public float getAvgScore() {
    	return this.avg_score;
    }
    public void setAvgScore(float avg_score) {
    	this.avg_score = avg_score;
    }
    
    public int getReviewNumber() {
    	return this.review_number;
    }
    public void setReviewNumber(int review_number) {
    	this.review_number = review_number;
    }

}
