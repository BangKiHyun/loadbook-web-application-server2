package next.controller;

import core.mvc.AbstractController;
import core.view.ModelAndView;
import next.dao.UserDao;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileController extends AbstractController {
    private UserDao userDao;

    public ProfileController(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        User user = userDao.findByUserId(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        request.setAttribute("user", user);
        return jspView("/users/profile.jsp");
    }
}
