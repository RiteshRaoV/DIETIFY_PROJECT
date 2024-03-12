package com.dietify.v1.Repository;

import com.dietify.v1.Entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepo extends JpaRepository<Favourite, Long> {

    List<Favourite> findByFavouriteIdAndType(String favouriteId, String type);

}
