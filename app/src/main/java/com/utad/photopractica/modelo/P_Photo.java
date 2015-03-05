package com.utad.photopractica.modelo;

/**
 * Created by masandroid on 27/01/15.
 */
public class P_Photo {

    private static final String TABLE_NAME = "PHOTO_TAGS";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "TITLE_TAG";
    private static final String COLUMN_CATEGORY_ID = "CATEGORY_ID";
    private static final String COLUMN_SUBCATEGORY_ID = "SUBCATEGORY_ID";
    private static final String COLUMN_URI_SITE = "URI_SITE";
    private static final String COLUMN_CREATE_AT = "CREATE_AT";
    private static final String COLUMN_LONG = "LONGITUD";
    private static final String COLUMN_LAT = "LATITUD";
    private static final String COLUMN_MUNICIPIO = "MUNICIPIO";
    private static final String COLUMN_PROVINCIA = "PROVINCIA";
    private static final String COLUMN_TIPO_NOMBRE_VIA = "TIPO_NOMBRE_VIA";
    private static final String COLUMN_COD_POSTAL = "COD_POSTAL";


    // Database creation sql statement
    private static final String CREATE_TABLE_PHOTO_TAGS = "create table "
            + TABLE_NAME
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null,"
            + COLUMN_CATEGORY_ID + " integer not null,"
            + COLUMN_SUBCATEGORY_ID + " integer not null,"
            + COLUMN_URI_SITE + " text not null,"
            + COLUMN_LONG + " double not null,"
            + COLUMN_LAT + " double not null,"
            + COLUMN_MUNICIPIO + " character(60),"
            + COLUMN_PROVINCIA + " character(60),"
            + COLUMN_TIPO_NOMBRE_VIA + " character(60),"
            + COLUMN_COD_POSTAL + " character(6),"
            + COLUMN_CREATE_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ");";


    public static String[] getTableCol() {
        String[] retorno = {
                getColumnId(),
                getColumnTitle(),
                getColumnCategoryId(),
                getColumnSubCategoryId(),
                getColumnUriSite(),
                getColumnLong(),
                getColumnLat(),
                getColumnMunicipio(),
                getColumnProvincia(),
                getColumnNombreTipoVia(),
                getColumnCodPostal(),
                getColumnCreateAt()
        };

        return retorno;

    }


    public static String getColumnMunicipio() {
        return COLUMN_MUNICIPIO;
    }

    public static String getColumnProvincia() {
        return COLUMN_PROVINCIA;
    }

    public static String getColumnNombreTipoVia() {
        return COLUMN_TIPO_NOMBRE_VIA;
    }

    public static String getColumnCodPostal() {
        return COLUMN_COD_POSTAL;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getColumnId() {
        return COLUMN_ID;
    }

    public static String getColumnTitle() {
        return COLUMN_TITLE;
    }

    public static String getColumnCategoryId() {
        return COLUMN_CATEGORY_ID;
    }

    public static String getColumnSubCategoryId() {
        return COLUMN_SUBCATEGORY_ID;
    }

    public static String getColumnUriSite() {
        return COLUMN_URI_SITE;
    }

    public static String getColumnCreateAt() {
        return COLUMN_CREATE_AT;
    }

    public static String getColumnLong() {
        return COLUMN_LONG;
    }

    public static String getColumnLat() {
        return COLUMN_LAT;
    }

    public static String getCreateTable() {
        return CREATE_TABLE_PHOTO_TAGS;
    }


}
