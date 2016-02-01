package mrinalika.dell.com.hfad;

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
 * Created by Dell on 27-Jan-16.
 */
public class SubscribedMagazineAdapter extends ArrayAdapter<Magazine>{
    public ArrayList<Magazine> SubMagazineList;
    LayoutInflater submlvi;
    int submlResource;
    ViewHolder submlholder;
    private String TAG="SubscribedMagazineAdapter";

    public SubscribedMagazineAdapter(Context context,int resource, ArrayList<Magazine> objects){
        super(context,resource,objects);
        submlvi=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        submlResource= resource;
        SubMagazineList=objects;
    //    magazine=new Getting_Magazines();
    }
    @Override
    public View getView(final int position, View convertView,final ViewGroup parent){
        View mlv=convertView;
        if(mlv==null){
            submlholder=new ViewHolder();
            mlv=submlvi.inflate(submlResource,null);
            submlholder.submltvname=(TextView)mlv.findViewById(R.id.subMagName);
            submlholder.submlivThumbnail=(ImageView)mlv.findViewById(R.id.subMagThumbnail);
            submlholder.submlbtnSubscriptionStatus=(Button)mlv.findViewById(R.id.subMagSubsStatus);
            submlholder.submlRead=(Button)mlv.findViewById(R.id.subMagRead);
            submlholder.submlIssue=(Button)mlv.findViewById(R.id.subMagIssue);
            mlv.setTag(submlholder);
        }else{
            submlholder=(ViewHolder)mlv.getTag();
        }
        //Getting_Magazines.magazineId=Integer.parseInt(MagazineList.get(position).getMagazineId());
        submlholder.submltvname.setText(SubMagazineList.get(position).getMagazineName());
        submlholder.submlbtnSubscriptionStatus.setText(SubMagazineList.get(position).getSubscriptionStatus());
        submlholder.submlbtnSubscriptionStatus.setText("Unsubscribe");
        submlholder.submlbtnSubscriptionStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0);
            }
        });
        submlholder.submlRead.setText("Read");
        submlholder.submlRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0);
            }
        });
        submlholder.submlIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0);
            }
        });
        submlholder.submlIssue.setText("Issue");
        submlholder.submlivThumbnail.setImageBitmap(
                BitmapFactory.decodeByteArray(SubMagazineList.get(position).getMagazineThumbnail(), 0, SubMagazineList.get(position).getMagazineThumbnail().length)
        );
        return mlv;
    }
    static class ViewHolder{
        public TextView submltvname;
        public ImageView submlivThumbnail;
        public Button submlbtnSubscriptionStatus;
        public Button submlRead;
        public Button submlIssue;
    }

}
