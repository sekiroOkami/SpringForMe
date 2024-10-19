package io.sekiro.inbox_app.inbox.folder;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends CassandraRepository<Folder, String> {//<entity, partition key>

//    @Query("""
//            SELECT * FROM folders_by_user WHERE id= :id
//            """) @Param("id")
    List<Folder> findAllById( String userId);
}
