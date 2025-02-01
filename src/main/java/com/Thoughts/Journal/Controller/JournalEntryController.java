package com.Thoughts.Journal.Controller;

import com.Thoughts.Journal.Entity.JournalEntry;
import com.Thoughts.Journal.Service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")

public class JournalEntryController {
    @Autowired
    JournalEntryService journalEntryService;
    @GetMapping
    public ResponseEntity<?> getAllJournalEntries()
    {
        List<JournalEntry>journalEntries=journalEntryService.getAll();
        if(journalEntries!=null) {
           return new ResponseEntity<>(journalEntryService.getAll(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getEntry(@PathVariable ObjectId myId){
        JournalEntry journalEntry=journalEntryService.getById(myId);
        if(journalEntry!=null) {
            return new ResponseEntity<>(journalEntryService.getAll(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<?> addEntry(@RequestBody JournalEntry myentry)
    {
            boolean insertionSuccessful=journalEntryService.add(myentry);
            if(insertionSuccessful) {
                return new ResponseEntity<>(true, HttpStatus.CREATED);
            }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId,@RequestBody JournalEntry entry){

            JournalEntry journalEntry=journalEntryService.updateById(myId, entry);
        if(journalEntry!=null){
            return new ResponseEntity<>(journalEntry,HttpStatus.OK);
        }else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
            JournalEntry journalEntry=journalEntryService.remove(myId);
            if(journalEntry!=null){
            return new ResponseEntity<>(journalEntry,HttpStatus.OK);
        }else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
