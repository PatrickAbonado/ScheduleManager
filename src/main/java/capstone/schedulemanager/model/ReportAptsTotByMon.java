package capstone.schedulemanager.model;

/** This class contains the attributes and methods for Reported Appointments Totals By Month type objects.*/
public class ReportAptsTotByMon {
    private String type;
    private int total;

    /** This is the constructor of Reported Appointments Totals By Month objects.
     * @param type The type of appointment
     * @param total The total number of appointments*/
    public ReportAptsTotByMon(String type, int total){
        this.type = type;
        this.total = total;
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
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }
}
