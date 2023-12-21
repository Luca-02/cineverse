package com.example.cineverse.utils.utils_account.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cineverse.utils.utils_account.account_data.ProfileInfoData;
import com.example.cineverse.view.verified_account.fragment.account.fragment_infousr.ProfileInfoDataPager;

import java.util.List;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {

    private List<ProfileInfoData> infoList;

    public ScreenSlidePagerAdapter(Fragment fa, List<ProfileInfoData> infoList) {
        super(fa);
        this.infoList = infoList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        ProfileInfoData data = infoList.get(position);
        Log.d("infoList", infoList.get(position).getText());

        return ProfileInfoDataPager.newInstance(data.getText(), data.getNumber(), data.getImageRes());
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }
}
