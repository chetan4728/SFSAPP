package com.ingenioussys.microfinance.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication.LoanApplicationForm;
import com.ingenioussys.microfinance.R;


public class LoanApplicationFormThree extends Fragment {

    Button next;
    EditText no_of_dep,house_hold_income,current_profession,lottery,spouse_name,border;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_loan_application_form_three, container, false);

        next = view.findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((LoanApplicationForm)getActivity()).selectIndex(3,4);
            }
        });
        return  view;
    }
}