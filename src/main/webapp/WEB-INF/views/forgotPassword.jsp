<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<h2 class="text-center mt-4">Forgot Password</h2>

<form id="forgotForm" class="border p-4 rounded bg-white shadow-sm" style="max-width: 500px; margin: auto;">
    <div class="mb-3">
        <label class="form-label">Email address:</label>
        <input type="email" class="form-control" id="email" required />
    </div>
    <button type="submit" class="btn btn-primary w-100">Send Reset Link / OTP</button>
</form>

<script>
document.getElementById("forgotForm").addEventListener("submit", async function(e) {
    e.preventDefault();
    const email = document.getElementById("email").value.trim();

    const res = await fetch("/api/auth/forgot-password", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email })
    });

    const data = await res.text();
    if (res.ok) {
        alert("✅ Password reset link or OTP sent to your email.");
        window.location.href = "/ui/reset-password";
    } else {
        alert("❌ Error: " + data);
    }
});
</script>

<%@ include file="footer.jsp" %>
