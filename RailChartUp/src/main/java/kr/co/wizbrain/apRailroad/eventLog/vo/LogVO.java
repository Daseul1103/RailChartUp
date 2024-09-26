package kr.co.wizbrain.apRailroad.eventLog.vo;

import java.util.Map;

/*로그 구성에 필요한 요소 vo*/
public class LogVO {
	//id
	public String loginId;
	//eventLog
	public EventLogVO evo;
	//tcms
	public TcmsRcvVO tvo;
	//hse
	public HseSndVO hvo;
	//comment
	public String comm;
	//key:속성명, value : 한글뜻
	public Map<String, String> ekMap;
	//buttonName : 어떻게 변경됬는지
	public String btnName;
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public EventLogVO getEvo() {
		return evo;
	}
	public void setEvo(EventLogVO evo) {
		this.evo = evo;
	}
	public TcmsRcvVO getTvo() {
		return tvo;
	}
	public void setTvo(TcmsRcvVO tvo) {
		this.tvo = tvo;
	}
	public HseSndVO getHvo() {
		return hvo;
	}
	public void setHvo(HseSndVO hvo) {
		this.hvo = hvo;
	}
	public String getComm() {
		return comm;
	}
	public void setComm(String comm) {
		this.comm = comm;
	}
	public Map<String, String> getEkMap() {
		return ekMap;
	}
	public void setEkMap(Map<String, String> ekMap) {
		this.ekMap = ekMap;
	}
	public String getBtnName() {
		return btnName;
	}
	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}
	
}
