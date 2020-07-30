package com.thuyhn.controller;

import java.util.Map;

//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thuyhn.service.SikuliService;

import repository.CustomerRepository;



@Controller
public class WelcomeController {
//	private static final Logger logger = LogManager.getLogger(WelcomeController.class);
    private final SikuliService sikuliService;
    
    /** The repository. */
    @Autowired
    private CustomerRepository repository;

    @Autowired
    public WelcomeController(SikuliService sikuliService) {
        this.sikuliService = sikuliService;
    }

//    @GetMapping("/")
//    public String index(Map<String, Object> model) {
//        logger.debug("index() is executed!");
//        logger.debug("Model:"+ model);
//        return "index";
//    }

    @GetMapping("/test")
    public String response(@RequestParam(name="name", required=false, defaultValue="") String name, Model model) {
        if(name.equalsIgnoreCase("sikuli")){
            sikuliService.test(model);
        }
        model.addAttribute("user",name);
        return "test";
    }
    
}
