package tool.xfy9326.floattext.Service;

import android.app.*;
import android.content.*;
import android.os.*;
import java.util.*;
import tool.xfy9326.floattext.Method.*;
import tool.xfy9326.floattext.Utils.*;

import android.preference.PreferenceManager;

public class FloatWindowStayAliveService extends Service
{
    private Bundle bundle = null;
    private Intent winIntent = null;
    private Timer timer = null;
    private boolean InHome = false;
    private List<String> home_app = new ArrayList<String>();
    private SharedPreferences sp = null;
    private boolean homeswitch = true;
    private boolean lasthome = true;
    private boolean lastswitch = false;

    @Override
    public IBinder onBind(Intent p1)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        init();
        timerset();
        create_notification();
		FloatManageMethod.setWinManager(this);
    }

    private void init()
    {
        winIntent = new Intent();
		bundle = new Bundle();
        timer = new Timer();
        home_app = FloatServiceMethod.getHomes(this);
        sp = PreferenceManager.getDefaultSharedPreferences(FloatWindowStayAliveService.this);
    }

    private void timerset()
    {
        timer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    if (hasFloatWin(FloatWindowStayAliveService.this))
                    {
                        homeswitch = sp.getBoolean("WinOnlyShowInHome", false);
                        if (homeswitch != lastswitch)
                        {
                            lasthome = true;
                        }
                        lastswitch = homeswitch;
                        if (homeswitch)
                        {
                            InHome = FloatServiceMethod.isHome(FloatWindowStayAliveService.this, home_app);
                        }
                        sendbroadcast();
                    }
                }
            }, 100, 1500);
    }

    private void sendbroadcast()
    {
        if (lasthome != InHome)
        {
            bundle.putBoolean("Float_InHome", InHome);
            winIntent.putExtras(bundle);
            winIntent.setAction(StaticString.TEXT_STATE_UPDATE_ACTION);
            sendBroadcast(winIntent);
            lasthome = InHome;
        }
    }

    private boolean hasFloatWin(Context ctx)
    {
        ArrayList<String> list = ((App)ctx.getApplicationContext()).getFloatText();
        boolean dynamicnum = false;
        if (list.size() > 0 && FloatServiceMethod.isScreenOn(FloatWindowStayAliveService.this))
        {
            dynamicnum = true;
        }
        return dynamicnum;
    }

    private void create_notification()
    {
        Notification notification = new Notification();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, START_STICKY, startId);
    }

    @Override
    public void onDestroy()
    {
        timer.cancel();
        stopForeground(true);
        super.onDestroy();
    }

}
