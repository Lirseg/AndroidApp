package com.example.myapplication.data;

import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser admin = null;

            if (username.equals("admin1") && password.equals("admin1")){
                admin =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                "Welcome Admin");
                return new Result.Success<>(admin);
            }
//            EditText userName = (EditText) getActivity().findViewById(R.id.username);
//            LoggedInUser fakeUser =
//                    new LoggedInUser(
//                            java.util.UUID.randomUUID().toString(),
//                            "Jane Doe");
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Welcome fake");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}