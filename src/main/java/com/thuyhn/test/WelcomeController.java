package com.thuyhn.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Mouse;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thuyhn.customer.CustomerRepository;

@Controller
public class WelcomeController {
	private static final Logger logger = LogManager.getLogger(WelcomeController.class);
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
////        logger.debug("index() is executed!");
////        logger.debug("Model:"+ model);
//        return "index";
//    }

//    @GetMapping("/test")
//    @RequestMapping(value = "/test")
    @PostMapping("/test")
    public String test(@RequestParam(name="name", required=false, defaultValue="") String name, Model model) {
        if(name.equalsIgnoreCase("sikuli")){
            sikuliService.test(model);
        }
//        model.addAttribute(attributeName, attributeValue)
        model.addAttribute("user",name);
        return "test";
    }
    
}
