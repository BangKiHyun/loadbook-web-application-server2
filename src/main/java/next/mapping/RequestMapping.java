package next.mapping;

import next.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private Map<String, Controller> mappings = new HashMap<>();

    public void init(){
        mappings.put("/users", new ListUserController());
        mappings.put("/users/form", new ForwardController());
        mappings.put("/users/create", new CreateUserController());
        mappings.put("/users/loginForm", new ForwardController());
        mappings.put("/users/login", new LoginController());
        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/profile", new ProfileController());
        mappings.put("/users/updateForm", new UpdateFormController());
        mappings.put("/users/update", new UpdateUserController());
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }
}
