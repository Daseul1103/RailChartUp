package kr.co.wizbrain.apRailroad.terminal.vo;

public class TerminalVO {
	//순번
	private String idx;
	//편성번호
	private String trainNum;
	//전열ip
	private String fIp;
	//후열ip
	private String tIp;
	//량수
	private String carCnt;
	//운행여부
	private String raceYn;
	//통계화면 활성 비활성 여부
	private String rst;
	
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getTrainNum() {
		return trainNum;
	}
	public void setTrainNum(String trainNum) {
		this.trainNum = trainNum;
	}
	public String getfIp() {
		return fIp;
	}
	public void setfIp(String fIp) {
		this.fIp = fIp;
	}
	public String gettIp() {
		return tIp;
	}
	public void settIp(String tIp) {
		this.tIp = tIp;
	}
	public String getCarCnt() {
		return carCnt;
	}
	public void setCarCnt(String carCnt) {
		this.carCnt = carCnt;
	}
	public String getRaceYn() {
		return raceYn;
	}
	public void setRaceYn(String raceYn) {
		this.raceYn = raceYn;
	}
	public String getRst() {
		return rst;
	}
	public void setRst(String rst) {
		this.rst = rst;
	}
	@Override
	public String toString() {
		return "TerminalVO [idx=" + idx + ", trainNum=" + trainNum + ", fIp=" + fIp + ", tIp=" + tIp + ", carCnt="
				+ carCnt + ", raceYn=" + raceYn + ", rst=" + rst + "]";
	}
	
}
