package com.ingenioussys.microfinance.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ingenioussys.microfinance.Activites.FieldManager.Survey.ManageSurveyActivity;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.model.Area;
import com.ingenioussys.microfinance.model.Survey;

import java.util.List;

public class VerificationSurveyAdapter extends BaseAdapter {
    Context context;
    List<Survey> surveys;
    List<Area> AreaList;
    public VerificationSurveyAdapter(Context context, List<Survey> surveys) {
        this.context = context;
        this.surveys = surveys;
    }

    @Override
    public int getCount() {
        return surveys.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Survey survey = surveys.get(i);
        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.verification_survey_item_list, null);

        }
        TextView  verificationStatus = view.findViewById(R.id.verificationStatus);
        TextView area_name =  view.findViewById(R.id.area_name);

        TextView created_by =  view.findViewById(R.id.created_by);
        TextView survey_name =  view.findViewById(R.id.survey_name);
        TextView uni_id =  view.findViewById(R.id.uni_id);

        area_name.setText(survey.getSurvey_area_name());
        survey_name.setText(survey.getSurvey_title());
        uni_id.setText(survey.getSurvey_uniqe_id());
        created_by.setText(survey.getSurvey_created());
        if(survey.getSurver_verfication_status()==0)
        {
            verificationStatus.setText("Pending");
            verificationStatus.setTextColor(context.getResources().getColor(R.color.red));
        }
        else
        {
            verificationStatus.setText("Approved");
            verificationStatus.setTextColor(context.getResources().getColor(R.color.green));
        }
       // Toast.makeText(context, ""+survey.getSurvey_id(), Toast.LENGTH_SHORT).show();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(context,ManageSurveyActivity.class);
                Bundle bundle =  new Bundle();
                bundle.putString("survey_id", String.valueOf(survey.getSurvey_uniqe_id()));
                bundle.putString("branch_id", String.valueOf(survey.getBranch_id()));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
