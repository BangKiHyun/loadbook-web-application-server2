package next.controller.qna;

import core.mvc.AbstractController;
import core.view.ModelAndView;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiListQuestionController extends AbstractController {
    private QuestionDao questionDao = QuestionDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jsonView().addObject("questions", questionDao.findAll());
    }
}
