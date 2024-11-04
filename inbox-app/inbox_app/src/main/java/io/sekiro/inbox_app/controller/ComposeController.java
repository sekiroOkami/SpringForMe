package io.sekiro.inbox_app.controller;

import io.sekiro.inbox_app.inbox.folder.Folder;
import io.sekiro.inbox_app.inbox.folder.FolderRepository;
import io.sekiro.inbox_app.inbox.folder.FolderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ComposeController {

    private final FolderRepository folderRepository;
    private final FolderService folderService;

    public ComposeController(FolderRepository folderRepository, FolderService folderService) {
        this.folderRepository = folderRepository;
        this.folderService = folderService;
    }

    @GetMapping("/compose")
    public String getComposePage(
            @RequestParam( required = false) String to,
            @AuthenticationPrincipal OAuth2User principal,
            Model model
    ) {
        // principal contain information about currently login user
        if (principal == null || !StringUtils.hasText(principal.getAttribute("login"))) {
            return "index";
        }

        // Fetch folder
        String userId = principal.getAttribute("login");
        List<Folder> userFolders = folderRepository.findAllById(userId);
        model.addAttribute("userFolders", userFolders );
        List<Folder> defaultFolders = folderService.fetchDefaultFolders(userId);
        model.addAttribute("defaultFolders", defaultFolders );

        if (StringUtils.hasText(to)) {
            String[] splitIds = to.split(",");
            List<String> uniqueToIds = Arrays.stream(splitIds)
                    .map(StringUtils::trimWhitespace)
                    .filter(StringUtils::hasText)
                    .distinct()
                    .collect(Collectors.toList());
            model.addAttribute("toIds", String.join(", ", uniqueToIds));
        }

        return "compose-page";
    }

    public ModelAndView sendEmail(
            @RequestBody MultiValueMap<String, String> formData,
            @AuthenticationPrincipal OAuth2User principal
    ) {
        // principal contain information about currently login user
        if (principal == null || !StringUtils.hasText(principal.getAttribute("login"))) {
            return new ModelAndView("redirect:/");
        }

        String from = principal.getAttribute("login");
        String toIds = formData.getFirst("toIds");
        String subject = formData.getFirst("subject");
        String body = formData.getFirst("body");
        return new ModelAndView();
    }
}
