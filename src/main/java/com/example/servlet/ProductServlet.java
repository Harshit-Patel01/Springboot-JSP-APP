package com.example.servlet;

import com.example.dao.ProductDAO;
import com.example.model.Product;
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

@WebServlet("/products/*")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        productDAO = new ProductDAO();
        gson = new Gson();

        // Initialize database on startup
        productDAO.initializeDatabase();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // List all products
            List<Product> products = productDAO.getAllProducts();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/products.jsp").forward(request, response);
        } else {
            // Get single product by ID
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                Product product = productDAO.getProductById(id);

                if (product != null) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    out.print(gson.toJson(product));
                    out.flush();
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");

        if (name == null || name.trim().isEmpty() || priceStr == null || priceStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name and price are required");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);

            boolean success = productDAO.createProduct(product);

            if (success) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print(gson.toJson(product));
                out.flush();
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create product");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid price format");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            
            // Parse form-urlencoded body for PUT request
            Map<String, String> params = parseFormData(request);
            String name = params.get("name");
            String priceStr = params.get("price");

            if (name == null || name.trim().isEmpty() || priceStr == null || priceStr.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name and price are required");
                return;
            }

            double price = Double.parseDouble(priceStr);
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setPrice(price);

            boolean success = productDAO.updateProduct(product);

            if (success) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print(gson.toJson(product));
                out.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID or price format");
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
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            boolean success = productDAO.deleteProduct(id);

            if (success) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
        }
    }
}

