package next.controller;

import core.mvc.AbstractController;
import core.view.ModelAndView;
import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    private UserDao userDao;

    public CreateUserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
        log.debug("User : {}", user);

        try {
            userDao.insert(user);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return jspView("redirect:/");
    }
}
