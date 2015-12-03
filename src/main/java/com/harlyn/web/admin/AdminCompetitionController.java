package com.harlyn.web.admin;

import com.harlyn.domain.competitions.Competition;
import com.harlyn.exception.CompetitionNotFoundException;
import com.harlyn.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Optional;

/**
 * Created by wannabe on 03.12.15.
 */
@Controller
@RequestMapping(value = "/admin/competition")
public class AdminCompetitionController {
    @Autowired
    private CompetitionService competitionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String newCompetitionPage(Model model) {
        return "admin/competition/new";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String newCompetitionAction(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "start_date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") Optional<Date> startDate,
            @RequestParam(value = "end_date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") Optional<Date> endDate
    ) {
        Competition competitionData = new Competition(name);
        if (startDate.isPresent()) {
            competitionData.setStartDate(startDate.get());
        }
        if (endDate.isPresent()) {
            competitionData.setEndDate(endDate.get());
        }
        return "redirect:/admin/competition/" + competitionService.createCompetition(competitionData);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String editCompetitionPage(@PathVariable(value = "id") Long id, Model model) {
        Competition competition = competitionService.findById(id);
        if (competition == null) {
            throw new CompetitionNotFoundException(id);
        }
        model.addAttribute("competition", competition);
        return "admin/competition/edit";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String editCompetitionAction(@PathVariable(value = "id") Long id,
                                        Model model,
                                        @RequestParam(value = "name") String name,
                                        @RequestParam(value = "start_date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") Optional<Date> startDate,
                                        @RequestParam(value = "end_date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") Optional<Date> endDate
    ) {
        Competition competition = competitionService.findById(id);
        if (competition == null) {
            throw new CompetitionNotFoundException(id);
        }
        Competition competitionData = new Competition(name);
        if (startDate.isPresent()) {
            competitionData.setStartDate(startDate.get());
        }
        if (endDate.isPresent()) {
            competitionData.setEndDate(endDate.get());
        }
        return "redirect:/admin/competition/" + competitionService.updateCompetition(competition, competitionData);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listCompetitionPage(Model model) {
        model.addAttribute("competitions", competitionService.findAll());
        return "admin/competition/list";
    }
}