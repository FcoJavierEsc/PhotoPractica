package com.utad.photopractica.modelo;

/**
 * Created by masandroid on 14/02/15.
 */
public class StringXml {
    String mOriginal;
    String mLowerCase;

    public StringXml(String original) {
        mOriginal = original;
        mLowerCase= original.toLowerCase();
    }

    public String Extract (String token){

        String buscarInicio="<"+token.toLowerCase()+">";
        String buscarFin="</"+token.toLowerCase()+">";

        int desde = mLowerCase.indexOf(buscarInicio);
        if (desde<0)
            return  null;
        desde += buscarInicio.length();
        int hasta = mLowerCase.indexOf(buscarFin);
        if (hasta <0)
            return null;

        return mOriginal.substring(desde,hasta);
    }
}
