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
    public View getView(final int position, View convertView,final ViewGroup parent){
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
        mlholder.mlbtnSubscriptionStatus.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){
                ((ListView) parent).performItemClick(v, position, 0);
             //Log.e(TAG, "Button Clicked"+"Mag Id"+MagazineList.get(position).getMagazineId()+" id"+v.getId()+"Text"+ mlholder.mlbtnSubscriptionStatus.getText());
             //new Getting_Magazines().setMagazineId(Integer.parseInt(MagazineList.get(position).getMagazineId()));
            }
        });
      /*  mlholder.mlbtnSubscriptionStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting_Magazines.magazineId=Integer.parseInt(MagazineList.get(position).getMagazineId());
               /* ((ListView) parent).performItemClick(v, position, 0);
            }
        });*/
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

