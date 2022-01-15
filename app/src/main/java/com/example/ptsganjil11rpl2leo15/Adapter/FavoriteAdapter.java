package com.example.ptsganjil11rpl2leo15.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptsganjil11rpl2leo15.Model.DataModel;
import com.example.ptsganjil11rpl2leo15.Model.ModelSports;
import com.example.ptsganjil11rpl2leo15.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<DataModel> dataModels;
    private Callback callback;


    public interface Callback{
        void onClick(int position);
    }

    public FavoriteAdapter(List<DataModel> dataModelList, Callback callback) {
        this.dataModels = dataModelList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteViewHolder holder, int position) {

        holder.nama.setText(dataModels.get(position).getNama());
        holder.deskripsi.setText(dataModels.get(position).getDeskripsi());
        Picasso.get()
                .load(dataModels.get(position).getImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return  (dataModels != null) ? dataModels.size() : 0;
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView nama;
        private TextView deskripsi;
        private CardView cardView;


        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
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
