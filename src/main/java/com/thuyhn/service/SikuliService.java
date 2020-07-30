package com.thuyhn.service;

import org.springframework.stereotype.Service;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.thuyhn.test.TestSpringBoot;
import com.thuyhn.test.TestSpringMVC;

@Service
public class SikuliService {
	
//	private static final Logger logger = LogManager.getLogger(SikuliService.class);

    public ModelAndView test(ModelAndView model) {
        TestSpringMVC test = new TestSpringMVC();
        test.readUtilFile(model);
//        logger.debug("Test is executed!");
        return model;

    }
    
    public Model test(Model model) {
        TestSpringBoot test = new TestSpringBoot();
        test.readUtilFile(model);
//        logger.debug("Test is executed!");
        return model;

    }

}
