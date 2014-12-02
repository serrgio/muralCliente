package muralufg.fabrica.inf.ufg.br.centralufg.model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import muralufg.fabrica.inf.ufg.br.centralufg.main.MainActivity;

public class NotificacaoPush {

    public static final int NOTIFICATION_ID = 1;

    public void mostraNotificacao(String titulo, String mensagem, Context context) {
        int count = 0;
        int i = 0;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(titulo).setContentText(mensagem);

        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtra("mensagem_recebida",mensagem);
        /*
        O MainActivity poderá ser substituído caso tenha sido criado um Activity de cartões feito
        pelo grupo da Iasmin.
         */
        TaskStackBuilder task = TaskStackBuilder.create(context);
        task.addParentStack(MainActivity.class);
        task.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = task.getPendingIntent
                (0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        Notification notificacao = mBuilder.build();
        if(count ==1){
            count ++;
        }else{
            i++;
        }
        notificacao.flags |= Notification.FLAG_AUTO_CANCEL;
        notificacao.defaults = Notification.DEFAULT_ALL;
        notificacao.number +=i;

        NotificationManager gerenciaNotificacao = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        gerenciaNotificacao.notify(0,notificacao);


    }
}


