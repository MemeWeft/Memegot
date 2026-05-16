package me.memeweft.paper.mongo;

import com.mongodb.client.MongoClient;
import me.memeweft.paper.config.ConfigService;
import net.minecraft.server.MinecraftServer;

import java.util.logging.Logger;

public class Mongo {

    private static final Logger LOGGER = Logger.getLogger("Memegot/Mongo");

    private static MongoClient client;

    public static void boot() {
        String uri = ConfigService.getGameConfig().mongoUri;
        client = MongoHandler.connect(uri);
        if (client == null) {
            LOGGER.severe("Could not connect to MongoDB. Shutting down server.");
            MinecraftServer.getServer().halt(false);
        }
    }

    public static void shutdown() {
        if (client != null) {
            MongoHandler.disconnect(client);
            client = null;
        }
    }

    static MongoClient getClient() {
        return client;
    }
}
