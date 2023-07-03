package com.shop.controller;

import com.shop.dao.HangDTDAO;
import com.shop.dao.LoaiDTDAO;
import com.shop.dao.PhoneDAO;
import com.shop.dao.SessionDAO;
import com.shop.entity.KhachHang;
import com.shop.entity.LoaiDT;
import com.shop.entity.Phone;
import com.shop.helper.PhoneHelper;
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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("{/admin,/adminUpdate,/del,/add}")
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private PhoneDAO phoneDAO;
    @Autowired
    private LoaiDTDAO loaiDTDAO;
    @Autowired
    private HangDTDAO hangDTDAO;
    @Autowired
    SessionDAO session;
    PhoneHelper phoneHelper = new PhoneHelper();
    PhoneValidate phoneValidate = new PhoneValidate();

    @GetMapping({"", "index"})
    public String index(Model model, @RequestParam Optional<String> txtSearch,
                        @RequestParam("soTrang") Optional<String> SoTrangString,
                        @RequestParam("message") Optional<Boolean> message) {
        List<Phone> list = new ArrayList<>();
        KhachHang user = (KhachHang) session.get("user");
        if (!user.isQuyen()) {
            String error = "Login failed!";
            return "redirect:/login?error=" + error;
        }
        model.addAttribute("sessionAdmin", user);
        if (txtSearch.isPresent()) {
            list = phoneDAO.findByName(txtSearch.get());
        } else {
            int soTrang = SoTrangString.isEmpty() ? 1 : Integer.parseInt(SoTrangString.get());
            model.addAttribute("soTrangHienTai", soTrang);
            int soSanPham = 6;
            model.addAttribute("soSanPhamHienTai", soSanPham);
            int tongSoTrang = phoneHelper.getTotalPage(soSanPham, phoneDAO.findAll());
            model.addAttribute("tongSoTrang", tongSoTrang);
            Pageable pageable = PageRequest.of(soTrang - 1, soSanPham);
            Page<Phone> pagePhone = phoneDAO.findAll(pageable);
            list = pagePhone.getContent();
        }
        model.addAttribute("listPhone", list);
        KhachHang khachHang = (KhachHang) session.get("userAdmin");
        if (khachHang != null) {
            model.addAttribute("message", "Xóa thành công!");
        } else {
            model.addAttribute("message", "Xóa thất thất bại!");
        }
        return "admin/index";
    }

    @GetMapping("index/update")
    public String showUpdate(Model model, @RequestParam("maDT") Optional<String> maDTString,
                             @RequestParam("message") Optional<Boolean> message) {
        KhachHang user = (KhachHang) session.get("user");
        if (!user.isQuyen()) {
            String error = "Login failed!";
            return "redirect:/login?error=" + error;
        }
        model.addAttribute("sessionAdmin", user);
        int maDT = maDTString.isEmpty() ? 1 : Integer.parseInt(maDTString.get());
        Phone phone = phoneDAO.findById(maDT).get();
        model.addAttribute("phoneItem", phone);
        model.addAttribute("listloaiDT", loaiDTDAO.findAll());
        model.addAttribute("listHangDT", hangDTDAO.findAll());
        if (message.isPresent()) {
            if (message.get()) {
                model.addAttribute("message", "Lưu thành công!");
            } else {
                model.addAttribute("message", "Lưu thất bại!");
            }
        }
        return "admin/update";
    }

    @PostMapping("index/update")
    public String update(Model model, @RequestParam("maDT") Optional<String> maDTString,
                         @RequestParam("tenDT") String tenDTString, @RequestParam("donGia") Optional<String> donGiaString,
                         @RequestParam("giamGia") Optional<String> giamGiaString, @RequestParam("moTa") String moTaString,
                         @RequestParam("maLoaiDT") Optional<String> maLoaiDTString,
                         @RequestParam("maHangDT") Optional<String> maHangDTString,
                         HttpServletRequest req) throws IOException, ServletException {
        String tenHinhAnh = phoneHelper.uploadImage(req).equals("") ? req.getParameter("hinhAnhTonTai") : phoneHelper.uploadImage(req);
        boolean message = true;
        List<Optional<String>> list = new ArrayList<>();
        list.add(maDTString);
        list.add(maLoaiDTString);
        list.add(maHangDTString);
        list.add(giamGiaString);
        list.add(donGiaString);
        if (!phoneValidate.listIsNullOrEmpty(list)) {
            Phone phone = phoneDAO.findById(Integer.parseInt(maDTString.get())).get();
            phone.setTenDT(tenDTString);
            phone.setGiamGia(Double.parseDouble(giamGiaString.get()) / 100);
            phone.setDonGia(Integer.parseInt(donGiaString.get()));
            phone.setMoTa(moTaString);
            phone.setLoaiDT(loaiDTDAO.findById(Integer.parseInt(maLoaiDTString.get())).get());
            phone.setHangDT(hangDTDAO.findById(Integer.parseInt(maHangDTString.get())).get());
            phone.setHinhAnh(tenHinhAnh);
            phoneDAO.save(phone);
            message = true;
        }else {
            message=false;
        }
        return "redirect:/admin/index/update?maDT="+Integer.parseInt(maDTString.get())+"&message="+message;
    }
    @GetMapping("index/insert")
    public String showInsert(Model model,
                             @RequestParam("message") Optional<Boolean> message) {
        KhachHang user = (KhachHang) session.get("user");
        if(!user.isQuyen()) {
            String error="Login failed!";
            return "redirect:/login?error="+error;
        }
        model.addAttribute("sessionAdmin", user);
        model.addAttribute("listLoaiDT", loaiDTDAO.findAll());
        model.addAttribute("listHangDT", hangDTDAO.findAll());
        if(message.isPresent()) {
            if(message.get()) {
                model.addAttribute("message","Thêm thành công!");
            }else {
                model.addAttribute("message","Thêm thất thất bại!");
            }
        }
        return "admin/insert";
    }
    @GetMapping("index/delete")
    public String delete(@RequestParam("idDT") Optional<String> maDTString) {
        int maDT = maDTString.isEmpty() ? -1 : Integer.parseInt(maDTString.get());
        if(maDT!=-1) {
            phoneDAO.delete(phoneDAO.findById(maDT).get());
            return "redirect:/admin/index?message="+true;
        }else {
            return "redirect:/admin/index?message="+false;
        }
    }
    @PostMapping("index/insert")
    public String insert(Model model,
                         @RequestParam("tenDT") String tenDTString, @RequestParam("donGia") Optional<String> donGiaString,
                         @RequestParam("giamGia") Optional<String> giamGiaString, @RequestParam("moTa") String moTaString,
                         @RequestParam("maLoaiDT") Optional<String> maLoaiDTString,
                         @RequestParam("maHangDT") Optional<String> maHangDTString,
                         HttpServletRequest req) throws IOException, ServletException {
        String tenHinhAnh = phoneHelper.uploadImage(req).equals("") ? req.getParameter("hinhAnhTonTai") : phoneHelper.uploadImage(req);
        boolean message=true;
        List<Optional<String>> list = new ArrayList<>();
        list.add(maLoaiDTString);
        list.add(maHangDTString);
        list.add(giamGiaString);
        list.add(donGiaString);
        if (!phoneValidate.listIsNullOrEmpty(list)) {
            Phone phone = new Phone();
            phone.setTenDT(tenDTString);
            phone.setGiamGia(Double.parseDouble(giamGiaString.get())/100);
            phone.setDonGia(Integer.parseInt(donGiaString.get()));
            phone.setMoTa(moTaString);
            phone.setLoaiDT(loaiDTDAO.findById(Integer.parseInt(maLoaiDTString.get())).get());
            phone.setHangDT(hangDTDAO.findById(Integer.parseInt(maHangDTString.get())).get());
            phone.setHinhAnh(tenHinhAnh);
            phoneDAO.save(phone);
            message=true;
        }else {
            message=false;
        }
        return "redirect:/admin/index/insert?message="+message;
    }
    @GetMapping("/logout")
    public String logout(@RequestParam Optional<String> urlReturn) {
        session.clear();
        return urlReturn.isPresent()?"redirect:/admin/" +urlReturn.get():"redirect:/admin";
    }
}