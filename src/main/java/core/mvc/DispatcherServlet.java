package core.mvc;

import core.view.ModelAndView;
import core.view.VIew;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private RequestMapping rm;

    @Override
    public void init() throws ServletException {
        rm = new RequestMapping();
        rm.init();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        log.debug("Method : {}, Request URI : {}", req.getMethod(), requestURI);

        Controller controller = rm.findController(requestURI);
        try {
            ModelAndView mav = controller.execute(req, resp);
            VIew view = mav.getvIew();
            view.render(mav.getModel(), req, resp);
        } catch (Throwable e) {
            log.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }
}
