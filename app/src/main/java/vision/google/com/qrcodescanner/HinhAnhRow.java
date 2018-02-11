package vision.google.com.qrcodescanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Khoa Tran on 10-02-2018.
 */

public class HinhAnhRow extends BaseAdapter {
    Context context;
    int mylayout;
    List<ClassAddPhone> arrayHinh;

    public HinhAnhRow(Context context, int mylayout, List<ClassAddPhone> arrayHinh) {
        this.context = context;
        this.mylayout = mylayout;
        this.arrayHinh = arrayHinh;
    }

    @Override
    public int getCount() {
        return arrayHinh.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayHinh.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTen, txtIMEI, txtGia;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = view;
        ViewHolder holder = new ViewHolder();
        if (rowview == null) {
            rowview = inflater.inflate(mylayout, null);
            holder.txtTen = (TextView) rowview.findViewById(R.id.RowName);
            holder.txtIMEI = (TextView) rowview.findViewById(R.id.RowIMEI);
            holder.txtGia = (TextView) rowview.findViewById(R.id.RowGia);
            holder.imageView = (ImageView) rowview.findViewById(R.id.RowImg);
            rowview.setTag(holder);
        }
        else{
            holder = (ViewHolder) rowview.getTag();
        }
        holder.txtTen.setText(arrayHinh.get(i).Ten);
        holder.txtIMEI.setText(arrayHinh.get(i).Imei);
        holder.txtGia.setText(arrayHinh.get(i).GiaBan);
        Picasso.with(context).load(arrayHinh.get(i).LinkHinh).into(holder.imageView);

        return rowview;
    }
}
