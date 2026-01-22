package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalService journalService;

    @GetMapping
    public ResponseEntity<?> getJournal(){
        List<JournalEntry> list = journalService.getAllJournalEntry();
        if(list != null){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> postJournal(@RequestBody JournalEntry myJournal){
        try{
            myJournal.setDate(LocalDateTime.now());
            return new ResponseEntity<>(journalService.saveJournalEntry(myJournal), HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getOneJournalById(@PathVariable ObjectId myId){
        Optional<JournalEntry> optionalJournalEntry = journalService.getJournalEntryById(myId);
        if(optionalJournalEntry.isPresent()){
            return new ResponseEntity<>(optionalJournalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id){
        journalService.deleteJournalEntryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
        Optional<JournalEntry> oldEntry = journalService.getJournalEntryById(id);
        if(oldEntry.get() != null){
            oldEntry.get().setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.get().getContent());
            oldEntry.get().setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.get().getTitle());
            return new ResponseEntity<>(journalService.saveJournalEntry(oldEntry.get()),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
