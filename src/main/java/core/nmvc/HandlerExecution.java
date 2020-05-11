package core.nmvc;

import core.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecution.class);

    private Object declareObject;
    private Method method;

    public HandlerExecution(Object declareObject, Method method) {
        this.declareObject = declareObject;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            return (ModelAndView) method.invoke(declareObject, request, response);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            log.error("{} method invoke fail. error message : {}", method, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
