package muralufg.fabrica.inf.ufg.br.centralufg.gcm;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by italogustavomirandamelo on 28/11/14.
 */
public class GCMSendMessageTest extends AsyncTask<Void, Void, String> {

    GoogleCloudMessaging gcm;
    Context context;

    public GCMSendMessageTest(Context context){
        this.context = context;
        gcm = GoogleCloudMessaging.getInstance(context);
    }

    @Override
    protected String doInBackground(Void... params) {

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);

        String msg = "";
        try {
            Bundle data = new Bundle();
            data.putString("my_message", "Hello World");
            data.putString("my_action",
                    "com.google.android.gcm.demo.app.ECHO_NOW");
            gcm.send("437002960869@gcm.googleapis.com", "1", data);
            msg = "Sent message";
        } catch (IOException ex) {
            msg = "Error :" + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
    }
}
