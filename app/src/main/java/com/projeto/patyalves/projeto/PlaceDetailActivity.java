package com.projeto.patyalves.projeto;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.projeto.patyalves.projeto.Util.DBHandlerP;
import com.projeto.patyalves.projeto.adapter.LocalAdapter;
import com.projeto.patyalves.projeto.api.APIUtils;
import com.projeto.patyalves.projeto.api.LocalsAPI;
import com.projeto.patyalves.projeto.model.Local;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceDetailActivity extends AppCompatActivity {

    private LocalAdapter localAdapter;
    private LocalsAPI localAPI;
    private Local local;
    Long id;



//    @BindView(R.id.fab) android.support.design.widget.FloatingActionButton fab;
//    @BindView(R.id.fab1) android.support.design.widget.FloatingActionButton fab1;
//    @BindView(R.id.fab2) android.support.design.widget.FloatingActionButton fab2;
//    @BindView(R.id.fab3) android.support.design.widget.FloatingActionButton fab3;



    private Boolean isFabOpen = false;
//    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    private BroadcastReceiver mReceiver;
    private DBHandlerP db;
    private ImageView ivFoto;
    private TextView tvNome, tvAddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_detail);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        db=new DBHandlerP(this);

        Bundle bundle=getIntent().getExtras();
         id=bundle.getLong("localId");
        Log.i("detalheLocal", "detalhes do--> "+id);


        final View fabMapa = findViewById(R.id.fabMapa);
        final View fabLigar = findViewById(R.id.fabLigar);
        final View fabVisitei = findViewById(R.id.fabVisitei);
        final View fabShare = findViewById(R.id.fabShare);


        ivFoto = (ImageView) findViewById(R.id.ivFoto);
        tvNome = (TextView) findViewById(R.id.tvNome);
        tvAddress = (TextView) findViewById(R.id.tvAddress);

        Log.d("Raj", "Fab try");
        loadDetails();


    }

    public void visitei(View v){
        Log.i("detalheLocal", "clicou no  visitei");
        Intent intent = new Intent(this,NewVisitedActivity.class);
        intent.putExtra("localId",local.getId());
        this.finish();
        startActivity(intent);
    }

    public void ligar(View v){
        String uri="";
        Log.i("detalheLocal", "clicou no  ligar");
        if(local.getTelefone()==null){
            uri = "tel:+55 11 960741456" ;
        }else {
            uri = "tel:" + local.getTelefone().replace(" ", "").replace("-", "");
        }
        Log.i("carregaLocais", "Call to " +uri);

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
//        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//
//            return;
//        }

        startActivity(intent);
    }

    public void mapa(View v){
        Log.i("detalheLocal", "clicou no  MAP");
        Intent intent = new Intent(this,PlaceDetailMapActivity.class);
        intent.putExtra("latitude", local.getLatitude());
        intent.putExtra("longitude", local.getLongitude());
        startActivity(intent);
    }

    public void share(View v){
        Log.i("detalheLocal", "clicou no  Share");
        String site="";
        if(local.getWebsite()!=null && !local.getWebsite().isEmpty()){
            site=local.getWebsite();
        }

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = this.getString(R.string.texto)+ " "+ local.getName() + " "+site;
//
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Match Places");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent,this.getString(R.string.shareVia)));


//        Intent share = new Intent(android.content.Intent.ACTION_SEND);
//        share.setType("text/plain");
//        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//
//        // Add data to the intent, the receiving app will decide
//        // what to do with it.
//        share.putExtra(Intent.EXTRA_SUBJECT, this.getString(R.string.was_here));
//        share.putExtra(Intent.EXTRA_TEXT, local.getName());
//
//        fragmentActivity.startActivity(Intent.createChooser(share, mContext.getString(R.string.share_location)));

    }

    private void loadDetails(){

        localAPI = APIUtils.getLocalsAPI();
        Log.i("detalheLocal", "carregando...");
        localAPI.getLocal(id).enqueue(new Callback<Local>() {
            @Override
            public void onResponse(Call<Local> call, Response<Local> response) {
                if (response.isSuccessful()) {
                   // Log.i("detalheLocal",response.body().getBairro());
                    local=response.body();

                    if(local.getEndereco()!=null && !local.getEndereco().isEmpty()){
                        tvAddress.setText(local.getEndereco().toString());
                    }
                    tvNome.setText(local.getName().toString());




                    if(local.getImagem()!=null){
                        byte[] decodedString = Base64.decode(local.getImagem(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        ivFoto.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 400, 400, true));
                       // holder.tvFoto.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 400, 400, true));
                    }
                    else{
                        byte[] decodedString = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAgAAAAIACAYAAAD0eNT6AAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAOEgAADhIBhi6TsAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAACyBSURBVHja7d15fF9lnejxYVUYdRhldLzjdcRlVGYcvTp6rzM4IIiiCMqiDCCCIiCyCS5souyb0LKvshQKXdibNl3omm5Jk6Zp0iZNmqVNm7aU7imoID73OeHnDEtbuiQ55/c77z/er3mNrxlmSM7v+X7ye855zl+FEP4KAMgXPwQAEAAAgAAAAAQAACAAAAABAAAIAABAAAAAAgAAEAAAgAAAAAQAACAAAAABAAAIAABAAAAAAgAAEAAAIAAAAAEAAAgAAEAAAAACAAAQAACAAAAABAAAIAAAAAEAAAgAAEAAAAACAAAQAACAAAAABAAAvW/p8uV/Gx0SnRvdHVVEy6Nl0cJobjQjejQ6PfqXaCc/OwEAQHEN/LdFX46ujmZFr0RhG62Ohkb/7mcqAADI5sDfKfpM9PNobPTidgz8LZkZHRXt4uctAABId+h/MPphNCRa2csDf3Maok/7+QsAAPpv4P9N9O3o9qi5nwb+pvyx8E2DewQEAAB9MPB3j/aPrih8Bf+nFIf+pkyI9va7EgAA7PjQ/1ThTv3yaGPGBv6mzEmeMPC7EwAAbNvA/4foxGhw4bG8UISSpwze5fcpAADY/MB/Z3RYdEvUWKQDf1OmR3v6HQsAAF4d+LtG+0WXFobkyyU09N9ogN+5AADI89DfNzo7Kos2lPDAf6PkJsXPuwYEAEBeBv77o+9Fg6KuHA38TUmOFN7NdSEAAEpx4L8j+kY0sHAwTuB1fuY6EQAApTDwd4m+GF1SeInOS4b8FrU5JEgAABTr0P94dEb0dLTOUN9m+7uOBABAMQz890bHRvdHnQb4DnvQdSUAALI48PeMDoluiOqiPxvavSo5xfAdrjUBAJD2wN85+kJ0UTSp8EIbg7pvfcu1JwAA0hj6H41+HD0RrTGQ+91trkMBANAfA3/v6LvRvVGHAZy6ZtelAADoi4H/9ujg6Lqo1j5+Jn3QtSoAAHpjH/9z0fnR+Oj3BmzmnezaFQAA2zP094lOiYZHqwzUojPUdSwAALZm4L87Ojq6K2o1QIve804FFAAAmxr4b4sOjK6OqqNXDM2S81nXugAADPydos9EP4/GRi8akCXvfNe+AADyOfQ/GP0wGhKtNBBzZ7zPgQAA8jHw94qOiG6PWgzA3Eue1tjDZ0MAAKU38HdP3v4WXRFVRn8y9HiDr/qsCACgNIb+p6Jzo/LCi18MObbktz43AgAozoH/D9GJ0eBohYHGNprjcyQAgOIY+O+KDo9uiZoMMHZQckzze322BACQvYG/a7RfdGk0PXrZ0KKXHeuzJgCAbAz9faOzo7JogwFFH7vf504AAOkM/PdHJ0SDoi4DiX62xOdQAAD9M/DfER0aDYzmGUBkwCd8NgUA0PsDf5foi9ElUUX0koFTGhYu6Qoj57aE26fWh/NHVYUThlWEk4ZPDT9+ckY4d0RlGDi5LkxpagtLsv/vcpbPqgAAemfofzw6I3o6WmdYlobFy5aF0Q0LwxXPzg6HPzQxfHRgWfjQjSPe0r/eWh5Of2pGmLqgPav/biN8bgUAsH0D/73RcckNVVGnYVk6pjW3h5um1IXjh1aEfW8ZtVUDf3P2ib43rCJMamzL2r9ncrPprj7LAgB464G/Z3RIdEM0t/A8tYFZAua2d4b7Zs7r+Rr/3+4YvUMDf3OSbw6uGV8bOpdl6t99P59tAQC8eeDvHH0huiiaFP3RsCydffzhs5vCL0ZWhS//7tk+Gfib862HJ4X6js6s/Cwu81kXAMCrQ/+j0enRE9Eaw7K09vEvH7dt+/h95YAYHfMWLcnCz2aGz70AgLwO/L2jY6J7o0WGZWnt4yd35PfGPn5fOOi+8aFxceoRkLwt8m+sBQIA8jDw3x4dHF0X1drHL7F9/Bl9u4/f2455dEoWfnbftjYIACjVffzPRedH46PfG5YltI9fk84+fm+6e3pD2j/L260VAgBKZejvE50aDY9WGZYltI9fn519/N7yL7eO6vn2IsWfbYt1QwBAsQ78d0dHR3dFbYZlCe3jL8j2Pn5vSb7FSPln/Y/WEgEAxTDw3xYdGF0dVUevGJb28YvZP8e4SbY0UvzZ/8jaIgAgiwN/p+gz0c+jsdGLhqV9/FJza8XcNH8Xw6w1AgCyMvQ/GJ0cDYlWGpb28Uvd1x+ckObvJrlXZmdrjwCANAb+XtERyR3JyU1JhqV9/Lz58IARaZ8L8DlrkQCA/hj4u0f7R1dElYUDSQxM+/i59nDV/DR/dxdYmwQA9NXQ/1R0XlQebTQsS0NL51L7+L3k7Gdmpvm7nGCdEgDQWwP/A9FJ0eBohWFZmvv4HxlgH7+3fPHusWn+bv8Q7WHtEgCwPQP/XdHh0S1Rk2FpH59tV7VwUZq/669ZywQAbM3A3y15n3h0aTQ9etmwtI/Pjrkt3ccBb7C2CQDY3NDfNzonKos2GJalt49/gH38VJ04fGqa18Jc65wAgL8M/PdHJ0SDoi7DsvT28Q+zj5+5dwN0xt9PStdG8nbM91n7BAD5HPjviA6NBkbzDMvS28c/zj5+5o2b15rmtXK8tVAAkI+Bv0v0xejXUUX0kmFZGuoK+/inPTndPn6RuerZ2WleOw9aGwUApTv0Px6dGT0drTcs7eOTLd9+eFKa19JS66QAoHQG/nuj46L7oyWGpX18si15R0Jrum8H/KS1UwBQnAN/z+iQ5JGe5K7ewo09hqZ9fIrIY7MXpHmtnW0tFQAUzz7+F6KLo0nRHw1L+/gUt1+Oqkrz2iuztgoAsjv0PxqdHj0RrTUs7eNTWpL3KqR4LXYnB31ZawUA2Rj4e0fHRPdGiwzLUtvHr7GPz5sk3wCleH1+ydorAEhn4O8RHRxdF9Xax7ePT/78bkZDmtfq5dZiAUD/DPydo88l7+SOxke/NyxLZx8/WciTffzP2cdnG5z2xPQ0r92Z1mYBQN8N/X2iU6Ph0SrDsnT28YfZx6cXfPb20Wley3+K9rJWCwB6Z+C/Ozo6uitqMyxLZx+/3D4+fWRKU3ua1/cR1m4BwPYN/LdFB0bXRNXRKwZmaZhqH59+csOkOWle63dYywUAWzfwd4o+E/0iGhu9aFjax4cdccyjU9K89hda2wUAmx/6H4xOjoZEKw1L+/jQmz5x88jQ0bUszc/Dh6z1AoBXB/5eyb5YdHvUYljax4e+VlbXkubn4xRrvwDI68DfPdo/ujKqLNwZa2jax4d+8+sx1Wl+VoabBQIgT0P/U9F5UXm00bC0jw9p+vqDE9L87KxOzikRAIZjqQ78D0QnRYOjFYalfXzIkg8PGBEaFy9J8/P0bwLAsCyVgf+u6PDo1qjJsCwNi7rs41O6Hq5qTPPzdaEAMDyLdeDvFu0XXRZNj142MEtvH/+TN480KChZZz8zM83P2kQBYJgW09DfNzonea914dWWBqZ9fChaX7x7bJqfvT9EewoAsjrw3x+dEA2KugzLEtzHv9c+PvlWtXBRmp/HQwQAWRn474gOjW6K5hmWJbiPP8g+PrzW7VPr0/x83igASGvg7xp9Mfp1VBG9ZGCW2D7+EPv4sCUnDZ+a5me1XgDQn0P/49GZ0dPResPSPj7k2aduLQ+dy1L9/P69AKCvBv77ouOi+6MlhqV9fOD1xs1rTfPz/D0BQG8N/D2TG0uSvaVobvRnA9M+PrB5V42fnebne5AAYHsH/i7RF6KLo0nRHw1M+/jA1vv2w5PS/Kx3CQC2Zeh/NDo9eiJaa1iWhjlti1/dx3/CPj70p48NLAutS7rS/Pz/swBgcwN/7+iY6HfRIsOyNDQX9vF/XmYfH9L22OwFaa4HPxUA/GXg7xEdHF0f1drHL619/MvG2seHrPnlqKo014dRAiC/A3/n6HPRBdH4whGRhmaJ7OMPmGQfH7LuwPueTXOtSF6RvpsAyM/Q/3B0ajS88G5oA7NE9vHvnW4fH4rR3PbONNeP/QVA6Q78d0dHR3dHbYZl6ezjD622jw+l4L4Z89JcT64QAKUz8N8WHRhdE1VHrxiYpbGPP2pui318KEHJSZopri+VAqB4B/5O0WeiX0RjoxcNzNJQ0WQfH/Ig2bZLca35U7SXACieof+P0cnR0GilYVl6+/ifvd0+PuRJEvwprj9HCoDsDvy9oiOiO6IWw9I+PlBabpxUl+Z6dKcAyM7A3z25MzO6MtmfKXxFY2jaxwdK1H8NmZLm+tQqANLdx/9UdF5UXng209C0jw/kxCfi+tAR/0hIcb3aRwD039D/QHRS9Ei0wrC0jw/kW1ldS5rr16kCoO8G/ruiw6Nbo6ZSGHbLV64Mz69eHVavWRPWrF0b1q1fHzZs2JAr66O18d971dr1YcWadWH56rUA22XVuhTX0O7ul7q7u9dlyPKoNhoZ3RNdGh0Y7Zr5AEiOV4z2iy6Lpkcvl8LQXxGHfjLwk8EXfxEA0J9WRQ9Eh0W7ZyYAklcrRudEZVF3KX21nQz+5C98Fx8AGbEoOj7aqd8DIA7G90cnRA9Fy0pxT3v5c8+FtWvXutAAyKrZyfZAnwdAHIp7R2dFNaV+Q9vKVatcWAAUi1u35h6B7Rn8X4ueil7Kw93syT6/iwmAIjMu2qtXAiAOw/8VPZGXx9i6VqwIa9etcxEBUKwWRB/d7gAoHM5zerQ+T8+xG/4AlIC26D3bHACFo3ifzNshNr72B6CETNjUPQFbGv57Fl6rm6vh74Y/AErQLVsVAIXT+iryNvyTR/1cJACUqMO2GACFPf/ReTy/3r4/ACVsfrTLlgLgnDwO/+SEPxcHACXu5E0GQByE/xr9IY8B4HhfAHJgabTH6wIgDsFdo/n++geAknbiGwPguLy+u95jfwDkyONvDIC6vAbABq/0BSA/NkS7/2X4fzWvw3+5r/8ByJ+v/iUAyvMaAKtWr3YhAJA31yXDf7foxdzu/69d60IAIG8eTQLg3/M6/B3+A0BOTU4C4II8B8B6z/8DkD8LkwAYlesA8AQAAPnzQhIAc/I08GvbFod7pjeEU5+YHj57+2gXAQC5lARASykP/ObOpWFIdVP4WVll2P/eceFDN454HRcBAHkNgK5SGviLupaFkXNbwqVja8I3B00MHx5Q9qahLwAAEADLl68r9qFf0dQebpxUF44dMiV84uaRWxz4AgAAXg2AlcU49GctXBSufHZ2+M9NfK0vAADgrQOgopgGf3Uc/McPrdihoS8AABAAy5ffXQyDv6NrWbhq/Ozw8ZtG9trwFwAA5DkAfpr14d/SuTQcOXhyrw5+AQBA3gMg028CnL9oSTjkgQl9MvwFAAB5DoB3Ri9ncfi3Le0KB933bJ8N/2IMgNVrV4SO9gmhbf59oWPugLBk9q/D8lnnhOeqzqSXTWmuCMM7XwB4kyei8qUvhInLNobK5zaGtjUbw/piC4DC64CnZjEAfjqisk+Hf7EEwIYN60N7+7iwuPaKsGbmSWHtzO/TD6bNLwsPL3oBYKsMXfxCmLR8Y+hcW1wB8JusDf9HZzX2+fAvhgDo7KwMS2vON5BTUDVvuEUN2C5JCDy3vjgCIFOvBF68bFn4v3eNyXUArFu3OnTMHWgQp6i2/kELGbDdHln8Qmh4fmPmA2DXaH1WAuDByvn9MvyzGgDPr+qMf/VfZAinrKHuDosYsMOmr9gYNmQ1AAoR8ExWAuCrD4zPbQCsXLWo5wY0Azh9C+bcYPECesX4ZdmLgNcGwJlZGP4zWzr6bfhnLQCSr/2X1lxo+GZE2+zLLVxAr0meFshqAHwiCwFw57T63AaAPf9s6ay+wKIF9KrGVRuzFwCFCFiSdgD8+MkZuQyARYsqDN2MSc5XsGABvWnI4hfCqvXZDID70w6Ag+8fn7sASJ7zX1b9c0M3Y1ZVnmLBAnrdtBUbMxkAx6UdAAf87tncBUB762gDN6MGL9pgwQJ61eBo2brsBcB7oz+nGQD73TMudwGwZPbFhm1GDWt/zoIF9LoZGfgW4HUBUIiAuWkGwH/cPTZXAZA882/QZtdjbcssVkCvS94lkMUAuCHNADjm0Sm5CoD21nKDNsOGdKy2WAF9Yuna7AXA19IMgEvGVOcrAOpvMWgzfQ/ARgsV0CfqUj4meFMBsGf0x7QC4KGq+bkKgOQNfwatpwCA/Jn5XMYCoBABE9MKgNmti3MVAF3VvzBsM2pF1ZkWKaBP3xiYxQC4KM1tgC/145MAqd8EWHmqYZtRS6t/ZpEC+szorhcyGQCfTzMAziurzE0ArK78oWGbUYtqLrZIAX1m5NJsBsDO0Zq0AmBIdZMAIAMvA7rCIgXkKwAKEfB4WgHQ3Lk0fGRAmQAgVc2111ukgFwGwGlpbgMcNmiiACBV8+putUgBuQyAj6QZAJePqxEApKqqYZhFCshfABQioD2tABhdv1AAkKqJTZMtUkBuA+CetAJg8bJlYd9bRgkAUjNiYZNFCshtAHwnzW2A44dWCABSfA/AGosUkNsAeE/0SloBcNOUOgFAKpbPOscCBeQ3AAoRUJNWAExv7hAApGJh7dUWKCD3AXBNmtsAX7hzjACg382Ze58FCsh9AByUZgCc8dQMAUC/mzp/pAUKyH0AvC16Ma0AeLByvgCg341unm2BAvIdAIUIGJtWADR0dIZ9BAD9aM3Mk8KQjtUWKEAAxEH8izS3AQ6+f7wAoN+01/zG4gQIgEIAfCbNALiofJYAoN9UNzxqcQIEQCEAdopWphUAT81pFgD0mzHNNRYnQAC8JgKGpBUAbUu7wj/dNFIA0OeS38ejHWstToAAeE0A/DDNbYCjHpksAOhzbbMvtzABAuANAfDBNAPgugm1AoA+N6thqIUJEACbiIDmtAJg4vw2AUCfK2+ZY2ECBMAmAuD2tAJgSfTp28oFAH1mVeUp4ZGOdRYmQABsIgC+neY2wMmPTxMA9JmGutstSoAA2EwA/E30p7QC4K5p9QIAx/8CAqC/A6AQATPTCoDZrYsFAH1i+axzwuBFGy1KgADYQgBcnuY2wJfuGScA6HU1DYMtSIAAeIsA+M80A+C8skoBQK97urXVggQIgLcIgN2i7rQCYEh1owCgVy2q+ZXFCBAAWxkBI9MKgAWdS8OHB5QJAHrNtPkjLUaAANjKAPhpmtsA3xw0UQDQK9bMPCkMa3/OYgQIgK0MgH9OMwAuG1sjAPDsPyAA+jsAChGwLK0AKK9fKADoBSeGp1rbLESAANjGAHgorQBYvGxZ2PeWUQKAHdI4Z6BFCBAA2xEAJ6S5DXD80AoBwA4ZsbDJIgQIgO0IgPenGQA3TakTAGy35trrLECAANiBCJiXVgBMb+4QAGy3US31FiBAAOxAANyU5rcAX7hzjABgm7XOvsLiAwiAHQyAQ9MMgDOemiEA2GZjmmssPoAA2MEAeEf0UloB8MDMeQKAbdJSe42FBxAAOxoAhQioSCsAGjo6wz4CgK2U/KyfbO2w8AACoJcC4JI0twG+cv94AcBWmdUw1KIDCIBeDIAvphkAF5bPEgC8pa5Z54VHOtZZdAAB0IsBsEu0Lq0AeLJ2gQDgLY1trrLgAAKgNwOgEAFPpxUAbUu7wscGlgkANqtpzgCLDSAA+igAzkhzG+CoRyYLADZpVeUp4fG2pRYbQAD0UQD8U5oBcN2EWgHAJk2f/4yFBhAAfRUAhQjoTCsAJs5vEwBs4pn/a+OHbKOFBhAAfRwA96UVAEuiT99WLgD4b8tnnRWGtT9nkQEEQD8EwLFpbgP88LFpAoAea2aeFEa11FlgAAHQTwHwd9Gf0wqAO6fVCwB6VM573OICCID+CoBCBMxJKwBqWhcLAApn/dv3BwRAfwfAb9PcBtjvnnECwL6/hQUQACkEwFfTDIBzR1QKgNzu+/8glNv3BwRAagGwR/SHtAJgSHWjAMilE8PEpkkWFEAApBUAhQiYkFYALOhcGj48oEwAOOwHQACkEAAXpLkN8M1BEwVAjtQ0DLaQAAIgIwHwb2kGwGVjawRATtTX3WkRAQRAhgJg52h1WgFQXr9QAOTAgtrfhsGLui0igADISgAUIuCxtAJgUdeysO8towRACWuffWl4tGOdBQQQABkMgFPT3AY4bmiFACjh4T+0/XmLByAAMhoA+6QZAAMn1wmAEtRce338y3+thQMQAFkNgEIEtKUVANOa2wVAiWmou8OePyAAiiQA7krzW4DP3zlGAJTKo371DzvfHxAARRQAR6cZAD95aoYAKIET/hzyAwiA4guAd0evpBUAD8ycJwCK+mz/k8KkpokWCUAAFFsAFCKgOq0AaOjoDPsIgKK0ourMUN4yxwIBCIAiDoCr09wG+Mr94wVAkVlYe3UY3r7c4gAIgCIPgC+nGQAXls8SAEXzlf+JoWre8DDYzX6AACiJAHhb9EJaAfBE7QIB4Ct/AAHQ3wFQiIAxaQVA29Ku8LGBZQLAV/4AAiCFAPh5mtsARw2eLAAyaFXlyWHmvCd95Q8IgBIOgE+nGQDXTqgVAFl7k9+cG8ITbYstAIAAKPEA2Cl6Lq0AmDC/VQBkRNes88K4BTN98AHyEACFCHg0rQDoXLY8fPq2cgGQouTfcVbDEK/wBchhAPwgzW2AHz42TQCkpKX22vBka4cPO0BOA+B/pxkAd06rFwD9rG325WFMc40POUCeA6AQAQvSCoCa1sUCoN/+4r8mjGqp8+EGEAD/HQC3pvktwH73jBMAfXxnf9nC+T7UAALgTQHwrTQD4NwRlQKgD47vbZxzU3hmYbMPM4AA2GwAvCt6Oa0AeHRWowDoJUuqfxlmznsiPN621IcYQABsVQTMSCsAFnQuDR8eUCYAttNzVT8Jc+ofCCMWNvrgAgiAbQ6Ay9LcBjh00EQBsI3P7zfOGdhzeM/gRRt8YAEEwHYHwH5pBsClY2sEwBadGBbXXBRm1w/qGfpDOlb7kAIIgF4JgN2i7rQCYNTcFgHwhoHfWX1Bz1f7zy6YHoa2r/ShBBAAfRYBZWkFwKKuZeGTN4/MZQA8X3lq6Ki5JDTU3R4q5z3eM/CHt6/wIQQQAP0WAOekuQ1w3NCKTAXAtMZRoaphWKiNf4U31N3R8zx9cope8pf58llnh1WVp7zFPv3JPTfoJS/aWVxzYfzfvSwsqP1t/Oc9GCoax4Tyljlx0C/3YQMQAKkHwL5pBsDAyXWZCoBHFr/1RZLcgJf8tf5ka3t4urW15/G7oe2r4n/e7UMEIACKIwAKEdCVVgBMa24vugAAQACUSgAMSvNbgM/fOUYAACAAUgiA76UZAKc/NUMAACAAUgiAv08zAO6fOU8AACAAUoqAhrQCoL6jM+wjAAAQAKkEwIA0vwU46L7xAgAAAZBCAHwjzQC4oHyWAABAAKQQAH8dvZRWADxRu0AAACAAUoqAKWkFQNvSrvCxgWUCAAABkEIA/CrNbYCjBk8WAAAIgBQC4P+lGQDXTqgVAAAIgBQCYJdobVoBMGF+qwAAQACkFAFPphUAncuWCwAABEBKAfCTNLcBBAAAAiCdAPiYAABAAOQsAAoRsEgAACAA8hcAvxMAAAiA/AXAMQIAAAGQvwDYO/qzAABAAOQoAAoRUCsAABAA+QuA6wUAAAIgfwFwsAAAQADkLwD2iH4vAAAQADkKgEIEjBcAAAiA/AXA+QIAAAGQvwD4nAAAQADkLwB2jlYJAAAEQI4CoBABwwQAAAIgfwFwigAAQADkLwA+JAAAEAA5C4BCBLQKAAAEQP4C4E4BAIAAyF8AHCkAABAA+QuAv41eEQAACIAcBUAhAqoEAAACIH8BcJUAAEAA5C8ADhAAAAiA/AXA7tELAgAAAZCjAChEwGgBAIAAyF8AnCcAABAA+QuAfxUAAAiA/AXATtEKAQCAAMhRABQi4BEBAIAAyF8AnCQAABAA+QuADwgAAARAzgKgEAFNAgAAAZC/ALhFAAAgAPIXAIcLAAAEQP4C4J3RywIAAAGQowAoRMA0AQCAAMhfAFwqAAAQAPkLgP8QAAAIgPwFwK7RBgEAgADIUQAUImCEAABAAOQvAM4SAAAIgPwFwCcFAMCOGdSxMdzdtjrctvC5cHPL8jCwuSvcsKAzXN+0KFzT2B6uamwNl89vCb+ZtyBc0tAYLqqfFy6YOzf8om5OOG/O7PDTOdXh7NqqcMbsmeHHNdPDqdVTw8mzpoQfzpocTqmuCKfVTAs/mT0jnBX/Z5L/2Z/V1YZfzq0LF9Y3hF81zI//3Kaef37yf+eapvZw/YLF4cbmJeGmlq5w68IV4Y7WlT3//93Xvk4ACIDXRcBSAQDwZr9rX9szQJNhfsX8heHiOLh/Vjc7DuOZPcP5uJljw5HTRoRDK54oGodPfSp8d0Z5+H7VhJ6wOKe2OsZIfbg0xsm1TR0xYJb1xEISNQKg9APgQQEA5MkDHRvCLfEv9asb23r+Ik/+Ek/+uk7+2v5e5bPh6Okjwzcrniyqwd4XjpxW1hM5J8fYSb6d+Hldbc83Dlc2tvZ8w3BP2xoBUOQBcLwAAEpR8pfsb+Nf779uaOr56jz5q/0700flfrD3pm9PeyacUDm+JxCSLYmrG9t7tkIGdXQLgCIIgPcJAKBYPVj4az7Z/z5/7txwes2Mnr/ivzX1aQM6Rck3KMfMGN3zrUqybZJsMQxoXtqzrSIAshUB9QIAyPaNdt09N7X9el5TOHdOTc/X0t+Z4a/5YnTEtBHhxKoJ4czZleGi+oZwXdOicG/bWgGQUgDcKACArH19n+zRJ3fJJ8PisKlPGZ4lLgm65CmI5CmJGxcsCQ+0rxcA/RAAXxcAQHpf43eHgc1Lw8X18+MAmBaOtk9PYRvh2Jlje74pSL75SZ5Q2NK9BQJg+wJgz+iPAgDoD3e1ruq5kzz5Kj+5geywqe64Z+sk3wQl10xyU2dy9sHtC58LDy3aKAB2MAImCQCgtyWLc7J3n+z1JgfcHDW9zCCjV31r2jPhB7MmhftauwTAdgbAxQIA6A3JiXPJXfnJo2HJs+SGFP3hhqYWAbCdAfAFAQBsr+TEvOSv/JOqJjpEBwFQZAGwS7RWAABbe5pecnTs2bWzHK6DACjmAChEwBMCANicO1qf7zk6N3lRjcfyEAClFQA/FgDAa2/gS16Ek9yt/18zRhsyCIASDoCPCgAgOcs9eeWs5/ERADkJgEIEdAgAyJ9729b0vOktOUffMEEA5DMA7hUAkJ8X6SQH8vyousKd+wgAAbD8uwIASltyznpyvGpygIrBgQAQAH8JgPdEfxYAUFqSI1N/UTfHG/QQAAJgixEwWwBAaZzIlzy2l5ydbkAgAATA1gTAtQIAivtlO8mje9+a+rTBgAAQANsUAF8RAFB8kpfuJO9Td0MfAkAAbG8AvD36vQCA4jis55rG9nBi1URDAAEgAHolAp4VAJDtR/h+3dAUjnFCHwiAXg6AXwoAyJ572tb03M1/xLQRFn0QAH0SAP9HAEC2XrebPLvvJTwgAPo6AHaKnhcAkP6hPcnb9yzwIAD6MwKGCgBI74U8p1RXWNhBAKQSAD8SANDfe/yrw1m1VR7lAwGQagD8owCA/nF/+/qem/sOd3gPCICMRECLAIC+M6hjY89xvUdOK7OIgwDIVADcIQCgb1zV2OY5fhAAmQ2AIwQA9PKd/c1Lwve9oAcEQMYDYK/oTwIAeueVvO7sBwFQTBFQKQBgx27wO6d2ljv7QQAUXQBcUaoB8FDHxnB366pw04KucEPj4nDtvPZwVcPCcNncBeGSOfPDhbX14Zc1teG8WdXhnKrKcMbM6eG0GRXhR9MmhZOmjg8nTBkbjp88Ov7XcT3//cnxPz9t+pRw+sxp4azKmeGnVVXhZ9U1Pf+MC2fPDb+aMy/8pq4xXN3QGgY2LQ13LVwVBnV0G5Il7PoFi8N3po+yQIMAKMoA2L8YA+DetrXh5gXLwjUNbT2D99w4xJPhnQztI8c/Hb42Zkj4z5EPhC+V3Z+6r5QPDoeNezx8d2JZOLHi2XD6jKk90XFxbUO4sn5hT6A80L7BQC0i97Wv6zm618IMAqCYA2D3aGMxBMB/TRoZvjF2WDhg5IOZGOy97etjhoZjJ5X3BMIFs+t6vrG4a+HzBm7GXNe0KBw9faRFGQRASURAeTEEQKkO/rdy4KiHwhHjnwo/mDo+nDtrVs+3Bve2rjGMU/ir/4zZMy3GIABKKgDOFQDF59Cxw2MUTAjnz64LA5qWuN+gD13T1B6O8lc/CIASDIB/EQDF74CRg8LRE54JP5k5vedGxzttH+yw37WvDafXzLAAgwAo6QhYLgBK8b6CYT1PLiTbBg+0rzfUt8HVjW2O8AUBkIsAeFgAlPo3BA/23GT4y5o54bbmFYb8ZiRPmPy4ZppFFwRAbgLgRAGQv3sIfjxjas+5BR5DfNVNzV3u8AcBkLsA+AcBkF9fHjWo54yCK+tbcnsz4aXzFoTDpj5lsQUBkK8AKETAfAHAwaMf6flm4MbGJTl5ZW93OLt2lkUWBECuA+BmAcBrfXPcYz2nLN7RsrIkh/89bWvC96smWGBBAOQ+AL4pANic5Cjj5Pji+0vkaYKBzUs92w8CQAAUAuCd0csCgC05qPzhnhcnFfM5A7+Z1xQOm+rtfSAABMBrI2CqAGBr7D/ygZ63JCanEBbTfv9ZtVUWVBAAAmATAfAbAcC2+s7EEeGK+ubw0KKNmR3+d7etDt+vHG8xBQEgADYTAP8uANiRswWSdxNk7T6BAc1LwlHTneoHAkAAbCkAdo3WCwB26FHC8sE9Jw5m4UyBqxrb7PeDABAAWxkBzwgAesM3xg4Lv6lrSm34X9nYGr5ZYfiDABAAWxsAZwoAetMR458K185r79fhf8X8hYY/CAABsI0B8AkBQF84bvLocPOCrj4f/pfPbzH8QQAIgO2MgCUCgL7wn2UPhJOnTQx3LVzVJ8P/snnNhj8IAAGwAwFwvwCgrw8UunhOQy8f8LPAYgkCQADsYAAcJwDoD8dOLu+VUwV/Pa/JQgkCQAD0QgC8N/qzAKBfvg0Y9XDPewa2d/hf0tBokQQBIAB6MQLmCgD69duASdv+bcCvGuZbIEEACIBeDoAbBAD97cBRD4WLauu3avhfVD/P4ggCQAD0QQB8TQCQlhOmjN3ikcLJo34WRhAAAqBvAmDP6A8CgLQcNu7xcFvzijcN/5tausJhU5+yMIIAEAB9GAETBQBpPy54VcPC/x7+97StCUdPH2lRBAEgAPo4AC4SAGTBubNm9bxc6PtVEyyIIAAEQD8EwOcFAFlxZMUIiyEIAAHQTwGwc7RGAJD6VsDYwRZCEAACoJ8j4HEBQJq+PPohiyAIAAGQQgCcJgBIy/6jHgzfmPK4RRAEgABIIQA+IgBI5c2BIx8Ih0wabgEEASAAUoyAdgFAf/vqhKEWP0AApBwAdwsA+vWmv3GPWPgAAZCBAPiOAKA/9/0Pte8PCIBMBMB7BAD95WsTh1n0AAGQFQKAfnkb4JiHLXiAABAAAiBvd/175A8QAAJAAOTMweOHWOwAASAABECeHFA+yEIHCAABIADyxoE/gAAQAALAi34ABIAAEAAlfeOfs/4BASAABIDjfgEEgAAQAF7zCyAABIAAcOIfIAAEgADAY3+AABAAAoAi2/t36A8gAASAAMjfkb8WNUAACAABkDNfefZRixogAASAAMgbz/0DAkAACIC8ve7XqX+AABAAAiB/vu7Mf0AACAAB4OAfAAEgAASAg38ABIAAEAClZP9RD1rIAAEgAASAR/8ABIAAEAAl7xA3/wECQAAIACf/AQgAASAASv3Z/zEPW8QAASAABEDeHOzFP4AAEAACIIeH/0x+zCIGCAABsH2+PGqQYerxPyCHBgiAfAfAN8YMNVCL0EHO/gd20D2tHQIgzwFwzMRnDNQi9NUJQy1gwA55cvFyAZDnADht2jgD1at/gRyqWL5GAOQ5AC6fXWmgFpkDygdZvIAdctT0stC8eqMAyHMAVCzpNFSL7fjfcY9YwIAdck5tVVi3oVsA5DkA3AhYhG//s/8PFPkNgAIgIwHwq5rpBqvn/4GcOHLaiFD//AYBIAC6Q/uqVeGg8ocN1yJhAQN2xJXz5ocNGZg9AiADv4TEdXXVhqsDgIAS990Z5WHB6g2ZmDsCICMBsHLdunDYuOGGbMZ9efRDFjFgu93R0hayMncEQEZ+EYnqZV22ApwACJSoi+rnhDUbugWAANi0p9pae94zb9hm9BHAZx+1kAHb7PSaaWHZumzNGwGQsQBIDG5p8pIgrwAGSsRP4vBvW70+c7NGAGQwABJTlnSGQ8YMMXS9AwAoYr+qnxOeW5/NOSMAMhoAiebnV4afTB9v8GbpEKCJwyxqwFs6Zsbo8EBrW1i7IbszRgBkOABe+23A9yaPNIAz4BABAGzxkJ+ycGNTY1i2bkPmZ4sAKIIA+Iu65cvDjXNnh2MnjXCjYFqnAE4abpEDXuc700eFC+dWhycXLQ7PFcHgFwBFGACvtXbDhtC0cmWY0LkoDGlZEB5a0Eg/uHburHBlXWX2za0KQ9oXAn2iNYzqXByqV6wIS9auzcSpfgIgRwEAAAJAAACAABAAACAABAAACAABAAACwEUAgAAQAAAgAAQAAAiAEg2AF10IAOTMiwKgu7vVhQBAzrQJgO7uKS4EAHJmqgDo7h7iQgAgZ4YJgO7u610IAOTMQAHQ3f01FwIAOXOEAOju3j3a4GIAIC9PAER75j4AChEwzAUBQE48k8w+AfBqABznggAgJ34gAP4nAN4eLXFRAFDiVkXvEgCvj4CTXRgAlLiz/zL3DP//CYBdoiYXBwAlKjn5djcBsOkIOMIFAkCJ+u5rZ57B/+YIuMtFAkCJGfzGeWfovzkAduv2fgAASsfM5GZ3AbB1EfB30SIXDQBFrjP6+03NOgN/8xHwyajdxQNAkUoeb//U5uacYb/lCNjbdgAARWhq9L4tzTiDfuveFXCfiwmAInHXax/3EwA7HgKHR40uLAAyKplRh2/tXDPct/2woFOj5S40ADJiafSjZEZty0wz2LcvBP46Orb71bcIepUwAP1tfWEGfS/aY3tmmYHeO/cIfD26JhoUPRvNj9ZEfwCAHbC2MFPGRQ9GV0QHbc0evwAAAAQAACAAAEAAAAACAAAQAACAAAAABAAAIAAAAAEAAAgAAEAAAAACAAAQAACAAAAABAAAIAAAAAEAAAgAAEAAAIAAAAAEAAAgAAAAAQAACAAAQAAAAAIAABAAAIAAAAAEAAAgAAAAAQAACAAAQAAAAFvn/wNpB+B7A97H/AAAAABJRU5ErkJggg==", Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        ivFoto.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 400, 400, true));
                    }
                }
            }

            @Override
            public void onFailure(Call<Local> call, Throwable t) {
                Log.i("detalheLocal", "erro ao carregar dados: "+t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
