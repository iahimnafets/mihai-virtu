package com.thevirtugroup.postitnote.repository;


import com.thevirtugroup.postitnote.exception.ApiRequestException;
import com.thevirtugroup.postitnote.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
@Slf4j
public class UserRepository {

    private HashMap<String, User > myUserTable = new HashMap<>();

    public User findUserByUsername(String username){
        if (myUserTable.containsKey( username)){
            return myUserTable.get(username);
        }
        return null;
    }


    public User findById(Long id){
        User user = null;
        for(User val : myUserTable.values()){
            if( val.getId().longValue() == id.longValue()){
                user = val;
                break;
            }
        }
        return user;
    }

    public void saveUser(User user ){
        log.info( "saveUser-RUN {}", user.getUsername() );
        if(myUserTable.containsKey( user.getUsername() )){
            throw new ApiRequestException(" Insert another username for this user  ");
        }
        this.myUserTable.put( user.getUsername() , user);
    }


}
