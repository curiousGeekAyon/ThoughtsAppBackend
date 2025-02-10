package com.Thoughts.Journal.Service;

import com.Thoughts.Journal.Entity.JournalEntry;
import com.Thoughts.Journal.Entity.User;
import com.Thoughts.Journal.Repository.JournalEntryRepository;
import com.Thoughts.Journal.Repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JournalEntryRepository journalEntryRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        try {
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            user.getRoles().add("USER");
            return userRepository.insert(user);
        } catch (Exception e) {
            return null;
        }
    }

    public User findUserByName(@NonNull String name) {
        return userRepository.findUserByName(name).orElse(null);
    }

    public User updateUser(User user) {
        User currUser = findUserByName(user.getName());
        if (currUser != null) {
            currUser.setName(user.getName());
            currUser.setPassword(passwordEncoder.encode(user.getPassword()));
            currUser.setRoles(user.getRoles());
            if (user.getJournalEntryList() != null && !user.getJournalEntryList().isEmpty()) {
                currUser.setJournalEntryList(user.getJournalEntryList());
            }
            userRepository.save(currUser);
        }
        return currUser;
    }

    public User updateUser(String userName, User user) {
        User currUser = findUserByName(userName);
        if (currUser != null) {
            currUser.setName(user.getName());
            currUser.setPassword(passwordEncoder.encode(user.getPassword()));
            currUser.setRoles(user.getRoles());
            if (user.getJournalEntryList() != null && !user.getJournalEntryList().isEmpty()) {
                currUser.setJournalEntryList(user.getJournalEntryList());
            }
            userRepository.save(currUser);
        }
        return currUser;
    }

    public User removeUser(String username) {
        User user = findUserByName(username);
        if (user == null) {
            return null;
        } else {
            List<JournalEntry> entries = user.getJournalEntryList();
            for (JournalEntry entry : entries) {
                journalEntryRepository.deleteById(entry.getId());
            }
            userRepository.delete(user);
            return user;
        }
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
