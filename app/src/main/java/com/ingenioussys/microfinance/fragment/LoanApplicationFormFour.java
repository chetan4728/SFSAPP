package com.ingenioussys.microfinance.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingenioussys.microfinance.R;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;

import java.util.ArrayList;


public class LoanApplicationFormFour extends Fragment {


    HintSpinner loan_cycle,marital_status,religion,cast,family_relation1,family_relation2,family_relation3,family_relation4,nominee_relation,loan_purpose;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_loan_application_form_four, container, false);
        loan_cycle = view.findViewById(R.id.loan_cycle);
        marital_status = view.findViewById(R.id.marital_status);
        religion = view.findViewById(R.id.religion);
        cast = view.findViewById(R.id.cast);
        family_relation1 = view.findViewById(R.id.family_relation1);
        family_relation2 = view.findViewById(R.id.family_relation2);
        family_relation3 = view.findViewById(R.id.family_relation3);
        family_relation4 = view.findViewById(R.id.family_relation4);
        nominee_relation = view.findViewById(R.id.nominee_relation);
        loan_purpose  = view.findViewById(R.id.loan_purpose);

        ArrayList<String> loan_cycle_array = new ArrayList<>();
        loan_cycle_array.add("1");
        loan_cycle_array.add("2");
        loan_cycle_array.add("3");
        loan_cycle_array.add("4");
        loan_cycle_array.add("5");

        ArrayList<String> marital_status_array = new ArrayList<>();
        marital_status_array.add("Single");
        marital_status_array.add("Married");
        marital_status_array.add("Widowed");

        ArrayList<String> religion_array = new ArrayList<>();
        religion_array.add("Hindu");
        religion_array.add("Muslim");
        religion_array.add("Christan");
        religion_array.add("other");

        ArrayList<String> cast_array = new ArrayList<>();
        cast_array.add("SC");
        cast_array.add("ST");
        cast_array.add("OBC");
        cast_array.add("GENERAL");
        cast_array.add("OTHERS");

        ArrayList<String> relation = new ArrayList<>();
        relation.add("Aunt");
        relation.add("Brother");
        relation.add("Brother in Law");
        relation.add("Daughter");
        relation.add("Daughter in Law");
        relation.add("Father");
        relation.add("Father in Law");
        relation.add("Husband");
        relation.add("Uncle");
        relation.add("Mother");
        relation.add("Mother in Law");
        relation.add("Nephew");
        relation.add("Sister");
        relation.add("Sister in Law");
        relation.add("Son");
        relation.add("Son in Law");
        relation.add("Mother's sister");

        ArrayList<String> loan_purpose_array = new ArrayList<>();
        loan_purpose_array.add("Jewellery");
        loan_purpose_array.add("Mess");
        loan_purpose_array.add("Tailoring");
        loan_purpose_array.add("Vegetable");
        loan_purpose_array.add("Laundry");
        loan_purpose_array.add("Athari Sales");
        loan_purpose_array.add("Goat Rearing");
        loan_purpose_array.add("Milk Vending");
        loan_purpose_array.add("Grocery Stores");
        loan_purpose_array.add("General Stores");
        loan_purpose_array.add("Flower Selling");
        loan_purpose_array.add("Beauty Parlour");
        loan_purpose_array.add("Tent House");
        loan_purpose_array.add("Saree Selling");
        loan_purpose_array.add("Saree Work");
        loan_purpose_array.add("Ready Made");
        loan_purpose_array.add("Electric Shop");
        loan_purpose_array.add("Electric Repairing");
        loan_purpose_array.add("Vehicle Repairing or Garage");
        loan_purpose_array.add("Bicycle Stores");
        loan_purpose_array.add("Bangle Shop");
        loan_purpose_array.add("Footwear Shop");
        loan_purpose_array.add("Fish Shop");
        loan_purpose_array.add("Chicken Shop");
        loan_purpose_array.add("Mutton Shop");
        loan_purpose_array.add("Spices Selling");
        loan_purpose_array.add("Tea Powder Selling");
        loan_purpose_array.add("Tea Stall");
        loan_purpose_array.add("Hotel");
        loan_purpose_array.add("Animal Husbandry");
        loan_purpose_array.add("Scrap Business");
        loan_purpose_array.add("Pig Rearing");
        loan_purpose_array.add("Flour Mill");
        loan_purpose_array.add("Medical Stores");
        loan_purpose_array.add("Hair Saloon");
        loan_purpose_array.add("Snack Selling");
        loan_purpose_array.add("Papad Making Selling");
        loan_purpose_array.add("Incense Stick Selling");
        loan_purpose_array.add("Gold Smith");
        loan_purpose_array.add("Ladies Emporium");
        loan_purpose_array.add("Broom Making and Selling");
        loan_purpose_array.add("Catering");
        loan_purpose_array.add("Pan Shop");
        loan_purpose_array.add("House Made Jewellery");
        loan_purpose_array.add("Kirana Shop");
        loan_purpose_array.add("Construction Contractor");
        loan_purpose_array.add("Vegetables Producer");
        loan_purpose_array.add("Fruits Seller");
        loan_purpose_array.add("Fruits Producer");
        loan_purpose_array.add("Garment Shop");
        loan_purpose_array.add("Other Activities");
        loan_cycle.setAdapter(new HintSpinnerAdapter<String>(getActivity(), loan_cycle_array, "Loan Cycle"));
        marital_status.setAdapter(new HintSpinnerAdapter<String>(getActivity(), marital_status_array, "Marital Status"));
        religion.setAdapter(new HintSpinnerAdapter<String>(getActivity(), religion_array, "Religion"));
        cast.setAdapter(new HintSpinnerAdapter<String>(getActivity(), cast_array, "Cast"));
        family_relation1.setAdapter(new HintSpinnerAdapter<String>(getActivity(), relation, "Family Relation 1"));
        family_relation2.setAdapter(new HintSpinnerAdapter<String>(getActivity(), relation, "Family Relation 2"));
        family_relation3.setAdapter(new HintSpinnerAdapter<String>(getActivity(), relation, "Family Relation 3"));
        family_relation4.setAdapter(new HintSpinnerAdapter<String>(getActivity(), relation, "Family Relation 4"));
        nominee_relation.setAdapter(new HintSpinnerAdapter<String>(getActivity(), relation, "Nominee Relation"));
        loan_purpose.setAdapter(new HintSpinnerAdapter<String>(getActivity(), loan_purpose_array, "Loan Purpose"));
        return  view;
    }
}