package kr.co.wizbrain.apRailroad.comuFail.vo;

public class ComuFailVO {
	//이벤트 발생시간
	private String evtTime;
	
	//이벤트명
	private String evtName;

	//이벤트 타입번호
	private String evtNum;
	
	//이벤트 발생대상
	private String evtTgt;
	
	//이벤트 설명
	private String comment;

	public String getEvtTime() {
		return evtTime;
	}

	public void setEvtTime(String evtTime) {
		this.evtTime = evtTime;
	}

	public String getEvtName() {
		return evtName;
	}

	public void setEvtName(String evtName) {
		this.evtName = evtName;
	}

	public String getEvtNum() {
		return evtNum;
	}

	public void setEvtNum(String evtNum) {
		this.evtNum = evtNum;
	}

	public String getEvtTgt() {
		return evtTgt;
	}

	public void setEvtTgt(String evtTgt) {
		this.evtTgt = evtTgt;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "ComuFailVO [evtTime=" + evtTime + ", evtName=" + evtName + ", evtNum=" + evtNum + ", evtTgt=" + evtTgt
				+ ", comment=" + comment + "]";
	}
	
}
