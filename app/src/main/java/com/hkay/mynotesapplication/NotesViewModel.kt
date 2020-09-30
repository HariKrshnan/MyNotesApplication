package com.hkay.mynotesapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hkay.mynotesapplication.room.NotesEntity
import com.hkay.mynotesapplication.room.NotesRepository
import com.hkay.mynotesapplication.room.NotesRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotesRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<NotesEntity>>

    init {
        val wordsDao = NotesRoomDatabase.getDatabase(application, viewModelScope).noteDao()
        repository = NotesRepository(wordsDao)
        allWords = repository.allWords
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: NotesEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
    
    fun update(word: NotesEntity) = viewModelScope.launch(Dispatchers.IO){
        repository.update(word)
    }
    fun delete(word: NotesEntity) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(word)
    }
}