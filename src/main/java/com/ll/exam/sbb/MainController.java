package com.ll.exam.sbb;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class MainController {

    @RequestMapping("/")
    public String root() {
        return "redirect:/question/list";
    }

    private static int count = 0;
    private final HttpSession session;
    private final List<Article> articleList = new ArrayList<>(
            Arrays.asList(new Article("제목1", "내용1"), new Article("제목2", "내용2")));

    @GetMapping("/addPerson/{id}")
    @ResponseBody
    public Person addPerson(Person person) {
        return person;
    }

    @GetMapping("/deleteArticle")
    @ResponseBody
    public String deleteArticle(@RequestParam Long id) {
        articleList.removeIf(article -> article.getId().equals(id));
        return "%d번 게시물이 삭제되었습니다.".formatted(id);
    }

    @GetMapping("/modifyArticle")
    @ResponseBody
    public String modifyArticle(@RequestParam Long id, @RequestParam String title, @RequestParam String body) {
        for (Article article : articleList) {
            if (article.getId().equals(id)) {
                article.changeArticle(title, body);
                return "%d번 게시물이 수정되었습니다.".formatted(id);
            }
        }
        return "%d번 게시물은 존재하지 않습니다.".formatted(id);
    }

    @GetMapping("/addArticle")
    @ResponseBody
    public String addArticle(@RequestParam String title, @RequestParam String body) {
        Article article = new Article(title, body);
        articleList.add(article);
        return "%d번 게시물이 생성되었습니다.".formatted(article.getId());
    }

    @GetMapping("/article/{id}")
    @ResponseBody
    public Article getArticle(@PathVariable Long id) {
        Article article = articleList
                .stream()
                .filter(a -> a.getId().equals(id)) // 1번
                .findFirst()
                .orElse(null);

        return article;
    }

    @GetMapping("/saveSessionAge/{age}")
    @ResponseBody
    public void saveSessionAge(@PathVariable int age) {
        session.setAttribute("age", age);
    }

    @GetMapping("/getSessionAge")
    @ResponseBody
    public int getSessionAge() {
        // req -> 쿠키 -> JSSEIONID -> 세션을 얻을 수 있다.
        return (int) session.getAttribute("age");
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String showGugudan(@RequestParam int dan, @RequestParam int limit) {

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= limit; i++) {
            String str = """
                    %d * %d = %d
                    """.formatted(dan, i, dan * i);
            sb.append(str).append("<br>");
        }

        return sb.toString();
    }

    @GetMapping("/gugudanS")
    @ResponseBody
    public String showGugudanByStream(Integer dan, Integer limit) {
        if (limit == null) {
            limit = 9;
        }

        if (dan == null) {
            dan = 9;
        }

        Integer finalDan = dan;
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan * i))
                .collect(Collectors.joining("<br>\n"));
    }

    @GetMapping("/mbti/{name}")
    @ResponseBody
    public String showMbti(@PathVariable String name) {
        Map<String, String> map = new HashMap<>();
        map.put("홍길동", "INFP");
        map.put("홍길순", "ENFP");
        map.put("박은빈", "INFJ");
        map.put("김동민", "INFP");

        return map.get(name);
    }
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
