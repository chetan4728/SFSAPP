package com.ingenioussys.microfinance.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.LoanTransactionActivity;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.database.AppDatabase;
import com.ingenioussys.microfinance.model.Center;
import com.ingenioussys.microfinance.model.Group;
import com.ingenioussys.microfinance.model.LoanApplication;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ingenioussys.microfinance.constant.Global.IMAGE_URL;

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
        created_date.setText(loanApplication.getLoan_application_number());
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MicroFinance/Documents/" +  loanApplication.getMember_photo_pr());
        //Glide.with(LoanActivityFour.this).load(photo).placeholder(R.drawable.ic_survey).into(member_photo_pr);
        ImageView profile_pic =  view.findViewById(R.id.profile_pic);
        //Toast.makeText(context, ""+photo, Toast.LENGTH_SHORT).show();
        Picasso.get().load(IMAGE_URL+loanApplication.getMember_photo_pr()).placeholder(R.drawable.ic_survey).into(profile_pic);
        if(loanApplication.getIs_blc_verfied()==0)
        {
            verificationStatus.setText("Pending");
            verificationStatus.setTextColor(context.getResources().getColor(R.color.red));
            view.setEnabled(false);
        }
//        else if(loanApplication.getApproved_status()==2)
//        {
//            verificationStatus.setText("Rejected");
//            verificationStatus.setTextColor(context.getResources().getColor(R.color.red));
//        }
        else if(loanApplication.getIs_blc_verfied()==1)
        {
            verificationStatus.setText("Approved");
            verificationStatus.setTextColor(context.getResources().getColor(R.color.green));
            view.setEnabled(false);
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
