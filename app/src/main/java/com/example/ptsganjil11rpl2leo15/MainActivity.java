package com.example.ptsganjil11rpl2leo15;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.ptsganjil11rpl2leo15.Adapter.PremierLeagueAdapter;
import com.example.ptsganjil11rpl2leo15.Model.ModelSports;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PremierLeagueAdapter sportAdapter;
    private ArrayList<ModelSports> sportsArrayList;

    private Button buttonFavorite;
    private ArrayList<Object> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        buttonFavorite = findViewById(R.id.btnFavorite);

        getData();

        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getData(){
        AndroidNetworking.get("https://www.thesportsdb.com/api/v1/json/2/search_all_teams.php?s=Soccer&c=Spain")
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        sportsArrayList = new ArrayList<>();

                        try {
                            JSONArray jsonArray = response.getJSONArray("teams");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String nama = jsonObject.getString("strTeam");
                                String diskripsi = jsonObject.getString("strDescriptionEN");
                                String image = jsonObject.getString("strTeamBadge");

                                sportsArrayList.add(new ModelSports(image,nama, diskripsi));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        sportAdapter = new PremierLeagueAdapter(sportsArrayList, new PremierLeagueAdapter.Callback() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                intent.putExtra("image", sportsArrayList.get(position).getImage());
                                intent.putExtra("nama", sportsArrayList.get(position).getNama());
                                intent.putExtra("deskripsi", sportsArrayList.get(position).getDeskripsi());
                                startActivity(intent);
                            }
                        });

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                        recyclerView.setAdapter(sportAdapter);
                        recyclerView.setLayoutManager(layoutManager);
                    }


                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

}