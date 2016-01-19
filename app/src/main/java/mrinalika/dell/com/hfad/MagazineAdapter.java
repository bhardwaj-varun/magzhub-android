package mrinalika.dell.com.hfad;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dell on 19-Jan-16.
 */
public class MagazineAdapter extends ArrayAdapter<Magazine> {
    public ArrayList<Magazine> MagazineList;
    LayoutInflater mlvi;
    int mlResource;
    ViewHolder mlholder;
    private String TAG="MAgazineAdapter";
    Getting_Magazines magazine;
    public MagazineAdapter(Context context,int resource, ArrayList<Magazine> objects){
        super(context,resource,objects);
        mlvi=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mlResource= resource;
        MagazineList=objects;
        magazine=new Getting_Magazines();
    }
    @Override
    public View getView(final int position, View convertView,ViewGroup parent){
        View mlv=convertView;
        if(mlv==null){
            mlholder=new ViewHolder();
            mlv=mlvi.inflate(mlResource,null);
            mlholder.mltvname=(TextView)mlv.findViewById(R.id.mtvname);
            mlholder.mlivThumbnail=(ImageView)mlv.findViewById(R.id.ivmThumbnail);
            mlholder.mlbtnSubscriptionStatus=(Button)mlv.findViewById(R.id.mlsubsidbtn);
            mlv.setTag(mlholder);
        }else{
            mlholder=(ViewHolder)mlv.getTag();
        }
        //Getting_Magazines.magazineId=Integer.parseInt(MagazineList.get(position).getMagazineId());
        mlholder.mltvname.setText(MagazineList.get(position).getMagazineName());
        mlholder.mlbtnSubscriptionStatus.setText(MagazineList.get(position).getSubscriptionStatus());
        mlholder.mlivThumbnail.setImageBitmap(
                BitmapFactory.decodeByteArray(MagazineList.get(position).getMagazineThumbnail(), 0, MagazineList.get(position).getMagazineThumbnail().length)
        );
    return mlv;
    }
    static class ViewHolder{
        public TextView mltvname;
        public ImageView mlivThumbnail;
        public Button mlbtnSubscriptionStatus;
    }
 }
