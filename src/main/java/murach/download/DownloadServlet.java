package murach.download;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import murach.business.User;
import murach.business.Product;
import murach.data.ProductIO;
import murach.data.UserIO;
import murach.util.CookieUtil;

import java.io.*;

public class DownloadServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewAlbums"; // default action
        }

        // perform action and set URL to appropriate page
        String url = "/index.jsp";
        if (action.equals("viewAlbums")) {
            url = "/index.jsp";
        } else if (action.equals("checkUser")) {
            url = checkUser(request, response);
        } else if (action.equals("viewCookies")) {
            url = "/view_cookies.jsp";
        } else if (action.equals("deleteCookies")) {
            url = this.deleteCookies(request,response);
        }

        // forward to the view
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");

        // perform action and set URL to appropriate page
        String url = "/index.jsp";
        if (action.equals("registerUser")) {
            url = registerUser(request, response);
        }

        // forward to the view
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String checkUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        ServletContext sc = this.getServletContext();

        // Get the path for products.txt
        String productPath = sc.getRealPath("WEB-INF/products.txt");

        // Get the product code from the request
        String productCode = request.getParameter("productCode");

        // Ensure the product code is not null or empty
        // Get the Product object for the current product
        Product product = ProductIO.getProduct(productCode, productPath);

        // Store the Product object in the session
        session.setAttribute("product", product);

        // Retrieve the user from the session
        User user = (User) session.getAttribute("user");
        String url;

        // If User object doesn't exist, check email cookie
        if (user == null) {
            Cookie[] cookies = request.getCookies();
            String emailAddress = CookieUtil.getCookieValue(cookies, "emailCookie");

            // If cookie doesn't exist, go to Registration page
            if (emailAddress == null || emailAddress.isEmpty()) {
                url = "/register.jsp";
            }
            // If cookie exists, create User object and go to Downloads page
            else {
                String path = sc.getRealPath("/WEB-INF/EmailList.txt");
                user = UserIO.getUser(emailAddress, path);

                // Ensure the user is found
                if (user == null) {
                    return "/error.jsp"; // Handle the scenario when the user is not found
                }

                // Store the User object in the session
                session.setAttribute("user", user);

                // Use product object to build the URL
                url = "/musicStore/sound/" + product.getCode() + "_download.jsp";
                System.out.println(product.getCode());
            }
        }
        // If User object exists, go to Downloads page
        else {
            // Use product object to build the URL
            url = "/musicStore/sound/" + product.getCode() + "_download.jsp";
            System.out.println(product.getCode());

        }

        return url;
    }


    private String registerUser(HttpServletRequest request, HttpServletResponse response) {
        // get the user data
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        // store the data in a User object
        User user = new User(email, firstName, lastName);  // Prefer immutable data structures

        // write the User object to a file
        ServletContext sc = getServletContext();
        String path = sc.getRealPath("/WEB-INF/EmailList.txt");
        UserIO.add(user, path);

        // store the User object as a session attribute
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        // add a cookie that stores the user's email to the browser
        Cookie c = new Cookie("emailCookie", email);
        c.setMaxAge(60 * 60 * 24 * 365 * 2); // set age to 2 years
        c.setPath("/"); // allow entire app to access it
        response.addCookie(c);

        Cookie firstNameCookie = new Cookie("firstName", firstName);
        firstNameCookie.setMaxAge(60 * 60 * 24 * 365 * 2);
        firstNameCookie.setPath("/");
        response.addCookie(firstNameCookie);

        // Get the Product object from the session
        Product product = (Product) session.getAttribute("product");

        // Get the product code and description
        String productCode = product.getCode();

        // create and return a URL for the appropriate Download page

        String url = "/musicStore/sound/" + productCode + "_download.jsp";
        System.out.println(product.getCode());

        return url;


    }
    private String deleteCookies(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0); //delete the cookie
            cookie.setPath("/"); //allow the download application to access it
            response.addCookie(cookie);
        }
        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("product");
        String url = "/delete_cookies.jsp";
        return url;
    }
}
