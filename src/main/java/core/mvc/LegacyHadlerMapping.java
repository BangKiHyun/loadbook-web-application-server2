package core.mvc;

import core.nmvc.HandlerMapping;
import next.controller.*;
import next.controller.qna.AddAnswerController;
import next.controller.qna.DeleteAnswerController;
import next.controller.qna.ShowController;
import next.dao.*;
import next.service.QnaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LegacyHadlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(LegacyHadlerMapping.class);

    private Map<String, Controller> mappings = new HashMap<>();

    public void init() {
        QuestionDao questionDao = new JdbcQuestionDao();
        AnswerDao answerDao = new JdbcAnswerDao();
        UserDao userDao = new UserDao();
        QnaService qnaService = new QnaService(questionDao, answerDao);
        mappings.put("/", new HomeController(questionDao));
        mappings.put("/users", new ListUserController());
        mappings.put("/users/form", new ForwardController("/users/form.jsp"));
        mappings.put("/users/create", new CreateUserController(userDao));
        mappings.put("/users/loginForm", new ForwardController("/users/login.jsp"));
        mappings.put("/users/login", new LoginController(userDao));
        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/profile", new ProfileController(userDao));
        mappings.put("/users/updateForm", new UpdateFormController(userDao));
        mappings.put("/users/update", new UpdateUserController(userDao));
        mappings.put("/qna/show", new ShowController(questionDao, answerDao));
        mappings.put("/api/qna/addAnswer", new AddAnswerController(questionDao, answerDao));
        mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController(answerDao));
        log.info("Initialized Request Mapping!");
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        return mappings.get(request.getRequestURI());
    }
}
