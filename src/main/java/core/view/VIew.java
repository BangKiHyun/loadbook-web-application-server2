package core.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VIew {
    void render(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
