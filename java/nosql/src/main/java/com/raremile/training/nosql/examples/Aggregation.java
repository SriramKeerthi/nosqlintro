package com.raremile.training.nosql.examples;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.raremile.training.nosql.app.MongoIntro;

import java.util.*;


/**
 * Created by SriramKeerthi on 1/3/2016.
 */
public class Aggregation extends MongoIntro
{
    DBCollection collection = getDb().getCollection( "PhoneNumbers" );

    private static final List<String> FIRST_NAMES = Arrays
        .asList( "Ram", "Amit", "Jaskaran", "Samarth", "Dheeraj", "Pallav", "Girish", "Hina", "Rupa", "Sanjana" );

    private static final List<String> LAST_NAMES = Arrays.asList( "Singh", "Pathak", "Goyal", "Mehta", "Tandon",
        "Yadav", "Kumar", "Verma", "Rao", "Raj", "Bose" );


    private void generateData()
    {
        Random random = new Random();
        collection.drop();
        Set<String> names = new HashSet<>();
        while ( names.size() < ( FIRST_NAMES.size() * LAST_NAMES.size() * 0.8 ) ) {
            names.add( FIRST_NAMES.get( random.nextInt( FIRST_NAMES.size() ) ) + " " + LAST_NAMES.get( random.nextInt
                ( LAST_NAMES.size() ) ) );
        }
        for ( String name : names ) {
            Set<String> phoneNumbers = new HashSet<>();
            int numberCount = 1 + random.nextInt( 5 );
            while ( phoneNumbers.size() < numberCount ) {
                phoneNumbers.add( String.valueOf( 4000000000L + Math.abs( random.nextLong() ) % 5000000000L ) );
            }
            collection
                .insert( new BasicDBObject( "_id", name ).append( "name", name ).append( "phone", phoneNumbers ) );
        }
    }


    public void run()
    {
        List<DBObject> pipeline = new ArrayList<>();
        pipeline.add(
            new BasicDBObject( "$match", new BasicDBObject( "name", new BasicDBObject( "$regex", "(Ram|Amit|Rupa)" ) )
            ) );
        pipeline.add( new BasicDBObject( "$unwind", "$phone" ) );
        pipeline.add( new BasicDBObject( "$project", new BasicDBObject( "firstLetter", new BasicDBObject( "$substr", new
            Object[] { "$name", 0, 2 } ) ).append( "name", 1 ).append( "phone", 1 ) ) );
        pipeline.add( new BasicDBObject( "$group", new BasicDBObject( "_id", "$firstLetter" ).append( "numbers", new
            BasicDBObject( "$addToSet", "$phone" ) ) ) );
        for ( DBObject result : collection.aggregate( pipeline ).results() ) {
            System.out.println( result );
        }
    }


    public static void main( String[] args )
    {
        Aggregation aggr = new Aggregation();
        aggr.run();
    }
}
