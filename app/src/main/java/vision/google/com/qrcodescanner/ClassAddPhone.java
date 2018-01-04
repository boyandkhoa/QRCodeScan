package vision.google.com.qrcodescanner;

import java.util.Date;

/**
 * Created by Khoa Tran on 01-01-2018.
 */

public class ClassAddPhone {
    public String Ten;
    public String Loai;
    public Integer GiaBan;
    public String NguoiNhap;
    public String NgayNhap;

    public ClassAddPhone() {
    }

    public ClassAddPhone(String ten, String loai, Integer giaBan, String nguoiNhap, String ngayNhap) {
        Ten = ten;
        Loai = loai;
        GiaBan = giaBan;
        NguoiNhap = nguoiNhap;
        NgayNhap = ngayNhap;
    }
}
