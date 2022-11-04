package com.murfy.groupify.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.murfy.groupify.models.Group;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchApi {
    private final String search_api_path = "search.php";

    private final Context context;

    public SearchApi(Context context){
        this.context = context;
    }

    public String getSearchFromJson(JSONObject res) throws JSONException {
        String searchTerm = res.getString("search_term");
        return searchTerm;
    }

    public void getRecentSearch(String user_id, CrudCallback<ArrayList<String>> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Crud.base_url + search_api_path + "?user_id=" + user_id,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            ArrayList<String> searches = new ArrayList<>();
                            JSONArray searches_array = res.getJSONArray("data");
                            for(int i = 0; i < searches_array.length(); i++){
                                JSONObject search = searches_array.getJSONObject(i);
                                searches.add(getSearchFromJson(search));
                            }
                            callback.onSuccess(searches);
                        }else{
                            CrudError error = new CrudError(status, res.getString("message"));
                            callback.onError(error);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        CrudError error = new CrudError(400, "Unknown Error occurred, try again later");
                        callback.onError(error);
                    }

                }, error -> {
            Log.i("ERROR", error.toString());
            CrudError err = new CrudError(500, error.getMessage());
            callback.onError(err);
        });

        Crud.getInstance(context).addRequestToQueue(stringRequest);
    }
    public void postRecentSearch(String user_id, String search_term, CrudCallback<Object> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Crud.base_url + search_api_path,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            callback.onSuccess(null);
                        }else{
                            CrudError error = new CrudError(status, res.getString("message"));
                            callback.onError(error);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        CrudError error = new CrudError(400, "Unknown Error occurred, try again later");
                        callback.onError(error);
                    }

                }, error -> {
            Log.i("ERROR", error.toString());
            CrudError err = new CrudError(500, error.getMessage());
            callback.onError(err);
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String,String>();
                params.put("user_id", user_id);
                params.put("search_term", search_term);
                return params;
            }
        };

        Crud.getInstance(context).addRequestToQueue(stringRequest);
    }
}