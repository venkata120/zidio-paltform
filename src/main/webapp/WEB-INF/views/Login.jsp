<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<h2 class="text-center mt-4">Login to Zidio Job Portal</h2>

<div class="container mt-4" style="max-width: 500px;">
    <form id="loginForm" class="border p-4 rounded bg-white shadow-sm">
        <div class="mb-3">
            <label for="email" class="form-label">Email address:</label>
            <input type="email" class="form-control" id="email" name="email" required />
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Password:</label>
            <input type="password" class="form-control" id="password" name="password" required />
        </div>

        <div id="loginError" class="alert alert-danger d-none" role="alert"></div>

        <button type="submit" class="btn btn-primary w-100">Login</button>

        <div class="text-center mt-3">
            <a href="/ui/forgot-password">Forgot Password?</a>
        </div>
    </form>
</div>

<script>
document.getElementById("loginForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const errorBox = document.getElementById("loginError");
    errorBox.classList.add("d-none");

    if (!email || !password) {
        errorBox.textContent = "Please enter both email and password.";
        errorBox.classList.remove("d-none");
        return;
    }

    const payload = { email, password };

    try {
        const res = await fetch("/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        const data = await res.json();

        if (res.ok && data.token) {
            localStorage.setItem("token", data.token);
            localStorage.setItem("email", data.email);
            localStorage.setItem("role", data.role);

            // ✅ Redirect based on role
            if (data.role === "STUDENT") {
                window.location.href = "/student/profile/form";
            } else if (data.role === "RECRUITER") {
                window.location.href = "/recruiter/profile/form";
            } else if (data.role === "ADMIN") {
                window.location.href = "/admin/users";
            } else {
                window.location.href = "/Dashboard"; // fallback
            }

        } else {
            errorBox.textContent = data.message || "Login failed. Please try again.";
            errorBox.classList.remove("d-none");
        }

    } catch (error) {
        errorBox.textContent = "An error occurred during login. Please try again.";
        errorBox.classList.remove("d-none");
        console.error("Login error:", error);
    }
});
</script>

<%@ include file="footer.jsp" %>
