<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<h2 class="text-center mt-4">Reset Your Password</h2>

<div class="container">
    <form id="resetForm" class="border p-4 rounded bg-white shadow-sm" style="max-width: 500px; margin: auto;">
        <div class="mb-3">
            <label for="email">Email:</label>
            <input type="email" class="form-control" id="email" required />
        </div>

        <div class="mb-3">
            <label for="otp">OTP:</label>
            <input type="text" class="form-control" id="otp" required />
        </div>

        <div class="mb-3">
            <label for="newPassword">New Password:</label>
            <input type="password" class="form-control" id="newPassword" required />
        </div>

        <button type="submit" class="btn btn-success w-100">Reset Password</button>
    </form>
</div>

<script>
document.getElementById("resetForm").addEventListener("submit", async function(e) {
    e.preventDefault();

    const payload = {
        email: document.getElementById("email").value.trim(),
        otp: document.getElementById("otp").value.trim(),  
        newPassword: document.getElementById("newPassword").value.trim()
    };

    try {
        const res = await fetch("/api/auth/reset-password", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        const data = await res.text();
        if (res.ok) {
            alert("✅ Password reset successfully. Please log in.");
            window.location.href = "/ui/login";
        } else {
            alert("❌ Error: " + data);
        }
    } catch (error) {
        alert("❌ Something went wrong: " + error.message);
    }
});
</script>

<%@ include file="footer.jsp" %>
