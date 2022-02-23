package com.pyfx902.prm391x_project_2_phuchvfx09449;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentAnimal extends Fragment {
    private Animal animal;
    private ImageView ivBGImage, ivIsLike, ivPhone;
    private TextView tvDetailName, tvDetailInfo, tvPhoneNumber;

    public FragmentAnimal(Animal animal) {
        this.animal = animal;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_animal, container, false);
        //Khai báo/Ánh xạ view
        initView(view);

        //Đặt giá trị cho các view
        ivBGImage.setImageBitmap(animal.getBgImg());
        tvDetailName.setText(animal.getName());
        tvDetailInfo.setText(animal.getDetail());
        tvPhoneNumber.setText(MainActivity.luuThongTin.getString(animal.getPath() + " phone", ""));

        if (animal.isLike()) {
            ivIsLike.setImageResource(R.drawable.ic_like);
        } else {
            ivIsLike.setImageResource(R.drawable.ic_not_like);
        }

        //Sự kiện khi click nút yêu thích: Thay đổi ảnh trái tim và cập nhật giá trị isLike
        ivIsLike.setOnClickListener(view1 -> {

            if (animal.isLike()) {
                ivIsLike.setImageResource(R.drawable.ic_not_like);
                animal.setLike(false);

            } else {
                ivIsLike.setImageResource(R.drawable.ic_like);
                animal.setLike(true);
            }
            //Lưu trạng thái yêu thích
            SharedPreferences.Editor editor = MainActivity.luuThongTin.edit();
            editor.putBoolean(animal.getPath() + " isLike", animal.isLike());
            editor.apply();
        });

        //Sự kiện click nút Phone: Mở dialog nhập số điện thoại
        ivPhone.setOnClickListener(view1 -> {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.custom_dialog);
            dialog.show();

            //Khai báo ánh xạ view trong dialog
            ImageView ivAvatar = dialog.findViewById(R.id.ivAvatar);
            EditText etPhone = dialog.findViewById(R.id.etPhone);
            Button btnSave = dialog.findViewById(R.id.btnSave),
                    btnDelete = dialog.findViewById(R.id.btnDelete);

            //Gán giá trị ảnh đại diện và số điện thoại (nếu có)
            ivAvatar.setImageBitmap(animal.getImage());
            etPhone.setText(MainActivity.luuThongTin.getString(animal.getPath(), ""));
            //Sự kiện nút Save và Delete
            btnSave.setOnClickListener(view2 -> {
                String phoneNumber = etPhone.getText().toString().trim();
                //Lưu thông tin số điện thoại
                if (MainActivity.luuThongTin.contains(phoneNumber)) {
                    Toast.makeText(getContext(), "Số điện thoại đã tồn tại", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = MainActivity.luuThongTin.edit();
                    //Xoá thông tin cũ
                    editor.remove(MainActivity.luuThongTin.getString(animal.getPath(), ""));
                    //Lưu thông tin mới
                    editor.putString(animal.getPath() + " phone", phoneNumber);
                    editor.putString(phoneNumber, animal.getPath());
                    editor.apply();

                    tvPhoneNumber.setText(etPhone.getText().toString().trim());
                }
                dialog.cancel();
            });
            btnDelete.setOnClickListener(view2 -> dialog.cancel());
        });

        return view;
    }

    private void initView(View view) {
        ivBGImage = view.findViewById(R.id.ivDetail);
        ivIsLike = view.findViewById(R.id.ivIsLike);
        ivPhone = view.findViewById(R.id.ivPhone);
        tvDetailName = view.findViewById(R.id.tvDetailName);
        tvDetailInfo = view.findViewById(R.id.tvDetailInfo);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
    }
}
