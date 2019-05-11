package com.example.agenda.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.agenda.R;
import com.example.agenda.models.Test;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestDetailsFragment extends Fragment {

    @BindView(R.id.subject)
    TextView subjectTextView;

    @BindView(R.id.date)
    TextView dateTextView;

    @BindView(R.id.topics)
    ListView topicsListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_details, container, false);
        ButterKnife.bind(this,view);

        Bundle bundle = getArguments();
        if(bundle != null){
            Test test = (Test) bundle.getSerializable("TEST");

            populateTest(test);
        }

        return view;
    }

    public void populateTest(Test test) {
        subjectTextView.setText(test.getSubject());
        dateTextView.setText(test.getDate());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,test.getTopics());
        topicsListView.setAdapter(adapter);
    }
}
