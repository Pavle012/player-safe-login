package io.SousaLJ.playersafelogin.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class PlayerSafeLoginConfig {
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final ServerConfig SERVER;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        SERVER = new ServerConfig(builder);
        SERVER_SPEC = builder.build();
    }

    public static class ServerConfig {
        public final ForgeConfigSpec.EnumValue<StorageType> storageType;
        public final ForgeConfigSpec.ConfigValue<String> mysqlHost;
        public final ForgeConfigSpec.IntValue mysqlPort;
        public final ForgeConfigSpec.ConfigValue<String> mysqlUser;
        public final ForgeConfigSpec.ConfigValue<String> mysqlPassword;
        public final ForgeConfigSpec.ConfigValue<String> mysqlDbName;
        public final ForgeConfigSpec.ConfigValue<String> sqlitePath;

        public ServerConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("Configurações do servidor").push("server");

            storageType = builder
                    .comment("Tipo de armazenamento")
                    .defineEnum("storageType", StorageType.FILE);

            builder.comment("Configurações do MySQL").push("mysql");
            mysqlHost = builder.define("host", "localhost");
            mysqlPort = builder.defineInRange("port", 3306, 1, 65535);
            mysqlUser = builder.define("user", "root");
            mysqlPassword = builder.define("password", "");
            mysqlDbName = builder.define("database", "safelogin");
            builder.pop();

            builder.comment("Configurações do SQLite").push("sqlite");
            sqlitePath = builder.define("path", "./safelogin/safelogin.db");
            builder.pop();

            builder.pop();
        }
    }
}
