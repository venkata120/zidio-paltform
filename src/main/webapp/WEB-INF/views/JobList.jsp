<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<div class="container mt-4">
    <h2 class="text-center mb-4">📄 All Available Jobs</h2>

    <table class="table table-bordered table-striped">
        <thead class="table-dark">
            <tr>
                <th>Title</th>
                <th>Designation</th>
                <th>Location</th>
                <th>Job Type</th>
                <th>Minimum Requirement</th>
                
            </tr>
        </thead>
        <tbody id="jobListBody">
            <!-- JS will insert rows here -->
        </tbody>
    </table>
</div>

<script>
document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem("token");

    if (!token) {
        alert("Session expired. Please login again.");
        window.location.href = "/ui/login";
        return;
    }

    fetch("/api/job/all", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to load jobs");
        }
        return response.json();
    })
    .then(data => {
        const tbody = document.getElementById("jobListBody");
        tbody.innerHTML = "";

        if (data.length === 0) {
            tbody.innerHTML = `<tr><td colspan="6" class="text-center">No jobs available</td></tr>`;
            return;
        }

        console.log(data);
        
        data.forEach(job => {
            if (!job || !job.jobId) return;

            const jobId = job.jobId;
            const title = job.title ?? "-";
            const designation = job.designation ?? "-";
            const location = job.location ?? "-";
            const jobType = job.jobType ?? "-";
            const minRequirement = job.minRequirement ?? "-";

            console.log("Job object:", job);
            let row=tbody.insertRow();
            let colomn1=row.insertCell(0);
            colomn1.innerHTML=title;
            
            let colomn2=row.insertCell(1);
            colomn2.innerHTML=designation;
            
            let colomn3=row.insertCell(2);
            colomn3.innerHTML=location;
            
            let colomn4=row.insertCell(3);
            colomn4.innerHTML=jobType;

            let colomn5=row.insertCell(4);
            colomn5.innerHTML=minRequirement;
            
         
             let newLink = document.createElement('a');
            newLink.href ="/student/job/view?jobId="+job.jobId; 
            
           
        });
    })
    .catch(error => {
        console.error("Error fetching jobs:", error);
        const tbody = document.getElementById("jobListBody");
        tbody.innerHTML = `<tr><td colspan="6" class="text-danger text-center">Failed to load jobs</td></tr>`;
    });
});
</script>

<%@ include file="footer.jsp" %>
