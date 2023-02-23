package com.example.lichthidaubongda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class WebActivity extends AppCompatActivity {

    WebView webView;
    Button buttonImport;
    ProgressBar progressBar;

    private String w_tenGiaiDau, w_logo, w_khuVuc, w_muaGiai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getSupportActionBar().setTitle("Import");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //region MapView
        buttonImport = findViewById(R.id.btn_import);
        progressBar = findViewById(R.id.prog_loading);
        //endregion

        //region webview
        webView = findViewById(R.id.wv_web);
        webView.loadUrl("https://thethao247.vn/");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //endregion

        buttonImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WebActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                String urlHTML = webView.getUrl();
                if (urlHTML.isEmpty()){
                    Log.d("test", "urlHTML is null (WebActivity line 89");
                } else {
                    DownloadThread thread = new DownloadThread(urlHTML);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Document document = thread.getDocument();
                    ReadGiaiDau(document);
                    ReadLichThiDau(document);

                    bundle.putString("TenGiaiDau", w_tenGiaiDau);
                    bundle.putString("MuaGiai", w_muaGiai);
                    Log.d("test", w_tenGiaiDau + " line 104 " + w_muaGiai);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intent.putExtra("Data", bundle);
                startActivity(intent);
            }
        });
    }

    private void ReadGiaiDau(Document document){
        Elements elements = document.getElementsByClass("box_info_league");
        Element element = elements.first();

        w_logo          = element.getElementsByTag("img").attr("src");
        w_tenGiaiDau    = element.getElementsByTag("li").get(0).getElementsByTag("div").text();
        w_khuVuc        = element.getElementsByTag("li").get(1).getElementsByTag("div").get(1).text();
        w_muaGiai       = element.getElementsByTag("li").get(2).getElementsByTag("div").get(1).text();

        Log.d("test", "web 126: " + w_tenGiaiDau);

        SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
        editor.remove("tenGiaiDau");
        editor.remove("muaGiai");
        editor.apply();
        editor.putString("tenGiaiDau", w_tenGiaiDau);
        editor.putString("muaGiai", w_muaGiai);
        editor.apply();

        CheckExistedGD(w_tenGiaiDau, w_logo, w_khuVuc, w_muaGiai);

    }

    private void CheckExistedGD(String t, String l, String k, String m){
        RequestQueue requestQueue = Volley.newRequestQueue(WebActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.urlCheckGD.GetUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("isNotExisted")){
                    Log.d("test", "insert success");
                    InsertDataGD(t, l, k, m);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Error Check GD " + error.toString());
            }
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ten_giai_dau", t);
                map.put("mua_giai", m);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void InsertDataGD(String t, String l, String k, String m){
        RequestQueue requestQueue = Volley.newRequestQueue(WebActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.urlInsertGD.GetUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("insertSucces")){
                    Log.d("test", "Insert GD success (WebActivity line 170)");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "Error insert GD (WebActivity line 178) " + error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ten_giai_dau", t);
                map.put("logo_giai_dau", l);
                map.put("khu_vuc", k);
                map.put("mua_giai", m);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ReadLichThiDau(Document document){
        Elements elementsVongDau = document.getElementsByClass("caption");
        Elements elementsLichThiDau = document.getElementsByClass("list_match");

        try{
            String gioThiDau, ten1, ten2, logo1, logo2, ngayThiDau = "", vongDau, more;
            for (int i = 0; i < elementsLichThiDau.size(); i++) {
                vongDau = elementsVongDau.get(i).getElementsByTag("h2").first().text();
                Elements listTag_li = elementsLichThiDau.get(i).getElementsByTag("li");
                for (Element li : listTag_li) {
                    if (li.hasClass("caption-days")) {
                        ngayThiDau = li.getElementsByTag("div").first().text(); //2
                        continue;
                    } else {
                        gioThiDau = li.getElementsByTag("div").get(0).getElementsByTag("a").first().text(); //3
                        ten1 = li.getElementsByTag("div").get(1).getElementsByTag("div").first().getElementsByTag("a").first().text(); //4
                        logo1 = li.getElementsByTag("div").get(1).getElementsByTag("div").first().getElementsByTag("img").attr("data-src"); //5
                        ten2 = li.getElementsByTag("div").get(1).getElementsByTag("div").last().getElementsByTag("a").first().text(); //6
                        logo2 = li.getElementsByTag("div").get(1).getElementsByTag("div").last().getElementsByTag("img").attr("data-src"); //7
                        more = li.getElementsByTag("div").get(2).getElementsByTag("a").first().attr("href");
                    }
                    CheckExistedLTD(ten1, ten2, logo1, logo2, gioThiDau, ngayThiDau, vongDau, more);
                }
            }

        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void CheckExistedLTD(String t1, String t2, String l1, String l2, String gio, String ngay, String vong, String more){
        RequestQueue requestQueue = Volley.newRequestQueue(WebActivity.this);
        StringRequest stringRequest =new StringRequest(Request.Method.POST, URL.urlCheckLTD.GetUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("isNotExisted")){
                    GetMaGiaiDau(t1, t2, l1, l2, gio, ngay, vong, more);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Error Check LTD" + error.toString());
            }
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ten_doi_bong_1", t1);
                map.put("ten_doi_bong_2", t2);
                map.put("ten_giai_dau", w_tenGiaiDau);
                map.put("mua_giai", w_muaGiai);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void InsertDataLTD(String t1, String t2, String l1, String l2, String gio, String ngay, String vong, String more, String magd) {
        RequestQueue requestQueue = Volley.newRequestQueue(WebActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.urlInsertLTD.GetUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("insertSucces")){
                    Log.d("test", "Insert LTD line 262");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "Error insert LTD" + error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ten_doi_1", t1);
                map.put("logo_doi_1", l1);
                map.put("ten_doi_2", t2);
                map.put("logo_doi_2", l2);
                map.put("gio_thi_dau", gio);
                map.put("ngay_thi_dau", ngay);
                map.put("vong_dau", vong);
                map.put("more", more);
                map.put("ma_giai_dau", magd);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetMaGiaiDau(String t1, String t2, String l1, String l2, String gio, String ngay, String vong, String more){
        RequestQueue requestQueue = Volley.newRequestQueue(WebActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.urlGetMaGiaiDau.GetUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.trim().equals("isNotExisted")){
                    InsertDataLTD(t1, t2, l1, l2, gio, ngay, vong, more, response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Error Get MAGD 302" + error.toString());
            }
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ten_giai_dau", w_tenGiaiDau);
                map.put("mua_giai", w_muaGiai);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}