package com.example.physicalplatform.matching;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.physicalplatform.R;
import com.example.physicalplatform.data.MatchingDetailDataset;
import com.example.physicalplatform.data.MatchingListDataset;
import com.example.physicalplatform.data.MatchingReviewListDataset;

import java.util.ArrayList;

import static com.example.physicalplatform.MainPageActivity.bottomNavigationView;

public class MatchingDetailFragment extends Fragment implements View.OnClickListener {
    private ViewGroup viewGroup;
    private Context context;
    private ImageView imageViewBackBtn;
    private ImageView imageViewDetailBackground;

    private TextView textViewTitle;
    private TextView textViewTrainer;
    private TextView textViewSummary;
    private TextView textViewPeriod;
    private TextView textViewRating;

    private TextView textViewIntroduction;
    private TextView textViewCurriculum;
    private TextView textViewClassPlan;
    private TextView textViewClassReview;

    private View viewIntroduction;
    private View viewCurriculum;
    private View viewClassPlan;
    private View viewClassReview;

    private LinearLayout linearLayoutIntroduction;
    private TextView textViewIntroductionContents;
    private TextView buttonIntroductionQuestion;
    private TextView buttonIntroductionRegister;

    private LinearLayout linearLayoutCurriculum;
    private TextView textViewCurriculumContents;

    private LinearLayout linearLayoutClassPlan;
    private TextView textViewClassPlanSummary;
    private CalendarView calendarViewClassPlan;

    private MatchingDetailDataset matchingDetailDatasets;

    private LinearLayout linearLayoutClassReview;
    private RecyclerView recyclerViewClassReviewList;
    private RecyclerView.LayoutManager classReviewLayoutManager;
    private MatchingFragmentReviewListRecyclerViewAdapter classReviewAdapter;
    private ArrayList<MatchingReviewListDataset> classReviewListDatasets;

    private MatchingListDataset matchingListDataset;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    public MatchingDetailFragment(MatchingListDataset matchingListDataset) {
        this.matchingListDataset = matchingListDataset;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.matching_page_detail, container, false);
        context = container.getContext();

        initLayout();

        imageViewDetailBackground.setImageResource(matchingListDataset.getBackgroundSrc());

        textViewTitle.setText(matchingDetailDatasets.getListTitle());
        textViewTrainer.setText(matchingDetailDatasets.getTrainer());
        textViewSummary.setText(matchingDetailDatasets.getSummary());
        textViewPeriod.setText(matchingDetailDatasets.getPeriod());
        textViewRating.setText(matchingDetailDatasets.getRating().toString());
        textViewIntroductionContents.setMovementMethod(new ScrollingMovementMethod());
        textViewIntroductionContents.setText(matchingDetailDatasets.getIntroductionContents());
        textViewCurriculumContents.setMovementMethod(new ScrollingMovementMethod());
        textViewCurriculumContents.setText(matchingDetailDatasets.getCurriculumContents());
        textViewClassPlanSummary.setText(matchingDetailDatasets.getClassPlanSummary());

        imageViewBackBtn.setOnClickListener(this);

        textViewIntroduction.setOnClickListener(this);
        textViewCurriculum.setOnClickListener(this);
        textViewClassPlan.setOnClickListener(this);
        textViewClassReview.setOnClickListener(this);

        buttonIntroductionQuestion.setOnClickListener(this);
        buttonIntroductionRegister.setOnClickListener(this);

