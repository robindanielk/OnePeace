package com.example.robin.onepeace.helper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class NetworkUtils {

    public static boolean checkConnection(){
        InetAddress inetAddress = null;
        try{
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                @Override
                public InetAddress call() throws Exception {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException e) {
                        return null;
                    }
                }
            });
            inetAddress = future.get(2000, TimeUnit.MILLISECONDS);
            future.cancel(true);
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (TimeoutException e){
            e.printStackTrace();
        }
        return inetAddress != null && !inetAddress.equals("");
    }

}
