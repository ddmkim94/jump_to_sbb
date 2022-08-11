package com.ll.exam.sbb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    Question findBySubjectLike(String keyword);

    // @Query에는 한 개의 쿼리만 넣을 수 있다! 단, 카운팅 쿼리는 분리해서 넣을 수 있음
    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
    void disableForeignKeyCheck();

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE question", nativeQuery = true)
    void truncate();

    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
    void enableForeignKeyCheck();
}