<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<h2 class="text-center mt-4">Register on Zidio Job Portal</h2>

<div class="container mt-4" style="max-width: 600px;">
    <form id="registerForm" class="border p-4 rounded bg-white shadow-sm">
        <!-- Full Name -->
        <div class="mb-3">
            <label for="name" class="form-label">Full Name:</label>
            <input type="text" class="form-control" id="name" required />
        </div>

        <!-- Email -->
        <div class="mb-3">
            <label for="email" class="form-label">Email address:</label>
            <input type="email" class="form-control" id="email" required />
        </div>

        <!-- Password -->
        <div class="mb-3">
            <label for="password" class="form-label">Password:</label>
            <input type="password" class="form-control" id="password" required />
        </div>

        <!-- Role -->
        <div class="mb-3">
            <label for="role" class="form-label">Select Role:</label>
            <select class="form-select" id="role" required>
                <option value="">-- Select Role --</option>
                <option value="STUDENT">Student</option>
                <option value="RECRUITER">Recruiter</option>
                <option value="ADMIN">Admin</option>
            </select>
        </div>

        <!-- Error Message -->
        <div id="registerError" class="alert alert-danger d-none" role="alert"></div>

        <!-- Submit Button -->
        <button type="submit" class="btn btn-success w-100">Register</button>
    </form>
</div>

<script>
document.getElementById("registerForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const role = document.getElementById("role").value;
    const errorBox = document.getElementById("registerError");

    errorBox.classList.add("d-none");

    if (!name || !email || !password || !role) {
        errorBox.textContent = "Please fill all fields.";
        errorBox.classList.remove("d-none");
        return;
    }

    const payload = { name, email, password, role };

    try {
        const res = await fetch("/api/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        const data = await res.json().catch(() => ({}));

        if (res.ok) {
            alert("Registration successful. You can now log in.");
            window.location.href = "/ui/login"; 
        } else {
            errorBox.textContent = data.message || "Registration failed.";
            errorBox.classList.remove("d-none");
        }
    } catch (err) {
        errorBox.textContent = "Something went wrong. Please try again.";
        errorBox.classList.remove("d-none");
    }
});
</script>

<%@ include file="footer.jsp" %>
