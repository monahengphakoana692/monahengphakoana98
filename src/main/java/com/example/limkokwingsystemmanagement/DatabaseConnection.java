package com.example.limkokwingsystemmanagement;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import static javax.management.Query.eq;

public class DatabaseConnection
{
    static MongoClient ServeSite = new MongoClient("localhost",27017);


    public static MongoDatabase getDatabaseConnection()
    {


        MongoDatabase LIMKOKWINGSYSTEMdatabase = ServeSite.getDatabase("LIMKOKWINGSYSTEM");

        return LIMKOKWINGSYSTEMdatabase;
    }

    public static MongoCollection getLIMKOKWINGSYSTEMdatabaseCollection(MongoDatabase connectedDatabase,String Role)//user roles are used as collection names
    {
        MongoCollection getUserData = connectedDatabase.getCollection(Role);//getting data from the collection

        return getUserData;
    }

    public static int write(Document details,String role)
    {

        MongoDatabase database = DatabaseConnection.getDatabaseConnection();//connecting to database and server

        MongoCollection collection = DatabaseConnection.getLIMKOKWINGSYSTEMdatabaseCollection(database,role);



        collection.insertOne(details);

        return -1;
    }

    static void close()
    {
        ServeSite.close();
    }

    public static DeleteResult delete(MongoCollection details, String role)
    {
        MongoDatabase database = DatabaseConnection.getDatabaseConnection();//connecting to database and server

        MongoCollection collection = DatabaseConnection.getLIMKOKWINGSYSTEMdatabaseCollection(database,role);

        Bson okay = (Bson) details;

       DeleteResult sub = collection.deleteOne(okay);

       return sub;
    }
}
