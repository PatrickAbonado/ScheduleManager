package capstone.schedulemanager.model;

/** This class contains the attributes and methods for Reported User Productivity type objects.*/
public class ReportUsrProd {
    private int userId;
    private String userName;
    private int createdDate;
    private int updatedDate;

    /** This is the constructor of Reported User Productivity objects.*/
    public ReportUsrProd(int userId, String userName, int createdDate, int updatedDate){
        this.userId = userId;
        this.userName = userName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the created
     */
    public int getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the created to set
     */
    public void setCreatedDate(int createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the updated
     */
    public int getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updated to set
     */
    public void setUpdatedDate(int updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
