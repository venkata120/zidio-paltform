<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<h2 class="text-center">Upload Your Resume</h2>

<div class="container mt-4" style="max-width: 600px;">
    <form id="resumeForm" class="border p-4 rounded bg-white shadow-sm" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="resumeFile" class="form-label">Select Resume (PDF):</label>
            <input type="file" class="form-control" id="resumeFile" accept="application/pdf" required />
        </div>
        <button type="submit" class="btn btn-success w-100">Upload Resume</button>
    </form>

    <!-- Resume actions -->
    <div id="resumeActions" class="mt-3 text-center" style="display:none;">
        <a id="viewResumeBtn" class="btn btn-primary me-2" target="_blank">View Resume</a>
        <button id="deleteResumeBtn" class="btn btn-danger">Delete Resume</button>
    </div>
</div>

<script>
const token = localStorage.getItem("token");
const resumeActions = document.getElementById("resumeActions");
const viewResumeBtn = document.getElementById("viewResumeBtn");
const deleteResumeBtn = document.getElementById("deleteResumeBtn");

// Load existing resume if available
async function loadResume() {
    try {
        const res = await fetch("/api/file/resume/mine", {
            method: "GET",
            headers: { "Authorization": "Bearer " + token }
        });
        if (res.ok) {
            const data = await res.json();
            viewResumeBtn.href = data.url; // backend already returns correct path
            resumeActions.style.display = "block";
        } else {
            resumeActions.style.display = "none";
        }
    } catch (err) {
        console.error("Error loading resume:", err);
    }
}

document.getElementById("resumeForm").addEventListener("submit", async function(e) {
    e.preventDefault();
    const fileInput = document.getElementById("resumeFile");
    const file = fileInput.files[0];

    if (!file || file.type !== "application/pdf") {
        alert("Please upload a valid PDF file.");
        return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
        const res = await fetch("/api/file/upload", {
            method: "POST",
            headers: { "Authorization": "Bearer " + token },
            body: formData
        });

        const data = await res.json();

        if (res.ok) {
            alert("Resume uploaded successfully!");
            viewResumeBtn.href = data.url;
            resumeActions.style.display = "block";
        } else {
            alert(data.message || "Upload failed");
        }
    } catch (error) {
        console.error("Upload error:", error);
        alert("An error occurred while uploading the resume.");
    }
});

// Delete resume
deleteResumeBtn.addEventListener("click", async function() {
    if (!confirm("Are you sure you want to delete your resume?")) return;

    try {
        const res = await fetch("/api/file/delete", {
            method: "DELETE",
            headers: { "Authorization": "Bearer " + token }
        });

        const msg = await res.text();
        alert(msg);
        resumeActions.style.display = "none";
    } catch (err) {
        console.error("Delete error:", err);
        alert("Error deleting resume.");
    }
});

// Load resume on page start
loadResume();
</script>

<%@ include file="footer.jsp" %>
