/*
 * Copyright 2017 ArtisTech, Inc.
 */
package com.artistech.ee.beans;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author matta
 */
public class Data extends DataBase {

    public static final String CAMR_DATA_DIR = "camr_out";
    public static final String LIBERAL_EVENT_DATA_DIR = "liberal_event_io";

    public Data(String key) {
        super(key);
    }
    
    public String getCamrOut() {
        return getPipelineDir() + File.separator + CAMR_DATA_DIR;
    }

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
}
