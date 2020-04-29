package next.controller;

import core.db.DataBase;
import core.mvc.AbstractController;
import core.view.ModelAndView;
import next.util.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListUserController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        request.setAttribute("user", DataBase.findAll());
        return jspView("/users/list.jsp");
    }
}
