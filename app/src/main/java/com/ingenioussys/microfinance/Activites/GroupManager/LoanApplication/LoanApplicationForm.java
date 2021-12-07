package com.ingenioussys.microfinance.Activites.GroupManager.LoanApplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ingenioussys.microfinance.fragment.LoanApplicationFormFour;
import com.ingenioussys.microfinance.fragment.LoanApplicationFormOne;
import com.ingenioussys.microfinance.fragment.LoanApplicationFormThree;
import com.ingenioussys.microfinance.fragment.LoanApplicationFormTwo;
import com.ingenioussys.microfinance.R;
import android.os.Bundle;

public class LoanApplicationForm extends AppCompatActivity {
    ViewPager viewPager;
    ViewPagerAdapter mPagerAdapter;
    int LoanApplication_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_application_form);

        viewPager = findViewById(R.id.application_form);
        getSupportActionBar().setTitle("Loan Application");
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradiant));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);



    }

    public void selectIndex(int newIndex ,int id) {
        viewPager.setCurrentItem(newIndex);
        LoanApplication_id = id;
        //Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        int currentPosition = viewPager.getCurrentItem();
        if (currentPosition != 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:

                    return new LoanApplicationFormOne();
                case 1:
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", LoanApplication_id);
                    LoanApplicationFormTwo formTwo =  new LoanApplicationFormTwo();
                    formTwo.setArguments(bundle);

                    return formTwo;
                case 2:
                    return new LoanApplicationFormThree();
                case 3:
                    return new LoanApplicationFormFour();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4; //three fragments
        }
    }

}