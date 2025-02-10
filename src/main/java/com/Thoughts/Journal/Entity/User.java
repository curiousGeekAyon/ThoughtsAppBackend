package com.Thoughts.Journal.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Users")
@Data
@NoArgsConstructor
public class User {
    @Id
    ObjectId id;
    @NonNull
    @Indexed(unique = true)
    String name;
    @NonNull
    String password;
    @DBRef
    List<JournalEntry> journalEntryList = new ArrayList<>();
    List<String> roles = new ArrayList<>();
}
