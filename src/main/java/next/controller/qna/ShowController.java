package next.controller.qna;

import core.mvc.AbstractController;
import core.view.ModelAndView;
import next.dao.AnswerDao;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowController extends AbstractController {
    private QuestionDao questionDao;
    private AnswerDao answerDao;

    public ShowController(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(request.getParameter("questionId"));

        ModelAndView mav = jspView("/qna/show.jsp");
        mav.addObject("question", questionDao.findById(questionId));
        mav.addObject("answers", answerDao.findAllByQuestionId(questionId));
        return mav;
    }
}
