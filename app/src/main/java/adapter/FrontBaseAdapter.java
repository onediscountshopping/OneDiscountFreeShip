package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.administrator.onediscountfreeship.ODApp;
import com.example.administrator.onediscountfreeship.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import bean.Goods;

/**
 * Created by yhy on 2016/7/26.
 */
public class FrontBaseAdapter extends BaseAdapter {
    private Context context;
    private List<Goods>data;
    private DecimalFormat format = new DecimalFormat("0.00");
    public FrontBaseAdapter(Context context, List<Goods> data) {
        this.context = context;
        if (data != null) {
        this.data = data;
        }else
            this.data = new ArrayList<>();
    }
    public void addList(List<Goods>list){
        data.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return data==null?0:data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_front_lv,parent,false);
            holder = new ViewHolder();
            holder.iv= (ImageView) convertView.findViewById(R.id.iv_item);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvNewPrice= (TextView) convertView.findViewById(R.id.tv_newPrice);
            holder.tvOldPrice= (TextView) convertView.findViewById(R.id.tv_oldPrice);
            holder.tvDiscount = (TextView) convertView.findViewById(R.id.tv_discount);
            holder.tvSaleTotal = (TextView) convertView.findViewById(R.id.tv_saleTotal);
            convertView.setTag(holder);
        }else
        holder = (ViewHolder) convertView.getTag();
        Goods goods = data.get(position);
        holder.tvName.setText(goods.getName());
        holder.tvNewPrice.setText("¥"+format.format(goods.getNewPrice()));
        holder.tvOldPrice.setText("原价¥"+format.format(goods.getOldPrice()));
        holder.tvDiscount.setText(format.format(10*goods.getNewPrice()/goods.getOldPrice())+"折");
        holder.tvSaleTotal.setText("已售"+goods.getSaleTotal()+"件");
        ImageRequest request = new ImageRequest(goods.getProductImg(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.iv.setImageBitmap(response);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                holder.iv.setImageResource(R.mipmap.noresult);
            }
        });
        request.setTag("cancel");
        ODApp.queue.add(request);
        return convertView;
    }

    static class ViewHolder{
        ImageView iv;
        TextView tvName,tvNewPrice,tvOldPrice,tvDiscount,tvSaleTotal;
    }
}
