<c:set var="thumbDir"><spring:eval expression="@location['study.thumb.dir.url']"/></c:set>
<c:set var="fileDir"><spring:eval expression="@location['study.file.dir.url']"/></c:set>
<c:set var="imageDir"><spring:eval expression="@location['study.image.dir.url']"/></c:set>
<c:set var="boardType"><spring:eval expression="@constant['board.type.id.study']"/></c:set>
<script>
var thumbDir = '<c:out value="${thumbDir}"/>';
</script>