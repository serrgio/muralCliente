package muralufg.fabrica.inf.ufg.br.centralufg.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 *
 * Created by italogustavomirandamelo on 27/11/14.
 *
 */
public class GcmIntentService extends IntentService {


    public GcmIntentService() {

        super("GcmIntentService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {

                sendNotification("Send error: " + extras.toString());

            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {

                sendNotification("Deleted messages on server: " + extras.toString());
                // If it's a regular GCM message, do some work.

            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                // This loop represents the service doing some work.

                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                }

                // Post notification of received message.
                sendNotification("Received: " + extras.toString());
            }
        }

        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }


    private void sendNotification(String msg) {

        //@TODO Método que chamará a notificação

    }
}
