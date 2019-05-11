package com.example.agenda.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.agenda.R;
import com.example.agenda.activities.TestListActivity;
import com.example.agenda.adapters.TestAdapter;
import com.example.agenda.models.Test;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestListFragment extends Fragment implements TestAdapter.AdapterClickListener {

    private List<Test> tests;
    private TestAdapter adapter;

    @BindView(R.id.recycler_tests)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_list,container,false);
        ButterKnife.bind(this,view);

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
        tests.add(pt);
        tests.add(math);

        setRecycler();

        return view;
    }

    private void setRecycler() {
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        adapter = new TestAdapter(tests,getContext(),this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTestItemClicked(View v, int position) {
        TestListActivity activity = (TestListActivity) getActivity();
        activity.setTest(tests.get(position));
    }
}