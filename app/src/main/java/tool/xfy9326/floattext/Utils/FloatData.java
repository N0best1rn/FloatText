package tool.xfy9326.floattext.Utils;

import android.app.*;
import android.content.*;
import android.util.*;
import java.io.*;
import java.util.*;
import org.json.*;
import tool.xfy9326.floattext.*;
import tool.xfy9326.floattext.Method.*;

/*
数据操作
*/

public class FloatData
{
	private Context ctx;
    private int DataNum = 0;
    public static int FloatDataVersion = 2;
	private SharedPreferences spdatat;
	private SharedPreferences.Editor speditt;
	private SharedPreferences spdata;
	private SharedPreferences.Editor spedit;

	public FloatData(Context ctx)
	{
		this.ctx = ctx;
		spdatat = ctx.getSharedPreferences("FloatTextList", Activity.MODE_PRIVATE);
        speditt = spdatat.edit();
        spdata = ctx.getSharedPreferences("FloatShowList", Activity.MODE_PRIVATE);
        spedit = spdata.edit();
	}

	//保存
    public void savedata()
    {
        App utils = ((App)ctx.getApplicationContext());
        spedit.putInt("Version", FloatDataVersion);
        speditt.putString("TextArray", TextArr_encode(utils.getTextData()).toString());
        spedit.putString("ColorArray", utils.getColorData().toString());
        spedit.putString("ThickArray", utils.getThickData().toString());
        spedit.putString("SizeArray", utils.getSizeData().toString());
        spedit.putString("ShowArray", utils.getShowFloat().toString());
        spedit.putString("LockArray", utils.getLockPosition().toString());
        spedit.putString("PositionArray", utils.getPosition().toString());
        spedit.putString("TopArray", utils.getTextTop().toString());
        spedit.putString("AutoTopArray", utils.getAutoTop().toString());
        spedit.putString("MoveArray", utils.getTextMove().toString());
        spedit.putString("SpeedArray", utils.getTextSpeed().toString());
        spedit.putString("ShadowArray", utils.getTextShadow().toString());
        spedit.putString("ShadowXArray", utils.getTextShadowX().toString());
        spedit.putString("ShadowYArray", utils.getTextShadowY().toString());
        spedit.putString("ShadowRadiusArray", utils.getTextShadowRadius().toString());
        spedit.putString("BackgroundColorArray", utils.getBackgroundColor().toString());
        spedit.putString("TextShadowColorArray", utils.getTextShadowColor().toString());
		spedit.putString("FloatSizeArray", utils.getFloatSize().toString());
		spedit.putString("FloatLongArray", utils.getFloatLong().toString());
		spedit.putString("FloatWideArray", utils.getFloatWide().toString());
        spedit.apply();
		speditt.apply();
    }

