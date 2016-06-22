package vo;

public class UserAccount {
	int m_UserId;
	int m_UserType;
	String m_UserEmail;
	String m_UserPasswordMd5;
	
	public UserAccount(int UserId,int UserType,String UserEmail,String UserPasswordMd5) {
		this.m_UserId = UserId;
		this.m_UserType = UserId;
		this.m_UserEmail = UserEmail;
		this.m_UserPasswordMd5 = UserPasswordMd5;
	}
	
	public int getM_UserId() {
		return m_UserId;
	}
	public void setM_UserId(int m_UserId) {
		this.m_UserId = m_UserId;
	}
	public String getM_UserEmail() {
		return m_UserEmail;
	}
	public void setM_UserEmail(String m_UserEmail) {
		this.m_UserEmail = m_UserEmail;
	}
	public String getM_UserPasswordMd5() {
		return m_UserPasswordMd5;
	}
	public void setM_UserPasswordMd5(String m_UserPasswordMd5) {
		this.m_UserPasswordMd5 = m_UserPasswordMd5;
	}

	public int getM_UserType() {
		return m_UserType;
	}

	public void setM_UserType(int m_UserType) {
		this.m_UserType = m_UserType;
	}
	
}
