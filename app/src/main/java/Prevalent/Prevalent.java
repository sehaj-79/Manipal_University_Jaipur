package Prevalent;


import com.google.firebase.database.DatabaseReference;

import Model.Users;

public class Prevalent
{

    public static DatabaseReference reference;
    public static Users currentOnlineUser;

    public static final String UserPhoneKey = "UserPhone";
    public static final String UserPasswordKey = "UserPassword";
    public static  final String UserBlood = "UserBlood";
    public static  final String UserEmail = "UserEmail";
    public static final String Userid = "Userid";
}