	//获取
    public void getSaveArrayData()
    {
        App utils = ((App)ctx.getApplicationContext());
        int version = spdata.getInt("Version", 0);
        String text = spdatat.getString("TextArray", "[]");
        ArrayList<String> textarr = new ArrayList<String>();
        if (version < 1)
        {
            textarr.addAll(StringToStringArrayList(text));
			speditt.putString("TextArray", TextArr_encode(textarr).toString());
			speditt.commit();
            updateVersion(1);
        }
		textarr.addAll(TextArr_decode(StringToStringArrayList(text)));
		if (version < 2)
		{
			String text_v = spdata.getString("TextArray", "[]");
			textarr.clear();
			if (version < 1)
			{
				textarr.addAll(StringToStringArrayList(text_v));
			}
			textarr.addAll(TextArr_decode(StringToStringArrayList(text_v)));
			spedit.remove("TextArray");
			speditt.putString("TextArray", TextArr_encode(textarr).toString());
			spedit.commit();
			speditt.commit();
			updateVersion(2);
		}
        DataNum = textarr.size();
        ArrayList<Float> size = NewFloatKey(spdata.getString("SizeArray", "[]"), "20.0");
        ArrayList<Integer> color = NewIntegerKey(spdata.getString("ColorArray", "[]"), "-61441");
        ArrayList<Boolean> thick = NewBooleanKey(spdata.getString("ThickArray", "[]"), "false");
        ArrayList<Boolean> show = NewBooleanKey(spdata.getString("ShowArray", "[]"), "true");
        ArrayList<Boolean> lock = NewBooleanKey(spdata.getString("LockArray", "[]"), "false");
        ArrayList<String> position = NewStringKey(spdata.getString("PositionArray", "[]"), "50_50");
        ArrayList<Boolean> top = NewBooleanKey(spdata.getString("TopArray", "[]"), "false");
        ArrayList<Boolean> autotop = NewBooleanKey(spdata.getString("AutoTopArray", "[]"), "true");
        ArrayList<Boolean> move = NewBooleanKey(spdata.getString("MoveArray", "[]"), "false");
        ArrayList<Integer> speed = NewIntegerKey(spdata.getString("SpeedArray", "[]"), "5");
        ArrayList<Boolean> shadow = NewBooleanKey(spdata.getString("ShadowArray", "[]"), "false");
        ArrayList<Float> shadowx = NewFloatKey(spdata.getString("ShadowXArray", "[]"), "10.0");
        ArrayList<Float> shadowy = NewFloatKey(spdata.getString("ShadowYArray", "[]"), "10.0");
        ArrayList<Float> shadowradius = NewFloatKey(spdata.getString("ShadowRadiusArray", "[]"), "5.0");
        ArrayList<Integer> backgroundcolor = NewIntegerKey(spdata.getString("BackgroundColorArray", "[]"), "16777215");
        ArrayList<Integer> textshadowcolor = NewIntegerKey(spdata.getString("TextShadowColorArray", "[]"), "1660944384");
		ArrayList<Boolean> floatsize = NewBooleanKey(spdata.getString("FloatSizeArray", "[]"), "false");
		ArrayList<Float> floatlong = NewFloatKey(spdata.getString("FloatLongArray", "[]"), "100");
		ArrayList<Float> floatwide = NewFloatKey(spdata.getString("FloatWideArray", "[]"), "100");
        utils.replaceDatas(textarr, color, size, thick, show, position, lock, top, autotop, move, speed, shadow, shadowx, shadowy, shadowradius, backgroundcolor, textshadowcolor, floatsize, floatlong, floatwide);
    }

	//输出
	public boolean OutputData(String path, int VersionCode)
	{
		String jsonresult = "";
		JSONObject mainobject = new JSONObject();
		JSONObject dataobject = new JSONObject();
		JSONObject textobject = new JSONObject();
		try
		{
			textobject.put("TextArray", spdatat.getString("TextArray", "[]"));

			xmltojson(dataobject, "SizeArray");
			xmltojson(dataobject, "ColorArray");
			xmltojson(dataobject, "ThickArray");
			xmltojson(dataobject, "ShowArray");
			xmltojson(dataobject, "LockArray");
			xmltojson(dataobject, "PositionArray");
			xmltojson(dataobject, "TopArray");
			xmltojson(dataobject, "AutoTopArray");
			xmltojson(dataobject, "MoveArray");
			xmltojson(dataobject, "SpeedArray");
			xmltojson(dataobject, "ShadowArray");
			xmltojson(dataobject, "ShadowXArray");
			xmltojson(dataobject, "ShadowYArray");
			xmltojson(dataobject, "ShadowRadiusArray");
			xmltojson(dataobject, "BackgroundColorArray");
			xmltojson(dataobject, "TextShadowColorArray");
			xmltojson(dataobject, "FloatSizeArray");
			xmltojson(dataobject, "FloatLongArray");
			xmltojson(dataobject, "FloatWideArray");

			mainobject.put("FloatText_Version", VersionCode);
			mainobject.put("Data_Version", FloatDataVersion);
			mainobject.put("Text", textobject);
			mainobject.put("Data", dataobject);

			jsonresult = mainobject.toString();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			return false;
		}
		if (!IOMethod.writefile(path, jsonresult))
		{
			return false;
		}
		return true;
	}

