package com.Thoughts.Journal.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@Document(collection = "journalentries")
@Data
public class JournalEntry {
    @Id
    ObjectId id;
    String title;
    String  description;
    LocalDateTime date;

    public JournalEntry(){
    }

    public JournalEntry(ObjectId id) {
        this.id = id;
    }

}
