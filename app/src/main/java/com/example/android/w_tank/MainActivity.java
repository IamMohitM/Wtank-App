package com.example.android.w_tank;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class MainActivity extends ListActivity {

    OkHttpClient client;
    public static final String TAG=MainActivity.class.getSimpleName();

    public static String[] nodes;
    public static ArrayList<String> nodeList=new ArrayList<String >();
    public static final String NODES_URL="http://aws2.axelta.com/services/device/deviceNodeAsset/Tiger/mohit@axelta.com/9jIQJj";
    ProgressDialog progressDialog;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView=getListView();
        client=new OkHttpClient();
        new GetNodes().execute(NODES_URL);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,NodeActivity.class);
                intent.putExtra("POSITION",position);
                startActivity(intent);
            }
        });
    }

    public class GetNodes extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Getting All Nodes..");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            try {

                JSONArray nodeArray=new JSONArray(s);
                JSONObject obj=nodeArray.getJSONObject(0);
                JSONArray nodeData=obj.getJSONArray("node_data");
                nodes=new String[nodeData.length()];
                for(int i=0;i<nodeData.length();i++)
                {
                    JSONObject ob=nodeData.getJSONObject(i);
                    nodes[i]=ob.getString("node_no");
                    nodeList.add("Node "+nodes[i]);
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,nodeList);
                listView.setAdapter(adapter);
            } catch (JSONException e) {
                Log.e(TAG,""+e.getMessage());
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String ...url) {
            return new HttpHandler().makeServiceCall(url[0]);
        }
    }
}
