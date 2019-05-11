package com.example.agenda.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.agenda.R;
import com.example.agenda.fragments.TestDetailsFragment;
import com.example.agenda.fragments.TestListFragment;
import com.example.agenda.models.Test;

import butterknife.ButterKnife;

public class TestListActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        ButterKnife.bind(this);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_list, new TestListFragment());
        if(isLandScapeMode()) transaction.replace(R.id.frame_info, new TestDetailsFragment());
        transaction.commit();
    }

    private boolean isLandScapeMode() {
        return getResources().getBoolean(R.bool.landScapeMode);
    }

    public void setTest(Test test) {
        FragmentManager manager = getSupportFragmentManager();
        if(!isLandScapeMode()){
            FragmentTransaction transaction = manager.beginTransaction();
            TestDetailsFragment detailsFragment = new TestDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("TEST",test);
            detailsFragment.setArguments(bundle);
            transaction.replace(R.id.frame_list, detailsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else{
            TestDetailsFragment detailsFragment = (TestDetailsFragment) manager.findFragmentById(R.id.frame_info);
            detailsFragment.populateTest(test);
        }
    }
}