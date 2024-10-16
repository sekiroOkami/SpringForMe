package io.sekiro.inbox_app.inbox.folder;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends CassandraRepository<Folder, String> {//<entity, partition key>
}
