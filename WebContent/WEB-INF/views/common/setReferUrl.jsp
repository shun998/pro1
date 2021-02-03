<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.zs.core.common.CommonBaseAction"%>
<%
 CommonBaseAction.setReferUrl(); 
 System.out.println("-----------referUrl-------"+session.getAttribute("_REFER_URL_"));
%>