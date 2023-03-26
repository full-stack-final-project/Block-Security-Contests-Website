
public class Review {
	protected float score;
	protected String comment;
	protected String sponsor;
	
	public Review(float score, String comment, String sponsor) {
		this.score = score;
		this.comment = comment;
		this.sponsor = sponsor;
	}
	
	public float getScore() {
		return this.score;
	}
	
	public String getSponsor() {
		return this.sponsor;
	}
	
	public String getComment() {
		return this.comment;
	}

}
