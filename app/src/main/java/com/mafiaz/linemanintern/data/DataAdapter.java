package com.mafiaz.linemanintern.data;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.mafiaz.linemanintern.R;
import com.mafiaz.linemanintern.functions.checkPosition;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.myViewHolder> {

    private final Activity activity;
    private ArrayList<RespondData.Coins> data;

    private final onItemClickListener listener;

    public DataAdapter(Activity activity, onItemClickListener listener, ArrayList<RespondData.Coins> inputData) {
        this.activity = activity;
        this.listener = listener;
        this.data = inputData;

    }

    public void setDataAdapter(ArrayList<RespondData.Coins> newData) {
        this.data.clear();
        this.data.addAll(newData);

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (checkPosition.getResult(position)) {
            return 1;  //Second View
        }
        return 0;  //First View
    }

    @NonNull
    @Override
    public DataAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == 1) {  //Second View
            View view = layoutInflater.inflate(R.layout.item_list_2, parent, false);
            return new myViewHolder(view, listener);
        }
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        return new myViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.myViewHolder holder, int position) {
        if (data.get(position).getIconUrl().contains(".png")) {
            Glide.with(activity).load(Uri.parse(data.get(position).getIconUrl()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_error)
                    .into(holder.iconImage);
        } else {
            GlideToVectorYou.init()
                    .with(activity)
                    .setPlaceHolder(R.drawable.img_placeholder, R.drawable.img_error)
                    .load(Uri.parse(data.get(position).getIconUrl()), holder.iconImage);
        }

        holder.title.setText(data.get(position).getSymbol());

        if (checkPosition.getResult(position)) {
            holder.desc.setText(data.get(position).getCoinrankingUrl());
        } else {
            holder.desc.setText(data.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iconImage;
        TextView title;
        TextView desc;
        CardView cardView;
        onItemClickListener listener;

        public myViewHolder(@NonNull View itemView, onItemClickListener listener) {
            super(itemView);

            iconImage = itemView.findViewById(R.id.item_icon);
            title = itemView.findViewById(R.id.item_title);
            desc = itemView.findViewById(R.id.item_desc);

            cardView = itemView.findViewById(R.id.card_item);
            this.listener = listener;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition());
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }
}
