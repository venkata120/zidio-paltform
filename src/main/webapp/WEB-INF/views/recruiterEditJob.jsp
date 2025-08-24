<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<h2 class="text-center">✏️ Edit Job Post</h2>

<div class="container mt-4 mb-5">
    <form id="editJobForm">
        <div class="mb-3">
            <label for="title" class="form-label">Job Title</label>
            <input type="text" class="form-control" id="title" required />
        </div>

        <div class="mb-3">
            <label for="designation" class="form-label">Designation</label>
            <input type="text" class="form-control" id="designation" required />
        </div>

        <div class="mb-3">
            <label for="skills" class="form-label">Skills</label>
            <input type="text" class="form-control" id="skills" placeholder="e.g. Java, Spring Boot, MySQL" />
        </div>

        <div class="mb-3">
            <label for="location" class="form-label">Location</label>
            <input type="text" class="form-control" id="location" required />
        </div>

        <div class="mb-3">
            <label for="minRequirement" class="form-label">Minimum Requirements</label>
            <textarea class="form-control" id="minRequirement" rows="3" required></textarea>
        </div>

        <div class="mb-3">
            <label for="jobType" class="form-label">Experience Level</label>
            <select class="form-control" id="jobType" required>
                <option value="FRESHER">Fresher</option>
                <option value="INTERNSHIP">Internship</option>
                <option value="EXPERIENCED">Experienced</option>
            </select>
        </div>
      
        <button type="submit" class="btn btn-success">Update Job</button>
    </form>
</div>

<script>
    const token = localStorage.getItem("token");
    const path = window.location.pathname;
    const jobId = path.substring(path.lastIndexOf("/") + 1);

    async function loadJobDetails() {
        try {
            const response = await fetch("/api/job/" + jobId, {
                headers: { "Authorization": "Bearer " + token }
            });

            if (response.ok) {
                const job = await response.json();
                fillForm(job);
            } else {
                alert("❌ Failed to load job details.");
            }
        } catch (error) {
            alert("❌ Network error while loading job details: " + error);
        }
    }

    function fillForm(job) {
        document.getElementById("title").value = job.title || "";
        document.getElementById("designation").value = job.designation || "";
        document.getElementById("skills").value = job.skills || "";
        document.getElementById("location").value = job.location || "";
        document.getElementById("minRequirement").value = job.minRequirement || "";
        document.getElementById("jobType").value = job.jobType || "FRESHER";
    }

    document.getElementById("editJobForm").addEventListener("submit", async function (e) {
        e.preventDefault();

        const updatedJob = {
            jobId: jobId, 
            title: document.getElementById("title").value,
            designation: document.getElementById("designation").value,
            skills: document.getElementById("skills").value,
            location: document.getElementById("location").value,
            minRequirement: document.getElementById("minRequirement").value,
            jobType: document.getElementById("jobType").value
        };

        try {
            const response = await fetch("/api/job/" + jobId, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + token
                },
                body: JSON.stringify(updatedJob)
            });

            if (response.ok) {
                alert("✅ Job updated successfully.");
                window.location.href = "/recruiter/my-jobs";
            } else {
                const err = await response.text();
                alert("❌ Update failed: " + err);
            }
        } catch (error) {
            alert("❌ Network error while updating job: " + error);
        }
    });

    document.addEventListener("DOMContentLoaded", loadJobDetails);
</script>

<%@ include file="footer.jsp" %>
