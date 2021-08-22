package com.example.tripplanner.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tripplanner.dao.UserDAO;
import com.example.tripplanner.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class TripPlannerDatabase extends RoomDatabase {

    private static TripPlannerDatabase instance;

    public abstract UserDAO userDAO();

    public static synchronized TripPlannerDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TripPlannerDatabase.class, "trip_planner_database")
            .fallbackToDestructiveMigration()
            .addCallback(roomCallback)
            .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDAO userDAO;

        private PopulateDbAsyncTask(TripPlannerDatabase db) {
            this.userDAO = db.userDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            userDAO.insert(new User("Ionuc", "Michnea", "michneaionuc@gmail.com", "qwerty23"));
            return null;
        }
    }
}
