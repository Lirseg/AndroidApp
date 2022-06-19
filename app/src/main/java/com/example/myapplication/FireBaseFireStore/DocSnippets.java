package com.example.myapplication.FireBaseFireStore;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.myapplication.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class DocSnippets {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void userScheduleTime(String user, String date, String sTime, String fTime){
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        data.put("date", date);
        data.put("sTime", sTime);
        data.put("fTime", fTime);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }


    public static void addUser(String email, String city, ArrayList available) {
        // [START add_ada_lovelace]
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("city", city);

        // Add a new document with a generated ID
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        // [END add_ada_lovelace]
    }

    public static void addEvent(String name, String date, String startTime,String finishTime,String peopleNeeded) {
        // [START add_ada_lovelace]
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        List<String> group = new ArrayList<>();
        user.put("name", name);
        user.put("peopleNeeded", peopleNeeded);
        user.put("date", date);
        user.put("startTime", startTime);
        user.put("endTime", finishTime);
        user.put("signedVolun",group);


        // Add a new document with a generated ID
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String string = name;
        db.collection("events")
                .document(string).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " );
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        // [END add_ada_lovelace]
    }


    public static List eventListMain;
    public List list2 = new ArrayList();

    public static void onDataLoaded(List eventList){
        List l;

        l=eventList;

    }

    public static boolean isEventFull(String name, TextView t) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Boolean result = false;
        db.collection("events").document(name).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String x = task.getResult().getData().get("peopleNeeded").toString();
                List<String> group = (List<String>) task.getResult().getData().get("signedVolun");

                System.out.println(group.size());

                if(Integer.parseInt(x) <= group.size())
                    t.setTextColor(Color.parseColor("#228B22") );
            }
        });
    return result;
    }



    public void updateList(List list){
        this.list2=list;
    }


    public int flag =1;

    public List<Object> getAllEvents(List<Object> list)  {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Object> numbers = new ArrayList<>();
        DocSnippets d = this;

        OnCompleteListener<QuerySnapshot> myListener= new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getData().get("name"));
                        Log.d(TAG, document.getId() + " => " + document.getData().get("name"));
                    }
                    Log.w(TAG, "Here we are finished"+list);
                    final List l = list;
                    onDataLoaded(list);
                    Log.w(TAG, "Here is main"+eventListMain);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        };
        db.collection("events")
                . get()
                .addOnCompleteListener(myListener).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                onDataLoaded(list);
                Log.w(TAG, "now were finished"+list);
                d.updateList(list);
                flag =0;
            }
        });

        Log.w(TAG, "Error getting documents."+list);

        return list;

    }



    public static void remove_event(String name){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("events").document(name)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });






//        db.collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        if(document.getId() == name) {
//                            Log.d(TAG, document.getId() + " => " + document.getData());
//                            db.collection("users").document(name).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
//                                }
//                            });
//                        }
//                    }
//                } else {
//                    Log.w(TAG, "Error getting documents.", task.getException());
//                }
//            }
//        } ) ;

    }


    public static void editEvent(String name, String date, String startTime,String finishTime,String peopleNeeded){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();

        db.collection("events").document(name).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                List<String> list = (List<String>) task.getResult().getData().get("signedVolun");
                user.put("name", name);
                user.put("peopleNeeded", peopleNeeded);
                user.put("date", date);
                user.put("startTime", startTime);
                user.put("endTime", finishTime);
                user.put("signedVolun",list );
                db.collection("events").document(name).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " );
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
        });



    }




}


