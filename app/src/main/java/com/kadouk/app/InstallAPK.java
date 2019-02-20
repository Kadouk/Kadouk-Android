package com.kadouk.app;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jahan on 2/18/2019 AD.
 */

public class InstallAPK extends AsyncTask<String,Void,Void> {

    ProgressBar progressBar;
    int status = 0;
    int progress = 0;
    NotificationCompat.Builder notificationBuilder;
    NotificationManager notificationManager;

    private Context context;
    public void setContext(Context context, ProgressBar progress){
        this.context = context;
        this.progressBar = progress;
    }

    public void onPreExecute() {
//        progressBar.

    }

    @Override
    protected Void doInBackground(String... arg0) {
        int sentData = 0;
        try {
            URL url = new URL(arg0[0]);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(false);
            c.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            c.setRequestProperty("Accept","*/*");
            c.connect();
            Log.e("File", "FileNotFoundException! lllp");
            File sdcard = Environment.getExternalStorageDirectory();

            File myDir = new File(sdcard,"Android/data/com.mycompany.android.games/temp");
            Log.e("File", "FileNotFoundException! lllp");
            myDir.mkdirs();
            
            Log.e("File", "FileNotFoundException! lllp");
            File outputFile = new File(myDir, "temp.apk");
            Log.e("File", "FileNotFoundException! lllp");
            if(outputFile.exists()){
                outputFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(outputFile);
            Log.e("File", "FileNotFoundException! lllp");
            int status = c.getResponseCode();
            Log.e("File", "FileNotFoundException!" + status );
            InputStream is = c.getInputStream();
            Log.e("File", "FileNotFoundException! lllp");
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
//                sentData += len1;
//                int progress = (int) ((sentData / (float) length) * 100);
//                publishProgress(progress);
            }
            fos.flush();
            fos.close();
            is.close();
            Log.e("File", "FileNotFoundException! lllp");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(sdcard,
                    "Android/data/com.mycompany.android.games/temp/temp.apk")),
                    "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
            context.startActivity(intent);


        } catch (FileNotFoundException fnfe) {
            status = 1;
            Log.e("File", "FileNotFoundException! " + fnfe);
        }

        catch(Exception e)
        {
            Log.e("UpdateAPP", "Exception " + e);
        }
        return null;
    }

    public void onPostExecute(Void unused) {
//        progressDialog.dismiss();
        if(status == 1)
            Toast.makeText(context,"Game Not Available",Toast.LENGTH_LONG).show();
    }
}