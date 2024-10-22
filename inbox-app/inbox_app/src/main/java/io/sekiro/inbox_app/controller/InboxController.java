package io.sekiro.inbox_app.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import io.sekiro.inbox_app.emaillist.EmailListItem;
import io.sekiro.inbox_app.emaillist.EmailListItemRepository;
import io.sekiro.inbox_app.inbox.folder.Folder;
import io.sekiro.inbox_app.inbox.folder.FolderRepository;
import io.sekiro.inbox_app.inbox.folder.FolderService;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("")
public class InboxController {

    private final FolderRepository folderRepository;
    private final FolderService folderService;

    private final EmailListItemRepository emailListItemRepository;

    public InboxController(FolderRepository folderRepository, FolderService folderService, EmailListItemRepository emailListItemRepository) {
        this.folderRepository = folderRepository;
        this.folderService = folderService;
        this.emailListItemRepository = emailListItemRepository;
    }

    @GetMapping("/folders")
    public String homePage(
            @RequestParam(required = false) String folder,
            @AuthenticationPrincipal OAuth2User principal,
            Model model
            ) {
        // principal contain information about currently login user
        if (principal == null || !StringUtils.hasText(principal.getAttribute("login"))) {
            return "index";
        }
        String userId = principal.getAttribute("login");
        List<Folder> userFolders = folderRepository.findAllById(userId);
        userFolders.stream().map(f -> f.getLabel()).forEach(System.out::println);
        model.addAttribute("userFolders", userFolders );
        List<Folder> defaultFolders = folderService.fetchDefaultFolders(userId);
        model.addAttribute("defaultFolders", defaultFolders );

        // Fetch message
        if (!StringUtils.hasText(folder)) {
            folder = "inbox";
        }
        List<EmailListItem> emailList = emailListItemRepository.findAllByKey_IdAndKey_Label(userId, folder);
        PrettyTime prettyTime = new PrettyTime();
        emailList.stream().forEach(emailListItem -> {
            UUID timeUUID = emailListItem.getKey().getTimeUUID();
            long l = Uuids.unixTimestamp(timeUUID);
            Date emailDateTime = new Date(l);
            emailListItem.setAgoTimeString(prettyTime.format(emailDateTime));
        });
        model.addAttribute("emailList", emailList);
        model.addAttribute("folderName", folder);
        return "inbox-page";

    }

    @GetMapping("/hello")
    public String hello() {
        return "test";
    }


}
