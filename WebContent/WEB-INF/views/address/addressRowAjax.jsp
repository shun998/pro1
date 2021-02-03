<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tr id="${a.addrId}">
	<td class="radiocheck">
		<div class="radio">
		  <label>
		    <input type="radio" name="address" id="address" value="${fn:escapeXml(a.addrProvince)} ${fn:escapeXml(a.addrCity)} ${fn:escapeXml(a.addrArea)} ${fn:escapeXml(a.addrContent)} (${fn:escapeXml(a.addrReceiver)}收) ${fn:escapeXml(a.addrTel)}" ${a.addrIsdefault==1?'checked':'' }>
		   
		  </label>
		</div>						
	</td>
	<td>${fn:escapeXml(a.addrProvince)} ${fn:escapeXml(a.addrCity)} ${fn:escapeXml(a.addrArea)}</td><td>${fn:escapeXml(a.addrContent)}</td>
	<td>(${fn:escapeXml(a.addrReceiver)}收) ${fn:escapeXml(a.addrTel)} </td>
	<td class="isDefault">${a.addrIsdefault==1?'默认地址':'' }</td>
	<td>
	 	<a href="#" onclick="handleAddressForm(${a.addrId})">修改</a> | 
	 	<a href="#" onclick="if(confirm('确定要删除这个收货地址吗？')) delAddress(${a.addrId});">删除</a>	| 
	 	<a href="#" onclick="setDefault(${a.addrId},event)">设为默认地址</a>				
	</td>
</tr>
		
