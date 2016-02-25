package magzhub.com.app;

/**
 * Created by Dell on 17-Jan-16.
 */
public class Category {
    private String id, name;
    private byte[] thumbnail;
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
    }
    public byte[] getThumbnail(){
        return  thumbnail;
    }
    public void setThumbnail(byte[] thumbnail){
        this.thumbnail=thumbnail;
    }
    public String getName(){
        return name;
    }
    public void setName(String  name){
        this.name=name;
    }
}
