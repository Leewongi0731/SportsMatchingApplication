package com.example.physicalplatform.setting;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.physicalplatform.DataBase;
import com.example.physicalplatform.MainPageActivity;
import com.example.physicalplatform.R;

public class SettingFragment extends Fragment {
    private ViewGroup viewGroup;
    private Context context;

    private TextView settingLoginUserName;
    private TextView textViewSettingNotice;
    private TextView textViewSettingQuestions;
    private ImageView settingLoginUserScore;

    private AppCompatActivity activity;
    private FragmentTransaction transaction;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.setting_page, container, false);
        context = container.getContext();


        settingLoginUserName = viewGroup.findViewById(R.id.settingLoginUserName);
        settingLoginUserName.setText( DataBase.MEMBER_DB.get(  MainPageActivity.LOGIN_USER_ID ).getName() );

        // set medal
        settingLoginUserScore = viewGroup.findViewById(R.id.settingLoginUserScore);
        String score = DataBase.MEMBER_DB.get(  MainPageActivity.LOGIN_USER_ID ).getScore();
        if( score == "금상" ) {
            settingLoginUserScore.setImageResource( R.drawable.ic_medal_1 );
        }else if(score == "은상"){
            settingLoginUserScore.setImageResource( R.drawable.ic_medal_2 );
        }else if(score == "동상"){
            settingLoginUserScore.setImageResource( R.drawable.ic_medal_3 );
        }else{
            settingLoginUserScore.setImageResource( R.drawable.ic_medal_4 );
        }

        textViewSettingNotice = viewGroup.findViewById(R.id.textViewSettingNotice);
        textViewSettingNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity = (AppCompatActivity)v.getContext();
                transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new SettingNoticeFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        textViewSettingQuestions = viewGroup.findViewById(R.id.textViewSettingQuestions);
        textViewSettingQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity = (AppCompatActivity)v.getContext();
                transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new SettingQuestionFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        
        return viewGroup;
    }
}