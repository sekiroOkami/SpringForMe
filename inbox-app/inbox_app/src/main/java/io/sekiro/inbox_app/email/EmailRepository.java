package io.sekiro.inbox_app.email;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface EmailRepository extends CassandraRepository<Email, UUID> {
}
