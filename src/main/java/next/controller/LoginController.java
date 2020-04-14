package next.controller;

import core.db.DataBase;
import core.mvc.Controller;
import next.model.User;
import next.util.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        User user = DataBase.findUserById(userId);

        if (user == null || !user.matchPassword(password)) {
            request.setAttribute("loginFailed", true);
            return "/users/login.jsp";
        }

        HttpSession session = request.getSession();
        session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }
}
