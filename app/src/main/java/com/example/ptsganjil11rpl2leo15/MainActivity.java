package com.example.ptsganjil11rpl2leo15;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.ptsganjil11rpl2leo15.Adapter.SportAdapter;
import com.example.ptsganjil11rpl2leo15.Model.ModelSports;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SportAdapter sportAdapter;
    private ArrayList<ModelSports> sportsArrayList;

    private Button buttonFavorite;

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
        AndroidNetworking.get("https://www.thesportsdb.com/api/v1/json/2/all_sports.php")
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
                            JSONArray jsonArray = response.getJSONArray("sports");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String nama = jsonObject.getString("strSport");
                                String diskripsi = jsonObject.getString("strSportDescription");
                                String image = jsonObject.getString("strSportThumb");

                                sportsArrayList.add(new ModelSports(image,nama, diskripsi));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        sportAdapter = new SportAdapter(sportsArrayList, new SportAdapter.Callback() {
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