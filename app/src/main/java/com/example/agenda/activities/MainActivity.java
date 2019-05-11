package com.example.agenda.activities;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.agenda.R;
import com.example.agenda.models.User;
import com.example.agenda.adapters.UserAdapter;
import com.example.agenda.providers.UserDAO;
import com.example.agenda.utils.SendUserTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{

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
        registerForContextMenu(recyclerView);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECEIVE_SMS},124);
        }
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
        Intent intentProfile = new Intent(MainActivity.this,ProfileActivity.class);
        intentProfile.putExtra("USER",new User());
        intentProfile.putExtra("POSITION",-1);
        intentProfile.putExtra("TAG","ADD");
        startActivityForResult(intentProfile,555);
    }

    /**
     * Context Menu
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int userId = item.getGroupId();
        switch(item.getItemId()){
            case 0:
                if(!users.get(userId).getPhone().isEmpty()){
                    // CHECK CALL PERMISSION
                    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},123);
                    }
                    else{
                        Intent intentCALL = new Intent(Intent.ACTION_CALL);
                        intentCALL.setData(Uri.parse("tel:"+users.get(userId).getPhone()));
                        try{ startActivity(intentCALL);  } catch (Exception e) {
                            Log.v("OPEN_CALL_ERROR",e.getMessage());
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle(getString(R.string.error_title));
                            builder.setMessage(e.getMessage()).create().show();
                        }
                    }
                } else Toast.makeText(this, getString(R.string.number_not_found) , Toast.LENGTH_LONG).show();
                break;
            case 1:
                if(!users.get(userId).getPhone().isEmpty()){
                    Intent intentSMS = new Intent(Intent.ACTION_VIEW);
                    intentSMS.setData(Uri.parse("sms:"+users.get(userId).getPhone()));
                    try{ startActivity(intentSMS); } catch (Exception e) {
                        Log.v("OPEN_SMS_ERROR",e.getMessage());
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle(getString(R.string.error_title));
                        builder.setMessage(e.getMessage()).create().show();
                    }
                } else Toast.makeText(this, getString(R.string.number_not_found) , Toast.LENGTH_LONG).show();
                break;
            case 2:
                if(!users.get(userId).getAddress().isEmpty()){
                    Intent intentSMS = new Intent(Intent.ACTION_VIEW);
                    intentSMS.setData(Uri.parse("geo:0,0?q="+users.get(userId).getAddress()));
                    try{ startActivity(intentSMS); } catch (Exception e) {
                        Log.v("OPEN_MAPS_ERROR",e.getMessage());
                        Toast.makeText(this, getString(R.string.address_not_found) , Toast.LENGTH_LONG).show();
                    }
                } else Toast.makeText(this, getString(R.string.address_not_found) , Toast.LENGTH_LONG).show();
                break;
            case 3:
                    if(!users.get(userId).getUrl().isEmpty()){
                        Intent intentSite = new Intent(Intent.ACTION_VIEW);
                        String site = users.get(userId).getUrl();
                        if(!site.startsWith("http://")) site = "http://" + site;
                        intentSite.setData(Uri.parse(site));
                        try{ startActivity(intentSite); } catch(Exception e) {
                            Log.v("OPEN_SITE_ERROR",e.getMessage());
                            Toast.makeText(this, getString(R.string.site_not_found) , Toast.LENGTH_LONG).show();
                        }
                    } else Toast.makeText(this, getString(R.string.site_not_found) , Toast.LENGTH_LONG).show();
                break;
            case 4:
                Intent intentProfile = new Intent(MainActivity.this,ProfileActivity.class);
                intentProfile.putExtra("USER",users.get(userId));
                intentProfile.putExtra("POSITION",userId);
                intentProfile.putExtra("TAG","EDIT");
                startActivityForResult(intentProfile,555);
                break;
            case 5:
                showDeleteDialog(userId);
                break;
        }
        return true;
    }

    /**
     * Confirm the user delete
     * @param position
     */
    private void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.confirmation));
        builder.setMessage(getString(R.string.delete_confirm));
        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
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
     * Search View inflate and query
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                search(s);
                return true;
            }
        });
        return true;
    }

    /**
     * Menu Options
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_send) {
            new SendUserTask(this).execute();
        }
        return true;
    }

    /**
     * Search user by name
     * @param s query string
     */
    private void search(String s) {
        List<User> filteredUsers = new ArrayList<>();
        for(User user : users){
            if(user.getName().toLowerCase().contains(s.toLowerCase())) filteredUsers.add(user);
        }
        UserAdapter filteredAdapter = new UserAdapter(filteredUsers,this);
        recyclerView.setAdapter(filteredAdapter);
    }

    /**
     * Result of others activities
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == 555){
            String tag = data.getStringExtra("TAG");
            User user = (User) data.getSerializableExtra("EDITUSER");
            if(tag.equals("EDIT")){
                int pos = data.getIntExtra("POSITION",-1);
                userDAO.update(user);
                users.set(pos,user);
            } else {
                userDAO.create(user);
                users.add(user);
                recyclerView.scrollToPosition(adapter.getItemCount());
            }
            adapter.notifyDataSetChanged();
        }
    }
}