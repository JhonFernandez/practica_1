package edu.pucmm.pratica1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Jhon on 25/5/2017.
 */
public class Main {
    public static void main(String[] args) {
//        Document doc = null;
//        try {
//            doc = Jsoup.connect("http://en.wikipedia.org/").get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p><p><a><img/><a/></p></body></html>";
        Document doc = Jsoup.parse(html);

//         A)  Indicar la cantidad de lineas del recurso retornado.
        System.out.println("Cantidad de lineas: "+doc.html().split("\n").length);

//        B)Indicar la cantidad de párrafos (p) que contiene el documento HTML .
        System.out.println("Cantidad de parrafos: "+doc.select("p").size());

//        C) Indicar la cantidad de imágenes (img) dentro de los párrafos que
//        contiene el archivo HTML.
        System.out.println("Cantidad de imagenes dentro de parrafos: "
                +doc.select("p img").size());




       /* Elements parrafos = doc.select("p");
        parrafos.forEach(element -> System.out.println(element.text()+"\n"));
*/
    }
}
