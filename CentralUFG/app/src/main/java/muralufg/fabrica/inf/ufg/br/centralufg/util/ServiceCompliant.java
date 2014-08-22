package muralufg.fabrica.inf.ufg.br.centralufg.util;

import android.app.Activity;
import android.content.Context;

public interface ServiceCompliant {

    public void handleError(String error);
    public void readObject(Object object);
    public Activity getContextActivity();
}
