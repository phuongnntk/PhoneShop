package com.shop.controller;

import com.shop.dao.MailerService;
import com.shop.dao.PhoneDAO;
import com.shop.dao.SessionDAO;
import com.shop.dao.ShoppingCartDAO;
import com.shop.entity.KhachHang;
import com.shop.entity.MailInfo;
import com.shop.entity.Phone;
import com.shop.helper.PhoneHelper;
import com.shop.message.MAIL_CONSTANT;
import com.shop.validate.PhoneValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class PhoneController {
    @Autowired
    private PhoneDAO phoneDAO;

    @Autowired
    private ShoppingCartDAO shoppingCartDAO;

    @Autowired
    SessionDAO session;

    @Autowired
    MailerService mailer;

    MAIL_CONSTANT MAIL_INFO = new MAIL_CONSTANT();
    PhoneHelper phoneHelper = new PhoneHelper();

    @GetMapping({"", "index"})
    public String showGiay(Model model, @RequestParam Optional<String> message,
                           @RequestParam("soTrang") Optional<String> soTrangString,
                           @RequestParam("soSanPham") Optional<String> soSanPhamString) {
        int soTrang = soTrangString.isEmpty() ? 1 : Integer.parseInt(soTrangString.get());
        model.addAttribute("soTrangHienTai", soTrang);
        int soSanPham = soTrangString.isEmpty() ? 6 : Integer.parseInt(soSanPhamString.get());
        model.addAttribute("soSanPhamHienTai", soSanPham);
        int tongSoTrang = phoneHelper.getTotalPage(soSanPham, phoneDAO.findAll());
        model.addAttribute("tongSoTrang", tongSoTrang);
        //Trang số "soTrang-1", số sản phẩm hiển thị "soSanPham"
        Pageable pageable = PageRequest.of(soTrang - 1, soSanPham);
        Page<Phone> pagePhone = phoneDAO.findAll(pageable);
        List<Phone> list = pagePhone.getContent();
        if (message.isPresent()) {
            model.addAttribute("message", message.get());
        }
        model.addAttribute("listPhone", list);
        model.addAttribute("tongSoLuongGioHang", shoppingCartDAO.getCount());
        KhachHang khachHang = (KhachHang) session.get("user");
        if (khachHang != null) {
            model.addAttribute("sessionUsername", khachHang.getTaiKhoan());
        }
        return "customer/categories";
    }
    @GetMapping("/detail")
    public String detail(Model model,@RequestParam String idProduct) {
        Phone phone=new PhoneValidate().checkIDPhone(idProduct)?phoneDAO.findById(Integer.parseInt(idProduct)).get():null;
        model.addAttribute("phone",phone);
        model.addAttribute("tongSoLuongGioHang",shoppingCartDAO.getCount());
        KhachHang khachHang=(KhachHang) session.get("user");
        if(khachHang!=null) {
            model.addAttribute("sessionUsername",khachHang.getTaiKhoan());
        }
        return "customer/detail";
    }
    @PostMapping("/sendMail")
    public String send(Model model,
                       @RequestParam String txtTo){
        MailInfo mail = new MailInfo();
        mail.setTo(txtTo);
        mail.setSubject(MAIL_INFO.REGISTER_SALE_SUBJECT);
        mail.setBody(MAIL_INFO.REGISTER_SALE_CONTENT);
        //Gửi mail
        mailer.queue(mail);
        return "redirect:/sendMail/result";
    }

    @GetMapping("/sendMail/result")
    public String sendMail(Model model) {
        return "customer/sendMailResult";
    }
}
