package magzhub.com.app;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dell on 30-Jan-16.
 */
public class IssueSubscribedMagazineAdapter extends ArrayAdapter<Magazine> {
    public ArrayList<Magazine> issueSubMagazineList;
    LayoutInflater issuesubmlvi;
    int issuesubmlResource;
    ViewHolder issuesubmlholder;
    private String TAG="IssueSubscribedMagazineAdapter";

    public IssueSubscribedMagazineAdapter(Context context,int resource, ArrayList<Magazine> objects){
        super(context,resource,objects);
        issuesubmlvi=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        issuesubmlResource= resource;
        issueSubMagazineList=objects;
        //    magazine=new Getting_Magazines();
        /*"issueid":13,"issueName":"June (2015)","issueThumbnail":*/
    }
    @Override
    public View getView(final int position, View convertView,final ViewGroup parent){
        View mlv=convertView;
        if(mlv==null){
            issuesubmlholder=new ViewHolder();
            mlv=issuesubmlvi.inflate(issuesubmlResource,null);
            issuesubmlholder.submlivIssueName=(TextView)mlv.findViewById(R.id.issuedMagIssueName);
            issuesubmlholder.submlivThumbnail=(ImageView)mlv.findViewById(R.id.issuesubMagThumbnail);
            issuesubmlholder.submltvname=(TextView)mlv.findViewById(R.id.issuesubMagName);
            issuesubmlholder.submlRead=(Button)mlv.findViewById(R.id.issuesubMagRead);
            mlv.setTag(issuesubmlholder);
        }else{
            issuesubmlholder=(ViewHolder)mlv.getTag();
        }
        //Getting_Magazines.magazineId=Integer.parseInt(MagazineList.get(position).getMagazineId());
        issuesubmlholder.submltvname.setText(issueSubMagazineList.get(position).getMagazineName());
        issuesubmlholder.submlRead.setText("Read");
        issuesubmlholder.submlRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0);
            }
        });
        issuesubmlholder.submlivIssueName.setText(issueSubMagazineList.get(position).getMagazineIssueName());
        issuesubmlholder.submlivThumbnail.setImageBitmap(
                BitmapFactory.decodeByteArray(issueSubMagazineList.get(position).getMagazineIssueThumbnail(), 0, issueSubMagazineList.get(position).getMagazineIssueThumbnail().length)
        );
        return mlv;
    }
    static class ViewHolder{
        public TextView submltvname;
        public ImageView submlivThumbnail;
        public TextView submlivIssueName;
        public Button submlRead;

    }





}
