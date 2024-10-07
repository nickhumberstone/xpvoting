package com.nickhumberstone.xpvoting;

import java.util.List;

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
        List<Proposal> proposalslist = proposalservice.proposals();
        model.addAttribute("proposalsForm", new ProposalsForm());
        model.addAttribute("proposalslist", proposalslist.stream().map(Proposal::getTopicTitle).toList());
        return "index";
    }

    @GetMapping("/proposalslist")
    public String proposalslist(Model model) {
        List<Proposal> proposalslist = proposalservice.proposals();
        model.addAttribute("proposalslist", proposalslist.stream().map(Proposal::getTopicTitle).toList());
        return "proposal-list";
    }

    @PostMapping("/")
    public String submitProposal(@ModelAttribute ProposalsForm proposalsform, Model model) {
        String proposal2 = proposalsform.getTopicTitle();
        proposalservice.addProposal(proposal2);
        return "redirect:/";
    }

}
