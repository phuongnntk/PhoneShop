package com.shop.controller;

import com.shop.dao.AccountDAO;
import com.shop.dao.SessionDAO;
import com.shop.entity.KhachHang;
import com.shop.message.MESSAGE_CONSTANT;
import com.shop.validate.AccountValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("register")
public class RegisterController {
    MESSAGE_CONSTANT MESSAGE = new MESSAGE_CONSTANT();
    AccountValidate accountValidate = new AccountValidate();

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    SessionDAO session;

    @GetMapping("")
    public String showRegister(Model model) {
        return "customer/register";
    }

    @PostMapping("")
    public String login(Model model,
                        @RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String repeatPass,
                        @RequestParam String fullname,
                        @RequestParam String phone,
                        @RequestParam String address) {
        boolean registerSuccess = true;
        List<String> listCheck = new ArrayList<>();
        listCheck.add(username);
        listCheck.add(password);
        listCheck.add(repeatPass);
        listCheck.add(fullname);
        listCheck.add(phone);
        listCheck.add(address);
        if (!accountValidate.listIsNullOrEmpty(listCheck)) {
            List<KhachHang> list = accountDAO.findAll();
            for (KhachHang account : list) {
                if (username.equalsIgnoreCase(account.getTaiKhoan())) {
                    model.addAttribute("message", MESSAGE.USERNAME_EXIST);
                    registerSuccess = false;
                    return "customer/register";
                }
            }
        }
        if (registerSuccess) {
            KhachHang kh = new KhachHang();
            kh.setTaiKhoan(username);
            kh.setMatKhau(password);
            kh.setTenKhachHang(fullname);
            kh.setSoDienThoai(phone);
            kh.setDiaChi(address);
            kh.setQuyen(false);
            accountDAO.save(kh);
            return "redirect:/login";
        } else {
            return "redirect:/register";
        }
    }
}
