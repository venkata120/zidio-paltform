<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Zidio Job Portal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />

    <style>
        body {
            margin: 0;
            padding-top: 60px;
            background-color: #f8f9fa;
        }
        .navbar-brand {
            font-weight: bold;
        }
        .role-badge {
            font-size: 0.8rem;
            margin-left: 10px;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="/Dashboard">Zidio Job Portal</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav" id="navbarUser">
                <!-- JS will populate -->
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <!-- Body content goes here -->
</div>

<script>
document.addEventListener("DOMContentLoaded", function () {
    const name = localStorage.getItem("email"); // or use localStorage.getItem("name") if available
    const role = localStorage.getItem("role");

    const navbar = document.getElementById("navbarUser");
    navbar.innerHTML = "";

    if (name && role) {
        navbar.innerHTML = `
            <li class="nav-item">
                <span class="navbar-text text-white">Welcome, ${name}</span>
                <span class="badge bg-info role-badge">${role}</span>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="#" onclick="logout()">Logout</a>
            </li>
        `;
    } else {
        navbar.innerHTML = `
            <li class="nav-item">
                <a class="nav-link" href="/ui/login">Login</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/ui/Register">Register</a>
            </li>
        `;
    }
});

function logout() {
    localStorage.clear();
    window.location.href = "/ui/login";
}
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
