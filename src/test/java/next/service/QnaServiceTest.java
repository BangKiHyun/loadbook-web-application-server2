package next.service;

import com.google.common.collect.Lists;
import next.dao.AnswerDao;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static next.model.QuestionTest.newQuestion;
import static next.model.UserTest.newUser;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QnaServiceTest {
    private AnswerDao answerDao;
    private QuestionDao questionDao;
    private QnaService qnaService;

    @Before
    public void setup() {
        answerDao = new JdbcAnswerDao();
        questionDao = new JdbcQuestionDao();
        qnaService = new QnaService(questionDao, answerDao);
    }

    @Test
    public void deleteQuestion_삭제할수_있음() throws Exception {
        User user = newUser("userId");
        Question question = new Question(1L, user.getUserId(), "title", "contents", new Date(), 0) {
            public boolean canDelete(User user, List<Answer> answers) throws IllegalArgumentException {
                return true;
            };
        };
        when(questionDao.findById(1L)).thenReturn(question);

        qnaService.deleteQuestion(1L, newUser("userId"));
        verify(questionDao).delete(question.getQuestionId());
    }

    @Test(expected = IllegalAccessException.class)
    public void deleteQuestion_삭제할수_없음() throws Exception {
        User user = newUser("userId");
        Question question = new Question(1L, user.getUserId(), "title", "contents", new Date(), 0) {
            public boolean canDelete(User user, List<Answer> answers) throws IllegalArgumentException{
                throw new IllegalArgumentException("삭제할 수 없음");
            };
        };
        when(questionDao.findById(1L)).thenReturn(question);

        qnaService.deleteQuestion(1L, newUser("userId"));
    }
}
