package com.hkay.mynotesapplication.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDAO {
    @Query("SELECT * FROM notes")
    fun getAll(): List<NotesEntity>

    @Query("SELECT * FROM notes WHERE textContent LIKE :textContent")
    fun findByTitle(textContent: String): NotesEntity

    @Insert
    fun insertAll(vararg todo: NotesEntity)

    @Delete
    fun delete(todo: NotesEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg word: NotesEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: NotesEntity)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()

    @Query("SELECT * from notes ORDER BY textContent ASC")
    fun getAlphabetizedWords(): LiveData<List<NotesEntity>>

}