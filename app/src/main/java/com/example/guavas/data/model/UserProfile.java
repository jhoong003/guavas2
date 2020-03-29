package com.example.guavas.data.model;

/**
 * This Entity class implements the UserProfile entity.
 * Attributes: firstname, lastname, gender, date of birth (day,month,year), weight, height and email
 *
 * @author zane_
 */
public class UserProfile {
    private String firstName;
    private String lastName;
    private String gender;
    private String dobD;
    private String dobM;
    private String dobY;
    private double weight;
    private double height;
    private String email;

    public UserProfile() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDobD() {
        return dobD;
    }

    public void setDobD(String dobD) {
        this.dobD = dobD;
    }

    public String getDobM() {
        return dobM;
    }

    public void setDobM(String dobM) {
        this.dobM = dobM;
    }

    public String getDobY() {
        return dobY;
    }

    public void setDobY(String dobY) {
        this.dobY = dobY;
    }

//    public UserProfile getUserProfile(String phonenumber) {
//        DatabaseReference reff;
//        //final UserProfile Profile = new UserProfile();
//
//        UserProfile profile;
//        String result[];
//        reff = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(phonenumber);
//        reff.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
////                    firstName[0] = dataSnapshot.child("firstName").getValue().toString();
////                    lastName[0] = dataSnapshot.child("lastName").getValue().toString();
////                    gender[0] = dataSnapshot.child("gender").getValue().toString();
////                    dobD[0] = dataSnapshot.child("dobD").getValue().toString();
////                    dobM[0] = dataSnapshot.child("dobM").getValue().toString();
////                    dobY[0] = dataSnapshot.child("dobY").getValue().toString();
////                    Height[0] = dataSnapshot.child("height").getValue().toString();
////                    Weight[0] = dataSnapshot.child("weight").getValue().toString();
////                    Email[0] = dataSnapshot.child("email").getValue().toString();
//
//                    profile.setHeight(dataSnapshot.child("height").getValue().toString());
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//        return Profile;
//    }
//
//    public UserProfile setProfile(UserProfile profile, DataSnapshot dataSnapshot) {
//        profile.setFirstName(dataSnapshot.child("firstName").getValue().toString());
//        profile.setLastName(dataSnapshot.child("lastName").getValue().toString());
//        profile.setGender(dataSnapshot.child("gender").getValue().toString());
//        profile.setDobD(dataSnapshot.child("dobD").getValue().toString());
//        profile.setDobM(dataSnapshot.child("dobM").getValue().toString());
//        profile.setDobY(dataSnapshot.child("dobY").getValue().toString());
//
//        int height = Integer.parseInt(dataSnapshot.child("height").getValue().toString());
//        profile.setHeight(height);
//        int weight = Integer.parseInt(dataSnapshot.child("weight").getValue().toString());
//        profile.setWeight(weight);
//
//        profile.setEmail(dataSnapshot.child("email").getValue().toString());
//        return profile;
//    }
//
//    public boolean updateUserProfile(String phone, UserProfile userProfile) {
//        final boolean[] update = {false};
//        DatabaseReference reff;
//        reff = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(phone);
//        reff.setValue(userProfile);
//
//        reff.setValue(userProfile, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                if (databaseError != null) {
//                    update[0] = true;
//                } else {
//                    update[0] = false;
//                }
//            }
//        });
//        return update[0];
//    }
}

