package com.example.agenda.utils;

import android.util.Log;

import com.example.agenda.models.User;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

public class UserConverter {

    public String toJSON(List<User> users) {
        JSONStringer jsonStringer = new JSONStringer();
        try {
            jsonStringer.object().key("list").array().object().key("user").array();
            for(User user : users){
                jsonStringer.object();
                jsonStringer.key("name").value(user.getName());
                jsonStringer.key("score").value(user.getScore());
                jsonStringer.endObject();
            }
            jsonStringer.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            Log.v("JSON_CONVERT_ERROR",e.getMessage());
        }
        return jsonStringer.toString();
    }
}
