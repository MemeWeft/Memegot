package me.memeweft.paper.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.event.ServerHeartbeatFailedEvent;
import com.mongodb.event.ServerHeartbeatSucceededEvent;
import com.mongodb.event.ServerMonitorListener;
import org.bson.Document;
import org.jspecify.annotations.NonNull;

import java.util.logging.Logger;

public class MongoHandler {

    private static final Logger LOGGER = Logger.getLogger("Memegot/Mongo");

    private static boolean wasDisconnected = false;

    static MongoClient connect(String uri) {
        MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(uri))
            .applyToServerSettings(builder -> builder.addServerMonitorListener(new ServerMonitorListener() {
                @Override
                public void serverHeartbeatSucceeded(@NonNull ServerHeartbeatSucceededEvent event) {
                    if (wasDisconnected) {
                        LOGGER.info("MongoDB connection restored.");
                        wasDisconnected = false;
                    }
                }

                @Override
                public void serverHeartbeatFailed(@NonNull ServerHeartbeatFailedEvent event) {
                    if (!wasDisconnected) {
                        LOGGER.warning("MongoDB heartbeat failed — connection lost.");
                        wasDisconnected = true;
                    }
                }
            }))
            .build();

        MongoClient client = MongoClients.create(settings);
        if (!healthCheck(client)) {
            client.close();
            return null;
        }
        LOGGER.info("MongoDB connected successfully.");
        return client;
    }

    private static boolean healthCheck(MongoClient client) {
        try {
            client.getDatabase("admin").runCommand(new Document("ping", 1));
            return true;
        } catch (Exception e) {
            LOGGER.severe("MongoDB health check failed: " + e.getMessage());
            return false;
        }
    }

    static boolean isConnected() {
        MongoClient client = Mongo.getClient();
        if (client == null) return false;
        try {
            client.getDatabase("admin").runCommand(new Document("ping", 1));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static void disconnect(MongoClient client) {
        client.close();
        LOGGER.info("MongoDB connection closed.");
    }
}
