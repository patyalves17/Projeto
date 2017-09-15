package com.projeto.patyalves.projeto.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.projeto.patyalves.projeto.MainActivity;
import com.projeto.patyalves.projeto.PlaceDetailFragment;
import com.projeto.patyalves.projeto.PlacesFragment;
import com.projeto.patyalves.projeto.model.Local;
import com.projeto.patyalves.projeto.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.projeto.patyalves.projeto.PlacesFragment.*;

/**
 * Created by abceducation on 18/08/17.
 */

public class LocalAdapter  extends RecyclerView.Adapter<LocalAdapter.LocalViewHolder>{

    private List<Local> locais;
    private Context context;
    private OnItemClickListener listener;

    public LocalAdapter(Context context, List<Local> locais, OnItemClickListener listener){
        this.context=context;
        this.locais=locais;
        this.listener = listener;
    }

    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View meuLayout = inflater.inflate(R.layout.local_row, parent,false);
        return new LocalViewHolder(meuLayout);
    }

    @Override
    public void onBindViewHolder(LocalViewHolder holder, final int position) {
        holder.tvNome.setText(locais.get(position).getName());
        if(locais.get(position).getBairro()!=null){
            holder.tvBairro.setText(locais.get(position).getBairro());
        }

        if(locais.get(position).getImagem()!=null){
            byte[] decodedString = Base64.decode(locais.get(position).getImagem(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.tvFoto.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 400, 400, true));
        }

        if(locais.get(position).getRate()!=null){
            holder.ratingBar.setRating(Float.parseFloat( locais.get(position).getRate().toString()));
        }

    //    holder.tvFoto.setImageBitmap(Bitmap.createBitmap(decodedByte));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("carregaLocais", "clickou");
                Log.i("carregaLocais", "position "+locais.get(position).getName());

                listener.onItemClick(v,locais.get(position).getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return locais.size();
    }




    public class LocalViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivLogo;
        @BindView(R.id.tvNome) TextView tvNome;
        @BindView(R.id.tvBairro) TextView tvBairro;
        @BindView(R.id.tvFoto) ImageView tvFoto;
        @BindView(R.id.ratingBar) RatingBar ratingBar;



        public LocalViewHolder(View itemView) {
            super(itemView);
            //tvNome=(TextView) itemView.findViewById(R.id.tvNome);
            ButterKnife.bind(this, itemView);
        }
    }

    public void update(List<Local> locais){
        this.locais=locais;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, Long localId);
    }
}
