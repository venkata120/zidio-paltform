<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<h2 class="text-center">Post a New Job</h2>

<form id="jobPostForm" class="border p-4 rounded bg-white shadow-sm" style="max-width: 700px; margin: auto;">

    <div class="mb-3">
        <label for="title" class="form-label">Job Title:</label>
        <input type="text" class="form-control" id="title" required />
    </div>

    <div class="mb-3">
        <label for="designation" class="form-label">Designation:</label>
        <input type="text" class="form-control" id="designation" required />
    </div>

    <div class="mb-3">
        <label for="skills" class="form-label">Skills (comma separated):</label>
        <input type="text" class="form-control" id="skills" placeholder="e.g. Java, Spring Boot, MySQL" />
    </div>

    <div class="mb-3">
        <label for="location" class="form-label">Location:</label>
        <input type="text" class="form-control" id="location" required />
    </div>

    <div class="mb-3">
        <label for="minRequirement" class="form-label">Minimum Requirements:</label>
        <textarea class="form-control" id="minRequirement" rows="3" required></textarea>
    </div>

    <div class="mb-3">
        <label for="jobType" class="form-label">Job Type:</label>
        <select class="form-select" id="jobType" required>
            <option value="">-- Select --</option>
            <option value="FRESHER">Fresher</option>
            <option value="INTERNSHIP">Internship</option>
            <option value="EXPERIENCED">Experienced</option>
        </select>
    </div>

    <button type="submit" class="btn btn-success w-100">Post Job</button>
</form>

<script>
document.getElementById("jobPostForm").addEventListener("submit", async function(e) {
    e.preventDefault();

    const token = localStorage.getItem("token");
   
    const job = {
        title: document.getElementById("title").value,
        designation: document.getElementById("designation").value,
        skills: document.getElementById("skills").value,
        location: document.getElementById("location").value,
        minRequirement: document.getElementById("minRequirement").value,
        jobType: document.getElementById("jobType").value
    };

    try {
        const res = await fetch("/api/job/post", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(job)
        });

        if (res.ok) {
            alert("✅ Job posted successfully!");
            window.location.href = "/recruiter/my-jobs";
        } else {
            const result = await res.json();
            alert("❌ Job post failed: " + (result.message || "Unknown error"));
        }
    } catch (error) {
        alert("❌ Network error: " + error);
    }
});
</script>

<%@ include file="footer.jsp" %>
