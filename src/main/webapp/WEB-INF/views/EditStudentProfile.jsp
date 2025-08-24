<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<h2 class="text-center">✏️ Edit Your Profile</h2>

<div class="container mt-4">
    <form onsubmit="updateProfile(event)" enctype="multipart/form-data">
        <div class="mb-3">
            <label class="form-label">Experience:</label>
            <input type="text" class="form-control" id="experience" name="experience" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Education:</label>
            <input type="text" class="form-control" id="education" name="education" required>
        </div>

        <button type="submit" class="btn btn-primary">Update Profile</button>
    </form>
</div>

<script>
    async function loadProfile() {
    	const token = localStorage.getItem("token"); 

        if (!token) {
            alert("You must be logged in.");
            window.location.href = "/ui/login";
            return;
        }

        const response = await fetch("/api/student/profile", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (response.ok) {
            const profile = await response.json();
            document.getElementById("experience").value = profile.experience || "";
            document.getElementById("education").value = profile.education || "";
        } else {
            const error = await response.text();
            alert("Failed to load profile: " + error);
            console.error("Load error:", error);
        }
    }

    async function updateProfile(event) {
        event.preventDefault();

        const token = sessionStorage.getItem("token"); 

        const experience = document.getElementById("experience").value;
        const education = document.getElementById("education").value;

        const formData = new FormData();
        formData.append("experience", experience);
        formData.append("education", education);

        const response = await fetch("/api/student/profile", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token
            },
            body: formData
        });

        if (response.ok) {
            alert("Profile updated successfully!");
            window.location.href = "/Dashboard";
        } else {
            const error = await response.text();
            alert("Failed to update profile: " + error);
            console.error("Update error:", error);
        }
    }

    window.onload = loadProfile;
</script>

<%@ include file="footer.jsp" %>
