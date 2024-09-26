package kr.co.wizbrain.apRailroad.user.vo;

public class UserVO {

    // 사용자 id 
    private String userId;

    // 사용자 비밀번호 
    private String userPw;

	// 사용자 이름 
    private String userName;

    // 사용자 직급 
    private String userRank;

    // 사용자 부서 
    private String userDept;

    // 사용자 전화번호 
    private String userPhone;

    // 사용자 이메일 
    private String userEmail;

    //사용자 레벨
    private String userLevel;
    
    //계정사용여부
    private String useYn;
    
    // 가입일 
    private String regDt;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRank() {
		return userRank;
	}

	public void setUserRank(String userRank) {
		this.userRank = userRank;
	}

	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	@Override
	public String toString() {
		return "UserVO [userId=" + userId + ", userPw=" + userPw + ", userName=" + userName + ", userRank=" + userRank
				+ ", userDept=" + userDept + ", userPhone=" + userPhone + ", userEmail=" + userEmail + ", userLevel="
				+ userLevel + ", useYn=" + useYn + ", regDt=" + regDt + "]";
	}

}
