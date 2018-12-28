package com.example.fouaad.hospitalstracker;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UserVisitsAdapter extends RecyclerView.Adapter<UserVisitsAdapter.MyViewHolder>{

    private List<UserVisit> visitsList;
    private ItemClickListener monClickListener;

    public interface ItemClickListener{
        void onItemClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView hospitalName, rating, state;

        public MyViewHolder(View view) {
            super(view);
            hospitalName = view.findViewById(R.id.txt_hospital_name);
            rating = view.findViewById(R.id.txt_rate);
            state = view.findViewById(R.id.txt_status);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            monClickListener.onItemClickListener(this, getAdapterPosition());
        }
    }

    public UserVisitsAdapter(List<UserVisit> visitsList, ItemClickListener listener) {
        this.visitsList = visitsList;
        this.monClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visits_list_item, parent, false);

        return new UserVisitsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserVisit u = visitsList.get(holder.getAdapterPosition());
        holder.hospitalName.setText(u.getHospital());
        if(u.getState().equals("0")){// visiting
            holder.rating.setText("no rate");
            holder.state.setText("Visitng");
            holder.state.setTextColor(Color.RED);
        }else{//visited
            holder.rating.setText("Your rate: " + u.getUserRating());
            holder.state.setText("Visited");
            holder.state.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return visitsList.size();
    }

}
