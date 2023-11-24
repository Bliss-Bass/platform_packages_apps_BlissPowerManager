package org.blissos.powermanager;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import org.blissos.powermanager.IBlissPower;

public class BlissPowerManager {
    private static final String TAG = "BlissPowerManager";

    public static final String SERVICE_NAME = "blisspower";

    private IBlissPower sService;
    private static BlissPowerManager sInstance;
    private Context mContext;

    BlissPowerManager(Context context) {
        Context appContext = context.getApplicationContext();
        mContext = appContext == null ? context : appContext;
        getService();

        if (sService == null) {
            throw new RuntimeException("Unable to get Bliss Power Service. The service" +
                    " either crashed, was not started, or the interface has been called too early" +
                    " in SystemServer init");
        }
    }

    public static synchronized BlissPowerManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new BlissPowerManager(context);
        }

        return sInstance;
    }

    /** @hide **/
    public IBlissPower getService() {
        if (sService != null) {
            return sService;
        }
        IBinder b = ServiceManager.getService(SERVICE_NAME);
        sService = IBlissPower.Stub.asInterface(b);

        if (sService == null) {
            Log.e(TAG, "null BlissPower service, SAD!");
            return null;
        }

        return sService;
    }

    /**
     * @return true if service is valid
     */
    private boolean checkService() {
        if (sService == null) {
            Log.w(TAG, "not connected to BlissPowerService");
            return false;
        }
        return true;
    }

    public void reboot() {
        try {
            if (checkService())
                sService.reboot();
        } catch (RemoteException e) {
            Log.e(TAG, "reboot failed", e);
        }
    }

    public void shutdown() {
        try {
            if (checkService())
                sService.shutdown();
        } catch (RemoteException e) {
            Log.e(TAG, "reboot failed", e);
        }
    }
}
