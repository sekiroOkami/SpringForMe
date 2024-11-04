package io.sekiro.inbox_app;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import io.sekiro.inbox_app.email.Email;
import io.sekiro.inbox_app.email.EmailRepository;
import io.sekiro.inbox_app.emaillist.EmailListItem;
import io.sekiro.inbox_app.emaillist.EmailListItemKey;
import io.sekiro.inbox_app.emaillist.EmailListItemRepository;
import io.sekiro.inbox_app.inbox.folder.Folder;
import io.sekiro.inbox_app.inbox.folder.FolderRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.stream.IntStream;

@SpringBootApplication
public class InboxAppApplication {

	private final FolderRepository folderRepository;

	private final EmailListItemRepository emailListItemRepository;

	private final EmailRepository emailRepository;

	public InboxAppApplication(FolderRepository folderRepository, EmailListItemRepository emailListItemRepository, EmailRepository emailRepository) {
		this.folderRepository = folderRepository;
		this.emailListItemRepository = emailListItemRepository;
		this.emailRepository = emailRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(InboxAppApplication.class, args);
	}

	@PostConstruct
	public void init() {
		folderRepository.save(new Folder("sekiroOkami", "Inbox", "blue"));
		folderRepository.save(new Folder("sekiroOkami", "Sent", "green"));
		folderRepository.save(new Folder("sekiroOkami", "Important", "yellow"));

		IntStream.range(0, 10)
				.mapToObj(i -> {
					EmailListItemKey key = new EmailListItemKey();
					key.setId("sekiroOkami");
					key.setLabel("Inbox");
					key.setTimeUUID(Uuids.timeBased());

					EmailListItem item = new EmailListItem();
					item.setKey(key);
					item.setTo(Arrays.asList("sekiroOkami", "Marika", "Radahn"));
					item.setSubject("Subject " + i);

					Email email = new Email();
					email.setId(key.getTimeUUID());
					email.setFrom("sekiroOkami");
					email.setSubject(item.getSubject());
					email.setBody("Body " + i);
					email.setTo(item.getTo());
					emailRepository.save(email);

					return item;
				})
				.forEach(emailListItemRepository::save);
	}

}
