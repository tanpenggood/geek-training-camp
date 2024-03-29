package com.itplh.projects.user.configuration.demo;

import java.util.Arrays;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 19:39
 */
public class PreferencesTest {

    public static void main(String[] args) throws BackingStoreException {
        Preferences preferences = Preferences.userRoot();
        preferences.put("my-key", "Hello, World");
        preferences.flush();
        System.out.println(preferences.get("my-key", null));

        // all keys
        Arrays.stream(preferences.keys()).forEach(System.out::println);
        // all values
        Arrays.stream(preferences.childrenNames()).forEach(System.out::println);
    }

}
