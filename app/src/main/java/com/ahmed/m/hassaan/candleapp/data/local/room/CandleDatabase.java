package com.ahmed.m.hassaan.candleapp.data.local.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ahmed.m.hassaan.candleapp.data.pojo.Article;
import com.ahmed.m.hassaan.candleapp.utils.App;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class CandleDatabase extends RoomDatabase {

    private static CandleDatabase instance;

    public abstract ArticlesDao articlesDao();

    public static synchronized CandleDatabase getInstance() {
        if (instance == null)
            instance =
                    Room
                            .databaseBuilder(App.mACTIVITY.getApplicationContext(), CandleDatabase.class, "CandleDb")
                            .fallbackToDestructiveMigration()
                            .build();


        return instance;
    }


}
