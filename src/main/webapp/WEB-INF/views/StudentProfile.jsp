<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<h2 class="text-center mt-4">👨‍🎓 Student Profile</h2>

<div class="container mt-4" style="max-width: 600px;">
    <form id="studentProfileForm" class="border p-4 rounded bg-white shadow-sm">

        <!-- Resume URL -->
        <div class="mb-3">
            <label for="resumeUrl" class="form-label">Resume URL:</label>
            <input type="url" class="form-control" id="resumeUrl" name="resumeUrl"
                   value="<c:out value='${student.resumeUrl}'/>" readonly />
            <div class="form-text">
                Upload your resume on the
                <a href="/student/resume/upload">Resume Upload</a> page.
            </div>
        </div>

        <!-- Education -->
        <div class="mb-3">
            <label for="education" class="form-label">Education:</label>
            <input type="text" class="form-control" id="education" name="education"
                   value="<c:out value='${student.education}'/>"
                   <c:if test="${not empty student}">readonly</c:if> />
        </div>

        <!-- Skills -->
        <div class="mb-3">
            <label for="skills" class="form-label">Skills (comma separated):</label>
            <input type="text" class="form-control" id="skills" name="skills"
                   value="<c:out value='${student.skills}'/>"
                   <c:if test="${not empty student}">readonly</c:if> />
        </div>

        <!-- Experience Level -->
        <div class="mb-3">
            <label for="experienceLevel" class="form-label">Experience Level:</label>
            <select class="form-select" id="experienceLevel" name="experienceLevel"
                    <c:if test="${not empty student}">disabled</c:if>>
                <option value="">-- Select --</option>
                <option value="FRESHER" <c:if test="${student.experienceLevel eq 'FRESHER'}">selected</c:if>>Fresher</option>
                <option value="INTERNSHIP" <c:if test="${student.experienceLevel eq 'INTERNSHIP'}">selected</c:if>>Internship</option>
                <option value="EXPERIENCED" <c:if test="${student.experienceLevel eq 'EXPERIENCED'}">selected</c:if>>Experienced</option>
            </select>
        </div>

      <!-- DigiLocker Verification -->
<div class="mb-3">
    <label class="form-label">DigiLocker Verification:</label>
    <button type="button" class="btn btn-outline-secondary w-100"
            onclick="triggerDigiLocker()"
            <c:if test="${not empty student}">disabled</c:if>>
        🔐 Verify with DigiLocker
    </button>
    <div id="digilockerStatus" class="form-text mt-1 text-success">
        <!-- JavaScript will populate status -->
    </div>
</div>



        <!-- Save Button only for Student -->
        <c:if test="${empty student}">
            <button type="submit" class="btn btn-primary w-100">💾 Save Profile</button>
        </c:if>

    </form>
</div>

<!-- JavaScript only for students -->
<c:if test="${empty student}">
<script>
document.addEventListener("DOMContentLoaded", async () => {
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");

    if (!token || role !== "STUDENT") {
        alert("Access denied. Please login as a student.");
        window.location.href = "/ui/login";
        return;
    }

    try {
        const res = await fetch("/api/student/profile", {
            headers: { "Authorization": "Bearer " + token }
        });

        if (res.ok) {
            const data = await res.json();
            document.getElementById("resumeUrl").value = data.resumeUrl || "";
            document.getElementById("education").value = data.education || "";
            document.getElementById("skills").value = data.skills || "";
            document.getElementById("experienceLevel").value = data.experienceLevel || "";
        }

        const digilockerRes = await fetch("/api/student/verification-status", {
            headers: { "Authorization": "Bearer " + token }
        });

        if (digilockerRes.ok) {
            const digilockerData = await digilockerRes.json();
            if (digilockerData.digilockerVerified) {
                document.getElementById("digilockerStatus").textContent = "✅ DigiLocker Verified";
            }
        }
    } catch (err) {
        console.error("Error loading student profile:", err);
    }
});

function triggerDigiLocker() {
    const email = localStorage.getItem("email");

    if (!email) {
        alert("Session expired. Please login again.");
        return;
    }

    window.location.href = "/api/auth/digilocker/start?email=" + encodeURIComponent(email);
}

document.getElementById("studentProfileForm").addEventListener("submit", async function (e) {
    e.preventDefault();
    const token = localStorage.getItem("token");

    if (!token) {
        alert("Session expired. Please log in again.");
        window.location.href = "/ui/login";
        return;
    }

    const body = {
        resumeUrl: document.getElementById("resumeUrl").value,
        education: document.getElementById("education").value,
        skills: document.getElementById("skills").value,
        experienceLevel: document.getElementById("experienceLevel").value
    };

    try {
        const res = await fetch("/api/student/profile", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        });

        const result = await res.json();

        if (res.ok) {
            alert("✅ Profile saved successfully!");
            window.location.href = "/Dashboard";
        } else {
            alert("❌ Error: " + (result.message || "Failed to save profile."));
        }
    } catch (err) {
        alert("⚠ Unexpected error. Try again.");
        console.error(err);
    }
});
</script>
</c:if>

<%@ include file="footer.jsp" %>