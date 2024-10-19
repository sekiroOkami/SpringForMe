package io.sekiro.inbox_app.controller;

import io.sekiro.inbox_app.inbox.folder.Folder;
import io.sekiro.inbox_app.inbox.folder.FolderRepository;
import io.sekiro.inbox_app.inbox.folder.FolderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class InboxController {

    private final FolderRepository folderRepository;
    private final FolderService folderService;

    public InboxController(FolderRepository folderRepository, FolderService folderService) {
        this.folderRepository = folderRepository;
        this.folderService = folderService;
    }

    @GetMapping("/folders")
    public String homePage(
            @AuthenticationPrincipal OAuth2User principal,
            Model model
            ) {
        // principal contain information about currently login user
        if (principal == null || !StringUtils.hasText(principal.getAttribute("login"))) {
            return "index";
        } else {
            String userId = principal.getAttribute("login");
            List<Folder> userFolders = folderRepository.findAllById(userId);
            userFolders.stream().map(folder -> folder.getLabel()).forEach(System.out::println);
            model.addAttribute("userFolders", userFolders );
            List<Folder> defaultFolders = folderService.fetchDefaultFolders(userId);
            model.addAttribute("defaultFolders", defaultFolders );
            return "inbox-page";
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "test";
    }


}
