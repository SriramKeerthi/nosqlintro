package com.raremile.training.nosql.examples;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.raremile.training.nosql.app.MongoIntro;


/**
 * Demonstrate the following functionality:
 * 1. Insert
 * 2. Insert with ID
 * 3. Insert with Duplicate ID
 * 4. Save
 * 5. Find One
 * 6. Find
 * 7. Operators
 *    $ne
 *    $gt/$gte
 *    $lt/$lte
 *    $in
 *    $regex
 * 8. Update
 *    update
 *    $set
 *    $unset
 *    multi
 *    upsert
 * 9. Remove
 */
public class CreateCollection extends MongoIntro
{
    DBCollection collection = getDb().getCollection( "CRUDExample" );


    private void run()
    {
        collection.drop();
        collection.insert( new BasicDBObject( "value", "Hello World!" ) );
        collection.insert( new BasicDBObject( "value", "Hello World!" ) );
        collection.remove( new BasicDBObject( "_id", collection.findOne( new BasicDBObject( "value", "Hello World!" ) )
            .get( "_id" ) ) );
        System.out.println( collection.findOne() );
    }


    private void runWithId()
    {
        collection.drop();
        BasicDBObject obj = new BasicDBObject( "_id", 1 );
        obj.append( "value", "Hello!" );
        collection.insert( obj );
        System.out.println( collection.findOne() );
        obj.remove( "value" );
        obj.append( "val", "Hi!" );
        collection
            .update( new BasicDBObject( "_id", 1 ), new BasicDBObject( "$unset", new BasicDBObject( "value", "" ) ),
                true, true );
        System.out.println( collection.findOne() );
    }


    public static void main( String[] args )
    {
        new CreateCollection().runWithId();
    }
}
