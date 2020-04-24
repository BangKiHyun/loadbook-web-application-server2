package core.mvc;

import next.controller.*;
import next.controller.qna.AddAnswerController;
import next.controller.qna.DeleteAnswerController;
import next.controller.qna.ShowController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);

    private Map<String, Controller> mappings = new HashMap<>();

    public void init() {
        mappings.put("/", new HomeController());
        mappings.put("/users", new ListUserController());
        mappings.put("/users/form", new ForwardController("/users/form.jsp"));
        mappings.put("/users/create", new CreateUserController());
        mappings.put("/users/loginForm", new ForwardController("/users/login.jsp"));
        mappings.put("/users/login", new LoginController());
        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/profile", new ProfileController());
        mappings.put("/users/updateForm", new UpdateFormController());
        mappings.put("/users/update", new UpdateUserController());
        mappings.put("/qna/show", new ShowController());
        mappings.put("/api/qna/addAnswer", new AddAnswerController());
        mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController());
        log.info("Initialized Request Mapping!");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }
}
