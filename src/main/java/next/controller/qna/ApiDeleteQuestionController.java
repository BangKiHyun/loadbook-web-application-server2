package next.controller.qna;

import core.mvc.AbstractController;
import core.view.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.Result;
import next.service.QnaService;
import next.util.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiDeleteQuestionController extends AbstractController {
    private QuestionDao questionDao = QuestionDao.getInstance();
    private AnswerDao answerDao = AnswerDao.getInstance();
    private QnaService qnaService = QnaService.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jsonView().addObject("result", Result.fail("Login is required"));
        }

        long questionId = Long.parseLong(request.getParameter("questionId"));
        Question question = questionDao.findById(questionId);
        try {
            qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(request.getSession()));
            return jsonView().addObject("result", Result.ok());
        } catch (Exception e) {
            return jsonView().addObject("result", Result.fail(e.getMessage()));
        }
    }
}
