/*
 * Copyright 2017 ArtisTech, Inc.
 */
package com.artistech.ee.yellow;

import com.artistech.ee.beans.Data;
import com.artistech.ee.beans.DataManager;
import com.artistech.ee.beans.PipelineBean;
import com.artistech.utils.ExternalProcess;
import com.artistech.utils.StreamGobbler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * Run the LiberalEvent step.
 *
 * @author matta
 */
public class LiberalEvent extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part pipeline_id_part = request.getPart("pipeline_id");
        String pipeline_id = IOUtils.toString(pipeline_id_part.getInputStream(), "UTF-8");
        Data data = (Data) DataManager.getData(pipeline_id);
        String liberal_event_out = data.getLiberalEventOut();
        File output_dir = new File(liberal_event_out);
        output_dir.mkdirs();
        String[] dirs = new String[]{"AMRNodeEdge", "AMRNodeEdgeSystem", "AMRParsingHuman", "AMRParsingSystem", "Cluster"};
        for (String dir : dirs) {
            File d = new File(liberal_event_out + File.separator + dir);
            d.mkdirs();
        }

        String[] camrFiles = data.getCamrFiles();
        String aligned_file = "";
        for (String res : camrFiles) {
            if (res.endsWith(".aligned")) {
                aligned_file = res;
                break;
            }
        }

        ArrayList<PipelineBean.Part> currentParts = data.getPipelineParts();
        PipelineBean.Part get = currentParts.get(data.getPipelineIndex());

        final String liberal_event_path = get.getParameter("path") != null ? get.getParameter("path").getValue() : getInitParameter("path");
        final String classpath = get.getParameter("classpath") != null ? get.getParameter("classpath").getValue() : getInitParameter("classpath");
        final String script = get.getParameter("script") != null ? get.getParameter("script").getValue() : getInitParameter("script");
        final String minCluster = get.getParameter("min_cluster").getValue();// != null ? get.getParameter("script").getValue() : getInitParameter("script");
        final String maxCluster = get.getParameter("max_cluster").getValue();// != null ? get.getParameter("script").getValue() : getInitParameter("script");

        FileUtils.copyFile(new File(data.getCamrOut() + File.separator + aligned_file), new File(liberal_event_out + File.separator + "AMRParsingSystem" + File.separator + aligned_file));

        ProcessBuilder pb = new ProcessBuilder("java", "-Xmx10g", "-cp", classpath, "bsh.Interpreter", script, liberal_event_out, minCluster, maxCluster);
        pb.directory(new File(liberal_event_path));
        //catch output...
        pb.redirectErrorStream(true);
        Process proc = pb.start();
        OutputStream os = new FileOutputStream(new File(data.getConsoleFile()), true);
        StreamGobbler sg = new StreamGobbler(proc.getInputStream(), os);
        sg.write("LiberalEvent");
        StringBuilder sb = new StringBuilder();
        for (String cmd : pb.command()) {
            sb.append(cmd).append(" ");
        }
        sg.write(sb.toString().trim());
        sg.start();
        ExternalProcess ex_proc = new ExternalProcess(sg, proc);
        data.setProc(ex_proc);

        // displays done.jsp page after upload finished
        getServletContext().getRequestDispatcher("/watchProcess.jsp").forward(
                request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Run LiberalEvent Step";
    }// </editor-fold>

}
