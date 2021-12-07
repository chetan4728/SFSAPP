package com.ingenioussys.microfinance.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.LoanTransactionActivity;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Group;
import com.ingenioussys.microfinance.model.LoanApplication;

import java.util.ArrayList;
import java.util.List;

public class LoanApplicationAdapter extends BaseAdapter {
    Context context;
    List<LoanApplication> loanApplications;
    List<Group> GroupList;
    List<Center> centers;
    public LoanApplicationAdapter(Context context, List<LoanApplication> loanApplications) {
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
            view = inflater.inflate(R.layout.loan_application_item_list, null);

        }
        TextView applicant_name = view.findViewById(R.id.applicant_name);
        TextView group_center_name = view.findViewById(R.id.group_center_name);
        TextView created_date = view.findViewById(R.id.created_date);
        TextView verificationStatus  = view.findViewById(R.id.status);
        applicant_name.setText(loanApplication.getApplicant_name());
        group_center_name.setText(loanApplication.getApplicant_name());
        created_date.setText(loanApplication.getCreated_date());

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

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoanTransactionActivity)context).viewLoanApplication(loanApplication.getLoan_application_number());
            }
        });

        return view;
    }
}
