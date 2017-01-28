/*
 * Copyright 2017 ArtisTech, Inc.
 */
package com.artistech.ee.beans;

import com.artistech.utils.ExternalProcess;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author matta
 */
public class Data {
    
    public static final String INPUT_DIR = "input";
    public static final String CAMR_DATA_DIR = "camr_out";
    public static final String LIBERAL_EVENT_DATA_DIR = "liberal_event_io";
//    public static final String TEST_LIST = "test_list";
    public String dataDir = "";

    private Calendar last_use;
    private final String key;
    private ExternalProcess proc;

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

//    public String getTestList() {
//        return getPipelineDir() + File.separator + TEST_LIST;
//    }

//    public void setTestList(String value) {
//        map.put("test_list", value);
//    }

    public String getPipelineDir() {
        return dataDir;
    }

    public void setPipelineDir(String value) {
        dataDir = value + File.separator + key;
    }

    public String getInput() {
        return getPipelineDir() + File.separator + INPUT_DIR;
    }

//    public void setInput(String value) {
//        map.put("input", value);
//    }

    public String[] getInputFiles() {
        File f = new File(getInput());
        if (f.exists()) {
            return f.list();
        }
        return new String[]{};
    }

    public String getCamrOut() {
        return getPipelineDir() + File.separator + CAMR_DATA_DIR;
    }

//    public void setCamrOut(String value) {
//        map.put("camr", value);
//    }

    public String[] getCamrFiles() {
        File dir = new File(getCamrOut());
        if (dir.exists()) {
            return dir.list();
        }
        return new String[]{};
    }

    public String getLiberalEventOut() {
        return getPipelineDir() + File.separator + LIBERAL_EVENT_DATA_DIR;
    }

//    public void setLiberalEventOut(String value) {
//        map.put("liberal-event", value);
//    }

    public String[] getLiberalEventFiles() {
        File f = new File(getLiberalEventOut());
        if (f.exists()) {
            Collection<File> listFiles = FileUtils.listFiles(f, null, true);
            ArrayList<String> ret = new ArrayList<>();
            for (File file : listFiles) {
                ret.add(file.getAbsolutePath().replace(getLiberalEventOut() + File.separator, ""));
            }
            Collections.sort(ret);
            return ret.toArray(new String[]{});
        }
        return new String[]{};
    }

    public String[] getFiles(String key) {
        File f = new File(getData(key));
        if (f.exists() && f.isDirectory()) {
            Collection<File> listFiles = FileUtils.listFiles(f, null, true);
            ArrayList<String> ret = new ArrayList<>();
            for (File file : listFiles) {
                ret.add(file.getAbsolutePath().replace(getData(key) + File.separator, ""));
            }
            Collections.sort(ret);
            return ret.toArray(new String[]{});
        }
        return new String[]{};
    }

    public String getData(String key) {
        return getPipelineDir() + File.separator + key;
    }

    public String[] getKeys() {
        ArrayList<String> keys = new ArrayList<>();
        Field[] fields = Data.class.getFields();
        for(Field f : fields) {
            int modifiers = f.getModifiers();
            if((modifiers & (Modifier.STATIC | Modifier.FINAL)) ==
                    (Modifier.STATIC | Modifier.FINAL)) {
                try {
                    keys.add(f.get(null).toString());
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return keys.toArray(new String[]{});
    }

    public ExternalProcess getProc() {
        return proc;
    }

    public void setProc(ExternalProcess value) {
        proc = value;
    }
}
