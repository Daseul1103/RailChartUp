package kr.co.wizbrain.apRailroad.setting.vo;

public class TimeRangeVO {
	//출근 시작 시간
	private String goworkSt;	
	//출근 종료 시간
	private String goworkEd;	
	//주간 시작 시간
	private String daySt;		
	//주간 종료 시간
	private String dayEd;		
	//퇴근 시작 시간
	private String gohomeSt;	
	//퇴근 종료 시간
	private String gohomeEd;	
	//야간 시작 시간
	private String nightSt;		
	//야간 종료 시간
	private String nightEd;		
	//봄 시작 월
	private String springSt;	
	//봄 종료 월
	private String springEd;	
	//여름 시작 월
	private String summerSt;	
	//여름 종료 월
	private String summerEd;	
	//가을 시작 월
	private String autumnSt;	
	//가을 종료 월
	private String autumnEd;	
	//겨울 시작 월
	private String winterSt;	
	//겨울 종료 월
	private String winterEd;
	
	public String getGoworkSt() {
		return goworkSt;
	}
	public void setGoworkSt(String goworkSt) {
		this.goworkSt = goworkSt;
	}
	public String getGoworkEd() {
		return goworkEd;
	}
	public void setGoworkEd(String goworkEd) {
		this.goworkEd = goworkEd;
	}
	public String getDaySt() {
		return daySt;
	}
	public void setDaySt(String daySt) {
		this.daySt = daySt;
	}
	public String getDayEd() {
		return dayEd;
	}
	public void setDayEd(String dayEd) {
		this.dayEd = dayEd;
	}
	public String getGohomeSt() {
		return gohomeSt;
	}
	public void setGohomeSt(String gohomeSt) {
		this.gohomeSt = gohomeSt;
	}
	public String getGohomeEd() {
		return gohomeEd;
	}
	public void setGohomeEd(String gohomeEd) {
		this.gohomeEd = gohomeEd;
	}
	public String getNightSt() {
		return nightSt;
	}
	public void setNightSt(String nightSt) {
		this.nightSt = nightSt;
	}
	public String getNightEd() {
		return nightEd;
	}
	public void setNightEd(String nightEd) {
		this.nightEd = nightEd;
	}
	public String getSpringSt() {
		return springSt;
	}
	public void setSpringSt(String springSt) {
		this.springSt = springSt;
	}
	public String getSpringEd() {
		return springEd;
	}
	public void setSpringEd(String springEd) {
		this.springEd = springEd;
	}
	public String getSummerSt() {
		return summerSt;
	}
	public void setSummerSt(String summerSt) {
		this.summerSt = summerSt;
	}
	public String getSummerEd() {
		return summerEd;
	}
	public void setSummerEd(String summerEd) {
		this.summerEd = summerEd;
	}
	public String getAutumnSt() {
		return autumnSt;
	}
	public void setAutumnSt(String autumnSt) {
		this.autumnSt = autumnSt;
	}
	public String getAutumnEd() {
		return autumnEd;
	}
	public void setAutumnEd(String autumnEd) {
		this.autumnEd = autumnEd;
	}
	public String getWinterSt() {
		return winterSt;
	}
	public void setWinterSt(String winterSt) {
		this.winterSt = winterSt;
	}
	public String getWinterEd() {
		return winterEd;
	}
	public void setWinterEd(String winterEd) {
		this.winterEd = winterEd;
	}
	@Override
	public String toString() {
		return "TimeRangeVO [goworkSt=" + goworkSt + ", goworkEd=" + goworkEd + ", daySt=" + daySt + ", dayEd=" + dayEd
				+ ", gohomeSt=" + gohomeSt + ", gohomeEd=" + gohomeEd + ", nightSt=" + nightSt + ", nightEd=" + nightEd
				+ ", springSt=" + springSt + ", springEd=" + springEd + ", summerSt=" + summerSt + ", summerEd="
				+ summerEd + ", autumnSt=" + autumnSt + ", autumnEd=" + autumnEd + ", winterSt=" + winterSt
				+ ", winterEd=" + winterEd + "]";
	}
	
}
