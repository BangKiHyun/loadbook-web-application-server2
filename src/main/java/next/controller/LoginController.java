package next.controller;

import core.db.DataBase;
import next.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("users/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = DataBase.findUserById(req.getParameter("userId'"));
        if(!isLogined(user, req)){
            req.setAttribute("loginFailed", true);
            return;
        }

        HttpSession session = req.getSession();
        if(session == null){
            session.setAttribute("user", user);
        }

        resp.sendRedirect("/");
    }

    private boolean isLogined(User user, HttpServletRequest req) {
        return user != null &&
                req.getParameter("userId").equals(user.getUserId()) &&
                req.getParameter("password").equals(user.getPassword());
    }
}
