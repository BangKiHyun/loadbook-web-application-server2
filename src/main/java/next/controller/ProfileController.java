package next.controller;

import core.db.DataBase;
import core.mvc.Controller;
import core.view.JspView;
import core.view.VIew;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileController implements Controller {
    private static final long serialVersionUID = 1L;

    @Override
    public VIew execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }

        request.setAttribute("user", user);

        return new JspView("/users/profile.jsp");
    }
}
