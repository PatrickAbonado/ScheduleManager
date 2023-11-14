package capstone.schedulemanager.model;

/** This class contains the attributes and methods for Reported User Productivity type objects.*/
public class ReportUsrProd {
    private int userId;
    private String userName;
    private int created;
    private int updated;

    /** This is the constructor of Reported User Productivity objects.*/
    public ReportUsrProd(int userId, String userName, int created, int updated){
        this.userId = userId;
        this.userName = userName;
        this.created = created;
        this.updated = updated;
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
    public int getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(int created) {
        this.created = created;
    }

    /**
     * @return the updated
     */
    public int getUpdated() {
        return updated;
    }

    /**
     * @param updated the updated to set
     */
    public void setUpdated(int updated) {
        this.updated = updated;
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
