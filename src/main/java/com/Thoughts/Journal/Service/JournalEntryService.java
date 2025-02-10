package com.Thoughts.Journal.Service;

import com.Thoughts.Journal.Entity.JournalEntry;
import com.Thoughts.Journal.Entity.User;
import com.Thoughts.Journal.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalEntryService {

    @Autowired
    JournalEntryRepository repository;
    @Autowired
    UserService userService;

    public List<JournalEntry> getAll(String userName) {
        User user = userService.findUserByName(userName);
        if (user == null) {
            return null;
        }
        return user.getJournalEntryList();
    }

    public JournalEntry getById(String userName, ObjectId myId) {
        User user = userService.findUserByName(userName);
        List<JournalEntry> entries = user.getJournalEntryList();
        for (JournalEntry journalEntry : entries) {
            if (journalEntry.getId().equals(myId)) {
                return repository.findById(myId).orElse(null);
            }
        }
        return null;
    }

    @Transactional
    public boolean add(String userName, JournalEntry myentry) {
        try {
            User user = userService.findUserByName(userName);
            if (user == null) {
                return false;
            }
            myentry.setDate(LocalDateTime.now());
            List<JournalEntry> journalEntries = user.getJournalEntryList();
            JournalEntry addedEntry = repository.insert(myentry);
            journalEntries.add(addedEntry);
            userService.saveUser(user);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Transactional
    public JournalEntry updateById(String userName, ObjectId myId, JournalEntry entry) {
        JournalEntry oldJournalEntry = getById(userName, myId);
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

    @Transactional
    public JournalEntry remove(String userName, ObjectId myId) {
        User user = userService.findUserByName(userName);
        if (user == null) {
            return null;
        }
        user.getJournalEntryList().removeIf(x -> x.getId().equals(myId));
        userService.saveUser(user);
       JournalEntry deletedJournalEntry=repository.findById(myId).orElse(null);
        if (deletedJournalEntry == null) {
            return null;
        }
       repository.deleteById(myId);
       return deletedJournalEntry;
    }
}
