<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type='text/javascript'>
	
	console.log('${CKEditorFuncNum}');
	console.log('${path}');
	window.parent.CKEDITOR.tools.callFunction('${CKEditorFuncNum}', '${path}', '파일 전송 완료.');
</script>