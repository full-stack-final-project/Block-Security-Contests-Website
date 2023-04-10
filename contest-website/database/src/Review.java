
public class Review {
	protected int score;
	protected String comment;
	
	
	public Review(int score, String comment) {
		this.score = score;
		this.comment = comment;
		
	}
	
	public float getScore() {
		return this.score;
	}
	
	
	public String getComment() {
		return this.comment;
	}

}
