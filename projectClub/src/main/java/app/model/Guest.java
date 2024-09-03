package app.model;

public class Guest {

    private long idGuest;
    private User userIdGuest;
    private Partner partnerIdGuest;
    private String statusGuest;

    public Guest() {
    }

    public long getIdGuest() {
        return idGuest;
    }

    public void setIdGuest(long idGuest) {
        this.idGuest = idGuest;
    }

    public User getUserIdGuest() {
        return userIdGuest;
    }

    public void setUserIdGuest(User userIdGuest) {
        this.userIdGuest = userIdGuest;
    }

    public Partner getPartnerIdGuest() {
        return partnerIdGuest;
    }

    public void setPartnerIdGuest(Partner partnerIdGuest) {
        this.partnerIdGuest = partnerIdGuest;
    }

    public String getStatusGuest() {
        return statusGuest;
    }

    public void setStatusGuest(String statusGuest) {
        this.statusGuest = statusGuest;
    }
}

