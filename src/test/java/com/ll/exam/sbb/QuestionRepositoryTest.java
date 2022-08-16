package com.ll.exam.sbb;

import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class QuestionRepositoryTest {

    @Autowired private QuestionRepository questionRepository;
    private static int lastSampleDataId;

    // 각 테스트 실행전에 남아있던 데이터를 지우고 다시 생성
    @BeforeEach
    public void beforeEach() {
        clearData();
        createSampleData();
    }

    public static int createSampleData(QuestionRepository questionRepository) {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2);

        return q2.getId();
    }

    private void createSampleData() {
        lastSampleDataId = createSampleData(questionRepository);
    }

    public static void clearData(QuestionRepository questionRepository) {
        questionRepository.deleteAll(); // DELETE FROM question;
        questionRepository.truncateTable();
    }

    private void clearData() {
        clearData(questionRepository);
    }

    @Test
    void removeQuestion() throws Exception {
        Question findQuestion1 = questionRepository.findById(1).orElseGet(null);
        questionRepository.delete(findQuestion1);

        Question findQuestion2 = questionRepository.findById(2).orElseGet(null);
        questionRepository.delete(findQuestion2);

        assertThat(questionRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    void updateQuestion() throws Exception {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        Question saveQuestion = questionRepository.save(q1);// 첫번째 질문 저장

        saveQuestion.setSubject("제목 변경!!");

        assertThat(saveQuestion.getSubject()).isEqualTo("제목 변경!!");
        assertThat(q1.getSubject()).isEqualTo(saveQuestion.getSubject());
    }

    @Test
    void saveQuestion() throws Exception {
        Question findQuestion = questionRepository.findById(1).orElseGet(null);
        assertThat(findQuestion).isNotNull();
    }

    @Test
    void findBySubject() throws Exception {
        Question findQuestion = questionRepository.findById(1).orElseGet(null);
        assertThat(findQuestion).isNotNull();
    }

    @Test
    void findBySubjectAndContent() throws Exception {
        Question findQuestion = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertThat(findQuestion).isNotNull();
    }

    @Test
    void findBySubjectLike() throws Exception {
        Question findQuestion = questionRepository.findBySubjectLike("sbb%");
        assertThat(findQuestion).isNotNull();
    }
}
