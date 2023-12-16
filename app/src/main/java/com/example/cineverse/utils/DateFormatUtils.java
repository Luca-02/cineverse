package com.example.cineverse.utils;

import static com.example.cineverse.utils.constant.Api.RESPONSE_DATE_FORMAT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The {@link DateFormatUtils} class provides utility methods for formatting dates.
 */
public class DateFormatUtils {

    /**
     * Formats the given date string using the specified date format.
     *
     * @param context     The application context.
     * @param dateString  The date string to format.
     * @return A formatted date string, or {@code null} if formatting fails.
     */
    public static String formatData(Context context, String dateString) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(RESPONSE_DATE_FORMAT);

        Date date;
        try {
            date = sdf.parse(dateString);
            java.text.DateFormat dateFormat = DateFormat.getLongDateFormat(context);
            if (date != null) {
                return dateFormat.format(date);
            } else {
                return null;
            }
        } catch (ParseException e) {
            return null;
        }
    }

}
