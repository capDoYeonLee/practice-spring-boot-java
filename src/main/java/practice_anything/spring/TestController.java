package practice_anything.spring;


import jakarta.persistence.GeneratedValue;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/")
    public void saveTest(@RequestBody TestDto testDto) {
        testService.saveMember(testDto);
    }

    @GetMapping("/")
    public void getTest() {
        testService.findMember(2l);
    }

    @GetMapping("/nplus")
    public void nplus() {
        System.out.println("=============================초기화=============================");
        testService.nplusone();
    }

    @GetMapping("/nonplus")
    public void noNPlus() {
        System.out.println("=============================초기화=============================");
        testService.noNPlus();
    }


}
