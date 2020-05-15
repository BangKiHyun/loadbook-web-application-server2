package next.service;

import com.google.common.collect.Lists;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;

import static next.model.QuestionTest.newQuestion;
import static next.model.UserTest.newUser;
import static org.mockito.Mockito.when;

public class QnaServiceTest {
    @Mock
    private AnswerDao answerDao;
    @Mock
    private QuestionDao questionDao;

    private QnaService qnaService;

    @Before
    public void setup() {
        qnaService = new QnaService(questionDao, answerDao);
    }

    @Test(expected = NullPointerException.class)
    public void deleteQuestion_없는_질문() throws Exception {
        when(questionDao.findById(1L)).thenReturn(null);

        qnaService.deleteQuestion(1L, newUser("userId"));
    }

    @Test(expected = NullPointerException.class)
    public void deleteQuestion_다른_사용자() throws Exception {
        Question question = newQuestion(1L, "rlrlvh");
        questionDao.insert(question);
        qnaService.deleteQuestion(1L, newUser("rlrlvh"));
    }

    @Test(expected = NullPointerException.class)
    public void deleteQuestion_같은_사용자_답변없음() throws Exception {
        Question question = newQuestion(1L, "rlrlvh");
        when(questionDao.findById(1L)).thenReturn(question);
        when(answerDao.findAllByQuestionId(1L)).thenReturn(Lists.newArrayList());

        qnaService.deleteQuestion(1L, newUser("rlrlvh"));
    }
}
