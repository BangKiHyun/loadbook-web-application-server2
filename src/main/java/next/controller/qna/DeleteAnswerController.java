package next.controller.qna;

import core.mvc.Controller;
import core.view.JsonView;
import core.view.VIew;
import next.dao.AnswerDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAnswerController implements Controller {
    @Override
    public VIew execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long answerId = Long.parseLong(request.getParameter("answerId"));
        AnswerDao answerDao = new AnswerDao();
        answerDao.delete(answerId);
        request.setAttribute("answerId", true);

        return new JsonView();
    }
}
