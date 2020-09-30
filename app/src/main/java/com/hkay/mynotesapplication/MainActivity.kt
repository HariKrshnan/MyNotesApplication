package com.hkay.mynotesapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.hkay.mynotesapplication.room.NotesEntity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.content_frame, FirstFragment()).commit()
    }

    /*val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "notes-list.db"
    ).build()
*/
    /*GlobalScope.launch {
        db.todoDao().insertAll(NotesEntity("1", "Content"))
        data = db.todoDao().getAll()

        data?.forEach {
            println(it)
        }
    }*/
}