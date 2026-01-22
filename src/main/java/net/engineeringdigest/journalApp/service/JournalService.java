package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.IJournalRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalService {
    @Autowired
    private IJournalRepo journalRepo;

    //This is the method who returns all the data from the database
    public List<JournalEntry> getAllJournalEntry(){
        return (List<JournalEntry>)journalRepo.findAll();
    }

    //This method is for getting one entry from the database based upon the id
    public Optional<JournalEntry> getJournalEntryById(ObjectId id){
        return journalRepo.findById(id);
    }

    //This is a save method for saving the entity in the database
    public JournalEntry saveJournalEntry(JournalEntry newEntry){
        return journalRepo.save(newEntry);
    }

    //This is a method which is used to delete an entity from the database based on the id
    public boolean deleteJournalEntryById(ObjectId id){
        journalRepo.deleteById(id);
        return true;
    }
}
