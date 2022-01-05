package outlandh.emr.tracking.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class TrackingSystemMongoConfig extends AbstractMongoClientConfiguration {

    @Value("${tracking-system.mongo.username:root}")
    private String username;

    @Value("${tracking-system.mongo.password:root}")
    private String password;

    @Value("${tracking-system.mongo.host:localhost}")
    private String host;

    @Value("${tracking-system.mongo.port:27017}")
    private String port;

    @Value("${tracking-system.mongo.database:TrackingSystem}")
    private String database;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        String connString = String.format(
                "mongodb://%s:%s@%s:%s/%s?authSource=admin",
                username,
                password,
                host,
                port,
                database
        );

        ConnectionString connectionString = new ConnectionString(connString);

        builder.applyConnectionString(connectionString);
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}
