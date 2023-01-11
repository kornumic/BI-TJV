package cz.cvut.fit.tjv.semestral.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorkManagerWebController {


    @GetMapping("/workmanager")
    public String show(Model model){
        return "workManager";
    }
}
