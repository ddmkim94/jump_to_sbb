package com.ll.exam.sbb.question;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuestionController {
    @RequestMapping("/question/list")
    public String list() {
        return "question list";
    }
}