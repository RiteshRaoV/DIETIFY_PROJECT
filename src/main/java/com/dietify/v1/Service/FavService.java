package com.dietify.v1.Service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dietify.v1.Entity.Favourite;
import com.dietify.v1.Entity.User;
import com.dietify.v1.Repository.FavouriteRepo;

@Service
public class FavService {
    @Autowired
    FavouriteRepo favRepo;

    public Favourite saveFavourite(User user, ObjectId favouriteId, String type, String title) {
        Favourite favourite = new Favourite(user, favouriteId, type, title);
        return favRepo.save(favourite);
    }

    public List<Favourite> findByUserIdAndType(int userId, String type) {
        return favRepo.findByUser_IdAndType(userId, type);
    }
}
