package com.Thoughts.Journal.Controller;

import com.Thoughts.Journal.Entity.JournalEntry;
import com.Thoughts.Journal.Service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")

public class JournalEntryController {
    @Autowired
    JournalEntryService journalEntryService;
    @GetMapping
    public ResponseEntity<?> getAllJournalEntries()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<JournalEntry> journalEntries = journalEntryService.getAll(userName);
        if(journalEntries!=null) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{journalId}")
    public ResponseEntity<?> getEntry(@PathVariable ObjectId journalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        JournalEntry journalEntry = journalEntryService.getById(userName, journalId);
        if(journalEntry!=null) {
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<?> addEntry(@RequestBody JournalEntry myentry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean insertionSuccessful = journalEntryService.add(userName, myentry);
            if(insertionSuccessful) {
                return new ResponseEntity<>(true, HttpStatus.CREATED);
            }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{myId}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId,@RequestBody JournalEntry entry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
//        List<JournalEntry>entries=authentication.
        JournalEntry journalEntry = journalEntryService.updateById(userName, myId, entry);
        if(journalEntry!=null){
            return new ResponseEntity<>(journalEntry,HttpStatus.OK);
        }else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        JournalEntry journalEntry = journalEntryService.remove(userName, myId);
            if(journalEntry!=null){
            return new ResponseEntity<>(journalEntry,HttpStatus.OK);
        }else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
