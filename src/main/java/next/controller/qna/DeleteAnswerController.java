package next.controller.qna;

import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.view.ModelAndView;
import next.dao.JdbcAnswerDao;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAnswerController extends AbstractController {
    private JdbcAnswerDao answerDao = JdbcAnswerDao.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long answerId = Long.parseLong(request.getParameter("answerId"));

        ModelAndView mav = jsonView();
        try{
            answerDao.delete(answerId);
            mav.addObject("result", Result.ok());
        }catch (DataAccessException e){
            mav.addObject("result", Result.fail(e.getMessage()));
        }
        return mav;
    }
}
