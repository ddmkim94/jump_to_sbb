package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    private static int count = 0;

    @GetMapping("/plus")
    @ResponseBody
    // @RequestParam name 속성에 원하는 파라미터 이름을 지정하면 그 이름으로 받아올 수 있다!
    public int showPlus(@RequestParam("num1") int a, @RequestParam int b) {
        return a + b;
    }

    @GetMapping("/minus")
    @ResponseBody
    public int showMinus(@RequestParam int a, @RequestParam int b) {
        return a - b;
    }

    @GetMapping("/increase")
    @ResponseBody
    public int showIncrease() {
        return count++;
    }

    @RequestMapping("/sbb")
    @ResponseBody
    // 메서드의 리턴값을 그대로 브라우저에 표시!
    // 아래 함수의 리턴값을 문자열화 해서 브라우저 응답의 바디에 담는다.
    public String index() {
        return "박은빈 오연서";
    }

    @GetMapping("/page1")
    @ResponseBody
    public String showPage1() {
        return """
                <form method="POST" action="/page2">
                    <input type="number" name="age" placeholder="나이" />
                    <input type="submit" value="page2로 POST 방식으로 이동" />
                </form>
                """;
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, POST 방식으로 오셨군요.</h1>
                """.formatted(age);
    }

    @GetMapping("/page2")
    @ResponseBody
    public String showPage2Get(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, POST 방식으로 오셨군요.</h1>
                """.formatted(age);
    }
}
