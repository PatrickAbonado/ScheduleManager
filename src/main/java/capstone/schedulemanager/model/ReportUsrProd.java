package capstone.schedulemanager.model;

/** This class contains the attributes and methods for Reported User Productivity type objects.*/
public class ReportUsrProd {
    private int userId;
    private String userName;
    private int createdTotal;
    private int updatedTotal;

    /** This is the constructor of Reported User Productivity objects.*/
    public ReportUsrProd(int userId, String userName, int createdTotal, int updatedTotal){
        this.userId = userId;
        this.userName = userName;
        this.createdTotal = createdTotal;
        this.updatedTotal = updatedTotal;
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
    public int getCreatedTotal() {
        return createdTotal;
    }

    /**
     * @param createdTotal the created to set
     */
    public void setCreatedTotal(int createdTotal) {
        this.createdTotal = createdTotal;
    }

    /**
     * @return the updated
     */
    public int getUpdatedTotal() {
        return updatedTotal;
    }

    /**
     * @param updatedTotal the updated to set
     */
    public void setUpdatedTotal(int updatedTotal) {
        this.updatedTotal = updatedTotal;
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
