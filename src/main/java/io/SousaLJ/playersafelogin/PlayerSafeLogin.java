package io.SousaLJ.playersafelogin;

import com.mojang.logging.LogUtils;
import io.SousaLJ.playersafelogin.commands.PlayerSafeLoginCommands;
import io.SousaLJ.playersafelogin.config.PlayerSafeLoginConfig;
import io.SousaLJ.playersafelogin.network.PlayerSafeLoginPacketHandler;
import io.SousaLJ.playersafelogin.storage.FileStorage;
import io.SousaLJ.playersafelogin.storage.MySQLStorage;
import io.SousaLJ.playersafelogin.storage.SQLiteStorage;
import io.SousaLJ.playersafelogin.storage.StorageProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(PlayerSafeLogin.MODID)
public class PlayerSafeLogin
{
    public static final String MODID = "playersafelogin";

    public static final Logger LOGGER = LogUtils.getLogger();

    private static StorageProvider storage;

    public PlayerSafeLogin() {

        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.addListener(PlayerSafeLoginCommands::registerCommands);

        PlayerSafeLoginPacketHandler.register();

        net.minecraftforge.fml.ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, PlayerSafeLoginConfig.SERVER_SPEC);
    }


    @SubscribeEvent
    private void onServerStarting(ServerStartingEvent event) {
        switch (PlayerSafeLoginConfig.SERVER.storageType.get()) {
            case FILE -> storage = new FileStorage();
            case MYSQL -> storage = createMySQLStorage();
            case SQLITE -> storage = new SQLiteStorage(PlayerSafeLoginConfig.SERVER.sqlitePath.get());
        }
    }
    public static ResourceLocation rl(String path) {
        return new ResourceLocation(PlayerSafeLogin.MODID, path);
    }

    private StorageProvider createMySQLStorage() {
        return new MySQLStorage(
                PlayerSafeLoginConfig.SERVER.mysqlHost.get(),
                PlayerSafeLoginConfig.SERVER.mysqlPort.get(),
                PlayerSafeLoginConfig.SERVER.mysqlUser.get(),
                PlayerSafeLoginConfig.SERVER.mysqlPassword.get(),
                PlayerSafeLoginConfig.SERVER.mysqlDbName.get()
        );
    }

    public static StorageProvider getStorage() {
        return storage;
    }
}
