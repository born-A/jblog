<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<c:if test="${not empty list}">
		<c:set var="list" value="${list }" />
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
				
				<c:choose>
			<c:when test='${empty post }'>
				<h4>${list.get(list.size() - 1).getTitle() }</h4>
					<p>
						${list.get(list.size() - 1).getContents() }
					<p>
			</c:when>
				
			<c:otherwise>
    			<h4>${post.getTitle() }</h4>
					<p>
						${post.getContents() }
					<p>
			</c:otherwise>
		</c:choose>
				</div>
				<ul class="blog-list">
				<c:forEach var="item" items="${list}">
					<li><a href="${pageContext.request.contextPath}/${id }/${item.getCategoryNo() }/${item.getNo() }">${item.getTitle() }</a> <span>${item.getRegDate() }</span>	</li>
				</c:forEach> 
				</ul>
			</div>
		</div>
		</c:if>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath }${vo.getLogo() }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			
			<ul>
				<c:forEach var="category" items="${categoryList }">
					<li><a href="${pageContext.request.contextPath}/${id }/${category.getNo() }">${category.getName() }</a></li>
				</c:forEach> 
			</ul>
		</div>
		
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>