	//导入
	public boolean InputData(String path)
	{
		File bak = new File(path);
		if (bak.exists() && !bak.isDirectory())
		{
			String[] data = IOMethod.readfile(bak);
			if (data[0] != "Failed")
			{
				String str = "";
				for (int i = 0;i < data.length;i++)
				{
					str += data[i];
				}
				if (str != "" && !str.startsWith("error"))
				{
					try
					{
						JSONObject mainobject = new JSONObject(str);
						//int FloatText_Version = mainobject.getInt("FloatText_Version");
						//int Data_Version = mainobject.getInt("Data_Version");
						JSONObject dataobject = mainobject.getJSONObject("Data");
						JSONObject textobject = mainobject.getJSONObject("Text");

						String text = textobject.getString("TextArray");
						String oldtext = spdatat.getString("TextArray", "[]");
						if (oldtext.equalsIgnoreCase("[]"))
						{
							oldtext = text;
						}
						else
						{
							oldtext = CombineArrayString(oldtext, text);
						}
						speditt.putString("TextArray", oldtext);

						Iterator it = dataobject.keys();
						while (it.hasNext())
						{
							String key = it.next().toString();
							if (spdata.contains(key))
							{
								String old = spdata.getString(key, "[]");
								String get = dataobject.getString(key);
								if (old.equalsIgnoreCase("[]"))
								{
									old = get;
								}
								else
								{
									old = CombineArrayString(old, get);
								}
								spedit.putString(key, old);
							}
						}

						spedit.commit();
						speditt.commit();
						return true;
					}
					catch (JSONException e)
					{
						e.printStackTrace();
						return false;
					}
				}
			}
		}
		return false;
	}

	private String CombineArrayString(String a1, String a2)
	{
		if (a1.length() == 2)
		{
			return a2;
		}
		String str = a1.substring(0, a1.length() - 1) + ", " + a2.substring(1, a2.length());
		return str;
	}

	private JSONObject xmltojson(JSONObject obj, String name) throws JSONException
	{
		obj.put(name, spdata.getString(name, "[]"));
		return obj;
	}

	private void updateVersion(int i)
	{
		spedit.putInt("Version", i);
		spedit.commit();
	}

    private static ArrayList<String> TextArr_decode(ArrayList<String> str)
    {
        ArrayList<String> output = new ArrayList<String>();
        output.addAll(str);
        if (str.size() > 0)
        {
            for (int i = 0;i < str.size();i++)
            {
                String result = new String(Base64.decode(str.get(i).getBytes(), Base64.NO_WRAP));
                output.set(i, result);
            }
        }
        return output;
    }

    private static ArrayList<String> TextArr_encode(ArrayList<String> str)
    {
        ArrayList<String> output = new ArrayList<String>();
        output.addAll(str);
        if (str.size() > 0)
        {
            for (int i = 0;i < str.size();i++)
            {
                String result = Base64.encodeToString(str.get(i).getBytes(), Base64.NO_WRAP);
                output.set(i, result);
            }
        }
        return output;
    }

    private ArrayList<String> NewStringKey(String fix, String def)
    {
        fix = NewKey(fix, def);
        ArrayList<String> res = StringToStringArrayList(fix);
        return FixKey(res, def);
    }

    private ArrayList<Integer> NewIntegerKey(String fix, String def)
    {
        fix = NewKey(fix, def);
        ArrayList<Integer> res = StringToIntegerArrayList(fix);
        return FixKey(res, Integer.valueOf(def));
    }

