package next.controller.qna;

import core.mvc.AbstractController;
import core.view.ModelAndView;
import next.dao.JdbcQuestionDao;
import next.model.Question;
import next.util.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateQustionController extends AbstractController {
    private JdbcQuestionDao questionDao = JdbcQuestionDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(!UserSessionUtils.isLogined(request.getSession())){
            return jspView("redirect:/users/loginForm");
        }

        long questionId = Long.parseLong(request.getParameter("questionId"));
        Question question = questionDao.findById(questionId);
        if(!question.isSameUser(UserSessionUtils.getUserFromSession(request.getSession()))){
            throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }

        Question newQuestion = new Question(question.getWriter(),
                request.getParameter("title"),
                request.getParameter("contents"));
        question.update(newQuestion);
        questionDao.update(question);
        return jspView("redirect:/");
    }
}
