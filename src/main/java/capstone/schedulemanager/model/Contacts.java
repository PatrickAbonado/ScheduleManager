package capstone.schedulemanager.model;

/** This class contains the attributes and methods for Appointment type objects.*/
public class Contacts {
    private int contactId;
    private String contactName;
    private String email;

    /** This is the constructor of Contacts objects.
     * @param contactName The contact name
     * @param contactId The contact ID
     * @param email The email*/
    public Contacts(int contactId, String contactName, String email){
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
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

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
