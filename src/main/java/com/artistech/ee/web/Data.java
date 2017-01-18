/*
 * Copyright 2017 ArtisTech, Inc.
 */
package com.artistech.ee.web;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

/**
 *
 * @author matta
 */
public class Data {

    private Calendar last_use;
    private final String key;
    private final HashMap<String, String> map = new HashMap<>();

    public Data(String key) {
        this.key = key;
        last_use = Calendar.getInstance();
    }

    /**
     * Set when the Data was last accessed.
     *
     * @return
     */
    public Calendar updateLastUse() {
        last_use = Calendar.getInstance();
        return getLastUse();
    }

    /**
     * Get when the data was last accessed.
     *
     * @return
     */
    public Calendar getLastUse() {
        return (Calendar) last_use.clone();
    }

    public String getKey() {
        return key;
    }

    public String getTestList() {
        return map.get("test_list");
    }

    public void setTestList(String value) {
        map.put("test_list", value);
    }

    public String getPipelineDir() {
        return map.get("pipeline_dir");
    }

    public void setPipelineDir(String value) {
        map.put("pipeline_dir", value);
    }

    public String getInput() {
        return map.get("input");
    }

    public void setInput(String value) {
        map.put("input", value);
    }

    public String[] getInputFiles() {
        if (map.containsKey("input")) {
            File f = new File(map.get("input"));
            return f.list();
        }
        return new String[]{};
    }

    public String getCamrOut() {
        return map.get("camr");
    }

    public void setCamrOut(String value) {
        map.put("camr", value);
    }

    public String[] getCamrFiles() {
        if (map.containsKey("camr")) {
            File f = new File(map.get("camr"));
            return f.list();
        }
        return new String[]{};
    }

    public String getData(String key) {
        return map.get(key);
    }

    public String[] getFiles(String key) {
        File f = new File(map.get(key));
        return f.list();
    }

    public String[] getKeys() {
        return map.keySet().toArray(new String[]{});
    }
}
