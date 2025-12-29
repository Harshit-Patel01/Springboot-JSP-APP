<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Teacher Management</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background-color: #f5f5f5;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            color: #333;
        }

        header {
            background-color: #ffffff;
            padding: 1rem 2rem;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border-bottom: 1px solid #ddd;
        }

        nav {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-size: 1.5rem;
            font-weight: bold;
            color: #333;
        }

        .nav-links {
            display: flex;
            gap: 1.5rem;
        }

        .nav-links a {
            color: #333;
            text-decoration: none;
            font-weight: 500;
            padding: 0.5rem 1rem;
            border-radius: 4px;
            transition: background-color 0.2s;
        }

        .nav-links a:hover {
            background-color: #e9e9e9;
        }

        .main-container {
            flex: 1;
            width: 100%;
            padding: 20px 40px;
            max-width: 100%;
        }

        h1 {
            color: #333;
            margin-top: 20px;
            margin-bottom: 20px;
            font-size: 2rem;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            background-color: #ffffff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
            color: #333;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #f0f0f0;
        }

        form {
            background-color: #ffffff;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 4px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border: 1px solid #ddd;
        }

        h2 {
            color: #333;
            margin-bottom: 15px;
            border-bottom: 1px solid #eee;
            padding-bottom: 10px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: 500;
            color: #555;
        }

        input[type="text"] {
            padding: 10px;
            margin-bottom: 15px;
            width: 100%;
            max-width: 400px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }

        input[type="text"]:focus {
            outline: none;
            border-color: #333;
            box-shadow: 0 0 0 2px rgba(51, 51, 51, 0.1);
        }

        button {
            padding: 10px 20px;
            background-color: #333;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.2s;
        }

        button:hover {
            background-color: #555;
        }

        .action-btn {
            padding: 8px 15px;
            margin: 0 5px;
            font-size: 13px;
        }

        .edit-btn {
            background-color: #2196F3;
        }

        .edit-btn:hover {
            background-color: #0b7dda;
        }

        .delete-btn {
            background-color: #f44336;
        }

        .delete-btn:hover {
            background-color: #d32f2f;
        }

        .error {
            color: #f44336;
            margin: 10px 0;
            font-size: 14px;
        }

        /* Modal styles */
        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 1000;
        }

        .modal.active {
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .modal-content {
            background-color: white;
            margin: 10% auto;
            padding: 30px;
            width: 50%;
            max-width: 500px;
            border-radius: 4px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
            position: relative;
        }

        .close-btn {
            position: absolute;
            top: 15px;
            right: 15px;
            font-size: 24px;
            font-weight: bold;
            color: #aaa;
            cursor: pointer;
            background: none;
            border: none;
            padding: 0;
            line-height: 1;
        }

        .close-btn:hover {
            color: #333;
        }

        @media (max-width: 768px) {
            .main-container {
                padding: 15px 20px;
            }

            .modal-content {
                width: 90%;
                margin: 20px auto;
            }

            input[type="text"] {
                max-width: 100%;
            }

            h1 {
                font-size: 1.5rem;
            }
        }
    </style>
</head>
<body>
    <header>
        <nav>
            <div class="logo">Teacher Management</div>
            <div class="nav-links">
                <a href="/">Home</a>
                <a href="/teachers">Teachers</a>
            </div>
        </nav>
    </header>

    <div class="main-container">
        <h1>Teacher Management</h1>

        <!-- Add Teacher Form -->
        <form id="addTeacherForm">
            <h2>Add New Teacher</h2>
            <div>
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>
            </div>
            <div>
                <label for="subject">Subject:</label>
                <input type="text" id="subject" name="subject" required>
            </div>
            <button type="submit">Add Teacher</button>
            <div id="error" class="error"></div>
        </form>

        <!-- Teacher List -->
        <h2>Teacher List</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Subject</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${teachers}" var="teacher">
                    <tr id="teacher-${teacher.id}">
                        <td>${teacher.id}</td>
                        <td>${teacher.name}</td>
                        <td>${teacher.subject}</td>
                        <td>
                            <button class="action-btn edit-btn" onclick="editTeacher(${teacher.id})">Edit</button>
                            <button class="action-btn delete-btn" onclick="deleteTeacher(${teacher.id})">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Edit Teacher Modal -->
        <div id="editModal" class="modal">
            <div class="modal-content">
                <button type="button" class="close-btn" onclick="closeEditModal()">×</button>
                <h2>Edit Teacher</h2>
                <form id="editTeacherForm">
                    <input type="hidden" id="editId" name="id">
                    <div>
                        <label for="editName">Name:</label>
                        <input type="text" id="editName" name="name" required>
                    </div>
                    <div>
                        <label for="editSubject">Subject:</label>
                        <input type="text" id="editSubject" name="subject" required>
                    </div>
                    <div style="margin-top: 20px; display: flex; gap: 10px;">
                        <button type="submit" style="flex: 1;">Save Changes</button>
                        <button type="button" onclick="closeEditModal()" style="flex: 1; background-color: #ccc; color: #333;">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        // Add Teacher
        document.getElementById('addTeacherForm').addEventListener('submit', function(e) {
            e.preventDefault();

            const name = document.getElementById('name').value.trim();
            const subject = document.getElementById('subject').value.trim();
            const errorElement = document.getElementById('error');

            // Client-side validation
            if (!name || !subject) {
                errorElement.textContent = 'Name and subject are required';
                return;
            }

            fetch('/teachers', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'name=' + encodeURIComponent(name) + '&subject=' + encodeURIComponent(subject)
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => { throw new Error(text) });
                }
                return response.json();
            })
            .then(teacher => {
                location.reload(); // Refresh the page to show the new teacher
            })
            .catch(error => {
                errorElement.textContent = error.message;
            });
        });

        // Edit Teacher
        function editTeacher(id) {
            // Get teacher details
            fetch('/teachers/' + id)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Failed to fetch teacher');
                    }
                    return response.json();
                })
                .then(teacher => {
                    // Populate edit form
                    document.getElementById('editId').value = teacher.id;
                    document.getElementById('editName').value = teacher.name;
                    document.getElementById('editSubject').value = teacher.subject;

                    // Show modal
                    const modal = document.getElementById('editModal');
                    modal.classList.add('active');
                })
                .catch(error => {
                    alert(error.message);
                });
        }

        // Save Edited Teacher
        document.getElementById('editTeacherForm').addEventListener('submit', function(e) {
            e.preventDefault();

            const id = document.getElementById('editId').value;
            const name = document.getElementById('editName').value.trim();
            const subject = document.getElementById('editSubject').value.trim();

            // Client-side validation for edit form
            if (!name || !subject) {
                alert('Name and subject are required');
                return;
            }

            fetch('/teachers/' + id, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'name=' + encodeURIComponent(name) + '&subject=' + encodeURIComponent(subject)
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => { throw new Error(text) });
                }
                return response.json();
            })
            .then(teacher => {
                location.reload(); // Refresh the page
            })
            .catch(error => {
                alert(error.message);
            });
        });

        // Delete Teacher
        function deleteTeacher(id) {
            if (confirm('Are you sure you want to delete this teacher?')) {
                fetch('/teachers/' + id, {
                    method: 'DELETE'
                })
                .then(response => {
                    if (!response.ok) {
                        return response.text().then(text => { throw new Error(text) });
                    }
                    location.reload(); // Refresh the page
                })
                .catch(error => {
                    alert(error.message);
                });
            }
        }

        // Close Edit Modal
        function closeEditModal() {
            const modal = document.getElementById('editModal');
            modal.classList.remove('active');
        }
    </script>
</body>
</html>

