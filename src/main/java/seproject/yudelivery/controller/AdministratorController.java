package seproject.yudelivery.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.dto.AdministratorDTO;
import seproject.yudelivery.entity.AdministratorEntity;
import seproject.yudelivery.repository.AdministratorRepository;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdministratorController {
    // 커밋 테스트 222

    @Autowired
    private AdministratorRepository administratorRepository;

    @GetMapping
    public String adminMain(@ModelAttribute AdministratorDTO administratorDTO, Model model) {
        List<AdministratorEntity> reportList = administratorRepository.findAllById(administratorDTO.getId());
        model.addAttribute("reportList", reportList);
        return "admin/main";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute AdministratorDTO administratorDTO) {
        log.info("delete Review completed");
        AdministratorEntity targetId = administratorRepository.findById(administratorDTO.getId()).orElse(null);
        log.info(targetId.toString());
        if (targetId != null) {
            administratorRepository.delete(targetId);
        }
        return "redirect:/admin/";
    }
}
