package muralufg.fabrica.inf.ufg.br.centralufg.notificacao;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import muralufg.fabrica.inf.ufg.br.centralufg.R;

public class Notificacao extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button button = (Button) findViewById(R.id.bottom);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        criarNotificacao(getApplicationContext(), new NotificacaoAlerta(
                "Nova Mensagem", "Clique para ler sua mensagem",
                "Nova Mensagem"), Notificacao.class);
    }

    protected void criarNotificacao(Context context,
                                    NotificacaoAlerta messagesAlerts, Class<?> activity) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notificaction = new Notification(R.drawable.ic_launcher,
                messagesAlerts.getTitle(), System.currentTimeMillis());
        notificaction.flags |= Notification.FLAG_INSISTENT;
        notificaction.flags |= Notification.FLAG_AUTO_CANCEL;
        PendingIntent p = PendingIntent.getActivity(this, 0,
                new Intent(this.getApplicationContext(), Notificacao.class), 0);
        notificaction.setLatestEventInfo(this, messagesAlerts.getSubTitle(),
                messagesAlerts.getBody(), p);
        notificationManager.notify(R.string.app_name, notificaction);
    }
}