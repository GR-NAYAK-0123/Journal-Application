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
public class UserService {
    @Autowired
    private IUserRepo userRepo;

    //This is the method who returns all the data from the database
    public List<User> getAllUser(){
        return (List<User>)userRepo.findAll();
    }

    //This method is for getting one entry from the database based upon the id
    public Optional<User> getUserById(ObjectId id){
        return userRepo.findById(id);
    }

    //This is a save method for saving the entity in the database
    public void saveUser(User newUser){
        try{
            userRepo.save(newUser);
        }
        catch(Exception e){
            log.error("Exception", e);
        }
    }

    //This is a method which is used to delete an entity from the database based on the id
    public boolean deleteUserById(ObjectId id){
        userRepo.deleteById(id);
        return true;
    }

    //This method is used to get a User Entity from the database based on the userName
    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }
}
