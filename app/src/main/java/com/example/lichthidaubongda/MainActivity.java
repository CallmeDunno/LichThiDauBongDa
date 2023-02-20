package com.example.lichthidaubongda;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    //region Variable
    ListView listView;
    ImageView imageView;
    TextView tv_tengiaidau, tv_muagiai, tv_khuvuc, tv_lv_empty;
    FloatingActionButton fabtn;
    Spinner spinnerDoiBong;



    private String f_tenGiaiDau;
    private String f_muaGiai;

    DB_SQLite dbSqLite;

    ArrayList<LichThiDau> danhSachTranDau = new ArrayList<>();
    LichThiDauAdapter lichThiDauAdapter = new LichThiDauAdapter(this, R.layout.list_item, danhSachTranDau);

    ArrayList<String> danhSachDoiBong = new ArrayList<>();
    ArrayAdapter<String> spinnerAdapter;
    public static SharedPreferences sharedPreferences;
    //endregion

    private void init() {
        listView = findViewById(R.id.lv_tttd);
        imageView = findViewById(R.id.img_avt);
        tv_tengiaidau = findViewById(R.id.tv_tengd);
        tv_muagiai = findViewById(R.id.tv_tenmuagiai);
        tv_khuvuc = findViewById(R.id.tv_tenkv);
        tv_lv_empty = findViewById(R.id.tv_lv_empty);
        fabtn = findViewById(R.id.fab_import);
        spinnerDoiBong = findViewById(R.id.sp_filter_db);

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
            if (window == null) {
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

    private void SelectedDataFromSQLite(){
        Cursor cursor = dbSqLite.GetData("SELECT * FROM LichThiDau");
        danhSachTranDau.clear();
        danhSachDoiBong.clear();
        danhSachDoiBong.add("Mặc định");
        while (cursor.moveToNext()){
            String t1 = cursor.getString(1);
            String l1 = cursor.getString(2);
            String t2 = cursor.getString(3);
            String l2 = cursor.getString(4);
            String gio = cursor.getString(5);
            String ngay = cursor.getString(6);
            String vong = cursor.getString(7);
            String more = cursor.getString(8);
            danhSachTranDau.add(new LichThiDau(t1, l1, t2, l2, gio, ngay, vong, more));
            if (!danhSachDoiBong.contains(t1)){
                danhSachDoiBong.add(t1);
            }
            if (!danhSachDoiBong.contains(t2)){
                danhSachDoiBong.add(t2);
            }
        }
        Collections.sort(danhSachTranDau, new LichThiDau.DateOrder());
        lichThiDauAdapter.notifyDataSetChanged();
        spinnerAdapter.notifyDataSetChanged();
    }

    private void SelectedDataFromSQLite(String str){
        Cursor cursor = dbSqLite.GetData("SELECT * FROM LichThiDau");
        danhSachTranDau.clear();
        while (cursor.moveToNext()){
            String t1 = cursor.getString(1);
            String t2 = cursor.getString(3);
            if (t1.equals(str) || t2.equals(str)){
                String l1 = cursor.getString(2);
                String l2 = cursor.getString(4);
                String gio = cursor.getString(5);
                String ngay = cursor.getString(6);
                String vong = cursor.getString(7);
                String more = cursor.getString(8);
                danhSachTranDau.add(new LichThiDau(t1, l1, t2, l2, gio, ngay, vong, more));
            }
        }
        Collections.sort(danhSachTranDau, new LichThiDau.DateOrder());
        lichThiDauAdapter.notifyDataSetChanged();
    }

    private void InsertDataIntoSQLite(String t1, String l1, String t2, String l2, String gio, String ngay, String vong, String more){
        String queryInsert = String.format("INSERT INTO LichThiDau VALUES (NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                t1, l1, t2, l2, gio, ngay, vong, more);
        dbSqLite.QueryData(queryInsert);
    }

    private void DeleteDataFromTable(){
        String queryDeleteDataSQLite = "DELETE FROM LichThiDau";
        dbSqLite.QueryData(queryDeleteDataSQLite);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        CheckNetwork();
        init();

        listView.setAdapter(lichThiDauAdapter);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, danhSachDoiBong);
        spinnerDoiBong.setAdapter(spinnerAdapter);

        dbSqLite = new DB_SQLite(this, "LichThiDauBongDa.sqlite", null, 1);

        sharedPreferences = getSharedPreferences("DataLichThiDauBongDa", MODE_PRIVATE);
        f_tenGiaiDau = sharedPreferences.getString("tenGiaiDau", "");
        f_muaGiai = sharedPreferences.getString("muaGiai", "");

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("Data");
            if (bundle != null) {
                String t = bundle.getString("TenGiaiDau");
                String m = bundle.getString("MuaGiai");
                GetDataGD(t, m);
                GetDataLTD(t, m);
            } else {
                GetDataGD(f_tenGiaiDau, f_muaGiai);
                SelectedDataFromSQLite();
            }

        } else {
            Toast.makeText(this, "intent null 150", Toast.LENGTH_SHORT).show();
        }

        Log.d("test", "size = " + danhSachTranDau.size());
        Log.d("test", "sizeDB = " + danhSachDoiBong.size());

        spinnerDoiBong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (danhSachDoiBong.get(i).equals("Mặc định")){
                    Log.d("test", "huhu");
                    SelectedDataFromSQLite();
                } else {
                    Toast.makeText(MainActivity.this, danhSachDoiBong.get(i), Toast.LENGTH_SHORT).show();
                    SelectedDataFromSQLite(danhSachDoiBong.get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WebActivity.class));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DialogMore(i);
            }
        });

    }

    private void DialogMore(int i){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_more_infor);
        Window window = dialog.getWindow();
        if (window == null) {
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

    private void GetDataGD(String t, String m) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.urlGetDataGD.GetUrl(),
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String ten_gd = jsonObject.getString("ten_giai_dau");
                        String logo_gd = jsonObject.getString("logo_giai_dau");
                        String khu_vuc_gd = jsonObject.getString("khu_vuc");
                        String mua_giai_gd = jsonObject.getString("mua_giai");
                        if (ten_gd.equals(t) && mua_giai_gd.equals(m)) {
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

    private void GetDataLTD(String ten, String mua) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL.urlGetDataLTD.GetUrl(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        DeleteDataFromTable();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String t1 = jsonObject.getString("ten_doi_bong_1");
                                String t2 = jsonObject.getString("ten_doi_bong_2");
                                String l1 = jsonObject.getString("logo_doi_bong_1");
                                String l2 = jsonObject.getString("logo_doi_bong_2");
                                String gio = jsonObject.getString("gio_thi_dau");
                                String ngay = jsonObject.getString("ngay_thi_dau");
                                String vong = jsonObject.getString("vong_dau");
                                String tenGD = jsonObject.getString("ten_giai_dau");
                                String muagiai = jsonObject.getString("mua_giai");
                                String more = jsonObject.getString("more");
                                if (tenGD.equals(ten) && muagiai.equals(mua)) {
                                    InsertDataIntoSQLite(t1, l1, t2, l2, gio, ngay, vong, more);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        SelectedDataFromSQLite();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "line 242 " + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void UpdateDataLTD(String t1, String t2) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
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
                map.put("ten_giai_dau", f_tenGiaiDau);
                map.put("mua_giai", f_muaGiai);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}