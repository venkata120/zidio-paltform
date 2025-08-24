<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<h2 class="text-center mt-4">👤 User Management (Admin Only)</h2>

<div class="container mt-4 mb-5">
    <div class="table-responsive">
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>User ID</th>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Role</th>
                    <th>Change Role</th>
                    <th>View Profile</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody id="userTableBody">
                <!-- Populated by JavaScript -->
            </tbody>
        </table>
    </div>
</div>

<script>
const token = localStorage.getItem("token");
const role = localStorage.getItem("role");

if (!token || role !== "ADMIN") {
    alert("Access denied. Only ADMIN can access this page.");
    window.location.href = "/ui/login";
}

async function loadUsers() {
    const tbody = document.getElementById("userTableBody");
    tbody.innerHTML = "<tr><td colspan='7'>Loading...</td></tr>";

    try {
        const response = await fetch("/api/auth/users/all", {
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (!response.ok) {
            throw new Error("Access denied or server error");
        }

        const users = await response.json();
        tbody.innerHTML = "";

        if (users.length === 0) {
            tbody.innerHTML = "<tr><td colspan='7' class='text-center text-warning'>No users found.</td></tr>";
            return;
        }

        users.forEach(function(user) {
            const row = document.createElement("tr");

            const roleOptions =
                '<option value="">-- Change Role --</option>' +
                '<option value="STUDENT">STUDENT</option>' +
                '<option value="RECRUITER">RECRUITER</option>' +
                '<option value="ADMIN">ADMIN</option>';

            let viewButton = "N/A";
            if (user.role === "STUDENT" || user.role === "RECRUITER") {
                viewButton = '<button class="btn btn-sm btn-info" onclick="viewProfile(\'' + user.role + '\', \'' + user.email + '\')">View</button>';
            }

            row.innerHTML =
                '<td>' + user.userId + '</td>' +
                '<td>' + user.email + '</td>' +
                '<td>' + user.name + '</td>' +
                '<td>' + user.role + '</td>' +
                '<td>' +
                    '<select class="form-select form-select-sm" onchange="changeRole(\'' + user.userId + '\', this.value)">' +
                        roleOptions +
                    '</select>' +
                '</td>' +
                '<td>' + viewButton + '</td>' +
                '<td>' +
                    '<button class="btn btn-sm btn-danger" onclick="deleteUser(\'' + user.userId + '\')">Delete</button>' +
                '</td>';

            tbody.appendChild(row);
        });
    } catch (error) {
        tbody.innerHTML = '<tr><td colspan="7" class="text-center text-danger">Failed to load users: ' + error.message + '</td></tr>';
    }
}

function viewProfile(role, email) {
    if (role === "STUDENT") {
        window.location.href = "/admin/student-profile?email=" + encodeURIComponent(email);
    } else if (role === "RECRUITER") {
        window.location.href = "/admin/recruiter-profile?email=" + encodeURIComponent(email);
    } else {
        alert("Only student or recruiter profiles can be viewed.");
    }
}

async function changeRole(userId, newRole) {
    if (!newRole) return;

    try {
        const res = await fetch("/api/auth/users/" + userId + "/role", {
            method: "PATCH",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ role: newRole })
        });

        if (res.ok) {
            alert("✅ Role updated.");
            loadUsers();
        } else {
            alert("Failed to update role.");
        }
    } catch (err) {
        alert("Error: " + err.message);
    }
}

async function deleteUser(userId) {
    if (!confirm("Are you sure you want to delete this user?")) return;

    try {
        const res = await fetch("/api/auth/users/" + userId, {
            method: "DELETE",
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (res.ok) {
            alert("✅ User deleted.");
            loadUsers();
        } else {
            alert("Failed to delete user.");
        }
    } catch (err) {
        alert("Error: " + err.message);
    }
}

document.addEventListener("DOMContentLoaded", loadUsers);
</script>

<%@ include file="footer.jsp" %>
