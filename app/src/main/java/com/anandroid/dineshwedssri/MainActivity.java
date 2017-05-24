package com.anandroid.dineshwedssri;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.anandroid.dineshwedssri.countdownview.CountdownView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.varunest.sparkbutton.SparkButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private ViewPagerAdapter viewPageAdapter;
    @BindView(R.id.viewpager_home)
    ViewPager viewpager;
    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.heart_btn)
    SparkButton heart_btn;
    @BindView(R.id.ponnu)
    CircleImageView ponnu;
    @BindView(R.id.mapla)
    CircleImageView mapla;
    @BindView(R.id.np_appbar_lay)
    AppBarLayout np_appbar_lay;
    @BindView(R.id.cv_countdownViewTest4)
    CountdownView cv_countdownViewTest4;

    private SharedPreferences prefs;
    private Boolean statusLocked;

    public static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 123;
    private static final String[] CHANNELS = new String[]{"KITKAT", "NOUGAT", "DONUT"};
    private List<String> mDataList = Arrays.asList(CHANNELS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tabs.setVisibility(View.GONE);
        mapla.setVisibility(View.GONE);
        ponnu.setVisibility(View.GONE);
        onViewCreate();

        //if we do this thing using INTENT then permission is not required
        boolean result = checkPermission();
        prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        statusLocked = prefs.getBoolean("locked", false);
        if (result) {
            if (!statusLocked) {
                addReminderInCalendar();
                addBeforTwoReminderInCalendar();
                prefs.edit().putBoolean("locked", true).commit();
            }
        }


        tabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Click", Toast.LENGTH_SHORT).show();

                Fragment mFragment = viewPageAdapter.getItem(viewpager.getCurrentItem());
                if (mFragment != null) {
                    if (mFragment instanceof LocationFrag) {
                        Toast.makeText(MainActivity.this, "LocationFrag", Toast.LENGTH_SHORT).show();

                    }
                }
            /*    Fragment fragment = getSupportFragmentManager().findFragmentByTag("location");
                int current = viewpager.getCurrentItem();

                Toast.makeText(MainActivity.this, "" + current, Toast.LENGTH_SHORT).show();

                if (fragment instanceof LocationFrag) {
//666
                    ((LocationFrag) fragment).focusOnMap();

                }*/
            }
        });


        Handler handler_two = new Handler();
        handler_two.postDelayed(new Runnable() {
            public void run() {
                mapla.setVisibility(View.VISIBLE);
                ponnu.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.BounceInLeft).duration(3200).repeat(0).playOn(ponnu);
                YoYo.with(Techniques.BounceInRight).duration(3200).repeat(0).playOn(mapla);

            }
        }, 1000);

        // Execute some code after 2 seconds have passed
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                heart_btn.setChecked(true);
                heart_btn.playAnimation();
            }
        }, 2500);

        // Collapse View
        Handler handler_collapse = new Handler();
        handler_collapse.postDelayed(new Runnable() {
            public void run() {

                np_appbar_lay.setExpanded(false, true);
            }
        }, 3000);
        // Collapse View
        Handler handler_appbar = new Handler();
        handler_appbar.postDelayed(new Runnable() {
            public void run() {

                tabs.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInUp).duration(3000).repeat(0).playOn(tabs);
            }
        }, 3000);

        np_appbar_lay.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    // Collapsed
                } else if (verticalOffset == 0) {
                    // Expanded
                    heart_btn.setChecked(true);
                    heart_btn.playAnimation();

                } else {
                    // Somewhere in between
                }
            }
        });


        // Date Difference
        getDateCounter();

