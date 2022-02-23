package com.pyfx902.prm391x_project_2_phuchvfx09449;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {

    private ArrayList<Animal> list;
    private Context context;

    //Khởi tạo adapter
    public AnimalAdapter(ArrayList<Animal> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_animal,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Lưu thông tin và trạng thái yêu thích của con vật vào holder
        holder.ivAnimal.setImageBitmap(list.get(position).getImage());
        holder.tvName.setText(list.get(position).getName());
        if (list.get(position).isLike()) {
            holder.ivLike.setImageResource(R.drawable.ic_like);
        } else {
            holder.ivLike.setImageResource(android.R.color.transparent);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAnimal, ivLike;
        TextView tvName;
        SendData sendData;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAnimal = itemView.findViewById(R.id.ivAnimal);
            ivLike = itemView.findViewById(R.id.ivLike);
            tvName = itemView.findViewById(R.id.tvName);
            sendData = (SendData) context;

            //Sự kiện click vào icon của con vật
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    //Truyền dữ liệu gồm danh sách các con vật và vị trí được click
                    //Áp dụng hiệu ứng mờ
                    sendData.getData(list,position);
                    view.setAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_fade_out));
                }
            });
        }
    }
}
