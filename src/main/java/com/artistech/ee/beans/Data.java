/*
 * Copyright 2017 ArtisTech, Inc.
 */
package com.artistech.ee.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.io.FileUtils;

/**
 * Data bean class for the yellow pipeline.
 *
 * @author matta
 */
public class Data extends DataBase {

    public static final String CAMR_DATA_DIR = "camr_out";
    public static final String LIBERAL_EVENT_DATA_DIR = "liberal_event_io";

    /**
     * Constructor.
     *
     * @param key
     */
    public Data(String key) {
        super(key);
    }

    /**
     * Get the CAMR output directory.
     *
     * @return
     */
    public String getCamrOut() {
        return getPipelineDir() + File.separator + CAMR_DATA_DIR;
    }

    /**
     * Get the output files from the CAMR step.
     *
     * @return
     */
    public String[] getCamrFiles() {
        File dir = new File(getCamrOut());
        if (dir.exists()) {
            return dir.list();
        }
        return new String[]{};
    }

    /**
     * Get the liberal output directory.
     *
     * @return
     */
    public String getLiberalEventOut() {
        return getPipelineDir() + File.separator + LIBERAL_EVENT_DATA_DIR;
    }

    /**
     * Get the output from the liberal event step.
     *
     * @return
     */
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
