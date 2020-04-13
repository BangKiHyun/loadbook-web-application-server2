package next.controller;

import core.db.DataBase;
import next.model.User;
import next.util.UserSessionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = {"users/login", "users/loginForm"})
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("user/login.jsp", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = DataBase.findUserById(userId);

        if (user == null || !user.matchPassword(password)) {
            req.setAttribute("loginFailed", true);
            forward("/users/login.jsp", req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
        resp.sendRedirect("/");
    }

    private void forward(String forwardUrl, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher(forwardUrl);
        rd.forward(req, resp);
    }
}
