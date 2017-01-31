package tool.xfy9326.floattext.API;

import android.*;
import android.content.*;
import android.content.pm.*;
import android.net.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.io.*;
import tool.xfy9326.floattext.*;
import tool.xfy9326.floattext.Method.*;

import android.support.v7.widget.Toolbar;
import tool.xfy9326.floattext.R;

public class ImportTTF extends AppCompatActivity
{
    private String FilePath = null;
	private static int FLOAT_TEXT_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_import);
		Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(tb);
		setAll();
    }

	private void setAll()
	{
		if (Build.VERSION.SDK_INT > 22)
		{
			if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
			{
				requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, FLOAT_TEXT_PERMISSION);
			}
			else
			{
				FloatManageMethod.preparefolder();
				FilePath = getIntentData();
				setView();
			}
		}
		else
		{
			FloatManageMethod.preparefolder();
			FilePath = getIntentData();
			setView();
		}
	}

    private String getIntentData()
    {
        String path = null;
        Intent intent = getIntent();
        String action = intent.getAction();
        if (intent.ACTION_VIEW.equals(action))
        { 
            Uri uri = intent.getData();
            path = uri.getPath().toString();
        }
        return path;
    }

    private void setView()
    {
        Button importfile = (Button) findViewById(R.id.button_importttf);
        TextView filename = (TextView) findViewById(R.id.textview_selectttf);
        final File ttf = new File(FilePath);
        filename.setText(ttf.getName());
        importfile.setOnClickListener(new OnClickListener(){
                public void onClick(View v)
                {
                    if (ttf.exists())
                    {
                        File ttf_c = new File(Environment.getExternalStorageDirectory().toString() + "/FloatText/TTFs/"  + ttf.getName());
                        if (!ttf_c.exists())
                        {
                            if (IOMethod.CopyFile(ttf, ttf_c))
                            {
                                Toast.makeText(ImportTTF.this, R.string.ttf_import_success, Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(ImportTTF.this, R.string.ttf_import_failed, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(ImportTTF.this, R.string.ttf_import_exist, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(ImportTTF.this, R.string.ttf_import_failed, Toast.LENGTH_SHORT).show();
                    }
					ImportTTF.this.finish();
                }
            });
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == FLOAT_TEXT_PERMISSION)
		{
			FloatManageMethod.preparefolder();
			FilePath = getIntentData();
			setView();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


}
