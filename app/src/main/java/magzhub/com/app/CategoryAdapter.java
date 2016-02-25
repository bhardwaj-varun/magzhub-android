package magzhub.com.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import java.util.ArrayList;

/**
 * Created by Dell on 17-Jan-16.
 */
public class CategoryAdapter extends ArrayAdapter<Category>{
    ArrayList<Category> CategoryList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public CategoryAdapter(Context context,int resource,ArrayList<Category> objects){
        super(context,resource,objects);
        vi=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        Resource=resource;
        CategoryList=objects;
    }
    @Override
    public View getView(int position,View convertView, ViewGroup parent){
        View v=convertView;
        if(v==null){
            holder= new ViewHolder();
            v=vi.inflate(Resource,null);
            holder.tvName = (TextView) v.findViewById(R.id.tvname);
            // holder.tvId = (TextView) v.findViewById(R.id.tvId);
            holder.ivThumbnail=(ImageView) v.findViewById(R.id.ivThumbnail);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        //    holder.imageview.setImageResource(R.drawable.ic_action_search);
        //new DownloadImageTask(holder.imageview).execute(CategoryList.get(position).getThumbnail());
        holder.tvName.setText(CategoryList.get(position).getName());
        holder.ivThumbnail.setImageBitmap(
                BitmapFactory.decodeByteArray(CategoryList.get(position).getThumbnail(), 0,CategoryList.get(position).getThumbnail().length)
        );
        //holder.tvId.setText(CategoryList.get(position).getId());
        //holder.ivThumbnail(CategoryList.get(position).getThumbnail());
        return v;
    }

    static class ViewHolder{
        public TextView tvName;
        public ImageView ivThumbnail;
    }
}
