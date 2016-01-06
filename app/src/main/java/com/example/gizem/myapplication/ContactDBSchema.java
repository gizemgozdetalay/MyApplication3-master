package com.example.gizem.myapplication;

/**
 * Created by Gizem on 03.01.2016.
 */
public final class ContactDBSchema {
    public static final class ContactTable{
        public static final String NAME = "contacts";
        public static final class Cols{
            public static final String NAME = "contactName";
            public static final String NUMBER = "contactNumber";
            public static final String EMAIL = "email";
            public static final String LOCATION = "location";
            public static final String INCOMING = "incomingCall";
            public static final String OUTCALL = "outgoingCall";
            public static final String MISSED = "missedCall";
            public static final String SENDMESS = "sendingMessage";
            public static final String COMEMES = "comingMessage";
        }
    }
}