package core.mvc;

import core.view.VIew;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    VIew execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
