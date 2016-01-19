package mrinalika.dell.com.hfad;

/**
 * Created by Dell on 19-Jan-16.
 */
public class Magazine {
    byte[] magazineThumbnail;
    String magazineId,magazineName,subscriptionStatus;
    int result_status;
    public String getMagazineId() {
        return magazineId;
    }
    public void setMagazineId(String magazineId) {
        this.magazineId = magazineId;
    }
    public byte[] getMagazineThumbnail(){
        return magazineThumbnail;
    }
    public void setMagazineThumbnail(byte[] magazineThumbnail){
        this.magazineThumbnail=magazineThumbnail;
    }
    public String getMagazineName(){
        return magazineName;
    }
    public void setMagazineName(String magazineName){
        this.magazineName=magazineName;
    }
    public String getSubscriptionStatus()
    {
        return subscriptionStatus;
    }
    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }
    public int getResult_status() {
        return result_status;
    }
    public void setResult_status(int result_status) {
        this.result_status = result_status;
    }
}
