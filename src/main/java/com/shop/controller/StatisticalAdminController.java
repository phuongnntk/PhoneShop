package com.shop.controller;

import com.shop.dao.ReportDAO;
import com.shop.dao.SessionDAO;
import com.shop.entity.KhachHang;
import com.shop.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("admin/statistical")
public class StatisticalAdminController {
    @Autowired
    ReportDAO reportDAO;

    @Autowired
    SessionDAO session;

    @GetMapping("")
    @Transactional(readOnly = true)
    public String index(Model model, @RequestParam("year") Optional<String> yearString) {
        KhachHang user = (KhachHang) session.get("user");
        if(!user.isQuyen()) {
            String error="Login failed!";
            return "redirect:/login?error="+error;
        }
        model.addAttribute("sessionAdmin", user);
        int year=yearString.isPresent()?Integer.parseInt(yearString.get()):2021;
        Report report=reportDAO.getReport(year);
        model.addAttribute("report", report);
        model.addAttribute("yearChoosen",year);
        System.out.println("size: "+report.getThang1());
        return "admin/statistical/index";
    }
}
