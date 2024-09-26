package kr.co.wizbrain.apRailroad.setting.vo;

public class SettingVO {
	//설정명
	public String setName;
	//설정값
	public String setVal;
	//설정 타입
	public String setType;
	//변경일시
	public String uptDt;
	//속성 설명
	public String comment;
	
	public String getSetName() {
		return setName;
	}
	public void setSetName(String setName) {
		this.setName = setName;
	}
	public String getSetVal() {
		return setVal;
	}
	public void setSetVal(String setVal) {
		this.setVal = setVal;
	}
	public String getSetType() {
		return setType;
	}
	public void setSetType(String setType) {
		this.setType = setType;
	}
	public String getUptDt() {
		return uptDt;
	}
	public void setUptDt(String uptDt) {
		this.uptDt = uptDt;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		return "SettingVO [setName=" + setName + ", setVal=" + setVal + ", setType=" + setType + ", uptDt=" + uptDt
				+ ", comment=" + comment + "]";
	}
	
}
