package com.example.nic_scm;

/**
 * Created by pavan on 6/6/2015.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class AndroidUtil {
    private Context con;
    public AndroidUtil(Context con){
        this.con=con;
    }

    public AndroidUtil() {

    }

    public boolean isOnline(){
        ConnectivityManager connectivity = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
}
