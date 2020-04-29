package next.controller;

import core.mvc.AbstractController;
import core.mvc.Controller;
import core.view.JspView;
import core.view.ModelAndView;
import core.view.VIew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.invalidate();

        return jspView("redirect:/");
    }
}
