package com.hkay.mynotesapplication.room

import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class NotesRepository(private val wordDao: NotesDAO) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<NotesEntity>> = wordDao.getAlphabetizedWords()

    suspend fun insert(note: NotesEntity) {
        wordDao.insert(note)
    }
    fun update(note: NotesEntity) {
        wordDao.update(note)
    }

    fun delete(note: NotesEntity) {
        wordDao.delete(note)
    }

}