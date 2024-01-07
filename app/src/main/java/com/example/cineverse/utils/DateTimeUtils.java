package com.example.cineverse.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;

import com.example.cineverse.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * The {@link DateTimeUtils} class provides utility methods for dates and time.
 */
public class DateTimeUtils {

    public static String formatDate(Context context, String dateFormatString, String dateString) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatString);

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

    public static String formatDateFromTimestamp(String dateFormatString, long timestamp) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatString);
        Date resultdate = new Date(timestamp);
        return sdf.format(resultdate);
    }

    public static String formatTimeAgo(Context context, long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(dateTime, now);

        if (duration.getSeconds() < 60) {
            return formatQuantity(context, duration.getSeconds(), R.string.second_ago, R.string.seconds_ago);
        } else if (duration.toMinutes() < 60) {
            return formatQuantity(context, duration.toMinutes(), R.string.minute_ago, R.string.minutes_ago);
        } else if (duration.toHours() < 24) {
            return formatQuantity(context, duration.toHours(), R.string.hour_ago, R.string.hours_ago);
        } else if (duration.toDays() < 7) {
            return formatQuantity(context, duration.toDays(), R.string.day_ago, R.string.days_ago);
        } else if (duration.toDays() < 30) {
            return formatQuantity(context, duration.toDays() / 7, R.string.week_ago, R.string.weeks_ago);
        } else if (duration.toDays() < 365) {
            return formatQuantity(context, duration.toDays() / 30, R.string.month_ago, R.string.months_ago);
        } else {
            return formatQuantity(context, duration.toDays() / 365, R.string.year_ago, R.string.years_ago);
        }
    }

    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    private static String formatQuantity(Context context, long value, int singularResourceId, int pluralResourceId) {
        if (value == 1) {
            return value + " " + context.getString(singularResourceId);
        } else {
            return value + " " + context.getString(pluralResourceId);
        }
    }

}
