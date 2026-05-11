package io.SousaLJ.playersafelogin.network;

import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public interface IPlayerSafeLoginPacket {
    void handle(Supplier<NetworkEvent.Context> context);
}
