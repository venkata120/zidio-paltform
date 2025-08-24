<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<h2 class="text-center mt-3">Job Details</h2>

<div class="container mt-4">
    <div class="card shadow p-4">
        <!-- Success Notification -->
        <div id="notification" class="alert alert-success d-none" role="alert"></div>

        <!-- Job Details Section -->
        <div id="jobDetailsContainer"></div>

        <!-- Apply Button -->
        <div id="applySection" class="mt-4 text-center" style="display: none;">
            <button id="applyBtn" class="btn btn-success btn-lg">Apply Now</button>
        </div>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const token = localStorage.getItem("token");
        const urlParams = new URLSearchParams(window.location.search);
        const jobId = urlParams.get("jobId");

        if (!token) {
            alert("Please login first!");
            window.location.href = "/ui/login";
            return;
        }

        if (!jobId) {
            alert("Job ID is missing in URL!");
            return;
        }

        // Fetch job details
        fetch("/api/job/" + jobId, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token
            }
        })
        .then(function(response) {
            if (!response.ok) throw new Error("Failed to fetch job details");
            return response.json();
        })
        .then(function(job) {
            const container = document.getElementById("jobDetailsContainer");

            // Safe null checks
            var jobTitle = job.title ? job.title : "No Title";
            var jobDesignation = job.designation ? job.designation : "Not Specified";
            var jobLocation = job.location ? job.location : "Not Specified";
            var jobType = job.jobType ? job.jobType : "Not Specified";
            var minReq = job.minRequirement ? job.minRequirement : "Not Specified";

            // Build HTML
            var headingHTML = 
                "<div class='mb-4'>" +
                    "<h3><strong>" + jobTitle + "</strong></h3>" +
                    "<p><strong>Designation:</strong> " + jobDesignation + "</p>" +
                    "<p><strong>Location:</strong> " + jobLocation + "</p>" +
                    "<p><strong>Job Type:</strong> " + jobType + "</p>" +
                    "<p><strong>Minimum Requirement:</strong> " + minReq + "</p>" +
                "</div>";

            container.innerHTML = headingHTML;

            // Show Apply Button
            document.getElementById("applySection").style.display = "block";
        })
        .catch(function(error) {
            console.error("Error:", error);
            alert("Could not load job details.");
        });

        // Apply Button Handler
        document.getElementById("applyBtn").addEventListener("click", function () {
            fetch("/api/job/apply/" + jobId, {
                method: "POST",
                headers: {
                    "Authorization": "Bearer " + token
                }
            })
            .then(function(res) {
                if (!res.ok) return res.text().then(function(msg) { throw new Error(msg); });
                return res.text();
            })
            .then(function(msg) {
                const note = document.getElementById("notification");
                note.innerText = "Application submitted successfully! Please check your email for confirmation.";
                note.classList.remove("d-none");

                setTimeout(function () {
                    window.location.href = "/student/applications";
                }, 3000);
            })
            .catch(function(err) {
                alert("❌ Failed to apply: " + err.message);
            });
        });
    });
</script>

<%@ include file="footer.jsp" %>