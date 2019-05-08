package com.example.agenda.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.agenda.R;
import com.example.agenda.models.User;
import com.example.agenda.adapters.UserAdapter;
import com.example.agenda.fragments.UserDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements UserDialogFragment.NoticeDialogListener,
        UserAdapter.UserAdapterListener {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private List<User> users;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        users = new ArrayList<>();

        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        adapter = new UserAdapter(users,this);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.fab_add)
    public void OnClick(){
        showUserDialogFragment(UserDialogFragment.CREATE_TAG,-1);
    }

    @Override
    public void onDialogPositiveClick(User user, String position) {
        // DAO
    }

    @Override
    public void onEditItemClick(View v, int position) {
        showUserDialogFragment(UserDialogFragment.EDIT_TAG,position);
    }

    private void showUserDialogFragment(String tag, int position){
        FragmentManager fragmentManager = getSupportFragmentManager();
        UserDialogFragment dialogFragment;
        if(tag.equals(UserDialogFragment.EDIT_TAG)){
            dialogFragment = UserDialogFragment.newInstance(UserDialogFragment.EDIT_TAG,"Atualizar",users.get(position),position);
        }else{
            dialogFragment = UserDialogFragment.newInstance(UserDialogFragment.CREATE_TAG,"Adicionar");
        }
        dialogFragment.show(fragmentManager,tag);
    }
}