<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<!-- 🔐 Session Validation -->
<script>
window.onload = function () {
    
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");


   

    if (!token || !role) {
        alert("Session expired. Please login again.");
        window.location.href = "/ui/login";  
        return;
    }

    // Load role-based menu
    const menu = document.getElementById("roleBasedMenu");
    if (role === "STUDENT") {
        menu.innerHTML = `
            <a href="/student/profile/form" class="list-group-item list-group-item-action">📄 Complete / View Profile</a>
            <a href="/student/jobs" class="list-group-item list-group-item-action">💼 Browse All Jobs</a>
            <a href="/jobRecommendations" class="list-group-item list-group-item-action">🎯 Recommended Jobs</a>

            <a href="/student/courses" class="list-group-item list-group-item-action">📚 My Courses</a>
            
        `;
    } else if (role === "RECRUITER") {
        menu.innerHTML = `
            <a href="/recruiter/profile/form" class="list-group-item list-group-item-action">👨‍💼 Create / Edit Recruiter Profile</a>
            <a href="/recruiter/job/post" class="list-group-item list-group-item-action">📝 Post a New Job</a>
            <a href="/recruiter/my-jobs" class="list-group-item list-group-item-action">📋 View My Job Posts</a>
        `;
    } else if (role === "ADMIN") {
        menu.innerHTML = `
            <a href="/admin/users" class="list-group-item list-group-item-action">🛠 Manage Users</a>
            <a href="/admin/students" class="list-group-item list-group-item-action">🎓 View Student Profiles</a>
            <a href="/admin/recruiters" class="list-group-item list-group-item-action">🧑‍💼 View Recruiter Profiles</a>
        `;
    } else {
        menu.innerHTML = `<div class="alert alert-warning">Unknown role. Please login again.</div>`;
    }
};
</script>

<!-- ✅ Page Heading -->
<h2 class="text-center mt-4">Welcome to Zidio Job Portal Dashboard</h2>

<!-- 📊 Summary Boxes -->
<div class="row text-center mt-4">
    <div class="col-md-4">
        <div class="card bg-primary text-white">
            <div class="card-body">
                <h5>Applied Jobs</h5>
                <h2>3</h2>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card bg-success text-white">
            <div class="card-body">
                <h5>Subscribed Courses</h5>
                <h2>2</h2>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card bg-warning text-white">
            <div class="card-body">
                <h5>Recommended Jobs</h5>
                <h2>5</h2>
            </div>
        </div>
    </div>
</div>

<div class="text-end me-4 mt-2">
    <a href="/notifications" class="btn btn-sm btn-outline-dark">🔔 View All Notifications</a>
</div>

<!-- 📢 Dynamic Notification Section -->
<div id="latestNotification" class="alert alert-info text-center mt-4" role="alert">
    🔄 Loading latest notification...
</div>


<!-- 📈 Chart Section -->
<div class="row mt-5">
    <div class="col-md-6 offset-md-3">
        <canvas id="jobTypeChart"></canvas>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
const ctx = document.getElementById('jobTypeChart').getContext('2d');
new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['Fresher', 'Internship', 'Experienced'],
        datasets: [{
            label: 'Recommended Jobs',
            data: [4, 1, 2],
            backgroundColor: ['#0d6efd', '#ffc107', '#198754']
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: { display: false }
        },
        scales: {
            y: { beginAtZero: true }
        }
    }
});
</script>

<!-- 📋 Role-based Menu Section -->
<div class="row justify-content-center mt-5">
    <div class="col-md-6">
        <div class="list-group" id="roleBasedMenu"></div>
    </div>
</div>

<%@ include file="footer.jsp" %>
