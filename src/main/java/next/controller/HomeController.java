package next.controller;

import core.db.DataBase;
import core.mvc.Controller;
import core.view.JspView;
import core.view.VIew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller {
    private static final long serialUserSIonUID = 1L;

    @Override
    public VIew execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("users", DataBase.findAll());
        return new JspView("home.jsp");
    }
}
