package com.suska.uin.tif.zorokunti.dificam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewLastBobot extends AppCompatActivity {

    String jsonstring;
    JSONObject jsonObject;
    JSONArray jsonArray;
    BobotAdapter bobotAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_last_bobot);
        listView = (ListView) findViewById(R.id.listView1);

        bobotAdapter = new BobotAdapter(this,R.layout.row_layout);

        listView.setAdapter(bobotAdapter);
        jsonstring = getIntent().getExtras().getString("json_data");
        try {
            jsonObject = new JSONObject(jsonstring);
            jsonArray = jsonObject.getJSONArray("server_response");

            int count = 0;
            String v0,v1,v2;

            while (count < jsonArray.length())
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                v0 = JO.getString("v0");
                v1 = JO.getString("v1");
                v2 = JO.getString("V2");

                bobot bbt = new bobot(v0,v1,v2);
                bobotAdapter.add(bbt);

                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
