package com.example.agenda.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.agenda.R;
import com.example.agenda.models.User;
import com.example.agenda.adapters.UserAdapter;
import com.example.agenda.fragments.UserDialogFragment;
import com.example.agenda.providers.UserDAO;

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
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUsers();
        setRecycler();
    }

    private void setRecycler() {
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        adapter = new UserAdapter(users,this);
        recyclerView.setAdapter(adapter);
    }

    private void setUsers()  {
        userDAO = new UserDAO(this);
        try{
            users = userDAO.searchAll();
        } catch(Exception e){
            Log.v("USERLIST ERROR", e.getMessage());
        }
    }

    @OnClick(R.id.fab_add)
    public void OnClick(){
        showUserDialogFragment(UserDialogFragment.CREATE_TAG,-1);
    }

    @Override
    public void onDialogPositiveClick(User user, int position) {
        if(user.getId() == 0) {
            try {
                userDAO.create(user);
                users.add(user);
                recyclerView.scrollToPosition(position);
            } catch (Exception e) {
                Log.v("CREATE USER ERROR", e.getMessage());
            }
        }else{
            try{
                userDAO.update(user);
                users.set(position,user);
            } catch(Exception e) { Log.v("UPDATE USER ERROR",e.getMessage()); }

        }
        adapter.notifyDataSetChanged();
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