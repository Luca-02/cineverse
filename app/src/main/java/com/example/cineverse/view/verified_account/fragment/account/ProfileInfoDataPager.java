package com.example.cineverse.view.verified_account.fragment.account;

import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cineverse.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileInfoDataPager#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileInfoDataPager extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_NAME = "arg_text";
    private static final String ARG_NUMBER = "arg_number";
    private static final String ARG_IMAGE_RES = "arg_image_res";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView infoImageView;
    private TextView infoNameView;
    private TextView infoNumberTextView;
    public ProfileInfoDataPager() {
        // Required empty public constructor
    }

    public static ProfileInfoDataPager newInstance(String text, int number, int imageRes) {
        ProfileInfoDataPager fragment = new ProfileInfoDataPager();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, text);
        args.putInt(ARG_NUMBER, number);
        args.putInt(ARG_IMAGE_RES, imageRes);
        fragment.setArguments(args);
        Log.d("nome utente", ARG_NAME);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileInfoDataPager.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileInfoDataPager newInstance(String param1, String param2) {
        ProfileInfoDataPager fragment = new ProfileInfoDataPager();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_info_data_pager, container, false);
        infoImageView = view.findViewById(R.id.infoImagePagerProfile);
        infoNameView = view.findViewById(R.id.infoNamePagerProfile);
        infoNumberTextView = view.findViewById(R.id.infoNumberPagerProfile);

        getInfoUpdate();

        return view;
    }

    public void getInfoUpdate(){
        // Retrieve arguments and set content
        Bundle args = getArguments();
        if (args != null) {
            infoNameView.setText(args.getString(ARG_NAME));
            infoNumberTextView.setText(String.valueOf(args.getInt(ARG_NUMBER)));
            infoImageView.setImageResource(args.getInt(ARG_IMAGE_RES));
        }else {
            Log.e("ProfileInfoDataPager", "Arguments are null");
        }
    }

}