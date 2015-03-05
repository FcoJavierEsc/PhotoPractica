package com.utad.photopractica.modelo;


public class P_ParClaveValor {

    public static final String DIC_TITULO = "TTLO";
    public static final String DIC_CATEGORIA = "CTGR";
    public static final String DIC_SUBCATEGORIA = "SCTG";
    public static final String DIC_ORGANIZADOPOR = "RGNZ";
    public static final String DIC_GPSACTIVO = "GPSA";

    public static final int VAL_ORG_CRONO = 0;
    public static final int VAL_ORG_COMENTARIO = 1;
    public static final int VAL_ORG_CAT_SUB = 2;
    public static final boolean VAL_GPS_ACTIVO = true;

    private static final String TABLE_NAME = "DICTIONARY";
    private static final String COLUMN_KEY = "KEY";
    private static final String COLUMN_VALUE = "VALUE";


    private static final String CREATE_TABLE_DICTIONARY = "create table "
            + TABLE_NAME
            + "("
            + COLUMN_KEY + " character(4) primary key , "
            + COLUMN_VALUE + " character(40)"
            + ");";


    public static String getCreateTable() {
        return CREATE_TABLE_DICTIONARY;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getColumnKey() {
        return COLUMN_KEY;
    }

    public static String getColumnValue() {
        return COLUMN_VALUE;
    }

    public static String[] getTableCol() {
        String[] retorno = {
                getColumnKey(),
                getColumnValue()
        };

        return retorno;

    }


}
