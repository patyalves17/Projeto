package com.projeto.patyalves.projeto.firebase;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.projeto.patyalves.projeto.MainActivity;
import com.projeto.patyalves.projeto.NewUserActivity;
import com.projeto.patyalves.projeto.PlaceDetailActivity;
import com.projeto.patyalves.projeto.PlaceDetailFragment;
import com.projeto.patyalves.projeto.R;


public class MeuFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void handleIntent(Intent intent){
        Log.i("Firebase", "handleIntent");
       // showNotification(remoteMessage.getData().get("descricao"), remoteMessage.getData().get("descricao"), value);

        if (intent.getExtras() != null) {

            for (String key : intent.getExtras().keySet()) {
                Object value = intent.getExtras().get(key);
                Log.d("Firebase: ", "Key: " + key + " Value: " + value);

            }
            String titulo=intent.getExtras().get("gcm.notification.title").toString();
            String descricao=intent.getExtras().get("gcm.notification.body").toString();
            Long placeId = Long.parseLong(intent.getExtras().get("placeId").toString());


            showNotification(titulo,descricao, placeId);
        }

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i("Firebase", "onMessageReceived");


        // Verifica se a mensagem contém uma carga útil de dados.
        if (remoteMessage.getData().size() > 0) {

            String value = remoteMessage.getData().get("placeId");
            Log.i("Firebase", "Get Key"+ value);

//            Bundle bundle = new Bundle();
//            bundle.putLong("localId", 15);
//            PlaceDetailFragment fragment=new PlaceDetailFragment();
//            fragment.setArguments(bundle);
//
//            FragmentManager fragmentManager = fragment.getFragmentManager();
//            FragmentTransaction ft = fragmentManager.beginTransaction();
//            ft.replace(R.id.content_main,fragment);
//            ft.addToBackStack(null);
//            ft.commit();


          //  showNotification(remoteMessage.getData().get("descricao"), remoteMessage.getData().get("descricao"), value);


        }

        // Verifica se a mensagem contem payload.
        if (remoteMessage.getNotification() != null) {
            String value = "";
         //   showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), value);
        }



    }

    private void showNotification(String titulo, String mensagem, Long value) {
        Log.i("Firebase", "Get Key"+ value);
        Log.i("Firebase", "showNotification");

        Intent intent = new Intent(this, PlaceDetailActivity.class);
        intent.putExtra("localId",value);


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(titulo+ " chave --> "+value)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(mensagem+ " chave --> "+value)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}