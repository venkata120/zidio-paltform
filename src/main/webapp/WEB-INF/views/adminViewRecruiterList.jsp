<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<h3 class="text-center mt-4">🏢 All Recruiters</h3>

<div class="container mt-4">
    <c:if test="${empty recruiters}">
        <p class="text-center text-muted">No recruiter profiles found.</p>
    </c:if>

    <c:if test="${not empty recruiters}">
        <div class="table-responsive">
            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>#</th>
                        <th>Company Name</th>
                        <th>Recruiter Name</th>
                        <th>Designation</th>
                        <th>Email</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="recruiter" items="${recruiters}" varStatus="loop">
                        <tr>
                            <td><c:out value="${loop.index + 1}" /></td>
                            <td><c:out value="${recruiter.companyName}" /></td>
                            <td><c:out value="${recruiter.recruiterName}" /></td>
                            <td><c:out value="${recruiter.designation}" /></td>
                            <td><c:out value="${recruiter.email}" /></td>
                            <td>
                                <a href='<c:url value="/admin/recruiter-profile"><c:param name="email" value="${recruiter.email}" /></c:url>' class="btn btn-sm btn-primary">
                                    View Profile
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>

<%@ include file="footer.jsp" %>
