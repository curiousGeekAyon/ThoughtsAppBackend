package com.Thoughts.Journal.Service;

import com.Thoughts.Journal.Entity.JournalEntry;
import com.Thoughts.Journal.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalEntryService {

    @Autowired
    JournalEntryRepository repository;

    public List<JournalEntry> getAll() {
       return repository.findAll();
    }

    public JournalEntry getById(ObjectId myId) {
       return  repository.findById(myId).orElse(null);
    }

    public boolean add(JournalEntry myentry) {
        try {
            repository.insert(myentry);
            myentry.setDate(LocalDateTime.now());
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public JournalEntry updateById(ObjectId myId, JournalEntry entry) {
        JournalEntry oldJournalEntry=getById(myId);
        if(oldJournalEntry!=null)
        {
            String newDescription=entry.getDescription();
            String newTitle= entry.getTitle();
            boolean changed=false;
            if(newDescription!=null&&!newDescription.isEmpty())
            {
                oldJournalEntry.setDescription(newDescription);
                changed=true;
            }
            if(newTitle!=null&&!newTitle.isEmpty())
            {
                oldJournalEntry.setTitle(newTitle);
                changed=true;
            }
            if(changed)
            {
                repository.save(oldJournalEntry);
            }
        }
        return oldJournalEntry;
    }

    public JournalEntry remove(ObjectId myId) {
       JournalEntry deletedJournalEntry=repository.findById(myId).orElse(null);
       repository.deleteById(myId);
       return deletedJournalEntry;
    }
}
