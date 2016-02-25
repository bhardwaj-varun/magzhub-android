package magzhub.com.app;

/**
 * Created by Dell on 19-Jan-16.
 */
public class Magazine {
    byte[] magazineThumbnail,magazineIssueThumbnail;
    String magazineId,magazineName,subscriptionStatus,readBtn,issueId, magazineIssueName;/*"issueid":13,"issueName":"June (2015)","issueThumbnail":*/
    int result_status;
    public String getMagazineId() {
        return magazineId;
    }

    public byte[] getMagazineIssueThumbnail() {
        return magazineIssueThumbnail;
    }

    public void setMagazineIssueThumbnail(byte[] magazineIssueThumbnail) {
        this.magazineIssueThumbnail = magazineIssueThumbnail;
    }

    public void setMagazineId(String magazineId) {

        this.magazineId = magazineId;
    }

    public String getMagazineIssueName() {
        return magazineIssueName;
    }

    public void setMagazineIssueName(String magazineIssueName) {
        this.magazineIssueName = magazineIssueName;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public byte[] getMagazineThumbnail(){

        return magazineThumbnail;
    }
    public void setMagazineThumbnail(byte[] magazineThumbnail){
        this.magazineThumbnail=magazineThumbnail;
    }
    public String getReadBtn(){
        return readBtn;
    }
    public void setReadBtn(String readBtn){
        this.readBtn=readBtn;
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
