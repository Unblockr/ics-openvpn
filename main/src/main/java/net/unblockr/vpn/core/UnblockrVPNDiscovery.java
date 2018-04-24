/*
 * Copyright (c) 2012-2018 Arne Schwabe
 * Distributed under the GNU GPL v2 with additional terms. For full terms see the file doc/LICENSE.txt
 */

package net.unblockr.vpn.core;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UnblockrVPNDiscovery {

    private AsyncTask<String, Void, String> m_httpget;

    public static final String COUNTRIES_URL = "http://mhtest.unblockr.net/countries.json";
    public static final String CONFIGS_URL_BASE = "http://mhtest.unblockr.net/";

    public static final String countriesElement = "countries";
    public static final String countryElement = "country";
    public static final String configElement = "config";

    private ArrayList<String> m_configs;

    private JSONArray m_countriesArray;

    public ArrayList<String> get_countries() throws Exception{
        ArrayList<String> countries = new ArrayList<>();
        for (int i = 0; i < m_countriesArray.length(); i++) {
            countries.add(m_countriesArray.getJSONObject(i).getString(countryElement));
        }
        return countries;
    }

    public ArrayList<String> get_configs() throws Exception{
        ArrayList<String> configs = new ArrayList<>();
        for (int i = 0; i < m_countriesArray.length(); i++) {
            configs.add(m_countriesArray.getJSONObject(i).getString(configElement));
        }
        return configs;
    }

    public UnblockrVPNDiscovery() {
        this.m_httpget = new AsyncHttpGet();
    }

    public void discover() {
        startGetCountries();
    }

    private void startGetCountries() {
        try {
            String r = m_httpget.execute(COUNTRIES_URL).get();
            m_countriesArray = new JSONObject(r).getJSONArray(countriesElement);
        }
        catch (Exception e) {

        }
    }

    private void startGetConfigs() {
        try {
            ArrayList<String> configs = get_configs();
            for (int i = 0; i < m_countriesArray.length(); i++) {
                StringBuffer sb = new StringBuffer(CONFIGS_URL_BASE);
                sb.append(configs.get(i)).append(".conf");
                String r = m_httpget.execute(sb.toString()).get();
                //
                // TODO store the result somewhere
            }
        }
        catch (Exception e) {

        }

    }

}
