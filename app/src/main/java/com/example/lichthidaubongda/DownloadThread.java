package com.example.lichthidaubongda;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DownloadThread extends Thread{

    private final String url;
    private Document document;

    public DownloadThread(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        try {
            Connection connection = Jsoup.connect(url);
            document = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Document getDocument() {
        return document;
    }
}
