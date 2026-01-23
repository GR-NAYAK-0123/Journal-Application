package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalService journalService;
    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getJournalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> list = user.getJournalEntries();
        if(list != null){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<?> postJournal(@RequestBody JournalEntry myJournal, @PathVariable String userName){
        try{
            journalService.saveJournalEntry(myJournal, userName);
            return new ResponseEntity<>(myJournal, HttpStatus.CREATED);
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

    @DeleteMapping("id/{userName}/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable String userName, @PathVariable ObjectId id){
        journalService.deleteJournalEntryById(userName, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry, @PathVariable String userName){
        Optional<JournalEntry> oldEntry = journalService.getJournalEntryById(id);
        if(oldEntry.isPresent()){
            oldEntry.get().setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.get().getContent());
            oldEntry.get().setTitle(!newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.get().getTitle());
            journalService.saveJournalEntry(oldEntry.get());
            return new ResponseEntity<>(oldEntry.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
