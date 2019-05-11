package com.example.agenda.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.agenda.R;
import com.example.agenda.adapters.TestAdapter;
import com.example.agenda.adapters.UserAdapter;
import com.example.agenda.models.Test;
import com.example.agenda.providers.TestDAO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity implements TestAdapter.AdapterClickListener {

    private List<Test> tests;
    private TestAdapter adapter;
    private TestDAO testDAO;

    @BindView(R.id.recycler_tests)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        setTests();
        setRecycler();
    }

    private void setTests() {
        tests = new ArrayList<>();

        List<String> topics = new ArrayList<>();
        topics.add("Objeto Direto");
        topics.add("Objeto Indireto");
        topics.add("Sujeito");
        Test pt = new Test(0,"Portugues","20/01/1999",topics);
        List<String> topicsMath = new ArrayList<>();
        topicsMath.add("Equações de Segundo Grau");
        topicsMath.add("Trigonometria");
        Test math = new Test(1,"Matemática","21/01/1999",topicsMath);
        tests.add(pt);
        tests.add(math);
    }

    private void setRecycler() {
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        adapter = new TestAdapter(tests,this,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTestItemClicked(View v, int position) {
        Toast.makeText(this, tests.get(position).toString(), Toast.LENGTH_SHORT).show();
    }
}
