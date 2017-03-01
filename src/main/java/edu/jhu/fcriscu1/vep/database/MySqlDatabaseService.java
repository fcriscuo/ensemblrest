package edu.jhu.fcriscu1.vep.database;
import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.ConnectionProviderFromUrl;
import com.github.davidmoten.rx.jdbc.Database;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import edu.jhu.fcriscu1.vep.util.PropertiesLoader;
import org.apache.log4j.Logger;
import scala.Tuple4;

/**
 * Created by fcriscuo on 10/18/15.
 */


public enum MySqlDatabaseService {
    INSTANCE;
    private static final Logger logger = Logger.getLogger(MySqlDatabaseService.class);
    private final Database db  = Suppliers.memoize(new MySqlDatabaseSupplier()).get();

    public Database database() { return this.db;}

    public void closeDatabase(){ this.db.close();}

private class MySqlDatabaseSupplier implements Supplier<Database> {

    private final String hostName;
    private final String user;
    private final String password;
    private final String database;
    private final String driver = "com.mysql.jdbc.Driver";
    private final Database db;

    public MySqlDatabaseSupplier() {
        this.hostName = PropertiesLoader.getInstance().getDbHost();
        this.database = PropertiesLoader.getInstance().getDbPortalDbName();
        this.user = PropertiesLoader.getInstance().getDbUser();
        this.password = PropertiesLoader.getInstance().getDbPassword();
        this.db = resolveDatabase();
        System.out.println("Connection to " + this.database + " established using properties");
    }

    public MySqlDatabaseSupplier(Tuple4<String, String, String, String> host_database_user_pw_tuple) {
        this.hostName = host_database_user_pw_tuple._1();
        this.database = host_database_user_pw_tuple._2();
        this.user = host_database_user_pw_tuple._3();
        this.password = host_database_user_pw_tuple._4();
        this.db = resolveDatabase();
        System.out.println("Connection to " + this.database + " established using parameters");
    }

    private Database resolveDatabase() {
        String connUrl = new StringBuilder("jdbc:mysql://")
                .append(this.hostName)
                .append("/")
                .append(database)
                .append("?user=")
                .append(this.user)
                .append("&password=")
                .append(password).toString();
        ConnectionProvider provider = new ConnectionProviderFromUrl(connUrl);
        System.out.println("Connection to " + connUrl + " established");
        return Database.builder().connectionProvider(provider).build();

    }

    @Override
    public Database get() {
        return this.db;
    }

}
    public static void main (String...args){
      Database db = MySqlDatabaseService.INSTANCE.database();
        logger.info("Database conneted");
       MySqlDatabaseService.INSTANCE.closeDatabase();
        logger.info("Database close");
    }
}

