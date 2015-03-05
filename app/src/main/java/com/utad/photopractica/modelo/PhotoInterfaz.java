package com.utad.photopractica.modelo;


public interface PhotoInterfaz {




    public void setPhotoItem(PhotoInterfaz m);

    public void setTitulo(String titulo);

    public void setCategoria(int id);

    public void setSubCategoria(int id);

    public String getCategoriaIcon();

    public String getSubCategoriaIcon();

    public int getCategoria();

    public int getSubCategoria();

    public long getID();

    public String getImagenPath();

    public String getTitulo();

    public PhotoGeoLocalizacion getPhotoGeoLocalizacion();

    public String getFecha_Hora();

    public String getFecha();

    public void setGeoLocalizacion(PhotoGeoLocalizacion photoGeoLocalizacion);

    public class PhotoGeoLocalizacion {
        private double longitud;
        private double latitud;
        private String provincia;
        private String municipio;
        private String codpostal;
        private String nombreTipoVia;

        public PhotoGeoLocalizacion(PhotoGeoLocalizacion geoDatos) {
            setLatitud(geoDatos.getLatitud());
            setLongitud(geoDatos.getLongitud());
            setProvincia(geoDatos.getProvincia());
            setCodpostal(geoDatos.getCodpostal());
            setNombreTipoVia(geoDatos.getNombreTipoVia());
            setMunicipio(geoDatos.getMunicipio());
        }

        public PhotoGeoLocalizacion() {
            setLongitud(0.0);
            setLatitud(0.0);
            setProvincia(null);
            setMunicipio(null);
            setCodpostal(null);
            setNombreTipoVia(null);
        }

        public PhotoGeoLocalizacion(double longitud,
                                    double latitud,
                                    String provincia,
                                    String municipio,
                                    String nombreTipoVia,
                                    String codpostal) {
            setLongitud(longitud);
            setLatitud(latitud);
            setProvincia(provincia);
            setMunicipio(municipio);
            setCodpostal(codpostal);
            setNombreTipoVia(nombreTipoVia);
        }

        public String getNombreTipoVia() {
            return nombreTipoVia;
        }

        public void setNombreTipoVia(String nombreTipoVia) {
            this.nombreTipoVia = nombreTipoVia;
        }

        public void setLongitud(double longitud) {
            this.longitud = longitud;
        }

        public void setLatitud(double latitud) {
            this.latitud = latitud;
        }

        public void setMunicipio(String municipio) {
            this.municipio = municipio;
        }

        public void setCodpostal(String codpostal) {
            this.codpostal = codpostal;
        }

        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }

        public String getProvincia() {
            return provincia;
        }

        public String getMunicipio() {
            return municipio;
        }

        public String getCodpostal() {
            return codpostal;
        }

        public double getLongitud() {
            return longitud;
        }

        public double getLatitud() {
            return latitud;
        }

        //TODO modificar para mostrar la información geográfica apetecida
        public String getGeoInfo() {
            if (municipio != null)
                return getMunicipio();
            return "N/A";//String.format("%6.4f %6.4f", longitud, latitud);
        }
    }

}
