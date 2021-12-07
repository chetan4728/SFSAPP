package com.ingenioussys.microfinance.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.LoanApplicationForm;
import com.ingenioussys.microfinance.R;


public class LoanApplicationFormTwo extends Fragment {

    Button next;
    EditText opening_bal,sale_amount,asset_sale,deb_receipts,family_income,other_income,
            current_bal,outgoing_amount,wages,income_tax,licensing,stationary_printing, repair_maintenance,motor_vehicle_ex, rents_rates,Loan,utilities,credit_card_fees,interest_paid,bank_charges,advertisement_and_marketing, solicitor_fees,total_income,purchase,accountant_fees;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_loan_application_form_two, container, false);
        next = view.findViewById(R.id.next);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int myInt = bundle.getInt("id", 0);

            Toast.makeText(getActivity(), ""+myInt, Toast.LENGTH_SHORT).show();
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((LoanApplicationForm)getActivity()).selectIndex(2,2);
            }
        });
        return  view;
    }
}