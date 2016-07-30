package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.example.administrator.onediscountfreeship.ODApp;
import com.example.administrator.onediscountfreeship.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import bean.Fengqiang;

/**
 * Created by yhy on 2016/7/28.
 */
public class FengqiangAdapter extends BaseAdapter {
    private Context context;
    private List<Fengqiang>list;
    private DecimalFormat format = new DecimalFormat("0.0");
    public FengqiangAdapter(Context context, List<Fengqiang> list) {
        this.context = context;
        if (list != null) {
        this.list = list;
        }else
            this.list=new ArrayList<>();
    }
    public void addList(List<Fengqiang>data){
        list.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fengqiang,parent, false);
            holder = new ViewHolder();
            holder.ivSml= (ImageView) convertView.findViewById(R.id.iv_fengqiang_sml);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_fengqiang_name);
            holder.tvDisCount = (TextView) convertView.findViewById(R.id.tv_fengqiang_discount);
            holder.tvEndDate = (TextView) convertView.findViewById(R.id.tv_fengqiang_endDate);
            holder.tv1= (TextView) convertView.findViewById(R.id.tv_fengqiang1);
            holder.tv2= (TextView) convertView.findViewById(R.id.tv_fengqiang2);
            holder.tv3= (TextView) convertView.findViewById(R.id.tv_fengqiang3);
            convertView.setTag(holder);
        }else
        holder = (ViewHolder) convertView.getTag();
        Fengqiang fengqiang = list.get(position);
        holder.tvName.setText(fengqiang.getName());
        holder.tvDisCount.setText(format.format(fengqiang.getDisCount())+"折起");
        String endDate = fengqiang.getEndDate();
        String substring = endDate.substring(endDate.indexOf("(") + 1, endDate.indexOf("+"));
        long mtime=Long.parseLong(substring)-System.currentTimeMillis();
        int hour= (int) (mtime/3600000);
        if(hour>23)
            holder.tvEndDate.setText("剩1天");
        else
            holder.tvEndDate.setText("剩"+hour+"小时");
        setImage(holder.ivSml,fengqiang.getImgUrlSml());
        List<Fengqiang.ProductInfo> infos = fengqiang.getInfos();
        TextView []tvs={holder.tv1, holder.tv2,holder.tv3};
        for (int i = 0; i < infos.size(); i++) {
            Fengqiang.ProductInfo productInfo = infos.get(i);
            setImage(tvs[i],productInfo.getProductImg());
            double discount=productInfo.getNewPrice()/productInfo.getOldPrice();
            tvs[i].setText("¥"+productInfo.getNewPrice()+"  "+format.format(discount)+"折");
        }
        return convertView;
    }

    private void setImage(final View view, String url) {
        ImageRequest request=new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                if(view instanceof ImageView){
                     ((ImageView) view).setImageBitmap(response);
                }else
                    view.setBackground(new BitmapDrawable(response));
            }
        },0,0, Bitmap.Config.RGB_565,null );
       request.setTag("cancel");
        ODApp.queue.add(request);
    }

    static class ViewHolder{
        ImageView ivSml;
        TextView tvName,tvEndDate,tvDisCount,tv1,tv2,tv3;
    }
}
