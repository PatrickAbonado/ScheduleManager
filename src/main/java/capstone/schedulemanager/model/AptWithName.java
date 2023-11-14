package capstone.schedulemanager.model;

import java.time.LocalDateTime;

/** This class contains the attributes and methods for the Appointments
 * with the addition of contact and customer names. This class was made for
 * the list output of contact and customer names through observable list arrays.*/
public class AptWithName extends Appointments{
    private String customerName;
    private String contactName;

    /** This is the constructor of Appointments with names objects.
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
     * @param userId The user ID
     * @param contactName The name of the contact
     * @param customerName The name of the customer*/
    public AptWithName(int appointmentId, String title, String description, String location,
                        String type, LocalDateTime startDateAndTime, LocalDateTime endDateAndTime,
                       LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy,
                       int customerId, int userId, int contactId, String customerName, String contactName){

        super(appointmentId, title, description,location, type, startDateAndTime, endDateAndTime,
                createDate, createdBy, lastUpdate,  lastUpdateBy,  customerId,  userId,  contactId);

        this.contactName = contactName;
        this.customerName = customerName;

    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName the contactName to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
