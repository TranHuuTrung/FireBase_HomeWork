package com.example.admin.firebase_homework;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Admin on 10/14/2017.
 */

public class SampleData {
    public static ArrayList<JournalEntry> getSampleJournalEntries() {
        ArrayList<JournalEntry> journalEnrties = new ArrayList<>();
        long dateModified = Calendar.getInstance().getTimeInMillis();

        JournalEntry journalEntry1 = new JournalEntry("", "DisneyLand Trip", "Content1", 0, dateModified);
        JournalEntry journalEntry2 = new JournalEntry("", "Gym Work Out", "Content2", 0, dateModified);
        JournalEntry journalEntry3 = new JournalEntry("", "Blog Post Idea", "Content3", 0, dateModified);
        JournalEntry journalEntry4 = new JournalEntry("", "Cupcake Recipe", "Content4", 0, dateModified);
        JournalEntry journalEntry5 = new JournalEntry("", "Networking Event", "Content5", 0, dateModified);
        journalEnrties.add(journalEntry1);
        journalEnrties.add(journalEntry2);
        journalEnrties.add(journalEntry3);
        journalEnrties.add(journalEntry4);
        journalEnrties.add(journalEntry5);
        return journalEnrties;
    }
}
