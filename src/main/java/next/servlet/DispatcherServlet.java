package next.servlet;

import next.controller.Controller;
import next.mapping.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String REDIRECT = "redirect:";

    RequestMapping mapping;

    @Override
    public void init() throws ServletException {
        mapping = new RequestMapping();
        mapping.init();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        if (url.startsWith(REDIRECT)) {
            try {
                url = url.substring(REDIRECT.length());
                Controller controller = mapping.findController(url);
                String redirectUrl = controller.execute(req, resp);
                resp.sendRedirect(redirectUrl);
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
        } else {
            try {
                Controller controller = mapping.findController(url);
                String nextUrl = controller.execute(req, resp);
                RequestDispatcher rd = req.getRequestDispatcher(nextUrl);
                rd.forward(req, resp);
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
        }
    }
}
