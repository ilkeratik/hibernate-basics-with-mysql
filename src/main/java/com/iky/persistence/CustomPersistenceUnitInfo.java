package com.iky.persistence;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import org.reflections.Reflections;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import static org.reflections.scanners.Scanners.SubTypes;

public class CustomPersistenceUnitInfo implements PersistenceUnitInfo {
    private final HikariDataSource ds = new HikariDataSource();
    private final List<String> entityClasses;
    public CustomPersistenceUnitInfo(){
        Properties properties = new Properties();
        try {
            InputStream input = CustomPersistenceUnitInfo.class
                    .getClassLoader().getResourceAsStream(("app.properties"));
            properties.load(input);
            ds.setJdbcUrl((String) properties.get("datasource_url"));
            ds.setUsername((String) properties.get("datasource_username"));
            ds.setPassword((String) properties.get("datasource_password"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // retrieve entity classes dynamically
        Reflections reflections = new Reflections("com.iky.entity", SubTypes.filterResultsBy( s -> true));
        entityClasses = reflections.getSubTypesOf(Object.class).stream().map(Class::getCanonicalName).toList();
    }

    @Override
    public String getPersistenceUnitName() {
        return "IkyPersistence";
    }

    @Override
    public String getPersistenceProviderClassName() {
        return "org.hibernate.jpa.HibernatePersistenceProvider";
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    public DataSource getJtaDataSource() {
        return ds;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return null;
    }

    @Override
    public List<String> getMappingFileNames() {
        return null;
    }

    @Override
    public List<URL> getJarFileUrls() {
        return null;
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }

    @Override
    public List<String> getManagedClassNames() {
        return entityClasses;
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return null;
    }

    @Override
    public ValidationMode getValidationMode() {
        return null;
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    @Override
    public void addTransformer(ClassTransformer classTransformer) {

    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }
}
