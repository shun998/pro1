(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
})(jQuery);

//js  webcontext
function getContextPath(){
	var  webroot=document.location.href;
    webroot=webroot.substring(webroot.indexOf('//')+2,webroot.length);
 	webroot=webroot.substring(webroot.indexOf('/')+1,webroot.length);
    webroot=webroot.substring(0,webroot.indexOf('/'));
    var contextPath="/"+webroot;
    return contextPath;
    //alert(contextPath);
}

//CheckBox
function selectAll(formId, cbName) {
	var o=$("form[id='"+formId+"'] input:checkbox[name='"+cbName+"']");
	for(i=0;i<o.size();i++){
		o[i].checked=true;
	}
	
}
//CheckBox
function unSelectAll(formId, cbName) {
	
	var o=$("form[id='"+formId+"'] input:checkbox[name='"+cbName+"']");
	for(i=0;i<o.size();i++){
		o[i].checked=false;
	}
	
	
}


function validateCheckbox(formId,cbName,alt) {
	var val=$("form[id='"+formId+"'] input:checkbox[name='"+cbName+"']:checked").size();
	if(val==0){
    	showMsgModal("提示",alt);
    	return false;
    }else{
        return true;
    }
}
function validateRadio(formId,raName,alt) {
	var val=$("form[id='"+formId+"'] input:radio[name='"+raName+"']").is(":checked");
	if(val==false){
    	showMsgModal("提示",alt);
    	return false;
    }else{
        return true;
    }	
}

function validatePass(formId,tId,tId1,alt) {
	var val=$("form[id='"+formId+"'] input:password[id='"+tId+"']").val();
	var val1=$("form[id='"+formId+"'] input:password[id='"+tId1+"']").val();

	if(val==null||val1==null||val!=val1){
    	showMsgModal("提示",alt);
    	return false;
    }else{
        return true;
    }	
}

function validateRequired(formId,tId,alt) {
	var val=$("form[id='"+formId+"'] input[id='"+tId+"']").val();
	
	if(val==null||val==""){
    	showMsgModal("提示",alt);
    	return false;
    }else{
        return true;
    }	
}
function validateEmail(formId,tId,alt) {
	var mail=$("form[id='"+formId+"'] input[id='"+tId+"']").val();
	if(mail==null||mail=="") return true;	
	var myReg = /^[_a-z0-9]+@([_a-z0-9]+\.)+[a-z0-9]{2,3}$/;		
	
	if(mail.length>0&&myReg.test(mail)==false) {
		showMsgModal("提示",alt);
		return false;
	}else{
		return true;
	}
}
function showMsgModal(title,body){
	$("#msgTitle").html(title);
	$("#msgBody").html(body);
	$("#msgModal").modal();
}

String.prototype.escapeHTML = function () {
	return $('<div/>').text(this.toString()).html();
};
