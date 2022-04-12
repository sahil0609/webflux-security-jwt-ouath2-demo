package org.github.webflux.auth.multipleauth.config;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;




@Configuration
@EnableReactiveMongoRepositories
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    
    private final String databaseName;
    private final String basePackage;
    
    public MongoConfig(@Value("${mongo.databaseName}") String databaseName, @Value("${mongo.basePackage}") String basePackage) {
        this.databaseName = databaseName;
        this.basePackage = basePackage;
        
    }
    
    //connecting with the default client at localhost
    
    @Override
    protected String getDatabaseName() {
        
        return databaseName;
    }
    
    @Override
    protected Collection<String> getMappingBasePackages() {
        return List.of(basePackage);
    }
    

    
}
