<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<div class="container mt-5">
    <h2 class="mb-4">📄 My Job Applications</h2>

    <div id="errorAlert" class="alert alert-danger d-none" role="alert">
        ❌ Failed to load job applications.
    </div>

    <table class="table table-bordered" id="applicationList">
        <thead class="table-dark">
            <tr>
                <th>Job Title</th>
                <th>Company</th>
                <th>Applied Date</th>
                <th>Status</th>
                <th>Reapply Status</th>
            </tr>
        </thead>
        <tbody id="applicationTableBody">
            <!-- Data will be injected via JavaScript -->
        </tbody>
    </table>
</div>

<script>
    document.addEventListener("DOMContentLoaded", async function () {
        const token = localStorage.getItem("token");

        if (!token) {
            window.location.href = "/ui/login";
            return;
        }

        try {
            const res = await fetch("/api/student/applications", {
                headers: {
                    "Authorization": "Bearer " + token
                }
            });

            if (!res.ok) throw new Error("Failed to fetch");

            const applications = await res.json();
            const tableBody = document.getElementById("applicationTableBody");
            tableBody.innerHTML = "";

            if (applications.length === 0) {
                const row = tableBody.insertRow();
                const cell = row.insertCell(0);
                cell.colSpan = 5;
                cell.innerHTML = "<em>No applications found.</em>";
                return;
            }

            applications.forEach(app => {
                const row = tableBody.insertRow();
                row.insertCell(0).textContent = app.jobTitle;
                row.insertCell(1).textContent = app.companyName;
                row.insertCell(2).textContent = app.appliedDate;

                const statusCell = row.insertCell(3);
                statusCell.textContent = app.status;
                statusCell.className = app.status === "SELECTED"
                    ? "text-success fw-bold"
                    : "text-danger fw-bold";

                const reapplyCell = row.insertCell(4);
                if (app.canReapply) {
                    reapplyCell.innerHTML = "<span class='text-success'>✅ Eligible</span>";
                } else {
                    reapplyCell.innerHTML = "<span class='text-warning'> Not eligible for 3 months</span>";
                }
            });

        } catch (err) {
            console.error("Error loading applications:", err);
            document.getElementById("errorAlert").classList.remove("d-none");
        }
    });
</script>

<%@ include file="footer.jsp" %>