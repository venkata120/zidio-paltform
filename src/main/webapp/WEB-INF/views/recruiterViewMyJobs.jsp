<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<h2 class="text-center mt-4">📋 My Job Posts</h2>

<div class="container mt-4 mb-5">
    <div class="table-responsive">
        <table class="table table-bordered table-hover">
            <thead class="table-dark">
               <tr>
                    <th style="width: 15%">Title</th>
                    <th style="width: 30%">Designation</th>
                    <th style="width: 15%">skills</th>
                    <th style="width: 15%">Location</th>
                    <th style="width: 10%">Job Type</th>
                    <th style="width: 20%">Actions</th>
                </tr>
            </thead>
            <tbody id="myJobsBody" class="text-wrap text-start"></tbody>
        </table>
    </div>
</div>

<style>
    td {
        white-space: pre-wrap; /* allows newlines */
        word-break: break-word; /* wraps long words */
    }
</style>
<script>
const token = localStorage.getItem("token");

async function loadMyJobs() {
    const response = await fetch("/api/job/my-jobs", {
        headers: {
        	
            "Authorization": "Bearer " + token
        }
    });

    const tbody = document.getElementById("myJobsBody");
    tbody.innerHTML = "";

    if (response.ok) {
        const data = await response.json();

        data.forEach(job => {
            if (!job || !job.jobId) return;

            const jobId = job.jobId;
            const title = job.title || "-";
            const designation = job.designation || "-";
            const skills      = job.skills || "-";
            const location = job.location || "-";
            const jobType = job.jobType || "-";

            let row = tbody.insertRow();


            let col1 = row.insertCell(0);
            col1.innerHTML = title;

            let col2 = row.insertCell(1);
            col2.innerHTML = designation;
             
           let col3 = row.insertCell(2);
            col3.innerHTML = skills;


            let col4 = row.insertCell(3);
            col4.innerHTML = location;

            let col5 = row.insertCell(4);
            col5.innerHTML = jobType;

            let col6 = row.insertCell(5);

            const editLink = document.createElement('a');
            editLink.href = "/recruiter/job/edit/" + jobId;  
            editLink.className = "btn btn-sm btn-primary me-2";
            editLink.innerText = "Edit";

            const deleteBtn = document.createElement('button');
            deleteBtn.className = "btn btn-sm btn-danger";
            deleteBtn.innerText = "Delete";
            deleteBtn.onclick = function () {
                deleteJob(jobId);
            };

            col6.appendChild(editLink);
            col6.appendChild(deleteBtn);
        });

    } else {
        tbody.innerHTML = `<tr><td colspan="6" class="text-center text-danger">Failed to load jobs.</td></tr>`;
    }
}

async function deleteJob(jobId) {
    if (confirm("Are you sure you want to delete this job?")) {
        const response = await fetch("/api/job/" + jobId, {
            method: "DELETE",
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (response.ok) {
            alert("✅ Job deleted successfully.");
            loadMyJobs();
        } else {
            alert("❌ Failed to delete job.");
        }
    }
}

document.addEventListener("DOMContentLoaded", loadMyJobs);
</script>

<%@ include file="footer.jsp" %>
