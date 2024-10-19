package io.sekiro.inbox_app.inbox.folder;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "folders_by_user")
public class Folder {
    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String id;
    @PrimaryKeyColumn(name = "label", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
    private String label;

    public Folder() {
    }

    public Folder(String userId, String label, String color) {
        this.id = userId;
        this.label = label;
        this.color = color;
    }

    @CassandraType(type = CassandraType.Name.TEXT)
    private String color;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
