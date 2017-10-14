package com.cosmo.arquitecturamvpbase.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cosmo.arquitecturamvpbase.App;

/**
 * Created by ana.marrugo on 12/10/2017.
 */

public class NetworkStateReceiver extends BroadcastReceiver {

    private static  boolean connected=false;
    @Override
    public void onReceive(Context context, Intent intent) {
         Boolean isConnected;
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();//para saber si hay internet o el cambio de estado
        if(networkInfo==null||networkInfo.getState()!=NetworkInfo.State.CONNECTED){
            isConnected=false;
        }else {
            //Verificar que se realiza la conexion
            isConnected=networkInfo.isConnectedOrConnecting();
        }


        waitAndCheckConnection(isConnected,context);
        connected=isConnected;
    }

    private void waitAndCheckConnection(final Boolean isConnected, final Context context) {
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                //contexto de la aplicacion
                App aplication= (App) context.getApplicationContext();
                // Es el main , la primera que se lanza antes cualquier
                if(connected=isConnected&&aplication!=null){
                    aplication.onNetworkStateChange(isConnected);
                }
            }
        });

        t.start();
    }
    //
    public static  boolean isConnected(){
        return  connected;
    }
}
