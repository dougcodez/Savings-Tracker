package dev.savingstracker.controller;

import dev.savingstracker.database.SavingsTrackerDataUtil;
import dev.savingstracker.model.Tracker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("tracker")
public class TrackerController {

    @RequestMapping(value = "")
    public String index(Model model) {
        List<Tracker> trackers = new LinkedList<>();
        SavingsTrackerDataUtil.transferDatabaseInfo(trackers);
        model.addAttribute("trackerObjects", trackers);
        model.addAttribute("title", "Savings Tracker");
        return"index";
    }
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddTrackerForm(Model model) {
        model.addAttribute("title", "Add Tracker");
        return "add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddTracker(@RequestParam String name,
                                 @RequestParam String date,
                                 @RequestParam String description,
                                 @RequestParam int amount) {
        Tracker trackerObject = new Tracker(name, date, description, amount);
        SavingsTrackerDataUtil.addTrackerObject(trackerObject);
        return "redirect:";
    }


}
