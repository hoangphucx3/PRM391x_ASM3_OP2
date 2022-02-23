package com.pyfx902.prm391x_project_2_phuchvfx09449;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.net.ssl.ManagerFactoryParameters;

public class MainActivity extends AppCompatActivity implements SendData {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ImageView ivBird, ivMammal, ivSea;
    private ArrayList<Animal> listBird, listMammal, listSea;
    private AnimalAdapter adapter;
    private RecyclerView recyclerView;
    private ViewPager2 viewPager2;
    public static SharedPreferences luuThongTin;
    private final int REQUEST_PERMISSION_CALL = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Kiểm tra cấp quyền
        requestPermission();
        //Ánh xạ view
        initView();

        //Cài đặt menu đóng/mở DrawerLauyout
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        //Cài đặt layout cho RecyclerView
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //Sự kiện click chọn loại động vật
        ivBird.setOnClickListener(view -> showListAnimal(listBird));
        ivMammal.setOnClickListener(view -> showListAnimal(listMammal));
        ivSea.setOnClickListener(view -> showListAnimal(listSea));
    }

    //Hiển thị danh sách các con vật trong RecyclerView
    //Đồng thời đóng DrawerLayout và ViewPager (nếu ViewPage đang mở)
    private void showListAnimal(ArrayList<Animal> listAnimal) {
        adapter = new AnimalAdapter(listAnimal, MainActivity.this);
        recyclerView.setAdapter(adapter);
        drawerLayout.close();
        viewPager2.setVisibility(View.GONE);
    }

    //Sự kiện click menu để đóng/mở DrawerLayout
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Thêm danh sách các con vật theo từng loài
    //Sử dụng dữ liệu trong assets
    public ArrayList<Animal> addListAnimal(String animalType) {
        ArrayList<Animal> listAnimal = new ArrayList<>();
        try {
            //Gán tên file trong folder animalTyper vào danh sách fileNames
            String[] fileNames = getApplicationContext().getAssets().list(animalType);

            for (String fileName : fileNames) {
                //tách lấy tên con vật từ tên file có dạng "ic_name.png"
                String name = fileName.substring(3, fileName.indexOf("."));
                //Đường dẫn tới file ảnh icon có định dạng .png
                String imgPath = animalType + "/" + fileName;
                Bitmap image = BitmapFactory.decodeStream(getApplicationContext().getAssets().open(imgPath));
                //Đường dẫn tới file ảnh background có định dạng .jpg
                String bgPath = "detail/photo/" + name + ".jpg";
                Bitmap bgImg = BitmapFactory.decodeStream(getApplicationContext().getAssets().open(bgPath));
                //Đường dẫn tới file text chứa mô tả về loài vật
                String detailPath = "detail/text/" + name + ".txt";
                InputStream inputStream = getApplicationContext().getAssets().open(detailPath);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder detail = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    detail.append(line).append("\n");
                }
                //Thêm con vật vào danh sách
                Animal animal = new Animal(name.toUpperCase(), image, bgImg, imgPath, detail.toString());
                //Cập nhật trạng thái yêu thích đã được lưu (nếu có)
                animal.setLike(luuThongTin.getBoolean(animal.getPath() + " isLike", false));
                listAnimal.add(animal);
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listAnimal;
    }

    //Ánh xạ view
    //Thêm danh sách các loài vật
    private void initView() {
        luuThongTin = getSharedPreferences("favAnimal", Context.MODE_PRIVATE);
        drawerLayout = findViewById(R.id.drawer_layout);
        ivBird = findViewById(R.id.title_bird);
        ivMammal = findViewById(R.id.title_mammal);
        ivSea = findViewById(R.id.title_sea);
        listBird = new ArrayList<>();
        listBird = addListAnimal("bird");
        listMammal = new ArrayList<>();
        listMammal = addListAnimal("mammal");
        listSea = new ArrayList<>();
        listSea = addListAnimal("sea");
        recyclerView = findViewById(R.id.list_detail);
        recyclerView.setHasFixedSize(true);
        viewPager2 = findViewById(R.id.view_pager_detail);
        viewPager2.setVisibility(View.GONE);
    }

    //Kiểm tra cấp quyền
    private void requestPermission() {
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Bạn cần cấp quyền để sử dụng ứng dụng", Toast.LENGTH_SHORT).show();
            requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CALL_LOG
            }, REQUEST_PERMISSION_CALL);
        }
    }

    //Truyền dữ liệu khi click vào ảnh con vật
    //Sử dụng dữ liệu để hiển thị thông tin chi tiết con vật bằng ViewPager
    @Override
    public void getData(ArrayList<Animal> animals, int position) {
        viewPager2.setVisibility(View.VISIBLE);

        PageViewAdapter pageViewAdapter = new PageViewAdapter(this, animals);
        viewPager2.setAdapter(pageViewAdapter);
        viewPager2.setCurrentItem(position, false);
        viewPager2.setAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_fade_in));
    }

    //Cài đặt chức năng nút back
    @Override
    public void onBackPressed() {
        if (viewPager2.isShown()) {
            //Khi ViewPage đang hiển thị, bấm Back sẽ đóng màn hình ViewPage thay vì thoát app
            //Đồng thời cập nhật danh sách con vật để hiện thị thay đổi giá trị yêu thích
            viewPager2.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            super.onBackPressed();
        }
    }
}