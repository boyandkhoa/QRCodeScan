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

public class HinhAnhAdapter extends BaseAdapter {
    Context context;
    int mylayout;
    List<ClassAddPhone> arrayHinh;

    public HinhAnhAdapter(Context context, int mylayout, List<ClassAddPhone> arrayHinh) {
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
        ImageView imageHinh;
        TextView txtTen, txtIMEI, txtGia, txtLoai, txtNguoiNhap, txtNgayNhap;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = view;
        ViewHolder holder = new ViewHolder();
        if (rowview == null) {
            rowview = inflater.inflate(mylayout, null);
            holder.txtIMEI = (TextView) rowview.findViewById(R.id.RowIMEI);
            holder.txtTen = (TextView) rowview.findViewById(R.id.RowName);
            holder.txtLoai = (TextView) rowview.findViewById(R.id.RowLoai);
            holder.txtGia = (TextView) rowview.findViewById(R.id.RowGia);
            holder.txtNgayNhap = (TextView) rowview.findViewById(R.id.RowNgayNhap);
            holder.txtNguoiNhap = (TextView) rowview.findViewById(R.id.RowNguoiNhap);
            holder.imageHinh = (ImageView) rowview.findViewById(R.id.RowImg);
            rowview.setTag(holder);
        } else {
            holder = (ViewHolder) rowview.getTag();
        }
        holder.txtIMEI.setText("IMEI: " + arrayHinh.get(i).Imei);
        holder.txtTen.setText("Tên: " + arrayHinh.get(i).Ten);
        holder.txtLoai.setText("Loại: " + arrayHinh.get(i).Loai);
        holder.txtGia.setText("Giá: " + arrayHinh.get(i).GiaBan);
        holder.txtNgayNhap.setText("Ngày nhập: " + arrayHinh.get(i).NgayNhap);
        holder.txtNguoiNhap.setText("Người nhập: " + arrayHinh.get(i).NguoiNhap);
        Picasso.with(context).load(arrayHinh.get(i).LinkHinh).into(holder.imageHinh);

        return rowview;
    }
}
