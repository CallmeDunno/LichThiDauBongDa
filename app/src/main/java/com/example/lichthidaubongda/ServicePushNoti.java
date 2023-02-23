package com.example.lichthidaubongda;

import static com.example.lichthidaubongda.Notification.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicePushNoti extends Service {

    private String tenGD;
    private String muagiai;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        tenGD       = intent.getStringExtra("tenGD");
        muagiai     = intent.getStringExtra("mg");
        ArrayList<LichThiDau> x = GetList();
        if (x.size() != 0){
            LichThiDau lichThiDau = x.get(0);

            Calendar now = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String datetimeCurr = simpleDateFormat.format(now.getTime());

            if (datetimeCurr.compareTo(lichThiDau.getDateTime().toString()) > 0){
                x.remove(lichThiDau);
                UpdateDataLTD(lichThiDau.getTeam_1(), lichThiDau.getTeam_2());
                DeleteData(lichThiDau.getTeam_1(), lichThiDau.getTeam_2(), lichThiDau.getRound());
                stopForeground(true);
            }
            startForeground(1, sendNotification(lichThiDau.getTeam_1(), lichThiDau.getTeam_2(), lichThiDau.getDateTime().toString()));
        }
        return START_NOT_STICKY;
    }

    private Notification sendNotification(String t1, String t2, String t3) {
        String[] field = t3.split("\\s+");
        String msg = String.format("Trận đấu giữa %s và %s sẽ diễn ra vào lúc %s, ngày %s trong khuôn khổ giải đấu %s, mùa giải %s.", t1, t2, field[1], field[0], tenGD, muagiai);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_foreground);
        Notification builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Thông báo")
                .setContentText(msg)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setColor(getResources().getColor(R.color.teal_200))
                .setSmallIcon(R.drawable.ball)
                .setLargeIcon(bitmap)
                .setContentIntent(pendingIntent)
                .build();
        return builder;

    }

    private void DeleteData(String s1, String s2, String s3){
        String queryDel = "DELETE FROM LichThiDau WHERE ten_doi_1 = '"+s1+"' AND ten_doi_2 = '"+s2+"' AND vong = '"+s3+"'";
        MainActivity.dbSqLite.QueryData(queryDel);
    }

    private ArrayList<LichThiDau> GetList() {
        String querySelect = "SELECT * FROM LichThiDau";
        Cursor cursor = MainActivity.dbSqLite.GetData(querySelect);
        ArrayList<LichThiDau> list = new ArrayList<>();
        while(cursor.moveToNext()){
            String id = cursor.getString(0);
            String t1 = cursor.getString(1);
            String l1 = cursor.getString(2);
            String t2 = cursor.getString(3);
            String l2 = cursor.getString(4);
            String gio = cursor.getString(5);
            String ngay = cursor.getString(6);
            String vong = cursor.getString(7);
            String more = cursor.getString(8);
            list.add(new LichThiDau(id, t1, l1, t2, l2, gio, ngay, vong, more));
        }
        Collections.sort(list, new LichThiDau.DateOrder());
        return list;
    }

    private void UpdateDataLTD(String t1, String t2) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.urlUpdateDataLTD.GetUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("updateSucces")) {
                    Log.d("test", "update success line 396 Main");
                } else {
                    Log.d("test", "update fail line 398 Main");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Error update line 404 main " + error.toString());
            }
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ten_doi_bong_1", t1);
                map.put("ten_doi_bong_2", t2);
                map.put("ten_giai_dau", tenGD);
                map.put("mua_giai", muagiai);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
