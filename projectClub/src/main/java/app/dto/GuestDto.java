package app.dto;


public class GuestDto {

    private long idGuest;
    private UserDto userIdGuest;
    private PartnerDto partnerIdGuest;
    private String statusGuest;

    public GuestDto() {}

    public long getIdGuest() {
        return idGuest;
    }

    public void setIdGuest(long idGuest) {
        this.idGuest = idGuest;
    }

    public UserDto getUserIdGuest() {
        return userIdGuest;
    }

    public void setUserIdGuest(UserDto userIdGuest) {
        this.userIdGuest = userIdGuest;
    }

    public PartnerDto getPartnerIdGuest() {
        return partnerIdGuest;
    }

    public void setPartnerIdGuest(PartnerDto partnerIdGuest) {
        this.partnerIdGuest = partnerIdGuest;
    }

    public String getStatusGuest() {
        return statusGuest;
    }

    public void setStatusGuest(String statusGuest) {
        this.statusGuest = statusGuest;
    }
}

