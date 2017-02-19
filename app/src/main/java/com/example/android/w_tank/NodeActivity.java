package com.example.android.w_tank;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class NodeActivity extends Activity {

    ProgressDialog progressDialog;
    private static final String TAG=NodeActivity.class.getSimpleName();
    private static final String NODE_URL="http://aws2.axelta.com/services/node/getTransactions?user_name=";
    private static final String LIMIT="&limit=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_node);
        String NODE_NO=MainActivity.nodes[getIntent().getIntExtra("POSITION",0)];
        String DEVICE_NO=getIntent().getStringExtra("device");
        String USER_NAME=getIntent().getStringExtra("user_name");
        Log.e(TAG,NODE_URL+USER_NAME+"&device_no="+DEVICE_NO+"&node_no="+NODE_NO+LIMIT);
        new GetNodeData().execute(NODE_URL+USER_NAME+"&device_no="+DEVICE_NO+"&node_no="+NODE_NO+LIMIT);

        getActionBar().setTitle("Node "+NODE_NO);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);

    }

    public class GetNodeData extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(NodeActivity.this);
            progressDialog.setMessage("Getting data for Node Selected..");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(progressDialog.isShowing())
                progressDialog.dismiss();

            try {
                JSONArray array=new JSONArray(s);
                JSONObject obj=array.getJSONObject(0);
                TextView timestamp=(TextView)findViewById(R.id.time_stamp_tv);
                TextView distance=(TextView)findViewById(R.id.distance_value_tv);
                RadioButton full=(RadioButton)findViewById(R.id.full);
                Date date=new Date(1000*Long.parseLong(obj.get("timestamp").toString()));
                SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                //SimpleDateFormat sdt=new SimpleDateFormat("hh:mm a");

                timestamp.setText(sdf.format(date));
                //timestamp.setText(obj.get("timestamp").toString());
                distance.setText(obj.getString("distance"));
                if("1".equals(obj.getString("full")))
                {
                    full.setVisibility(View.VISIBLE);
                    full.setChecked(true);
                    full.setText("Full");
                }
                else if("1".equals(obj.getString("empty")))
                {
                    full.setChecked(true);
                    full.setText("Empty");
                }
                else
                    full.setVisibility(View.INVISIBLE);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String ...url) {
            return new HttpHandler().makeServiceCall(url[0]);

        }
    }
}
