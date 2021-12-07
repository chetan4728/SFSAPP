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

import com.ingenioussys.microfinance.Activites.FieldManager.Group.GroupVerificationActivity;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.model.Group;

import java.util.List;

public class GroupVerificationAdapter extends BaseAdapter {
    Activity context;
    List<Group> groups;
    public GroupVerificationAdapter(Activity context, List<Group> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public int getCount() {
        return groups.size();
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
        Group group = groups.get(i);
        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.center_verification_item_list, null);

        }
        //Toast.makeText(context, ""+survey.getArea_id(), Toast.LENGTH_SHORT).show();
        TextView area_name =  view.findViewById(R.id.area_name);

        ImageView spinner_item = view.findViewById(R.id.spinner_item);
        TextView  verificationStatus = view.findViewById(R.id.verificationStatus);
        area_name.setText(group.getGroup_name());

        TextView created_by =  view.findViewById(R.id.created_by);
        created_by.setText(group.getCreated_date());
        if(group.getGroupStatus()==0)
        {
            verificationStatus.setText("Pending");
            verificationStatus.setTextColor(context.getResources().getColor(R.color.red));
        }
        else if(group.getGroupStatus()==2)
        {
            verificationStatus.setText("Rejected");
            verificationStatus.setTextColor(context.getResources().getColor(R.color.red));
        }
        else if(group.getGroupStatus()==1)
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
            inflater.inflate(R.menu.popup_group, popup.getMenu());
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            
            if(menuItem.getItemId() == R.id.view_center)
            {
               // Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                ((GroupVerificationActivity)context).ViewGroupDetail(id);
            }
            else if(menuItem.getItemId() == R.id.view_members)
            {
                if(groups.get(id).getGroupStatus()==0)
                {
                    Toast.makeText(context, "Please Approved Group First", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ((GroupVerificationActivity)context).ViewMemberDetail(id);
                }
            }

            return true;
        }
    }
}
