package next.controller;

import core.mvc.AbstractController;
import core.view.ModelAndView;
import next.dao.UserDao;
import next.model.User;
import next.util.UserSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateFormController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UpdateFormController.class);
    private UserDao userDao = UserDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = userDao.findByUserId(request.getParameter("userId"));
        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        request.setAttribute("user", user);
        return jspView("users/updateForm.jsp");
    }
}
