<c:set var="thumbDir"><spring:eval expression="@location['blog.thumb.dir.url']"/></c:set>
<c:set var="fileDir"><spring:eval expression="@location['blog.file.dir.url']"/></c:set>
<c:set var="imageDir"><spring:eval expression="@location['blog.image.dir.url']"/></c:set>
<c:set var="boardType"><spring:eval expression="@constant['board.type.id.blog']"/></c:set>
<script>
var imageDir = '<c:out value="${imageDir}"/>';
var thumbDir = '<c:out value="${thumbDir}"/>';
</script>