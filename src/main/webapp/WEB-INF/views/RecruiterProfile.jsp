<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<h2 class="text-center">Recruiter Profile</h2>

<div class="container mt-4" style="max-width: 600px;">
    <form id="recruiterProfileForm" class="border p-4 rounded bg-white shadow-sm">

        <div class="mb-3">
            <label for="companyName" class="form-label">Company Name:</label>
            <input type="text" class="form-control" id="companyName" name="companyName"
                   value="${recruiter.companyName}" <c:if test="${not empty recruiter}">readonly</c:if> required />
        </div>

        <div class="mb-3">
            <label for="recruiterName" class="form-label">Recruiter Name:</label>
            <input type="text" class="form-control" id="recruiterName" name="recruiterName"
                   value="${recruiter.recruiterName}" <c:if test="${not empty recruiter}">readonly</c:if> required />
        </div>

        <div class="mb-3">
            <label for="designation" class="form-label">Designation:</label>
            <input type="text" class="form-control" id="designation" name="designation"
                   value="${recruiter.designation}" <c:if test="${not empty recruiter}">readonly</c:if> required />
        </div>

        <c:if test="${empty recruiter}">
            <button type="submit" class="btn btn-primary w-100">Save Profile</button>
        </c:if>
    </form>
</div>

<script>
document.addEventListener("DOMContentLoaded", async () => {
    const token = localStorage.getItem("token");

    // Only load via API if recruiter model attribute is not present (i.e. recruiter self-view)
    const recruiterExists = "<c:out value='${not empty recruiter}' />";
    if (recruiterExists !== 'true') {
        try {
            const res = await fetch("/api/recruiter/profile", {
                headers: { "Authorization": "Bearer " + token }
            });

            if (res.ok) {
                const data = await res.json();
                document.getElementById("companyName").value = data.companyName || "";
                document.getElementById("recruiterName").value = data.recruiterName || "";
                document.getElementById("designation").value = data.designation || "";
            }
        } catch (err) {
            console.warn("Recruiter profile not found.");
        }
    }
});

document.getElementById("recruiterProfileForm").addEventListener("submit", async function(e) {
    e.preventDefault();
    const token = localStorage.getItem("token");

    const payload = {
        companyName: document.getElementById("companyName").value,
        recruiterName: document.getElementById("recruiterName").value,
        designation: document.getElementById("designation").value
    };

    const res = await fetch("/api/recruiter/profile", {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
    });

    if (res.ok) {
        alert("Profile saved successfully!");
        window.location.href = "/Dashboard";
    } else {
        const error = await res.json();
        alert("Error: " + (error.message || "Failed to save profile"));
    }
});
</script>

<%@ include file="footer.jsp" %>