//NNN

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // For Updaating Focus Again to Map
                if (position == 1) {

                    Fragment mFragment = viewPageAdapter.getItem(viewpager.getCurrentItem());
                    if (mFragment != null) {
                        if (mFragment instanceof LocationFrag) {
                            ((LocationFrag) mFragment).focusOnMap();
                        }
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void onViewCreate() {
        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);
        // initMagicIndicator3();
    }


    private void setupViewPager(ViewPager viewPager) {
        viewPageAdapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        viewPageAdapter.addFragment(new InvitationFrag(), getResources().getString(R.string.invitatiom));
        viewPageAdapter.addFragment(new LocationFrag(), getResources().getString(R.string.location));
        viewPager.setAdapter(viewPageAdapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }


        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }


    /**
     * Adds Events and Reminders in Calendar.
     */
    private void addReminderInCalendar() {
        Calendar cal = Calendar.getInstance();

        cal.set(2017, 5, 3);
        Uri EVENTS_URI = Uri.parse(getCalendarUriBase(true) + "events");
        ContentResolver cr = getContentResolver();
        TimeZone timeZone = TimeZone.getDefault();

        /** Inserting an event in calendar. */
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE, "Tomorrow is Dinesh & Sri's Wedding");
        values.put(CalendarContract.Events.DESCRIPTION, "Tomorrow is Dinesh & Sri's Wedding");
        //   values.put(CalendarContract.Events.DESCRIPTION, "Dinesh & Sri's Wedding Two More Days to go");
        values.put(CalendarContract.Events.ALL_DAY, 0);
        // event starts at 11 minutes from now
        values.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis() + 1 * 60 * 1000);
        // ends 60 minutes from now
        values.put(CalendarContract.Events.DTEND, cal.getTimeInMillis() + 2 * 60 * 1000);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        values.put(CalendarContract.Events.HAS_ALARM, 1);
        Uri event = cr.insert(EVENTS_URI, values);

        // Display event id.
        //   Toast.makeText(getApplicationContext(), "Event added :: ID :: " + event.getLastPathSegment(), Toast.LENGTH_SHORT).show();

        /** Adding reminder for event added. */
        Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(true) + "reminders");
        values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(event.getLastPathSegment()));
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        values.put(CalendarContract.Reminders.MINUTES, 10);
        cr.insert(REMINDERS_URI, values);
    }

    private void addBeforTwoReminderInCalendar() {
        Calendar cal = Calendar.getInstance();

        cal.set(2017, 5, 2);
        Uri EVENTS_URI = Uri.parse(getCalendarUriBase(true) + "events");
        ContentResolver cr = getContentResolver();
        TimeZone timeZone = TimeZone.getDefault();

        /** Inserting an event in calendar. */
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE, "Dinesh & Sri's Wedding Two More Days to go");
        values.put(CalendarContract.Events.DESCRIPTION, "Dinesh & Sri's Wedding Two More Days to go");
        values.put(CalendarContract.Events.ALL_DAY, 0);
        // event starts at 11 minutes from now
        values.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis() + 1 * 60 * 1000);
        // ends 60 minutes from now
        values.put(CalendarContract.Events.DTEND, cal.getTimeInMillis() + 2 * 60 * 1000);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        values.put(CalendarContract.Events.HAS_ALARM, 1);
        Uri event = cr.insert(EVENTS_URI, values);

        // Display event id.
        //   Toast.makeText(getApplicationContext(), "Event added :: ID :: " + event.getLastPathSegment(), Toast.LENGTH_SHORT).show();

        /** Adding reminder for event added. */
        Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(true) + "reminders");
        values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(event.getLastPathSegment()));
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        values.put(CalendarContract.Reminders.MINUTES, 10);
        cr.insert(REMINDERS_URI, values);
    }


    /**
     * Returns Calendar Base URI, supports both new and old OS.
     */
    private String getCalendarUriBase(boolean eventUri) {
        Uri calendarURI = null;
        try {
            if (android.os.Build.VERSION.SDK_INT <= 7) {
                calendarURI = (eventUri) ? Uri.parse("content://calendar/") : Uri.parse("content://calendar/calendars");
            } else {
                calendarURI = (eventUri) ? Uri.parse("content://com.android.calendar/") : Uri
                        .parse("content://com.android.calendar/calendars");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendarURI.toString();
    }

    public boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_CALENDAR)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission Necessary");
                    alertBuilder.setMessage("Remainder For Marriage");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        ///  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_CALENDAR:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                    try {
                        addReminderInCalendar();

                    } catch (Exception e) {

                    }
                } else {
//code for deny
                }
                break;
        }
    }


    //1 minute = 60 seconds
    //1 hour = 60 x 60 = 3600
    //1 day = 3600 x 24 = 86400
    public void printDifference(Date startDate, Date endDate) {

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60; //60,000
        long hoursInMilli = minutesInMilli * 60;  //3,600,000
        long daysInMilli = hoursInMilli * 24;  //86,400,000

      /*  long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);
        Log.i("DATE PRINTINGGG", "" + elapsedDays + " " + elapsedHours + "  " + elapsedMinutes + "  " + elapsedSeconds);
*/
        // NN
        CountdownView mCvCountdownViewTest4 = (CountdownView) findViewById(R.id.cv_countdownViewTest4);
        // long time4 = (long) elapsedDays * elapsedHours * elapsedMinutes * elapsedSeconds * 1000;
        //  long time4 = (long) 16 * 17 * 60 * 60 * 1000;
        mCvCountdownViewTest4.start(different);
    }

    public void getDateCounter() {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        int am_pm;
        try {
            Calendar c = Calendar.getInstance();
            // c.set(Calendar.HOUR_OF_DAY, 24);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int date = c.get(Calendar.DATE);
            int hour = c.get(Calendar.HOUR);
            int minute = c.get(Calendar.MINUTE);
            int sec = c.get(Calendar.SECOND);
            am_pm = c.get(Calendar.AM_PM);

            c.get(Calendar.AM_PM);
            //0 = AM  1 = PM
            if (am_pm == 1) {
                hour = hour + 12;
            }
            //   Toast.makeText(this, "" + hour, Toast.LENGTH_SHORT).show();

            String date1_str = "" + date + "/" + month + "/" + year + " " + hour + ":" + minute + ":" + sec;

            // Date date1 = simpleDateFormat.parse("23/5/2017 17:35:55");
            Date date1 = simpleDateFormat.parse(date1_str);
            Date date2 = simpleDateFormat.parse("04/6/2017 6:35:55");

            printDifference(date1, date2);

        } catch (java.text.ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "ParseException", Toast.LENGTH_SHORT).show();

        }
    }


}
