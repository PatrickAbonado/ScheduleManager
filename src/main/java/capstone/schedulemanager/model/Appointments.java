package capstone.schedulemanager.model;

import java.time.LocalDateTime;

/**
 *
 * @author Patrick Abonado 003619914
 */

/** This class contains the attributes and methods for Appointment type objects.*/
public class Appointments {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private String createdBy;
    private String lastUpdateBy;
    private int customerId;
    private int userId;
    private int contactId;
    private LocalDateTime startDateAndTime;
    private LocalDateTime endDateAndTime;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdate;


    /** This is the constructor of Appointment objects.
     * @param appointmentId The appointment ID
     * @param contactId The contact ID
     * @param title The title of the appointment
     * @param createDate The date and time the appointment was created
     * @param createdBy The user that created the appointment
     * @param customerId The customer's ID
     * @param description The description of the appointment
     * @param endDateAndTime The end of the appointment date and time
     * @param lastUpdate The date and time of the appointment's last update
     * @param lastUpdateBy The last user to update the appointment
     * @param location The location of the appointment
     * @param startDateAndTime The start date and time of the appointment
     * @param type The type of the appointment
     * @param userId The user ID*/
    public Appointments(int appointmentId, String title, String description, String location,
                        String type, LocalDateTime startDateAndTime, LocalDateTime endDateAndTime, LocalDateTime createDate, String createdBy,
                        LocalDateTime lastUpdate, String lastUpdateBy, int customerId, int userId, int contactId){

        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.createdBy = createdBy;
        this.lastUpdateBy = lastUpdateBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the appointmentId
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * @param appointmentId the appointmentId to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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
     * @return the lastUpdateBy
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * @param lastUpdateBy the lastUpdateBy to set
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
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
     * @return the contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @param contactId the contactId to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the startDateAndTime
     */
    public LocalDateTime getStartDateAndTime() {
        return startDateAndTime;
    }

    /**
     * @param startDateAndTime the startDateAndTime to set
     */
    public void setStartDateAndTime(LocalDateTime startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }

    /**
     * @return the endDateAndTime
     */
    public LocalDateTime getEndDateAndTime() {
        return endDateAndTime;
    }

    /**
     * @param endDateAndTime the endDateAndTime to set
     */
    public void setEndDateAndTime(LocalDateTime endDateAndTime) {
        this.endDateAndTime = endDateAndTime;
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

}

