<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<!-- 🔔 Page Heading -->
<h2 class="text-center mt-4">🔔 Your Notifications</h2>

<!-- 📩 Notification List -->
<div class="container mt-4" style="max-width: 800px;">
    <ul class="list-group" id="notificationList">
        <li class="list-group-item text-center">🔄 Loading notifications...</li>
    </ul>
</div>

<!-- 🔧 Script to Fetch and Display Notifications -->
<script>
document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem("token");
    const list = document.getElementById("notificationList");

    if (!token) {
        list.innerHTML = '<li class="list-group-item text-danger text-center">⚠️ Session expired. Please log in again.</li>';
        return;
    }

    fetch("/api/notifications", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(function(response) {
        return response.json();
    })
    .then(function(data) {
        list.innerHTML = "";

        if (!data || data.length === 0) {
            list.innerHTML = '<li class="list-group-item text-center">ℹ️ No notifications found.</li>';
            return;
        }

        for (var i = 0; i < data.length; i++) {
            var notification = data[i];
            console.log("📨 Notification:", notification); // Debug log

            var li = document.createElement("li");
            li.className = "list-group-item d-flex justify-content-between align-items-center";

            var subject = notification.subject || "📢 No Subject";
            var message = notification.message || "No content";

            var time = "Unknown";
            if (notification.sentAt) {
                try {
                    time = new Date(notification.sentAt).toLocaleString();
                } catch (e) {
                    console.warn("Invalid sentAt format:", notification.sentAt);
                }
            }

            li.innerHTML =
                '<div>' +
                    '<strong>' + subject + '</strong><br/>' +
                    '<small>' + message + '</small>' +
                '</div>' +
                '<span class="badge bg-secondary">' + time + '</span>';

            list.appendChild(li);
        }
    })
    .catch(function(error) {
        console.error("❌ Error loading notifications:", error);
        list.innerHTML = '<li class="list-group-item text-danger text-center">❌ Failed to load notifications.</li>';
    });
});
</script>

<%@ include file="footer.jsp" %>
