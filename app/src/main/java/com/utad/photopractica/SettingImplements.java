package com.utad.photopractica;

import com.utad.photopractica.modelo.sqlite3.SqlitePersist;

public class SettingImplements implements SettingInterfaz {

    private SqlitePersist mSqlitePersist;

    public SettingImplements() {
        mSqlitePersist = new SqlitePersist();
    }

    @Override
    public boolean isGps() {
        return mSqlitePersist.isGps();
    }

    @Override
    public String getPrerencesTit() {
        return mSqlitePersist.getPrerencesTit();
    }

    @Override
    public int getPreferencesCat() {
        return mSqlitePersist.getPreferencesCat();
    }

    @Override
    public int getPreferencesSubCat() {
        return mSqlitePersist.getPreferencesSubCat();
    }

    @Override
    public int getPreferencesOrg() {
        return mSqlitePersist.getPreferencesOrg();
    }

    @Override
    public void savePreferencesTit(String tit) {
        mSqlitePersist.savePreferencesTit(tit);

    }

    @Override
    public void savePreferencesCat(int cat) {
        mSqlitePersist.savePreferencesCat(cat);

    }

    @Override
    public void savePreferencesSubCat(int sub) {
        mSqlitePersist.savePreferencesSubCat(sub);

    }

    @Override
    public void savePreferencesOrg(int org) {
        mSqlitePersist.savePreferencesOrg(org);
    }

    @Override
    public void savePreferencesGps(boolean gps) {
        mSqlitePersist.savePreferencesGps(gps);
    }
}
