package next.controller.qna;

import core.mvc.AbstractController;
import core.view.ModelAndView;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.model.Question;
import next.model.Result;
import next.service.QnaService;
import next.util.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiDeleteQuestionController extends AbstractController {
    private QnaService qnaService = QnaService.getInstance(JdbcQuestionDao.getInstance(), JdbcAnswerDao.getInstance());

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jsonView().addObject("result", Result.fail("Login is required"));
        }

        long questionId = Long.parseLong(request.getParameter("questionId"));
        Question question = qnaService.findById(questionId);
        try {
            qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(request.getSession()));
            return jsonView().addObject("result", Result.ok());
        } catch (Exception e) {
            return jsonView().addObject("result", Result.fail(e.getMessage()));
        }
    }
}
