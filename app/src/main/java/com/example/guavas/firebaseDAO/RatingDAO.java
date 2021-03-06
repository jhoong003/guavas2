package com.example.guavas.firebaseDAO;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.guavas.data.model.UserRatingData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Optional;

/**
 * This class holds the logic for saving rating to the database via firebase.
 */
public class RatingDAO implements com.example.guavas.firebaseDAO.MedsDAO<UserRatingData> {
    private DatabaseReference mDatabase;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Optional<UserRatingData> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<UserRatingData> getAll() {
        return null;
    }

    /**
     * Saves rating to the database.
     * @param userRatingData the rating to save.
     */
    @Override
    public void save(UserRatingData userRatingData) {
        mDatabase = FirebaseDatabase.getInstance().getReference("user-rating");
        mDatabase.child(userRatingData.getUserID()).setValue(userRatingData.getUserRating());
    }

    @Override
    public void update(UserRatingData userRatingData, String[] params) {
    }

    @Override
    public void delete(UserRatingData userRatingData) {
    }
}
