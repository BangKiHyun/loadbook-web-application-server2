package next.controller.qna;

import core.mvc.Controller;
import core.view.JsonView;
import core.view.VIew;
import next.dao.AnswerDao;
import next.model.Answer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddAnswerController implements Controller {
    @Override
    public VIew execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Answer answer = new Answer(
                request.getParameter("writer"),
                request.getParameter("contents"),
                Long.parseLong(request.getParameter("questionId"))
        );

        AnswerDao answerDao = new AnswerDao();
        Answer saveAnswer = answerDao.insert(answer);
        request.setAttribute("answer", saveAnswer);

        return new JsonView();
    }
}
