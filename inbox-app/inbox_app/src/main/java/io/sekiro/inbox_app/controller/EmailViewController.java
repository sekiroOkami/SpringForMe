package io.sekiro.inbox_app.controller;

import io.sekiro.inbox_app.email.Email;
import io.sekiro.inbox_app.email.EmailRepository;
import io.sekiro.inbox_app.inbox.folder.Folder;
import io.sekiro.inbox_app.inbox.folder.FolderRepository;
import io.sekiro.inbox_app.inbox.folder.FolderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class EmailViewController {

    private final FolderRepository folderRepository;
    private final FolderService folderService;

    private final EmailRepository emailRepository;

    public EmailViewController(FolderRepository folderRepository, FolderService folderService, EmailRepository emailRepository) {
        this.folderRepository = folderRepository;
        this.folderService = folderService;
        this.emailRepository = emailRepository;
    }

    @GetMapping("/emails/{id}")
    public String emailView(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable(name = "id") UUID id,
            Model model
    ) {
        // principal contain information about currently login user
        if (principal == null || !StringUtils.hasText(principal.getAttribute("login"))) {
            return "index";
        }
        String userId = principal.getAttribute("login");
        List<Folder> userFolders = folderRepository.findAllById(userId);
        model.addAttribute("userFolders", userFolders);
        List<Folder> defaultFolders = folderService.fetchDefaultFolders(userId);
        model.addAttribute("defaultFolders", defaultFolders);
        Optional<Email> optionalEmail = emailRepository.findById(id);
        if (!optionalEmail.isPresent()) {
            return "inbox-page";
        }
        Email email = optionalEmail.get();
        String toIds = String.join(", ", email.getTo());
        model.addAttribute("email", email);
        model.addAttribute("toIds", toIds);
        return "email-page";
    }

}
