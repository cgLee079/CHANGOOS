<c:set var="thumbDir"><spring:eval expression="@location['photo.thumb.dir.url']"/></c:set>
<c:set var="tempDir"><spring:eval expression="@location['temp.photo.dir.url']"/></c:set>
<c:set var="originDir"><spring:eval expression="@location['photo.origin.dir.url']"/></c:set>
<script>
var thumbDir = '<c:out value="${thumbDir}"/>';
var tempDir = '<c:out value="${tempDir}"/>';
var originDir = '<c:out value="${originDir}"/>';
</script>