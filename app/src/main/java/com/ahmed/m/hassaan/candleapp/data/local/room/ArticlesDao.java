package com.ahmed.m.hassaan.candleapp.data.local.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ahmed.m.hassaan.candleapp.data.pojo.Article;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Article.class)
    Completable insertArticle(Article article);

    @Query("select * from articles order by id desc limit 1")
    Single<Article> getArticle();

    @Query("delete from articles")
    Completable deleteArticles();
}
