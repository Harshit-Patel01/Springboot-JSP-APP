<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Teacher Management System</title>
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

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 40px 20px;
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        .welcome-card {
            background-color: #ffffff;
            padding: 60px 40px;
            border-radius: 4px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border: 1px solid #ddd;
            text-align: center;
            max-width: 600px;
            width: 100%;
        }

        h1 {
            color: #333;
            margin-bottom: 20px;
            font-size: 2.5rem;
        }

        p {
            color: #555;
            font-size: 1.1rem;
            margin-bottom: 40px;
            line-height: 1.6;
        }

        .btn {
            display: inline-block;
            padding: 12px 30px;
            background-color: #333;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.2s;
            border: none;
        }

        .btn:hover {
            background-color: #555;
        }

        @media (max-width: 768px) {
            .container {
                padding: 20px;
            }

            .welcome-card {
                padding: 40px 20px;
            }

            h1 {
                font-size: 2rem;
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

    <div class="container">
        <div class="welcome-card">
            <h1>Welcome to Teacher Management System</h1>
            <p>Manage your teachers with ease. Create, update, and delete teachers with a simple and intuitive interface.</p>
            <a href="/teachers" class="btn">View Teachers</a>
        </div>
    </div>
</body>
</html>
