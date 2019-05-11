package com.example.agenda.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.agenda.models.User;
import com.example.agenda.providers.UserDAO;

import java.util.List;

public class SendUserTask extends AsyncTask<Void,Void,String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private ProgressDialog dialog;

    public SendUserTask(Context context) {
        this.context = context;
        dialog = new ProgressDialog(context);
    }

    @Override
    protected String doInBackground(Void... objects) {
        UserDAO userDAO = new UserDAO(context);
        List<User> users = userDAO.searchAll();
        String json = new UserConverter().toJSON(users);
        WebClient webClient = new WebClient();
        return webClient.post(json);
    }

    @Override
    protected void onPreExecute() {
        dialog.setTitle("Aguarde");
        dialog.setMessage("Enviando usuários...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
