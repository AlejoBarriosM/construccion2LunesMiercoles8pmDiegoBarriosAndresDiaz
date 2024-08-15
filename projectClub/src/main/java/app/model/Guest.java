package app.model;

public class Guest {

    private long idGuest;
    private long userIdGuest;
    private long partnerIdGuest;
    private String statusGuest;

    public Guest() {
    }

    public long getIdGuest() {
        return idGuest;
    }

    public void setIdGuest(long idGuest) {
        this.idGuest = idGuest;
    }

    public long getUserIdGuest() {
        return userIdGuest;
    }

    public void setUserIdGuest(long userIdGuest) {
        this.userIdGuest = userIdGuest;
    }

    public long getPartnerIdGuest() {
        return partnerIdGuest;
    }

    public void setPartnerIdGuest(long partnerIdGuest) {
        this.partnerIdGuest = partnerIdGuest;
    }

    public String getStatusGuest() {
        return statusGuest;
    }

    public void setStatusGuest(String statusGuest) {
        this.statusGuest = statusGuest;
    }
}
