package com.example.lichthidaubongda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ImageView imageView;
    TextView tv_tengiaidau, tv_muagiai, tv_khuvuc, tv_lv_empty;
    FloatingActionButton fabtn;
    Spinner spinnerDoiBong;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    private static String f_tenGiaiDau;
    private static String f_muaGiai;

    private ArrayList<LichThiDau> danhSachTranDau;
    private ArrayList<String> danhSachDoiBong;
    private LichThiDauAdapter lichThiDauAdapter;
    private ArrayAdapter<String> adapterDB;
    public static SharedPreferences sharedPreferences;

    private void init(){
        listView = findViewById(R.id.lv_tttd);
        imageView = findViewById(R.id.img_avt);
        tv_tengiaidau = findViewById(R.id.tv_tengd);
        tv_muagiai = findViewById(R.id.tv_tenmuagiai);
        tv_khuvuc = findViewById(R.id.tv_tenkv);
        tv_lv_empty = findViewById(R.id.tv_lv_empty);
        fabtn = findViewById(R.id.fab_import);
        spinnerDoiBong = findViewById(R.id.sp_filter_db);

        danhSachDoiBong = new ArrayList<>();
        adapterDB = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, danhSachDoiBong);

        danhSachTranDau = new ArrayList<>();
        lichThiDauAdapter = new LichThiDauAdapter(MainActivity.this, R.layout.list_item, danhSachTranDau);
        listView.setAdapter(lichThiDauAdapter);
    }

    private void CheckNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

        } else {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_cus);
            dialog.setCanceledOnTouchOutside(false);
            Window window = dialog.getWindow();
            if (window == null){
                return;
            }
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Button btn_ok = dialog.findViewById(R.id.btn_ok);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        CheckNetwork();
        init();

        sharedPreferences = getSharedPreferences("DataLichThiDauBongDa", MODE_PRIVATE);
        f_tenGiaiDau    = sharedPreferences.getString("tenGiaiDau", "");
        f_muaGiai       = sharedPreferences.getString("muaGiai", "");

        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getBundleExtra("Data");
            if (bundle != null){
                String t     = bundle.getString("TenGiaiDau");
                String m     = bundle.getString("MuaGiai");
                GetDataGD(t, m);
                GetDataLTD(t, m, true);
            } else {
                GetDataGD(f_tenGiaiDau, f_muaGiai);
                GetDataLTD(f_tenGiaiDau, f_muaGiai, true);
            }
        } else {
            Toast.makeText(this, "intent null 150", Toast.LENGTH_SHORT).show();
        }

        fabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WebActivity.class));
            }
        });

        if (danhSachTranDau.size() == 0){
            tv_lv_empty.setVisibility(View.VISIBLE);
        }

    }

    private void GetDataGD(String t, String m){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.urlGetDataGD.GetUrl(),
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String ten_gd = jsonObject.getString("ten_giai_dau");
                        String logo_gd = jsonObject.getString("logo_giai_dau");
                        String khu_vuc_gd = jsonObject.getString("khu_vuc");
                        String mua_giai_gd = jsonObject.getString("mua_giai");
                        if (ten_gd.equals(t) && mua_giai_gd.equals(m)){
                            Glide.with(MainActivity.this).load(logo_gd).into(imageView);
                            tv_tengiaidau.setText(ten_gd);
                            tv_khuvuc.setText(khu_vuc_gd);
                            tv_muagiai.setText(mua_giai_gd);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "line 196 " + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDataLTD(String ten, String mua, Boolean b){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.urlGetDataLTD.GetUrl(), null,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                danhSachTranDau.clear();
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String t1       = jsonObject.getString("ten_doi_bong_1");
                        String t2       = jsonObject.getString("ten_doi_bong_2");
                        String l1       = jsonObject.getString("logo_doi_bong_1");
                        String l2       = jsonObject.getString("logo_doi_bong_2");
                        String gio      = jsonObject.getString("gio_thi_dau");
                        String ngay     = jsonObject.getString("ngay_thi_dau");
                        String vong     = jsonObject.getString("vong_dau");
                        String tenGD    = jsonObject.getString("ten_giai_dau");
                        String muagiai  = jsonObject.getString("mua_giai");
                        String more     = jsonObject.getString("more");
                        if (tenGD.equals(ten) && muagiai.equals(mua)){
                            tv_lv_empty.setVisibility(View.INVISIBLE);
                            danhSachTranDau.add(new LichThiDau(t1, l1, t2, l2, gio, ngay, vong, more));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (b){
                    GetDanhSachTranDau();
//                    Alarm_Notification();
                }
                SelectItemListView();
                Collections.sort(danhSachTranDau, new LichThiDau.DateOrder());
                lichThiDauAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "line 242 " + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDataLTD(String ten, String mua, String tendb){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.urlGetDataLTD.GetUrl(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        danhSachTranDau.clear();
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String t1       = jsonObject.getString("ten_doi_bong_1");
                                String t2       = jsonObject.getString("ten_doi_bong_2");
                                String l1       = jsonObject.getString("logo_doi_bong_1");
                                String l2       = jsonObject.getString("logo_doi_bong_2");
                                String gio      = jsonObject.getString("gio_thi_dau");
                                String ngay     = jsonObject.getString("ngay_thi_dau");
                                String vong     = jsonObject.getString("vong_dau");
                                String more     = jsonObject.getString("more");
                                String tenGD    = jsonObject.getString("ten_giai_dau");
                                String muagiai  = jsonObject.getString("mua_giai");
                                if (tenGD.equals(ten) && muagiai.equals(mua)){
                                    if (t1.equals(tendb) || t2.equals(tendb)){
                                        LichThiDau lichThiDau = new LichThiDau(t1, l1, t2, l2, gio, ngay, vong, more);
                                        tv_lv_empty.setVisibility(View.INVISIBLE);
                                        danhSachTranDau.add(lichThiDau);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Collections.sort(danhSachTranDau, new LichThiDau.DateOrder());
                        lichThiDauAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "line 285 " + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDanhSachTranDau() {
        Log.d("test", "Spinner: " + danhSachTranDau.size());

        danhSachDoiBong.clear();
        danhSachDoiBong.add("Mặc định");
        for (LichThiDau lichThiDau : danhSachTranDau){
            if (!danhSachDoiBong.contains(lichThiDau.getTeam_1())){
                danhSachDoiBong.add(lichThiDau.getTeam_1());
            }
            if (!danhSachDoiBong.contains(lichThiDau.getTeam_2())){
                danhSachDoiBong.add(lichThiDau.getTeam_2());
            }
        }
        spinnerDoiBong.setAdapter(adapterDB);
        SpinnerSelectItem();
    }

    private void SpinnerSelectItem(){
        spinnerDoiBong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tendb = danhSachDoiBong.get(i);
                if(!tendb.equals("Mặc định")){
                    GetDataLTD(f_tenGiaiDau, f_muaGiai, tendb);
                } else {
                    GetDataLTD(f_tenGiaiDau, f_muaGiai, false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void SelectItemListView(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.item_more_infor);
                Window window = dialog.getWindow();
                if (window == null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams windowAttr = window.getAttributes();
                windowAttr.gravity = Gravity.BOTTOM;
                window.setAttributes(windowAttr);

                WebView webView = dialog.findViewById(R.id.wv_more);
                webView.loadUrl(danhSachTranDau.get(i).getMore());
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                dialog.show();
            }
        });
    }

//    private void Alarm_Notification(){
//        Calendar calendar = Calendar.getInstance();
//        Calendar calendarLTD = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
//        String[] field = danhSachTranDau.get(0).getDateTime().toString().split("\\s+");
//        String date = field[0];
//        String[] dateField = date.split("-");
//        String day = dateField[0];
//        String month = dateField[1];
//        String year = dateField[2];
//
//        String time = field[1];
//        String[] timeFiled = time.split(":");
//        String hour = timeFiled[0];
//        String minute = timeFiled[0];
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            calendarLTD.set(Calendar.DATE, Integer.parseInt(day));
//            calendarLTD.set(Calendar.MONTH, Integer.parseInt(month));
//            calendarLTD.set(Calendar.YEAR, Integer.parseInt(year));
//            calendarLTD.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
//            calendarLTD.set(Calendar.MINUTE, Integer.parseInt(minute + 100));
////            calendarLTD.set(Calendar.MINUTE, 100);
//        }
//        if (simpleDateFormat.format(calendar.getTime()).compareTo(danhSachTranDau.get(0).getDateTime().toString()) == 0) {
//            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
//            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
//            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
//        }
//        if (simpleDateFormat.format(calendar.getTime()).equals(simpleDateFormat.format(calendarLTD.getTime()))){
//            UpdateDataLTD(danhSachTranDau.get(0).getTeam_1(), danhSachTranDau.get(0).getTeam_2());
//        }
//
//    }

    private void UpdateDataLTD(String t1, String t2){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.urlUpdateDataLTD.GetUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("updateSucces")){
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
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ten_doi_bong_1", t1);
                map.put("ten_doi_bong_2", t2);
                map.put("ten_giai_dau", f_tenGiaiDau);
                map.put("mua_giai", f_muaGiai);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}