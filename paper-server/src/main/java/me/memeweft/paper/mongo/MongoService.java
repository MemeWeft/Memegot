package me.memeweft.paper.mongo;

import com.mongodb.client.MongoDatabase;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class MongoService {

    public static MongoDatabase getDatabase(String name) {
        return Mongo.getClient().getDatabase(name);
    }

    public static <T> CompletableFuture<T> async(Function<MongoService, T> task) {
        return CompletableFuture.supplyAsync(() -> task.apply(null));
    }

    public static boolean isConnected() {
        return MongoHandler.isConnected();
    }
}
