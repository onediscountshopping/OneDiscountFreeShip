package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.onediscountfreeship.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import bean.Wortharound_bean;

/**
 * Created by 熊二 on 2016/7/26.
 */
public class Wortharound_listviewAdapter extends BaseAdapter {
    private List<Wortharound_bean.RowsBean> data;
    private Context context;

    public Wortharound_listviewAdapter(Context context) {
        data = new ArrayList<>();
        this.context = context;
    }


    public void setData(List<Wortharound_bean.RowsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<Wortharound_bean.RowsBean> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.wortharound_item, parent, false);

            holder = new ViewHolder();

            holder.Discount = (TextView) convertView.findViewById(R.id.Discount);
            holder.IsBaoYou = (TextView) convertView.findViewById(R.id.IsBaoYou);
            holder.Name = (TextView) convertView.findViewById(R.id.W_Name);
            holder.NewPrice = (TextView) convertView.findViewById(R.id.NewPrice);
            holder.OldPrice = (TextView) convertView.findViewById(R.id.OldPrice);
            holder.ProductImg = (ImageView) convertView.findViewById(R.id.W_ProductImg);
            holder.SaleTotal= (TextView) convertView.findViewById(R.id.SaleTotal);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Wortharound_bean.RowsBean wortharoundBean = data.get(position);

        DecimalFormat df = new DecimalFormat("#.00");

        double n = wortharoundBean.getOldPrice();
        double x = wortharoundBean.getNewPrice();
        double p = wortharoundBean.getDiscount();
        String m = df.format(n);
        String y = df.format(x);
        String q = df.format(p);

        holder.Discount.setText(q);
        holder.ProductImg.setImageResource(R.mipmap.kuzi);
        holder.OldPrice.setText(m);
        holder.NewPrice.setText(y);
        holder.Name.setText(wortharoundBean.getName());
        holder.SaleTotal.setText(wortharoundBean.getSaleTotal()+"");

        String productImg = wortharoundBean.getProductImg();
        Picasso.with(context)
                .load(productImg)
                .placeholder(R.mipmap.kuzi)
                .error(R.mipmap.ic_launcher)
                .config(Bitmap.Config.ARGB_8888)
                .resize(180,140)
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {

                        return source;
                    }
                    @Override
                    public String key() {
                        return "key";
                    }
                })
                .into(holder.ProductImg);
        return convertView;
    }

    static class ViewHolder {
        ImageView ProductImg;
        TextView Name, NewPrice, OldPrice, Discount, SaleTotal, IsBaoYou;
    }
}
