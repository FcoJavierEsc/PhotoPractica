package com.utad.photopractica.modelo.geo;


import android.util.Log;

import com.utad.photopractica.modelo.PhotoInterfaz;
import com.utad.photopractica.modelo.StringXml;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GeoToponimo implements Runnable {


    private PhotoInterfaz.PhotoGeoLocalizacion mGeoLocalizacion;

    private GeoToponimoListener mGeoToponimoListener;

    public interface GeoToponimoListener {
        public void dialogFinish(PhotoInterfaz.PhotoGeoLocalizacion geoLocalizacion);
    }

    public GeoToponimo(PhotoInterfaz.PhotoGeoLocalizacion geoLocalizacion, GeoToponimoListener geoToponimoListener) {
        mGeoLocalizacion = geoLocalizacion;
        mGeoToponimoListener = geoToponimoListener;
    }

    @Override
    public void run() {


        double longitud = mGeoLocalizacion.getLongitud();
        double latitud = mGeoLocalizacion.getLatitud();

        String longitudStr = String.format(Locale.US,"%.8f", longitud);
        String latitudStr = String.format(Locale.US,"%.8f", latitud);


        String mandar = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<Execute service=\"WPS\" status=\"false\" store=\"false\" version=\"0.4.0\" xmlns=\"http://www.opengeospatial.net/wps\" xmlns:gml=\"http://www.opengis.net/gml\" xmlns:om=\"http://www.opengis.net/om\" xmlns:ows=\"http://www.opengeospatial.net/ows\" xmlns:pak=\"http://www.opengis.net/examples/packet\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.opengeospatial.net/wps ..\\wpsExecute.xsd\">" +
                "<ows:Identifier>com.ign.process.geometry.ClosestMultiplePointFinder</ows:Identifier>" +
                "<DataInputs>" +
                "<Input>" +
                "<ows:Identifier>data</ows:Identifier>" +
                "<ows:Title>Punto</ows:Title>" +
                "<ComplexValue defaultSchema=\"http://www.idee.es/parser/ArrayList.xsd\">" +
                "<gml:Point>" +
                "<gml:coord>" +
                "<gml:X>" + longitudStr + "</gml:X>" +
                "<gml:Y>" + latitudStr + "</gml:Y>" +
                "</gml:coord>" +
                "</gml:Point>" +
                "</ComplexValue>" +
                "</Input>" +
                "</DataInputs>" +
                "<OutputDefinitions>" +
                "<Output>" +
                "<ows:Identifier>result</ows:Identifier>" +
                "<ows:Title>Lista de portales</ows:Title>" +
                "<ows:Abstract>xml con la lista de portales y las coordenadas de busqueda" +
                "</ows:Abstract>" +
                "<ComplexOutput defaultFormat=\"text/XML\" defaultSchema=\"http://www.idee.es/portalList.xsd\">" +
                "<SupportedComplexData>" +
                "<Schema>http://www.idee.es/portalList.xsd</Schema>" +
                "</SupportedComplexData>" +
                "</ComplexOutput>" +
                "</Output>" +
                "</OutputDefinitions>" +
                "</Execute>";

        Log.v("GPS","MANDAMOS["+mandar+"]");
        Log.v("GPS","Mas vale tarde que nunca "+longitudStr+" y " + latitudStr);
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.cartociudad.es/wps/WebProcessingService");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<>(1);
            nameValuePairs.add(new BasicNameValuePair("request", mandar));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF8"), 8);
            StringBuilder sb = new StringBuilder();
            String line ;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            StringXml result = new StringXml(sb.toString());
            Log.v("GPS","RESPUESTA "+sb.toString());
            String provincia = result.Extract("provincia");
            if (provincia == null) return;
            String municipio = result.Extract("municipio");
            if (municipio == null) return;
            String tipoVia = result.Extract("tipoVia");
            if (tipoVia == null) return;

            String nombreVia = result.Extract("nombrevia");
            if (nombreVia == null) return;
            String cp = result.Extract("cp");
            if (cp == null) return;


            mGeoLocalizacion = null;
            mGeoLocalizacion = new PhotoInterfaz.PhotoGeoLocalizacion(longitud,
                    latitud,
                    provincia,
                    municipio,
                    tipoVia+" "+nombreVia,
                    cp);

            mGeoToponimoListener.dialogFinish(mGeoLocalizacion);
            Log.v("GPS", "VA " + mGeoLocalizacion.getMunicipio());
        } catch (ClientProtocolException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
