package com.przeskocz.AdvertEduPortal.controller;

import com.przeskocz.AdvertEduPortal.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.util.Map;

@Controller
public abstract class CommonController {

    @Autowired
    protected CommonService commonService;

    protected Map<Long, String> searchOptions;

    @PostConstruct
    public void init() {
        searchOptions = commonService.getSearchOptions();
    }

    protected void buildMyModel(Model model) {
        model.addAttribute("mapSearchOptions", searchOptions);
    }


    protected void buildMyModelAndView(ModelAndView model) {
        model.addObject("mapSearchOptions", searchOptions);
    }

}
