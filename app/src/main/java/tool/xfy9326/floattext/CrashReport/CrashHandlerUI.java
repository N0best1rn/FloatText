package tool.xfy9326.floattext.CrashReport;

import android.content.pm.*;
import android.view.*;
import android.widget.*;
import java.util.*;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View.OnClickListener;
import tool.xfy9326.floattext.R;

public class CrashHandlerUI extends AppCompatActivity
{
    private String Log = "Report Error";
    private String Device = "Device Unknown";
    private String clsname;
    private String mail;
    private String AppName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crashhandler_ui);
		Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(tb);
        Log = getIntent().getStringExtra("CrashLog");
        Device = getIntent().getStringExtra("DeviceInfo");
        clsname = getIntent().getStringExtra("ClassName");
        AppName = getIntent().getStringExtra("AppName");
        mail = getIntent().getStringExtra("Mail");
        buttonset();
    }

    private void buttonset()
    {
        TextView logshow = (TextView) findViewById(R.id.textview_crashlog);
        logshow.setText(Log);
        Button exit = (Button) findViewById(R.id.button_nosendcrashlog);
        exit.setOnClickListener(new OnClickListener(){
                public void onClick(View v)
                {
                    exit();
                }
            });
        Button send = (Button) findViewById(R.id.button_sendcrashlog);
        send.setOnClickListener(new OnClickListener(){
                public void onClick(View v)
                {
                    sendmail();
                }
            });
    }

    private void sendmail()
    {
        String MailSend = AppName + getString(R.string.crashreport_mail_main) + "\n\n" + getString(R.string.crashreport_mail_report) + ":\n" + Device + "\n\n" + Log;
        Intent data=new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:" + mail));
        data.putExtra(Intent.EXTRA_SUBJECT, AppName + getString(R.string.crashreport_mail_title));
        data.putExtra(Intent.EXTRA_TEXT, MailSend.replace("\n", "<br>"));
        PackageManager pm = this.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(data, PackageManager.MATCH_DEFAULT_ONLY);
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
        if (resolveInfos.size() == 0)
        {
            Toast.makeText(this, getString(R.string.crashreport_no_mail_app) + mail, Toast.LENGTH_LONG).show();
        }
        else
        {
            startActivity(data);
        }
    }


    private void exit()
    {
        this.finish();
        System.exit(0);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                exit();
                break;
        }
        return false;
    }

}
