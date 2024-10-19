package io.sekiro.inbox_app;

import io.sekiro.inbox_app.inbox.folder.Folder;
import io.sekiro.inbox_app.inbox.folder.FolderRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InboxAppApplication {

	private final FolderRepository folderRepository;

	public InboxAppApplication(FolderRepository folderRepository) {
		this.folderRepository = folderRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(InboxAppApplication.class, args);
	}

	@PostConstruct
	public void init() {
		folderRepository.save(new Folder("sekiroOkami", "Inbox", "blue"));
		folderRepository.save(new Folder("sekiroOkami", "Sent", "green"));
		folderRepository.save(new Folder("sekiroOkami", "Important", "yellow"));
	}

}
