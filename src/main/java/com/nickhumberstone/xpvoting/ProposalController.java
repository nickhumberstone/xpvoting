package com.nickhumberstone.xpvoting;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProposalController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("proposals", new Proposals());
        return "index";
    }
}
