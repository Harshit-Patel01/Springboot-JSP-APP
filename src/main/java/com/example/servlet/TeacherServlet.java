package com.example.servlet;

import com.example.dao.TeacherDAO;
import com.example.model.Teacher;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/teachers/*")
public class TeacherServlet extends HttpServlet {
    private TeacherDAO teacherDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        teacherDAO = new TeacherDAO();
        gson = new Gson();

        // Initialize database on startup
        teacherDAO.initializeDatabase();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // List all teachers
            List<Teacher> teachers = teacherDAO.getAllTeachers();
            request.setAttribute("teachers", teachers);
            request.getRequestDispatcher("/teachers.jsp").forward(request, response);
        } else {
            // Get single teacher by ID
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                Teacher teacher = teacherDAO.getTeacherById(id);

                if (teacher != null) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    out.print(gson.toJson(teacher));
                    out.flush();
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Teacher not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid teacher ID");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String subject = request.getParameter("subject");

        if (name == null || name.trim().isEmpty() || subject == null || subject.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name and subject are required");
            return;
        }

        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setSubject(subject);

        boolean success = teacherDAO.createTeacher(teacher);

        if (success) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(teacher));
            out.flush();
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create teacher");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Teacher ID is required");
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            
            // Parse form-urlencoded body for PUT request
            Map<String, String> params = parseFormData(request);
            String name = params.get("name");
            String subject = params.get("subject");

            if (name == null || name.trim().isEmpty() || subject == null || subject.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name and subject are required");
                return;
            }

            Teacher teacher = new Teacher();
            teacher.setId(id);
            teacher.setName(name);
            teacher.setSubject(subject);

            boolean success = teacherDAO.updateTeacher(teacher);

            if (success) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print(gson.toJson(teacher));
                out.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Teacher not found");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid teacher ID");
        }
    }
    
    /**
     * Parse form-urlencoded request body for PUT requests.
     * Servlets don't automatically parse form data for PUT/DELETE requests.
     */
    private Map<String, String> parseFormData(HttpServletRequest request) throws IOException {
        Map<String, String> params = new HashMap<>();
        StringBuilder body = new StringBuilder();
        
        try (BufferedReader reader = request.getReader()) {
            reader.lines().forEach(body::append);
        }
        
        String bodyStr = body.toString();
        if (!bodyStr.isEmpty()) {
            for (String pair : bodyStr.split("&")) {
                String[] keyValue = pair.split("=", 2);
                if (keyValue.length == 2) {
                    String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8.name());
                    String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.name());
                    params.put(key, value);
                }
            }
        }
        
        return params;
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Teacher ID is required");
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            boolean success = teacherDAO.deleteTeacher(id);

            if (success) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Teacher not found");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid teacher ID");
        }
    }
}