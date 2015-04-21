package org.nupter.turingrobot;

import android.test.AndroidTestCase;
import android.util.Log;

import org.nupter.turingrobot.utils.HttpUtils;

/**
 * Created by panl on 15/4/20.
 */
public class TestHttpUtils extends AndroidTestCase {
    public void testInfo(){
        String res = HttpUtils.doGet("你好");
        Log.i("TAG",res);
        String res1 = HttpUtils.doGet("给我讲个笑话");
        Log.i("TAG",res1);

    }
}
