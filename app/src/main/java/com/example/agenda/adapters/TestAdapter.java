package com.example.agenda.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.agenda.R;
import com.example.agenda.models.Test;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    public interface AdapterClickListener{
        void onTestItemClicked(View v, int position);
    }

    private static AdapterClickListener listener;
    private List<Test> testList;
    private Context context;

    public TestAdapter(List<Test> testList, Context context,AdapterClickListener listener) {
        TestAdapter.listener = listener;
        this.testList = testList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_test,viewGroup,false);
        return new TestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Test test = testList.get(i);
        viewHolder.subjectTextView.setText(test.getSubject());
        viewHolder.dateTextView.setText(test.getDate());
        viewHolder.topicsTextView.setText(test.getTopics().toString());
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.card_subject)
        TextView subjectTextView;

        @BindView(R.id.card_date)
        TextView dateTextView;

        @BindView(R.id.card_topics)
        TextView topicsTextView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onTestItemClicked(v,this.getAdapterPosition());
        }
    }
}
