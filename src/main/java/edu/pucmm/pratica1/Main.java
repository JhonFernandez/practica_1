package edu.pucmm.pratica1;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import static spark.Spark.post;

/**
 * Created by Jhon on 25/5/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {

        Document doc = null;
        boolean urlValida = false;
        String url = "";
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

//        0)Pruebas con el servidor local
//        post("/practica1", (req, res) -> {
//            String texto = "Asignatura: "+req.queryParams("asignatura");
//            System.out.println(texto);
//            return texto;
//        });

        do {

            System.out.println("Digite una URL valida: ");
            try {
                url = lector.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                doc = Jsoup.connect(url).get();

                urlValida = true;
            } catch (Exception e) {
                System.out.println("URL MALO XD");
            }
        } while (!urlValida);


//        String html = "<html><head><title>First parse</title></head>"
//                + "<body><p>Parsed HTML into a doc.</p><p><a><img/><a/></p><ol><li></li><li></li><ol></body></html>";
//        Document doc = Jsoup.parse(html);

//        A)  Indicar la cantidad de lineas del recurso retornado.
        System.out.println("Cantidad de lineas: " + extractLineCount(url));

//        B)Indicar la cantidad de párrafos (p) que contiene el documento HTML .
        System.out.println("Cantidad de parrafos: " + doc.select("p").size());

//        C) Indicar la cantidad de imágenes (img) dentro de los párrafos que
//        contiene el archivo HTML.
        System.out.println("Cantidad de imagenes dentro de parrafos: "
                + doc.select("p img").size());

//        D) Indicar la cantidad de formularios (form) que contiene el HTML por
//        categorizando por el método implementado POST o GET.
        System.out.println("Cantidad de formularios (POST): " + doc.select("form[method=post]").size());
        System.out.println("Cantidad de formularios (GET): " + doc.select("form[method=get]").size());

//        E) Para cada formulario mostrar los campos del tipo input y su
//        respectivo tipo que contiene en el documento HTML.
        int contador = 0;
        Elements forms = doc.select("form");
        for (Element form : forms) {
            contador++;
            System.out.println("Form #" + contador);
            System.out.println(forms.select("input"));
        }

//        F) Para cada formulario “parseado”, identificar que el método de envío
//        del formulario sea por utilizando el método POST y enviar una
//        petición al servidor, con el parámetro llamado asignatura y valor
//        practica1 y mostrar la respuesta por la salida estandar


        System.out.println("Peticiones a los form[post]");
        forms = doc.select("form[method=post]");
        for (Element form : forms) {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            String urlPost = url.split("/")[0] + "//" + url.split("/")[2] + form.attr("action");
            System.out.println(urlPost);
            HttpPost httpPost = new HttpPost(urlPost);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("asignatura", "practica1"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            System.out.println("respuesta:");
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
    }

    private static String extractContent(String urlString)
            throws MalformedURLException, IOException {
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        InputStream is = urlConnection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String content = "";
        String linea = br.readLine();
        while (null != linea) {
            content += linea + "\n";
            linea = br.readLine();
        }
        return content.trim();
    }

    private static int extractLineCount(String urlString)
            throws MalformedURLException, IOException {
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        InputStream is = urlConnection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        int count = 0;
        String linea = br.readLine();
        while (null != linea) {
            count++;
            linea = br.readLine();
        }
        return count;
    }
}
