package next.controller;

import core.db.DataBase;
import core.mvc.Controller;
import core.view.JspView;
import core.view.VIew;
import next.util.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListUserController implements Controller {

    @Override
    public VIew execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return new JspView("redirect:/users/loginForm");
        }

        request.setAttribute("user", DataBase.findAll());
        return new JspView("/users/list.jsp");
    }
}
