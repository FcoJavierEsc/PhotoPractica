package com.utad.photopractica;

/**
 * Las posibles opciones que hay en el modelo
 */
public interface SettingInterfaz {

    public boolean isGps();
    public String getPrerencesTit();
    public int getPreferencesCat();
    public int getPreferencesSubCat();
    public int getPreferencesOrg();

    public void savePreferencesTit(String tit);
    public void savePreferencesCat(int cat);
    public void savePreferencesSubCat(int sub);
    public void savePreferencesOrg(int org);
    public void savePreferencesGps(boolean gps);

}
