package com.example.physicalplatform.health;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class HealthTestResultFragment extends Fragment {
    private ViewGroup viewGroup;
    private Context context;

    private TextView textViewMedal;
    private ImageView healthTestMedalImage;
    private String test1;
    private String test2;
    private String test3;
    private TextView healthTestUserName;
    private TextView healthTestUserPositionAge;
    private Button healthTestDetailTestBtn;
    private Button healthTestMainBtn;

    private AppCompatActivity activity;
    private FragmentTransaction transaction;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.health_test_result, container, false);
        context = container.getContext();


        Bundle bundle=getArguments();
        if(bundle !=null) {
            test1 = bundle.getString("test1");
            test2 = bundle.getString("test2");
            test3 = bundle.getString("test3");
        }

        updateUserScore();

        getRecommendExercise();

        // set medal
        textViewMedal = viewGroup.findViewById(R.id.textViewMedal);
        healthTestMedalImage = viewGroup.findViewById(R.id.healthTestMedalImage);
        String score = DataBase.MEMBER_DB.get(  MainPageActivity.LOGIN_USER_ID ).getScore();
        if( score == "금상" ) {
            textViewMedal.setText( "금상" );
            healthTestMedalImage.setImageResource( R.drawable.ic_medal_1 );
        }else if(score == "은상"){
            textViewMedal.setText("은상");
            healthTestMedalImage.setImageResource( R.drawable.ic_medal_2 );
        }else if(score == "동상"){
            textViewMedal.setText("동상");
            healthTestMedalImage.setImageResource( R.drawable.ic_medal_3 );
        }else{
            textViewMedal.setText("참가상");
            healthTestMedalImage.setImageResource( R.drawable.ic_medal_4 );
        }


        // set Name
        healthTestUserName = viewGroup.findViewById(R.id.healthTestUserName);
        healthTestUserName.setText( DataBase.MEMBER_DB.get(  MainPageActivity.LOGIN_USER_ID ).getName()  );

        // set position, Age
        healthTestUserPositionAge = viewGroup.findViewById(R.id.healthTestUserPositionAge);
        String tmp = DataBase.MEMBER_DB.get(  MainPageActivity.LOGIN_USER_ID ).getLocation() + " ";
        tmp += DataBase.MEMBER_DB.get(  MainPageActivity.LOGIN_USER_ID ).getAge() + "세";
        healthTestUserPositionAge.setText( tmp );


        // click webPageBtn

        healthTestDetailTestBtn = viewGroup.findViewById(R.id.healthTestDetailTestBtn);
        healthTestDetailTestBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse( "http://14.49.46.105/front/center/centerIntroList.do" ));
                context.startActivity(intent);
            }
        });



        // click homeBtn
        healthTestMainBtn = viewGroup.findViewById(R.id.healthTestMainBtn);
        healthTestMainBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                activity = (AppCompatActivity)v.getContext();
                transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new HealthFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return viewGroup;
    }

    private void updateUserScore(){
        try {
            int t1 = Integer.parseInt(test1);
            int t2 = Integer.parseInt(test2);
            int t3 = Integer.parseInt(test3);

            if (t1 >= 21 && t2 >= 140 && t3 >= 18.4) {
                DataBase.MEMBER_DB.get(MainPageActivity.LOGIN_USER_ID).setScore("금상");
            } else if (t1 >= 17 && t2 >= 106 && t3 >= 16.1) {
                DataBase.MEMBER_DB.get(MainPageActivity.LOGIN_USER_ID).setScore("은상");
            } else {
                DataBase.MEMBER_DB.get(MainPageActivity.LOGIN_USER_ID).setScore("동상");
            }
        }catch (Exception e){
            DataBase.MEMBER_DB.get(MainPageActivity.LOGIN_USER_ID).setScore("은상");
        }
    }


    private String[] getRecommendExercise(){
        // 운동추천 모델 돌리고 결과 return
        String[] recommendExercise = { "목운동", "허리운동" };
        return recommendExercise;
    }
}