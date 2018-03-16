package vision.google.com.qrcodescanner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Khoa Tran on 10-02-2018.
 */

public class NhanVienAdapter extends BaseAdapter {
    Activity context;
    LayoutInflater layoutInflater;
    int mylayout;
    List<ClassAddUser> arrayHinh;

    public NhanVienAdapter(Activity context, int mylayout, List<ClassAddUser> arrayHinh) {
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
        TextView txtTen,txtSDTNhanVien;
        Button btnActive,btnAdmin;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = view;
        ViewHolder holder = new ViewHolder();
        if (rowview == null) {
            rowview = inflater.inflate(mylayout, null);
            holder.txtTen = (TextView) rowview.findViewById(R.id.txtRowNhanVien);
            holder.txtSDTNhanVien = (TextView) rowview.findViewById(R.id.txtRowSDTNhanVien);
            holder.btnActive = (Button) rowview.findViewById(R.id.btnRowActive);
            holder.btnAdmin = (Button) rowview.findViewById(R.id.btnRowAdmin);
            rowview.setTag(holder);
        } else {
            holder = (ViewHolder) rowview.getTag();
        }
        holder.txtTen.setText(arrayHinh.get(i).HoTen);
        holder.txtSDTNhanVien.setText(arrayHinh.get(i).NumberPhone);
        if (arrayHinh.get(i).Active.equals("0")){
            holder.btnActive.setText("Khóa");
            holder.btnActive.setBackgroundResource(R.drawable.radius_button4);
        }
        else{
            holder.btnActive.setText("Mở");
            holder.btnActive.setBackgroundResource(R.drawable.radius_button2);
        }

        if (arrayHinh.get(i).Admin.equals("0")){
            holder.btnAdmin.setText("NV");
            holder.btnAdmin.setBackgroundResource(R.drawable.radius_button);
        }
        else{
            holder.btnAdmin.setText("QL");
            holder.btnAdmin.setBackgroundResource(R.drawable.radius_button3);
        }

        holder.btnActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Active", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Admin", Toast.LENGTH_SHORT).show();
            }
        });

        return rowview;
    }



}
