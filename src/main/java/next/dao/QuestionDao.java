package next.dao;

import next.model.Question;

import java.util.List;

public interface QuestionDao {
    Question insert(Question question);
    void update(Question question);
    Question findById(Long questionId);
    List<Question> findAll();
    void delete(long questionId);
}
