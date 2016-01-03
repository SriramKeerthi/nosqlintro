package com.raremile.training.nosql.dao;

import com.mongodb.MongoClient;


/**
 * Created by SriramKeerthi on 1/3/2016.
 */
public class MongoConnector
{
    private static final String MONGO_URL = "localhost";


    public static MongoClient getClient()
    {
        return new MongoClient( MONGO_URL );
    }
}
