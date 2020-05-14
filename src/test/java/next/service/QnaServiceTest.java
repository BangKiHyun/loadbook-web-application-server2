package next.service;

import next.dao.MockAnswerDao;
import next.dao.MockQuestionDao;
import next.model.Question;
import org.junit.Before;
import org.junit.Test;

import static next.model.QuestionTest.newQuestion;
import static next.model.UserTest.newUser;

public class QnaServiceTest {
    private QnaService qnaService;
    private MockAnswerDao answerDao;
    private MockQuestionDao questionDao;

    @Before
    public void setup(){
        answerDao = new MockAnswerDao();
        questionDao = new MockQuestionDao();
        qnaService = QnaService.getInstance(questionDao, answerDao);
    }

    @Test(expected = NullPointerException.class)
    public void deleteQuestion_없는_질문() throws Exception{
        qnaService.deleteQuestion(1L, newUser("userId"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteQuestion_다른_사용자() throws Exception{
        Question question = newQuestion(1L, "rlrlvh");
        questionDao.insert(question);
        qnaService.deleteQuestion(1L, newUser("rlrlvh"));
    }
    
    @Test(expected = NullPointerException.class)
    public void deleteQuestion_같은_사용자_답변없음() throws Exception{
        Question question = newQuestion(1L, "rlrlvh");
        questionDao.insert(question);
        qnaService.deleteQuestion(1L, newUser("rlrlvh"));
    }
}
