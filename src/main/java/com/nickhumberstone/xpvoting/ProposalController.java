package com.nickhumberstone.xpvoting;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProposalController {

    private ProposalService proposalservice;

    public ProposalController(ProposalService proposalservice) {
        this.proposalservice = proposalservice;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("proposals", new Proposals());
        model.addAttribute("proposalslist", proposalservice.proposals());
        return "index";
    }

    @PostMapping("/")
    public String submitProposal(@ModelAttribute Proposals proposal, Model model) {
        String proposal2 = proposal.getProposal();
        proposalservice.addProposal(proposal2);
        return "redirect:/";
    }

}
