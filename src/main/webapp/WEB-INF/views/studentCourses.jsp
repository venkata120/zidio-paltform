<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<div class="container mt-4">
    <h3>📚 My Subscribed Courses</h3>
    <table class="table table-bordered">
        <thead class="table-success">
            <tr>
                <th>Course Name</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody id="subscribedCoursesTable">
            <tr><td colspan="4" class="text-center">Loading...</td></tr>
        </tbody>
    </table>

    <hr>

    <h3>🎓 All Available Courses</h3>
    <table class="table table-bordered table-striped">
        <thead class="table-primary">
            <tr>
                <th>Name</th>
                <th>Type</th>
                <th>Duration (Months)</th>
                <th>Price</th>
                <th>Description</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody id="availableCoursesTable">
            <tr><td colspan="6" class="text-center">Loading...</td></tr>
        </tbody>
    </table>
</div>

<script>
    const token = localStorage.getItem("token");

    // === Load My Subscriptions ===
    fetch("/api/courses/my-subscriptions", {
        headers: { "Authorization": "Bearer " + token }
    })
    .then(res => res.json())
    .then(data => {
        const tbody = document.getElementById("subscribedCoursesTable");
        tbody.innerHTML = "";

        if (data.length === 0) {
            let row = tbody.insertRow();
            let cell = row.insertCell(0);
            cell.colSpan = 4;
            cell.className = "text-center";
            cell.innerText = "No subscriptions yet";
        } else {
            data.forEach(sub => {
                let row = tbody.insertRow();
                row.insertCell(0).innerText = sub.courseName;
                row.insertCell(1).innerText = sub.startDate;
                row.insertCell(2).innerText = sub.endDate;
                row.insertCell(3).innerText = sub.active ? "Active" : "Inactive";
            });
        }
    })
    .catch(err => {
        console.error("Error fetching subscriptions:", err);
        alert("Failed to load subscriptions.");
    });

    // === Load Available Courses ===
    fetch("/api/courses/all", {
        headers: { "Authorization": "Bearer " + token }
    })
    .then(res => res.json())
    .then(data => {
        const tbody = document.getElementById("availableCoursesTable");
        tbody.innerHTML = "";

        data.forEach(course => {
            const { id, name, type, durationMonths, price, description } = course;
            let row = tbody.insertRow();

            row.insertCell(0).innerText = name ?? "-";
            row.insertCell(1).innerText = type ?? "-";
            row.insertCell(2).innerText = durationMonths ?? "-";
            row.insertCell(3).innerText = price ?? "-";  
            row.insertCell(4).innerText = description ?? "-";

            // Create Subscribe Link
            let subscribeLink = document.createElement('a');
            subscribeLink.href = "#";
            subscribeLink.innerHTML = "Subscribe";
            subscribeLink.className = "btn btn-primary btn-sm";
            subscribeLink.onclick = function () {
                if (confirm("Are you sure you want to subscribe to this course?")) {
                    fetch("/api/courses/subscribe/" + id, {
                        method: "POST",
                        headers: {
                            "Authorization": "Bearer " + token
                        }
                    })
                    .then(res => {
                        if (!res.ok) throw new Error("Subscription failed");
                        return res.text();
                    })
                    .then(message => {
                        alert(message);
                        location.reload();
                    })
                    .catch(err => {
                        console.error("Error:", err);
                        alert("Error subscribing to course.");
                    });
                }
            };

            let actionCell = row.insertCell(5);
            actionCell.appendChild(subscribeLink);
        });
    })
    .catch(err => {
        console.error("Error loading courses:", err);
        alert("Failed to load available courses.");
    });
</script>

<%@ include file="footer.jsp" %>
