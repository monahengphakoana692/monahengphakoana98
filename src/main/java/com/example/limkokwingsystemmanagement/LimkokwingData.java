package com.example.limkokwingsystemmanagement;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

public class LimkokwingData
{

    public static FindIterable<Document> fetchData(MongoCollection groupedData)
    {
        FindIterable<Document> dataIterms = groupedData.find();//fetching the whole data from the set


        return  dataIterms;
    }
}
