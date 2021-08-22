package com.example.tripplanner.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.tripplanner.dao.UserDAO;
import com.example.tripplanner.model.User;
import com.example.tripplanner.room.TripPlannerDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository {
    private UserDAO userDAO;
    private LiveData<List<User>> users;

    public UserRepository(Application application) {
        TripPlannerDatabase database = TripPlannerDatabase.getInstance(application);
        userDAO = database.userDAO();
        users = userDAO.getUsers();
    }

    public void insert(User user) {
        new InsertUserAsyncTask(userDAO).execute(user);
    }

    public void update(User user) {
        new UpdateUserAsyncTask(userDAO).execute(user);
    }

    public void delete(User user) {
        new DeleteUserAsyncTask(userDAO).execute(user);
    }

    public User getUserByEmail(String email) {
        User user = null;
        try {
            return new GetUserByEmailAsyncTask(userDAO).execute(email).get();
        } catch (ExecutionException | InterruptedException ie){}
        return null;
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDAO userDAO;

        private InsertUserAsyncTask(UserDAO userDAO) {
            this.userDAO = userDAO;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDAO.insert(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDAO userDAO;

        private UpdateUserAsyncTask(UserDAO userDAO) {
            this.userDAO = userDAO;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDAO.update(users[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDAO userDAO;

        private DeleteUserAsyncTask(UserDAO userDAO) {
            this.userDAO = userDAO;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDAO.delete(users[0]);
            return null;
        }
    }

    private static class GetUserByEmailAsyncTask extends AsyncTask<String, Void, User> {
        private UserDAO userDAO;

        private GetUserByEmailAsyncTask(UserDAO userDAO) {
            this.userDAO = userDAO;
        }
        @Override
        protected User doInBackground(String... email) {
            return userDAO.getUserByEmail(email[0]);
        }
    }
}
