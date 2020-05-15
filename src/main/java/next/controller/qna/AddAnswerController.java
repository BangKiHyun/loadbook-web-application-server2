package next.controller.qna;

import core.mvc.AbstractController;
import core.view.ModelAndView;
import next.dao.AnswerDao;
import next.dao.JdbcAnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddAnswerController extends AbstractController {
    private QuestionDao questionDao;
    private AnswerDao answerDao;

    public AddAnswerController(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Answer answer = new Answer(
                request.getParameter("writer"),
                request.getParameter("contents"),
                Long.parseLong(request.getParameter("questionId"))
        );

        Answer saveAnswer = answerDao.insert(answer);
        request.setAttribute("answer", saveAnswer);

        return jsonView().addObject("answer", saveAnswer);
    }
}
