package capstone.schedulemanager.model;


import java.time.LocalDateTime;

/** This class contains the attributes and methods for customers type objects. This version of customer object
 * includes the name of the city/province and country they are associated with.*/
public class CustsWthNms extends Customers{
    private String divisionName;
    private String countryName;
    private int countryId;

    /**
     * This is the constructor of Customers objects.
     * @param customerId the customer ID to set
     * @param customerName the customer name to set
     * @param address the address to set
     * @param postalCode the postal code to set
     * @param phoneNum the phone number to set
     * @param createDate the create date and time to set
     * @param createdBy the created by user to set
     * @param lastUpdate the last update date and time to set
     * @param lastUpdatedBy the last user to update to set
     * @param divisionId the division ID to set
     * @param countryId The country ID to set
     * @param countryName The country name to set
     * @param divisionName The division name to set*/
    public CustsWthNms(int customerId, String customerName, String address, String postalCode, String phoneNum,
                       LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                       int divisionId, String divisionName, String countryName, int countryId) {
        super(customerId, customerName, address, postalCode, phoneNum, createDate, createdBy, lastUpdate,
                lastUpdatedBy, divisionId);
        this.divisionName = divisionName;
        this.countryName = countryName;
        this.countryId = countryId;
    }

    /**
     * @return the name of the place ID
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @param divisionName the name of the division to set
     * */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * @return the name of the country
     * */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the name of the country to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return The country ID
     * */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId The country ID to set
     * */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
