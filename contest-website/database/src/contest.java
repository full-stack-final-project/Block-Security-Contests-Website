import java.time.LocalDateTime;

public class contest {
	protected String contest_id;
	protected String sponsor_id;
	protected String title;
	protected LocalDateTime begin_time;
	protected LocalDateTime end_time;
	protected String status;
	protected String requirement_list;
	protected long sponsor_fee;
	
	public contest(String contest_id, String sponsor_id) {
		this.contest_id = contest_id;
		this.sponsor_id = sponsor_id;
	}
	
	public contest(String contest_id, String sponsor_id, String title, LocalDateTime begin_time, LocalDateTime end_time, String status, String requirement_list, long fee) {
		this.contest_id = contest_id;
		this.sponsor_id = sponsor_id;
		this.title = title;
		this.begin_time = begin_time;
		this.end_time = end_time;
		this.status = status;
		this.requirement_list = requirement_list;
		this.sponsor_fee = fee;
	}
	
	public String getContestID() {
		return this.contest_id;
	}
	public void setContestID(String id) {
		this.contest_id = id;
	}
	
	public String getSponsorID() {
		return this.sponsor_id;
	}
	public void setSponsorID(String id) {
		this.sponsor_id = id;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public LocalDateTime getBeginTime() {
		return this.begin_time;
	}
	public void setBeginTime(LocalDateTime begin) {
		this.begin_time = begin;
	}
	
	public LocalDateTime getEndTime() {
		return this.end_time;
	}
	public void getEndTime(LocalDateTime end) {
		this.end_time = end;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRequirementList() {
		return this.requirement_list;
	}
	public void setRequirementList(String requirement_list) {
		this.requirement_list = requirement_list;
	}
	
	public long getSponsorFee() {
		return this.sponsor_fee;
	}
	public void setSponsorFee(long fee) {
		this.sponsor_fee = fee;
	}

}
