package com.shop.controller;

import com.shop.dao.OrderDAO;
import com.shop.dao.OrderDetailDAO;
import com.shop.dao.SessionDAO;
import com.shop.entity.DonHang;
import com.shop.entity.KhachHang;
import com.shop.helper.OrderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/order")
public class OrderAdminController {
    @Autowired
    SessionDAO session;
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    OrderDetailDAO orderDetailDAO;

    OrderHelper orderHelper = new OrderHelper();

    @GetMapping("")
    public String showOrder(Model model, @RequestParam Optional<String> txtSearch,
                            @RequestParam("soTrang") Optional<String> soTrangString,
                            @RequestParam("message") Optional<Boolean> message) {
        List<DonHang> list=new ArrayList<>();
        KhachHang user = (KhachHang) session.get("user");
        if(!user.isQuyen()) {
            String error="Không đủ quyền truy cập!";
            return "redirect:/login?error="+error;
        }
        model.addAttribute("sessionAdmin", user);
        if(txtSearch.isPresent()) {
            list = orderDAO.findByStatus(txtSearch.get());
        }else {
            int soTrang = soTrangString.isEmpty() ? 1 : Integer.parseInt(soTrangString.get());
            model.addAttribute("soTrangHienTai", soTrang);
            int soSanPham = 6;
            model.addAttribute("soSanPhamHienTai", soSanPham);
            int tongSoTrang = orderHelper.getTotalPage(soSanPham, orderDAO.findAll());
            model.addAttribute("tongSoTrang", tongSoTrang);
            // Trang số "soTrang-1", số sản phẩm hiển thị "soSanPham"
            Pageable pageable = PageRequest.of(soTrang - 1, soSanPham);
            Page<DonHang> pageDonHang = orderDAO.findAll(pageable);
            list = pageDonHang.getContent();
        }
        model.addAttribute("listDonHang", list);
        KhachHang khachHang = (KhachHang) session.get("userAdmin");
        if (khachHang != null) {
            model.addAttribute("sessionUsername", khachHang.getTaiKhoan());
        }
        if(message.isPresent()) {
            if(message.get()) {
                model.addAttribute("message","Xóa thành công!");
            }else {
                model.addAttribute("message","Xóa thất thất bại!");
            }
        }
        return "admin/order/order";
    }
}
