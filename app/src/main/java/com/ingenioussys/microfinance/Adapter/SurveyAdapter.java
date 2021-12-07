package com.ingenioussys.microfinance.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.model.Area;
import com.ingenioussys.microfinance.model.Survey;

import java.util.List;

public class SurveyAdapter extends BaseAdapter {
    Context context;
    List<Survey> surveys;
    List<Area> AreaList;
    public SurveyAdapter(Context context, List<Survey> surveys) {
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
            view = inflater.inflate(R.layout.survey_item_list, null);

        }
        ImageView spinner_item = view.findViewById(R.id.spinner_item);
        //Toast.makeText(context, ""+survey.getArea_id(), Toast.LENGTH_SHORT).show();
        TextView area_name =  view.findViewById(R.id.area_name);
        TextView survey_name =  view.findViewById(R.id.survey_name);
        TextView uni_id =  view.findViewById(R.id.uni_id);
        TextView  verificationStatus = view.findViewById(R.id.verificationStatus);
        area_name.setText(survey.getSurvey_area_name());
        survey_name.setText(survey.getSurvey_title());
        uni_id.setText(survey.getSurvey_uniqe_id());
        TextView created_by =  view.findViewById(R.id.created_by);
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
        spinner_item.setOnClickListener(new myClickListener(i));
        return view;
    }

    class myClickListener implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        int id;

        public myClickListener(int id) {
            this.id = id;
        }
        @Override
        public void onClick(View v) {
            PopupMenu popup = new PopupMenu(context, v);
            popup.setOnMenuItemClickListener(this);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.popup, popup.getMenu());
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if(menuItem.getItemId() == R.id.view_center)
            {
                // Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
               // ((ViewCenterTransactionActivity)context).ViewCenterDetail(id);
            }
            else if(menuItem.getItemId() == R.id.view_group)
            {

                   // ((ViewCenterTransactionActivity)context).ViewGroupDetail(id);

            }

            return true;
        }
    }
}
