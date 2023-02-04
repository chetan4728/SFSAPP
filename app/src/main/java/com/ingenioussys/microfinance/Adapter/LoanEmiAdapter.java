package com.ingenioussys.microfinance.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ingenioussys.microfinance.Activites.GroupManager.collection.CollectPayment;
import com.ingenioussys.microfinance.R;
import com.ingenioussys.microfinance.model.LoanEmi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoanEmiAdapter extends BaseAdapter {
    ArrayList<LoanEmi> loanEmi;
    Context context;
    public LoanEmiAdapter(ArrayList<LoanEmi> loanEmi, Context context) {
        this.loanEmi = loanEmi;
        this.context = context;
    }



    @Override
    public int getCount() {
        return loanEmi.size();
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
        LoanEmi emi = loanEmi.get(i);
        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.emi_listin_layout, null);


        }
        TextView title = view.findViewById(R.id.title);
        TextView emiName = view.findViewById(R.id.emi);
        TextView status = view.findViewById(R.id.status);
        title.setText("EMI "+emi.getEmi_no()+" - "+emi.getInc_date());
        emiName.setText("₹"+emi.getScheduled_payment());
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        String[] toComapre = fDate.split("-");
        String[] forComapre = emi.getInc_date().split("-");
        //Toast.makeText(context, ""+myear[0], Toast.LENGTH_SHORT).show();
        if(emi.getStatus()==0)
        {
            status.setText("Unpaid");
        }
        else
        {
            status.setText("Paid");
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String cuDate = toComapre[0]+toComapre[1];
                String checkDate = forComapre[0]+forComapre[1];
           //     Toast.makeText(context, ""+cuDate+"----"+checkDate, Toast.LENGTH_SHORT).show();
                if(cuDate.equals(checkDate))
                {
                    //Toast.makeText(context, ""+emi.getLoan_distribution_emi_id(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, CollectPayment.class);
                    Bundle b = new Bundle();
                    b.putString("emi_id", String.valueOf(emi.getLoan_distribution_emi_id()));
                    b.putString("loan_application_no", emi.getLaon_application_no());

                    intent.putExtras(b);
                    context.startActivity(intent);
                }
                else
                {
                 //   Toast.makeText(context, ""+fDate+"-------"+emi.getInc_date(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "This Emi not scheduled for today", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }
}
