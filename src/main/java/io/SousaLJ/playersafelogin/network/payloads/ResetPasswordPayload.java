package io.SousaLJ.playersafelogin.network.payloads;

import io.SousaLJ.playersafelogin.client.ClientPasswordManager;
import io.SousaLJ.playersafelogin.network.IPlayerSafeLoginPacket;
import io.SousaLJ.playersafelogin.network.PlayerSafeLoginPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public record ResetPasswordPayload(UUID playerId, String newPassword) implements IPlayerSafeLoginPacket {

    public ResetPasswordPayload(FriendlyByteBuf buf) {
        this(buf.readUUID(), buf.readUtf());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(playerId);
        buf.writeUtf(newPassword);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ClientPasswordManager.deleteSavedPassword();
            String hashedPassword = ClientPasswordManager.hashPassword(newPassword);
            ClientPasswordManager.savePassword(hashedPassword, this.newPassword);
            PasswordAutenticationPayload payload = new PasswordAutenticationPayload(playerId, hashedPassword);
            PlayerSafeLoginPacketHandler.sendToServer(payload);
        });
        context.setPacketHandled(true);
    }
}
