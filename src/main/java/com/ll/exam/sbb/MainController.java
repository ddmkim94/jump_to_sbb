package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("/sbb")
    @ResponseBody
    // 메서드의 리턴값을 그대로 브라우저에 표시!
    // 아래 함수의 리턴값을 문자열화 해서 브라우저 응답의 바디에 담는다.
    public String index() {
        return "박은빈";
    }
}
