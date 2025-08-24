<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<h3 class="text-center mt-4">📋 All Registered Students</h3>

<div class="container mt-4">
    <c:if test="${empty students}">
        <p class="text-center text-muted">No students found.</p>
    </c:if>

    <c:if test="${not empty students}">
        <div class="table-responsive">
            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>#</th>
                        <th>Email</th>
                        <th>Education</th>
                        <th>Skills</th>
                        <th>Experience</th>
                        <th>DigiLocker</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="student" items="${students}" varStatus="loop">
                        <tr>
                            <td><c:out value="${loop.index + 1}"/></td>
                            <td><c:out value="${student.email}"/></td>
                            <td><c:out value="${student.education}"/></td>
                            <td><c:out value="${student.skills}"/></td>
                            <td><c:out value="${student.experienceLevel}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${student.digilockerVerified}">
                                        ✅ Verified
                                    </c:when>
                                    <c:otherwise>
                                        ❌ Not Verified
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="/admin/student-profile?email=${student.email}" class="btn btn-primary btn-sm">View Profile</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>

<%@ include file="footer.jsp" %>
