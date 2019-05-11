package com.example.agenda.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.agenda.R;
import com.example.agenda.models.Test;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestDetailsActivity extends AppCompatActivity {

    Test test;

    @BindView(R.id.subject)
    TextView subjectTextView;

    @BindView(R.id.date)
    TextView dateTextView;

    @BindView(R.id.topics)
    ListView topicsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        test = (Test) intent.getSerializableExtra("TEST");

        subjectTextView.setText(test.getSubject());
        dateTextView.setText(test.getDate());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,test.getTopics());
        topicsListView.setAdapter(adapter);
    }
}
