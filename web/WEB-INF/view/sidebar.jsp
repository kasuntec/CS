<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul class="sidebar-nav">
       <c:forEach var="accessPage" items="${accessPageList}">
        <c:if test="${accessPage.catergory!='Reports' && accessPage.catergory!='Master'}">
            <li>
                <a href="/CreativeEdge/${accessPage.url}"><i class="fa ${accessPage.icon} fa-1x " ></i> ${accessPage.pageName}</a>
            </li>
        </c:if>
    </c:forEach>

</ul>
