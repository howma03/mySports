package net.gark.mysports.domain.interfaces;

import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mark on 11/06/2017.
 */
public class IUtility {

    static String pattern = "yyyy/MM/dd HH:mm:ss";
    static SimpleDateFormat SDF = null;

    static {
        SDF = new SimpleDateFormat(pattern);
    }

    public static String format(final Date date) {
        if (date == null) {
            return "";
        }
        synchronized (SDF) {
            return SDF.format(date);
        }
    }

    static Object COPY(final Object from, final Object to) {
        if (from == null) {
            return null;
        }
        if (to == null) {
            return null;
        }
        BeanUtils.copyProperties(from, to);
        return to;
    }

}
