package com.example.tripplanner.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightDestination;
import com.example.tripplanner.model.User;
import com.example.tripplanner.repository.UserRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<List<User>> users;
    private Amadeus amadeus;

    public UserViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new UserRepository(application);
        users = repository.getUsers();
        amadeus = Amadeus
                .builder("AGecnx7oQQSgayNVCMIdvnzbfcxSXxax", "xGdqMZWLDLGZxMPQ")
                .build();
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public User getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public AsyncTask<Void, Void, String> getLocations() {
        return new UserViewModel.TestAmadeus(this.amadeus).execute();
    }

//    private static class TestAmadeus extends AsyncTask<String, Void, String> {
//        private Amadeus amadeus;
//
//        private TestAmadeus(Amadeus userDAO) {
//            this.amadeus = amadeus;
//        }
//        @Override
//        protected String doInBackground(String... email) {
//            try {
//                FlightDestination[] locations = amadeus.shopping.flightDestinations.get(Params
//                        .with("origin", "MAD"));
//
//                System.out.println("xxxxxxx2" +locations[0].getResponse().getBody());
//                return locations[0].getResponse().getBody();
//            } catch (ResponseException e) {
//                System.out.println("xxxxxxx3");
//                e.printStackTrace();
//            }
//
//            System.out.println("xxxxxxx4");
//            return "null";
//        }
//    }

    private class TestAmadeus extends AsyncTask<Void, Void, String> {
        private Amadeus amadeus;

        private TestAmadeus(Amadeus amadeus) {
            this.amadeus = amadeus;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                FlightDestination[] locations = amadeus.shopping.flightDestinations.get(Params
                        .with("origin", "MAD"));

                System.out.println("yyyyyyyy2" +locations[0].getResponse().getBody());
                if(locations[0].getResponse().getBody() != null) {
                    return locations[0].getResponse().getBody().toString();
                }
            } catch (ResponseException e) {
                System.out.println("yyyyyy33");
                e.printStackTrace();
            }

            System.out.println("yyyyyyy4");
            return null;
        }
    }
}
