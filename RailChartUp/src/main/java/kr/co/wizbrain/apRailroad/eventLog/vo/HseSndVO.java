package kr.co.wizbrain.apRailroad.eventLog.vo;

public class HseSndVO {
	// 송신시간
	public String sndDt;
	// 운행번호
	public int trainNum;
	// 열차번호(고유값)
	public int carNum;
	// 수신시간
	public String rcvDt;
	// 열차타입(6또는8)
	public String trainType;
	// 열차방향(1또는8)
	public String trainDirect;
	//평균혼잡도
	public int avgCrwd;
	// 차량무게1
	public int carWgt1;
	// 차량무게2
	public int carWgt2;
	// 차량무게3
	public int carWgt3;
	// 차량무게4
	public int carWgt4;
	// 차량무게5
	public int carWgt5;
	// 차량무게6
	public int carWgt6;
	// 차량무게7
	public int carWgt7;
	// 차량무게8
	public int carWgt8;
	
	public String getSndDt() {
		return sndDt;
	}
	public void setSndDt(String sndDt) {
		this.sndDt = sndDt;
	}
	public int getTrainNum() {
		return trainNum;
	}
	public void setTrainNum(int trainNum) {
		this.trainNum = trainNum;
	}
	public int getCarNum() {
		return carNum;
	}
	public void setCarNum(int carNum) {
		this.carNum = carNum;
	}
	public String getRcvDt() {
		return rcvDt;
	}
	public void setRcvDt(String rcvDt) {
		this.rcvDt = rcvDt;
	}
	public String getTrainType() {
		return trainType;
	}
	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}
	public String getTrainDirect() {
		return trainDirect;
	}
	public void setTrainDirect(String trainDirect) {
		this.trainDirect = trainDirect;
	}
	public int getAvgCrwd() {
		return avgCrwd;
	}
	public void setAvgCrwd(int avgCrwd) {
		this.avgCrwd = avgCrwd;
	}
	public int getCarWgt1() {
		return carWgt1;
	}
	public void setCarWgt1(int carWgt1) {
		this.carWgt1 = carWgt1;
	}
	public int getCarWgt2() {
		return carWgt2;
	}
	public void setCarWgt2(int carWgt2) {
		this.carWgt2 = carWgt2;
	}
	public int getCarWgt3() {
		return carWgt3;
	}
	public void setCarWgt3(int carWgt3) {
		this.carWgt3 = carWgt3;
	}
	public int getCarWgt4() {
		return carWgt4;
	}
	public void setCarWgt4(int carWgt4) {
		this.carWgt4 = carWgt4;
	}
	public int getCarWgt5() {
		return carWgt5;
	}
	public void setCarWgt5(int carWgt5) {
		this.carWgt5 = carWgt5;
	}
	public int getCarWgt6() {
		return carWgt6;
	}
	public void setCarWgt6(int carWgt6) {
		this.carWgt6 = carWgt6;
	}
	public int getCarWgt7() {
		return carWgt7;
	}
	public void setCarWgt7(int carWgt7) {
		this.carWgt7 = carWgt7;
	}
	public int getCarWgt8() {
		return carWgt8;
	}
	public void setCarWgt8(int carWgt8) {
		this.carWgt8 = carWgt8;
	}
	
	@Override
	public String toString() {
		return "HseSndVO [sndDt=" + sndDt + ", trainNum=" + trainNum + ", carNum=" + carNum + ", rcvDt=" + rcvDt
				+ ", trainType=" + trainType + ", trainDirect=" + trainDirect + ", avgCrwd=" + avgCrwd + ", carWgt1="
				+ carWgt1 + ", carWgt2=" + carWgt2 + ", carWgt3=" + carWgt3 + ", carWgt4=" + carWgt4 + ", carWgt5="
				+ carWgt5 + ", carWgt6=" + carWgt6 + ", carWgt7=" + carWgt7 + ", carWgt8=" + carWgt8 + "]";
	}
	
}
