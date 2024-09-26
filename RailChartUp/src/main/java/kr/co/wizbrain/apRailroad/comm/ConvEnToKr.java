package kr.co.wizbrain.apRailroad.comm;

public enum ConvEnToKr {
	dayWgtRlxSt("일일 여유 시작값"),dayWgtRlxEd("일일 여유 종료값"),dayWgtUsSt ("일일 보통 시작값"),dayWgtUsEd ("일일 보통 종료값"),dayWgtCauSt("일일 주의 시작값"),dayWgtCauEd("일일 주의 종료값"),dayWgtCwdSt("일일 혼잡 시작값"),dayWgtCwdEd("일일 혼잡 종료값"),
	mdWgtRlxSt("월간 여유 시작값"),mdWgtRlxEd("월간 여유 종료값"),mdWgtUsSt ("월간 보통 시작값"),mdWgtUsEd ("월간 보통 종료값"),mdWgtCauSt("월간 주의 시작값"),mdWgtCauEd("월간 주의 종료값"),mdWgtCwdSt("월간 혼잡 시작값"),mdWgtCwdEd("월간 혼잡 종료값"),
	monWgtRlxSt("월별 여유 시작값"),monWgtRlxEd("월별 여유 종료값"),monWgtUsSt ("월별 보통 시작값"),monWgtUsEd ("월별 보통 종료값"),monWgtCauSt("월별 주의 시작값"),monWgtCauEd("월별 주의 종료값"),monWgtCwdSt("월별 혼잡 시작값"),monWgtCwdEd("월별 혼잡 종료값"),
	yearWgtRlxSt("연도별 여유 시작값"),yearWgtRlxEd("연도별 여유 종료값"),yearWgtUsSt ("연도별 보통 시작값"),yearWgtUsEd ("연도별 보통 종료값"),yearWgtCauSt("연도별 주의 시작값"),yearWgtCauEd("연도별 주의 종료값"),yearWgtCwdSt("연도별 혼잡 시작값"),yearWgtCwdEd("연도별 혼잡 종료값"),
	seaWgtRlxSt("계절별 여유 시작값"),seaWgtRlxEd("계절별 여유 종료값"),seaWgtUsSt ("계절별 보통 시작값"),seaWgtUsEd ("계절별 보통 종료값"),seaWgtCauSt("계절별 주의 시작값"),seaWgtCauEd("계절별 주의 종료값"),seaWgtCwdSt("계절별 혼잡 시작값"),seaWgtCwdEd("계절별 혼잡 종료값");
    
    public String krName;
    public String enName;
	
    ConvEnToKr(String valued){
    	this.krName=valued;
    }
    
    public String getValue() {
    	return krName;
    }
}
