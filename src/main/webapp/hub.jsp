<!--
 * Copyright 2017 ArtisTech, Inc.
-->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:useBean scope="request" class="com.artistech.ee.web.DataManager" id="dataBean" type="com.artistech.ee.web.DataManager">
        <jsp:setProperty name="dataBean" property="*" />
    </jsp:useBean>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Hub</title>
        <link rel='stylesheet' href='style.css' type='text/css'>
        <script type='text/javascript'>
            var pipeline_id = "<c:out value="${dataBean.pipeline_id}" />";
        </script>
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    </head>
    <body>
        <h1>Hub:</h1>
        Your pipeline_id is: <c:out value="${dataBean.pipeline_id}" />
        <br />
        <%--<c:out value="${fn:length(dataBean.data.inputFiles)}" />--%>
        <c:if test="${fn:length(dataBean.data.inputFiles) gt 0}">
            <ul>Input Files:
                <c:forEach var="inputFile" items="${dataBean.data.inputFiles}">
                    <li><a target="_blank" href="ViewRaw?stage=input&pipeline_id=<c:out value="${dataBean.pipeline_id}"/>&file=<c:out value="${inputFile}"/>"><c:out value="${inputFile}"/></a></li>
                    </c:forEach>
            </ul>
        </c:if>
        <c:if test="${fn:length(dataBean.data.camrFiles) gt 0}">
            <ul>CAMR Output Files:
                <c:forEach var="inputFile" items="${dataBean.data.camrFiles}">
                    <li><a target="_blank" href="ViewRaw?stage=camr&pipeline_id=<c:out value="${dataBean.pipeline_id}"/>&file=<c:out value="${inputFile}"/>"><c:out value="${inputFile}"/></a></li>
                    </c:forEach>
            </ul>
        </c:if>
        <c:if test="${fn:length(dataBean.data.liberalEventFiles) gt 0}">
            <ul>LiberalEvent Files:
                <c:forEach var="inputFile" items="${dataBean.data.liberalEventFiles}">
                    <li><a target="_blank" href="ViewRaw?stage=liberal-event&pipeline_id=<c:out value="${dataBean.pipeline_id}"/>&file=<c:out value="${inputFile}"/>"><c:out value="${inputFile}"/></a></li>
                    </c:forEach>
            </ul>
        </c:if>
        <ul>
            <c:if test="${fn:length(dataBean.data.camrFiles) eq 0}">
                <li><a href="camr.jsp?pipeline_id=<c:out value="${dataBean.pipeline_id}" />">camr</a></li>
                </c:if>
            <c:if test="${fn:length(dataBean.data.camrFiles) gt 0}">
                <li><a href="liberalEvent.jsp?pipeline_id=<c:out value="${dataBean.pipeline_id}" />">LiberalEvent</a></li>
                </c:if>
        </ul>
        <br />
    </body>
</html>
