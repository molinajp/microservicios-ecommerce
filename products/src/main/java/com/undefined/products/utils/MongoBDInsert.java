package com.undefined.products.utils;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

@Component
public class MongoBDInsert {

    @Value("${database.url}")
    private String uri;

    private static String uriStatic;

    public static void insertData() {
        // crear datos en db mongo
        boolean dbIsCreated = false;
        MongoClient client = MongoClients.create(uriStatic);

        MongoIterable<String> databaseNames = client.listDatabaseNames();
        for (String name : databaseNames) {
            if (name.equals("globallogic-proyect")) {
                dbIsCreated = true;
            }
        }
        if (!dbIsCreated) {
            MongoDatabase database = client.getDatabase("globallogic-proyect");

            MongoCollection<Document> collection = database.getCollection("Products");

            Document product01 = Document.parse("{_id: 1, description: 'Milk', stock: 597, price: 2748}");
            Document product02 = Document.parse("{_id: 2, description: 'Cheese', stock: 803, price: 3630}");
            Document product03 = Document.parse("{_id: 3, description: 'Table', stock: 109, price: 2481}");
            Document product04 = Document.parse("{_id: 4, description: 'Snacks', stock: 886, price: 1872}");
            Document product05 = Document.parse("{_id: 5, description: 'Meal', stock: 23, price: 2014}");
            Document product06 = Document.parse("{_id: 6, description: 'Cake', stock: 993, price: 1041}");
            Document product07 = Document.parse("{_id: 7, description: 'Cookies', stock: 50, price: 3448}");
            Document product08 = Document.parse("{_id: 8, description: 'Ice cream', stock: 799, price: 1376}");
            Document product09 = Document.parse("{_id: 9, description: 'Ball', stock: 396, price: 1169}");
            Document product10 = Document.parse("{_id: 10, description: 'Baby', stock: 109, price: 1759}");

            collection.insertMany(
                    List.of(product01, product02, product03, product04, product05, product06,
                            product07, product08, product09, product10));
        }
        client.close();
    }

    @Value("${database.url}")
    public void setUriStatic(String uri) {
        MongoBDInsert.uriStatic = uri;
    }
}
