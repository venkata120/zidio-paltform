</div> 

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
document.addEventListener("DOMContentLoaded", function () {
    const role = localStorage.getItem("role");
    const email = localStorage.getItem("email");

    const navLinks = document.getElementById("navDynamicLinks");
    navLinks.innerHTML = "";

    if (!role) {
        navLinks.innerHTML += `
            <li class="nav-item"><a class="nav-link" href="/ui/login">Login</a></li>
            <li class="nav-item"><a class="nav-link" href="/ui/Register">Register</a></li>
        `;
        return;
    }

    if (role === "STUDENT") {
        navLinks.innerHTML += `
            <li class="nav-item"><a class="nav-link" href="/student/jobs">Jobs</a></li>
            <li class="nav-item"><a class="nav-link" href="/student/applications">My Applications</a></li>
            <li class="nav-item"><a class="nav-link" href="/student/courses">Courses</a></li>
            <li class="nav-item"><a class="nav-link" href="/student/profile/form">My Profile</a></li>
            <li class="nav-item"><a class="nav-link" href="/jobRecommendations">Recommendations</a></li>
           
        `;
    } else if (role === "RECRUITER") {
        navLinks.innerHTML += `
            <li class="nav-item"><a class="nav-link" href="/recruiter/my-jobs">My Jobs</a></li>
            <li class="nav-item"><a class="nav-link" href="/recruiter/job/post">Post Job</a></li>
            <li class="nav-item"><a class="nav-link" href="/recruiter/profile/form">My Profile</a></li>
        `;
    } else if (role === "ADMIN") {
        navLinks.innerHTML += `
            <li class="nav-item"><a class="nav-link" href="/admin/users">Manage Users</a></li>
            <li class="nav-item"><a class="nav-link" href="/admin/students">Students</a></li>
            <li class="nav-item"><a class="nav-link" href="/admin/recruiters">Recruiters</a></li>
        `;
    }

    // Common links
    navLinks.innerHTML += `
    	<li class="nav-item"><a class="nav-link" href="/notifications">🔔</a></li>
        <li class="nav-item">
            <a class="nav-link text-danger" href="/ui/login" onclick="logout()">Logout</a>
        </li>
    `;
});

function logout() {
    localStorage.clear();
}
</script>

</body>
</html>
