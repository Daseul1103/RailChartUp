package kr.co.wizbrain.apRailroad.main.vo;

import java.sql.Timestamp;

public class MainVO {
	
	//전일 주의
	private String ydCare;
	//전일 혼잡
	private String ydCrowd;
	//금일 주의
	private String tdCare;
	//금일 혼잡
	private String tdCrowd;
	//시간 주기
	private String timeRange;
	//갱신 주기
	private String resetRange;
	
	public String getYdCare() {
		return ydCare;
	}
	public void setYdCare(String ydCare) {
		this.ydCare = ydCare;
	}
	public String getYdCrowd() {
		return ydCrowd;
	}
	public void setYdCrowd(String ydCrowd) {
		this.ydCrowd = ydCrowd;
	}
	public String getTdCare() {
		return tdCare;
	}
	public void setTdCare(String tdCare) {
		this.tdCare = tdCare;
	}
	public String getTdCrowd() {
		return tdCrowd;
	}
	public void setTdCrowd(String tdCrowd) {
		this.tdCrowd = tdCrowd;
	}
	public String getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}
	public String getResetRange() {
		return resetRange;
	}
	public void setResetRange(String resetRange) {
		this.resetRange = resetRange;
	}
	
	@Override
	public String toString() {
		return "MainVO [ydCare=" + ydCare + ", ydCrowd=" + ydCrowd + ", tdCare=" + tdCare + ", tdCrowd=" + tdCrowd
				+ ", timeRange=" + timeRange + ", resetRange=" + resetRange + "]";
	}
	
}
