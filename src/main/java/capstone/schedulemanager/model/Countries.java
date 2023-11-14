package capstone.schedulemanager.model;

import java.time.LocalDateTime;

/** This class contains the attributes and methods for Countries type objects.*/
public class Countries {
    private int countryId;
    private String countryName;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    /** This is the constructor of Countries objects.
     * @param countryName The country name
     * @param countryId The country ID
     * @param lastUpdate The date and time last updated
     * @param createdBy The user that created the entry
     * @param createDate The date and time the entry was created
     * @param lastUpdatedBy The user that updated the data last*/
    public Countries(int countryId, String countryName, LocalDateTime createDate,
                     String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy){
        this.countryId = countryId;
        this.countryName = countryName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(int countryId){
        this.countryId = countryId;
    }

    /**
     * @return the countryId
     */
    public int getCountryId(){
        return countryId;
    }

    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName){
        this.countryName = countryName;
    }

    /**
     * @return the countryName
     */
    public String getCountryName(){
        return countryName;
    }

    /**
     * @return the createDate
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the lastUpdate
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy the lastUpdatedBy to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

}