    private ArrayList<Float> NewFloatKey(String fix, String def)
    {
        fix = NewKey(fix, def);
        ArrayList<Float> res = StringToFloatArrayList(fix);
        return FixKey(res, Float.valueOf(def));
    }

    private ArrayList<Boolean> NewBooleanKey(String fix, String def)
    {
        fix = NewKey(fix, def);
        ArrayList<Boolean> res = StringToBooleanArrayList(fix);
        return FixKey(res, Boolean.valueOf(def));
    }

    private String NewKey(String fix, String def)
    {
        if (fix.equalsIgnoreCase("[]") && DataNum != 0)
        {
            ArrayList<String> str = new ArrayList<String>();
            for (int i = 0;i < DataNum;i++)
            {
                str.add(def);
            }
            fix = str.toString();
        }
        return fix;
    }

    private ArrayList<String> FixKey(ArrayList<String> str, String def)
    {
        while (str.size() < DataNum)
        {
            str.add(def);
        }
        return str;
    }

    private ArrayList<Float> FixKey(ArrayList<Float> str, Float def)
    {
        while (str.size() < DataNum)
        {
            str.add(def);
        }
        return str;
    }

    private ArrayList<Integer> FixKey(ArrayList<Integer> str, Integer def)
    {
        while (str.size() < DataNum)
        {
            str.add(def);
        }
        return str;
    }

    private ArrayList<Boolean> FixKey(ArrayList<Boolean> str, Boolean def)
    {
        while (str.size() < DataNum)
        {
            str.add(def);
        }
        return str;
    }

    public final static ArrayList<String> StringToStringArrayList(String str)
    {
        ArrayList<String> arr = new ArrayList<String>();
        if (str.contains("[") && str.length() >= 3)
        {
            str = str.substring(1, str.length() - 1);
            if (str.contains(","))
            {
                String[] temp = str.split(",");
                for (int i = 0;i < temp.length;i++)
                {
                    if (i != 0)
                    {
                        temp[i] = temp[i].substring(1, temp[i].length());
                    }
                    arr.add(temp[i].toString());
                }
            }
            else
            {
                arr.add(str.toString());
            }
        }
        return arr;
    }

    public final static ArrayList<Float> StringToFloatArrayList(String str)
    {
        ArrayList<Float> arr = new ArrayList<Float>();
        if (str.contains("[") && str.length() >= 3)
        {
            str = str.substring(1, str.length() - 1);
            if (str.contains(","))
            {
                String[] temp = str.split(",");
                for (int i = 0;i < temp.length;i++)
                {
                    temp[i] = temp[i].replaceAll("\\s+", "");
                    arr.add(Float.parseFloat(temp[i]));
                }
            }
            else
            {
                arr.add(Float.parseFloat(str));
            }
        }
        return arr;
    }

    public final static ArrayList<Integer> StringToIntegerArrayList(String str)
    {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        if (str.contains("[") && str.length() >= 3)
        {
            str = str.substring(1, str.length() - 1);
            if (str.contains(","))
            {
                String[] temp = str.split(",");
                for (int i = 0;i < temp.length;i++)
                {
                    temp[i] = temp[i].replaceAll("\\s+", "");
                    arr.add(Integer.parseInt(temp[i]));
                }
            }
            else
            {
                arr.add(Integer.parseInt(str));
            }
        }
        return arr;
    }

    public final static ArrayList<Boolean> StringToBooleanArrayList(String str)
    {
        ArrayList<Boolean> arr = new ArrayList<Boolean>();
        if (str.contains("[") && str.length() >= 3)
        {
            str = str.substring(1, str.length() - 1);
            if (str.contains(","))
            {
                String[] temp = str.split(",");
                for (int i = 0;i < temp.length;i++)
                {
                    temp[i] = temp[i].replaceAll("\\s+", "");
                    arr.add(Boolean.parseBoolean(temp[i]));
                }
            }
            else
            {
                arr.add(Boolean.parseBoolean(str));
            }
        }
        return arr;
    }
}