        return viewGroup;
    }

    private void initLayout() {
        initDataset();

        imageViewBackBtn = viewGroup.findViewById(R.id.imageViewBackBtn);
        imageViewDetailBackground = viewGroup.findViewById(R.id.imageViewDetailBackground);

        textViewTitle = viewGroup.findViewById(R.id.textViewTitle);
        textViewTrainer = viewGroup.findViewById(R.id.textViewTrainer);
        textViewSummary = viewGroup.findViewById(R.id.textViewSummary);
        textViewPeriod = viewGroup.findViewById(R.id.textViewPeriod);
        textViewRating = viewGroup.findViewById(R.id.textViewRating);

        textViewIntroduction = viewGroup.findViewById(R.id.textViewIntroduction);
        textViewCurriculum = viewGroup.findViewById(R.id.textViewCurriculum);
        textViewClassPlan = viewGroup.findViewById(R.id.textViewClassPlan);
        textViewClassReview = viewGroup.findViewById(R.id.textViewClassReview);

        viewIntroduction = viewGroup.findViewById(R.id.viewIntroduction);
        viewCurriculum = viewGroup.findViewById(R.id.viewCurriculum);
        viewClassPlan = viewGroup.findViewById(R.id.viewClassPlan);
        viewClassReview = viewGroup.findViewById(R.id.viewClassReview);

        linearLayoutIntroduction = viewGroup.findViewById(R.id.linearLayoutIntroduction);
        textViewIntroductionContents = viewGroup.findViewById(R.id.textViewIntroductionContents);
        buttonIntroductionQuestion = viewGroup.findViewById(R.id.buttonIntroductionQuestion);
        buttonIntroductionRegister = viewGroup.findViewById(R.id.buttonIntroductionRegister);

        linearLayoutCurriculum = viewGroup.findViewById(R.id.linearLayoutCurriculum);
        textViewCurriculumContents = viewGroup.findViewById(R.id.textViewCurriculumContents);

        linearLayoutClassPlan = viewGroup.findViewById(R.id.linearLayoutClassPlan);
        textViewClassPlanSummary = viewGroup.findViewById(R.id.textViewClassPlanSummary);
        calendarViewClassPlan = viewGroup.findViewById(R.id.calendarViewClassPlan);

        linearLayoutClassReview = viewGroup.findViewById(R.id.linearLayoutClassReview);
        recyclerViewClassReviewList = viewGroup.findViewById(R.id.recyclerViewClassReviewList);
        classReviewAdapter = new MatchingFragmentReviewListRecyclerViewAdapter(context, classReviewListDatasets);

        recyclerViewClassReviewList.setAdapter(classReviewAdapter);
        recyclerViewClassReviewList.setHasFixedSize(true);

        classReviewLayoutManager = new LinearLayoutManager(context);
        recyclerViewClassReviewList.setLayoutManager(classReviewLayoutManager);


        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
    }

    private void initDataset() {
        // Detail Page
        String listTitle = matchingListDataset.getListTitle();
        String period = matchingListDataset.getPeriod() + " " + matchingListDataset.getClassTime();
        String introductionContents = "헬스를 전문적으로 배우는 것이 아닌 근력을 탄탄하고 키우고 싶은 노인분들을 대상으로 운영하고자 합니다.\n강의 수강신청 하실 때 이 부분을 유념해주시고 질문사항이 있으면 아래 강좌에 대해 질문하기 버튼을 클릭하시면 됩니다.";
        String curriculumContents = "1주차 - 강좌 오리엔테이션\n2주차 - 의자 이용 근력운동 루틴 프로그램\n3주차 - 치매를 위한 근력운동\n4주차 - 우울증을 위한 근력운동\n5주차 - 관절염을 위한 근력운동\n6주차 - 요통을 위한 근력운동\n7주차 - 혼자하는 헬스 트레이닝 루틴\n8주차 - 마무리 강좌";
        String classPlanSummary = "매주 수요일 9시부터 수업이 진행됩니다.";

        matchingDetailDatasets = new MatchingDetailDataset(listTitle,"이강사","생활 근력을 키우는 헬스트레이닝",period,4.6,introductionContents,curriculumContents,classPlanSummary);

        // Detail Page Reviews
        classReviewListDatasets = new ArrayList<>();
        classReviewListDatasets.add(new MatchingReviewListDataset(R.drawable.profile_1,"배영자","정말 좋은 수업해주셔서 감사합니다.",4.6,"2020.12.04"));
        classReviewListDatasets.add(new MatchingReviewListDataset(R.drawable.profile_2,"김순자","강사님이 정말 친절해요.",4.0,"2020.12.03"));
        classReviewListDatasets.add(new MatchingReviewListDataset(R.drawable.profile_3,"이영희","친절한 강사님이였어요.",4.2,"2020.12.02"));
        classReviewListDatasets.add(new MatchingReviewListDataset(R.drawable.profile_4,"이영철","다음에 또 수강할 생각이에요.",3.9,"2020.11.28"));
        classReviewListDatasets.add(new MatchingReviewListDataset(R.drawable.profile_5,"김성수","수업내용이 알찹니다.",3.8,"2020.11.20"));
        classReviewListDatasets.add(new MatchingReviewListDataset(R.drawable.profile_6,"최미경","커리큘럼이 저랑 딱 맞아요.",4.7,"2020.10.15"));
        classReviewListDatasets.add(new MatchingReviewListDataset(R.drawable.profile_7,"최영수","연관 강좌도 듣고 싶어요.",4.8,"2020.10.03"));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.imageViewBackBtn:
                fragmentManager.popBackStackImmediate();
                break;
            case R.id.textViewIntroduction:
                setTextSelected(R.color.custom_red, R.color.black, R.color.black, R.color.black, View.VISIBLE, View.GONE, View.GONE, View.GONE);
                break;
            case R.id.textViewCurriculum:
                setTextSelected(R.color.black, R.color.custom_red, R.color.black, R.color.black, View.GONE, View.VISIBLE, View.GONE, View.GONE);
                break;
            case R.id.textViewClassPlan:
                setTextSelected(R.color.black, R.color.black, R.color.custom_red, R.color.black, View.GONE, View.GONE, View.VISIBLE, View.GONE);
                break;
            case R.id.textViewClassReview:
                setTextSelected(R.color.black, R.color.black, R.color.black, R.color.custom_red, View.GONE, View.GONE, View.GONE, View.VISIBLE);
                break;
            case R.id.buttonIntroductionQuestion:
                fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                bottomNavigationView.setSelectedItemId(R.id.nav_chat);
                break;
            case R.id.buttonIntroductionRegister:
                // matchingListDataset.setRegistered(true);
                Toast.makeText(context, matchingListDataset.getListTitle() + " 강좌가 수강신청 되었습니다.", Toast.LENGTH_SHORT).show();
                fragmentManager.popBackStackImmediate();
                break;
        }
    }

    private void setTextSelected(Integer c1, Integer c2, Integer c3, Integer c4, Integer v1, Integer v2, Integer v3, Integer v4) {
        textViewIntroduction.setTextColor(getResources().getColor(c1));
        textViewCurriculum.setTextColor(getResources().getColor(c2));
        textViewClassPlan.setTextColor(getResources().getColor(c3));
        textViewClassReview.setTextColor(getResources().getColor(c4));

        viewIntroduction.setVisibility(v1);
        viewCurriculum.setVisibility(v2);
        viewClassPlan.setVisibility(v3);
        viewClassReview.setVisibility(v4);

        linearLayoutIntroduction.setVisibility(v1);
        linearLayoutCurriculum.setVisibility(v2);
        linearLayoutClassPlan.setVisibility(v3);
        linearLayoutClassReview.setVisibility(v4);
    }
}

