package com.example.ptsganjil11rpl2leo15.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptsganjil11rpl2leo15.Model.ModelSports;
import com.example.ptsganjil11rpl2leo15.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PremierLeagueAdapter extends RecyclerView.Adapter<PremierLeagueAdapter.SportViewHolder> {

    private ArrayList<ModelSports> listModelSport;
    private Callback callback;

    public interface Callback{
        void onClick(int position);
    }

    public PremierLeagueAdapter(ArrayList<ModelSports> sportsArrayList, Callback callback) {
        this.listModelSport = sportsArrayList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public PremierLeagueAdapter.SportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        return new SportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PremierLeagueAdapter.SportViewHolder holder, int position) {

        holder.nama.setText(listModelSport.get(position).getNama());
        holder.deskripsi.setText(listModelSport.get(position).getDeskripsi());
        Picasso.get()
                .load(listModelSport.get(position).getImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return  (listModelSport != null) ? listModelSport.size() : 0;
    }

    public class SportViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView nama;
        private TextView deskripsi;
        private CardView cardView;

        public SportViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageSports);
            nama = itemView.findViewById(R.id.tvNamaSports);
            deskripsi = itemView.findViewById(R.id.tvDeskripsiSports);
            cardView = itemView.findViewById(R.id.cardview);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(getLayoutPosition());
                }
            });
        }
    }
}
