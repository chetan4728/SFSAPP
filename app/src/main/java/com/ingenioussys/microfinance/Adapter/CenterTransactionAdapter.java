package com.ingenioussys.microfinance.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ingenioussys.microfinance.Activites.GroupManager.Center.ViewCenterTransactionActivity;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.model.Area;
import com.ingenioussys.microfinance.model.Center;

import java.util.List;

public class CenterTransactionAdapter extends BaseAdapter {
    Activity context;
    List<Center> centers;
    List<Area> AreaList;
    public CenterTransactionAdapter(Activity context, List<Center> centers) {
        this.context = context;
        this.centers = centers;
    }

    @Override
    public int getCount() {
        return centers.size();
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
        Center survey = centers.get(i);
        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.center_verification_item_list, null);

        }
        TextView area_name =  view.findViewById(R.id.area_name);

        area_name.setText(survey.getArea_name()+"-"+survey.getCenter_name());

        ImageView spinner_item = view.findViewById(R.id.spinner_item);
        TextView  verificationStatus = view.findViewById(R.id.verificationStatus);


        TextView created_by =  view.findViewById(R.id.created_by);
        created_by.setText(survey.getCreated_date());
        TextView emp_name =  view.findViewById(R.id.emp_name);
        emp_name.setText("Created By- "+survey.getFs_name());

        if(survey.getCenter_status()==0)
        {
            verificationStatus.setText("Pending");
            verificationStatus.setTextColor(context.getResources().getColor(R.color.red));
        }
        else if(survey.getCenter_status()==2)
        {
            verificationStatus.setText("Rejected");
            verificationStatus.setTextColor(context.getResources().getColor(R.color.red));
        }
        else if(survey.getCenter_status()==1)
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
            inflater.inflate(R.menu.center_tr_popup, popup.getMenu());
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            

           if(menuItem.getItemId() == R.id.view_group)
            {
                if(centers.get(id).getCenter_status()==0)
                {
                    Toast.makeText(context, "Please Approved Center First", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ((ViewCenterTransactionActivity)context).ViewGroupDetail(id);
                }
            }

            return true;
        }
    }
}
