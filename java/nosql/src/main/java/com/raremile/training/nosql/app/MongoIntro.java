package com.raremile.training.nosql.app;

import com.mongodb.DB;
import com.raremile.training.nosql.dao.MongoConnector;


/**
 * Created by SriramKeerthi on 1/3/2016.
 */
public class MongoIntro
{
    private DB db = MongoConnector.getClient().getDB( "NoSQLIntro" );


    public DB getDb()
    {
        return db;
    }
}
