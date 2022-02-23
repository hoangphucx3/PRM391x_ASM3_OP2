package com.pyfx902.prm391x_project_2_phuchvfx09449;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class PageViewAdapter extends FragmentStateAdapter {

    private ArrayList<Animal> listAnimal;

    public PageViewAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Animal> listAnimal) {
        super(fragmentActivity);
        this.listAnimal = listAnimal;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new FragmentAnimal(listAnimal.get(position));
    }

    @Override
    public int getItemCount() {
        return listAnimal.size();
    }
}
