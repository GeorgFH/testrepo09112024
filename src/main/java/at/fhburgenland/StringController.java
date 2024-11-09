package at.fhburgenland;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StringController {
    @Autowired
    private StringService stringService = new StringService();

    @GetMapping("/upper")
    String upper(@RequestParam(value = "string") final String text) {
        return stringService.toUpperCase(text);
    }
}
