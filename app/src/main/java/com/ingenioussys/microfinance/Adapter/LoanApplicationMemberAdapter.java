package com.ingenioussys.microfinance.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ingenioussys.microfinance.Activites.GroupManager.CGT.CGTProcessActivity;
import com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.LoanTransactionActivity;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Group;
import com.ingenioussys.microfinance.model.LoanApplication;

import java.util.ArrayList;
import java.util.List;

public class LoanApplicationMemberAdapter extends BaseAdapter {
    Context context;
    List<LoanApplication> loanApplications;
    List<Group> GroupList;
    List<Center> centers;
    public LoanApplicationMemberAdapter(Context context, List<LoanApplication> loanApplications) {
        this.context = context;
        this.loanApplications = loanApplications;
    }

    @Override
    public int getCount() {
        return loanApplications.size();
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
        LoanApplication loanApplication = loanApplications.get(i);
        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.loan_application_item_cgt_list, null);

        }
        TextView applicant_name = view.findViewById(R.id.applicant_name);
        CheckBox check_cust = view.findViewById(R.id.check_cust);
        TextView verificationStatus  = view.findViewById(R.id.status);
        applicant_name.setText(loanApplication.getApplicant_name());
        loanApplication.isActive();
        check_cust.setTag(loanApplication.getLoan_application_number());

        check_cust.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    //Toast.makeText(context, ""+loanApplication.getLoan_application_number(), Toast.LENGTH_SHORT).show();
                    ((CGTProcessActivity) context).get_checked_ids(loanApplication.getLoan_application_number());
                }
                else
                {
                    ((CGTProcessActivity) context).get_checked_ids_pop(loanApplication.getLoan_application_number());
                }
            }
        });


        if(loanApplication.getApproved_status()==0)
        {
            verificationStatus.setText("Pending");
            verificationStatus.setTextColor(context.getResources().getColor(R.color.red));
        }
        else if(loanApplication.getApproved_status()==2)
        {
            verificationStatus.setText("Rejected");
            verificationStatus.setTextColor(context.getResources().getColor(R.color.red));
        }
        else if(loanApplication.getApproved_status()==1)
        {
            verificationStatus.setText("Approved");
            verificationStatus.setTextColor(context.getResources().getColor(R.color.green));
        }



        return view;
    }
}
