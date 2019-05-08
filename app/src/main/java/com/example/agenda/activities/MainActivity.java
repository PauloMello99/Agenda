package com.example.agenda.activities;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements
        UserDialogFragment.NoticeDialogListener,
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

    /**
     * Configure RecyclerView
     */
    private void setRecycler() {
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        adapter = new UserAdapter(users,this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Set the saved users to the list
     */
    private void setUsers()  {
        userDAO = new UserDAO(this);
        users = userDAO.searchAll();
    }

    /**
     * Floating Action Button Click
     */
    @OnClick(R.id.fab_add)
    public void OnClick(){
        showUserDialogFragment(UserDialogFragment.CREATE_TAG,-1);
    }

    /**
     * Listener for DialogFragment Creation
     * @param user
     * @param position
     */
    @Override
    public void onDialogPositiveClick(User user, int position) {
        if(user.getId() < 0) {
            userDAO.create(user);
            users.add(user);
            recyclerView.scrollToPosition(adapter.getItemCount());
        }else{
            userDAO.update(user);
            users.set(position,user);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Listener for DialogFragment Edit
     * @param v
     * @param position
     */
    @Override
    public void onEditItemClick(View v, int position) {
        showUserDialogFragment(UserDialogFragment.EDIT_TAG,position);
    }

    @Override
    public void onRemoveItemLongClick(View v, int position) {
        showDeleteDialog(v,position);
    }

    private void showDeleteDialog(View v, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmação");
        builder.setMessage("Tem certeza que deseja excluir esse usuário?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userDAO.delete(users.get(position));
                users.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.cancel,null);
        builder.create();
        builder.show();
    }

    /**
     * Instance of DialogFragment
     * @param tag
     * @param position
     */
    private void showUserDialogFragment(String tag, int position){
        FragmentManager fragmentManager = getSupportFragmentManager();
        UserDialogFragment dialogFragment;
        if(tag.equals(UserDialogFragment.EDIT_TAG)){
            dialogFragment = UserDialogFragment.newInstance(UserDialogFragment.EDIT_TAG,"Atualizar",users.get(position),position);
        }else{
            dialogFragment = UserDialogFragment.newInstance(UserDialogFragment.CREATE_TAG,"Adicionar");
            dialogFragment.setCancelable(false);
        }
        dialogFragment.show(fragmentManager,tag);
    }
}