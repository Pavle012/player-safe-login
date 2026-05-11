package io.SousaLJ.playersafelogin.network.payloads;

import io.SousaLJ.playersafelogin.PlayerSafeLogin;
import io.SousaLJ.playersafelogin.network.IPlayerSafeLoginPacket;
import io.SousaLJ.playersafelogin.util.AuthLog;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public record PasswordAutenticationPayload(UUID playerId, String hashedPassword) implements IPlayerSafeLoginPacket {

    public PasswordAutenticationPayload(FriendlyByteBuf buf) {
        this(buf.readUUID(), buf.readUtf());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(playerId);
        buf.writeUtf(hashedPassword);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (PlayerSafeLogin.getStorage().requiresInitialSetup(playerId)) {
                PlayerSafeLogin.getStorage().setupPassword(playerId, hashedPassword);
                PlayerSafeLogin.LOGGER.info("Password set up for player: " + playerId);
            } else {
                boolean isValid = PlayerSafeLogin.getStorage().validatePassword(playerId, hashedPassword);
                if (!isValid) {
                    ServerPlayer serverPlayer = context.getSender();

                    if (serverPlayer != null) {
                        /*
                        * Início do registro do log de tentativas de login
                        * */
                        String playerName = serverPlayer.getGameProfile().getName();
                        String uuid = serverPlayer.getUUID().toString();
                        String ip = serverPlayer.connection.connection.getRemoteAddress().toString();
                        AuthLog.logFailedAttempt(playerName, uuid, ip);
                        /*
                        * Fim do registro do log de tentativas de login
                        * */

                        serverPlayer.connection.disconnect(Component.translatable("playersafelogin.kick.invalid_password"));
                    } else {
                        PlayerSafeLogin.LOGGER.error("Jogador nulo ao tentar desconectar por senha inválida.");
                    }
                } else {
                    PlayerSafeLogin.LOGGER.info("Password validated for player: " + playerId);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
