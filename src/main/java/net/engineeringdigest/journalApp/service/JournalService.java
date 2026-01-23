package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.IJournalRepo;
import net.engineeringdigest.journalApp.repository.IUserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JournalService {
    @Autowired
    private IJournalRepo journalRepo;
    @Autowired
    private IUserRepo userRepo;
    @Autowired
    private UserService userService;

    //This is the method who returns all the data from the database
    public List<JournalEntry> getAllJournalEntry(){
        return (List<JournalEntry>)journalRepo.findAll();
    }

    //This method is for getting one entry from the database based upon the id
    public Optional<JournalEntry> getJournalEntryById(ObjectId id){
        return journalRepo.findById(id);
    }

    //This is a save method for saving the entity in the database
    public void saveJournalEntry(JournalEntry newEntry, String userName){
        try{
            User user = userRepo.findByUserName(userName);
            newEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalRepo.save(newEntry);
            user.getJournalEntries().add(saved);
            userRepo.save(user);
        }
        catch(Exception e){
            log.error("Exception", e);
        }
    }

    public void saveJournalEntry(JournalEntry newEntry){
        journalRepo.save(newEntry);
    }

    //This is a method which is used to delete an entity from the database based on the id
    public void deleteJournalEntryById(String userName, ObjectId id){
        User user = userRepo.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        journalRepo.deleteById(id);
    }
}
