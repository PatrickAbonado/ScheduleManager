package capstone.schedulemanager.dao;

import capstone.schedulemanager.utilities.helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** This class provides methods to access and manipulate contacts data from the contacts table in the mysql database.*/
public abstract class ContactsData {

    /** This method queries the contacts table for all contacts data. The data is then stored as objects in an
     * array list.
     * @return contacts The list of contacts and their data on the database*/
    public static ArrayList<capstone.schedulemanager.model.Contacts> getCtsList() {

        ArrayList<capstone.schedulemanager.model.Contacts> contacts = new ArrayList<>();

        try{
            String sql = "SELECT * from contacts";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                capstone.schedulemanager.model.Contacts contact = new capstone.schedulemanager.model.Contacts(contactId, contactName, email);

                contacts.add(contact);
            }

        } catch (SQLException e) {
            helpers.databsConErrMsg();
        }

        return contacts;
    }

    /** This method gets the contact ID from the contact's String name.
     * @param contName The name whose contact ID is being requested
     * @return id The contact ID of the contact name*/
    public static int getContactId(String contName){
        int id = 0;

        for (capstone.schedulemanager.model.Contacts contact: getCtsList()) {
            if(contact.getContactName().equals(contName)){
                id = contact.getContactId();
            }
        }

        return id;
    }

    /** This method gets the contact's name from their contact ID.
     * @param contactId The contact ID whose name is requested
     * @return name The name associated with the contact ID*/
    public static String getContactName(int contactId){
        String name = "";

        for (capstone.schedulemanager.model.Contacts contact: getCtsList()) {
            if(contact.getContactId() == contactId){
                name = contact.getContactName();
            }
        }

        return name;
    }

}
