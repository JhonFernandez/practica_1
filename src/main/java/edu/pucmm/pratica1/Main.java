package edu.pucmm.pratica1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Jhon on 25/5/2017.
 */
public class Main {
    public static void main(String[] args) {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://en.wikipedia.org/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements parrafos = doc.select("p");
        parrafos.forEach(element -> System.out.println(element.text()+"\n"));

    }
}
