package io.SousaLJ.playersafelogin.network;

import io.SousaLJ.playersafelogin.PlayerSafeLogin;
import io.SousaLJ.playersafelogin.network.payloads.PasswordAutenticationPayload;
import io.SousaLJ.playersafelogin.network.payloads.ResetPasswordPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PlayerSafeLoginPacketHandler {
    private static final String PROTOCOL_VERSION = "1.0";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(PlayerSafeLogin.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;
        INSTANCE.registerMessage(id++, PasswordAutenticationPayload.class, PasswordAutenticationPayload::encode, PasswordAutenticationPayload::new, PasswordAutenticationPayload::handle);
        INSTANCE.registerMessage(id++, ResetPasswordPayload.class, ResetPasswordPayload::encode, ResetPasswordPayload::new, ResetPasswordPayload::handle);
    }

    public static void sendToServer(Object msg) {
        INSTANCE.sendToServer(msg);
    }

    public static void sendToClient(Object msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}
