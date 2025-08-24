<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<h2 class="text-center mt-3">Recommended Jobs</h2>

<div class="container mt-4">
    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>Title</th>
                    <th>Designation</th>
                    <th>Location</th>
                    <th>Job Type</th>
                    <th>View</th>
                </tr>
            </thead>
            <tbody id="jobRecommendationBody">
                <!-- Job rows will be inserted here -->
            </tbody>
        </table>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
    	const token = localStorage.getItem("token");

        if (!token) {
            alert("Please log in to access recommended jobs.");
            window.location.href = "/ui/login";
            return;
        }

        fetch("/api/job/recommend", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token
            }
        })
        
       
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to load recommended jobs.");
            }
            return response.json();
        })
        .then(data => {
            const tbody = document.getElementById("jobRecommendationBody");
            tbody.innerHTML = "";

            if (!Array.isArray(data) || data.length === 0) {
                tbody.innerHTML = `<tr><td colspan="5" class="text-center text-muted">No recommended jobs found.</td></tr>`;
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
                
             
                 let newLink = document.createElement('a');
                newLink.href ="/student/job/view?jobId="+job.jobId; 
                
                newLink.innerHTML="view";
                
                let colomn5=row.insertCell(4);
                colomn5.appendChild(newLink);
   
                
            });
        })
        .catch(error => {
            console.error("Error loading jobs:", error);
            alert("Could not load recommended jobs. Please try again later.");
        });
    });
</script>


<%@ include file="footer.jsp" %>


