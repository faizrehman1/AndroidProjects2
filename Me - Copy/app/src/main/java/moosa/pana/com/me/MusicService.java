package moosa.pana.com.me;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Moosa on 8/31/2015.
 */
public class MusicService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
