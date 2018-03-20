package pers.zuqiuyu.mymonitor.utils;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by z5139 on 2016/5/20 0020.
 */
public class GetCurrentTime {

    public static String getTime() {
        String time;
        SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddHHmmss");
        time = dateformat.format(new Date());
        return time;
    }
}
