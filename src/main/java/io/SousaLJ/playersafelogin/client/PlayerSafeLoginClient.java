package io.SousaLJ.playersafelogin.client;

import io.SousaLJ.playersafelogin.PlayerSafeLogin;
import io.SousaLJ.playersafelogin.client.gui.PasswordScreen;
import io.SousaLJ.playersafelogin.network.PlayerSafeLoginPacketHandler;
import io.SousaLJ.playersafelogin.network.payloads.PasswordAutenticationPayload;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;


@net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = PlayerSafeLogin.MODID, bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PlayerSafeLoginClient {
    public static boolean passwordScreenCompleted = ClientPasswordManager.hasSavedPassword();

   @SubscribeEvent
    public static void onClientStarted(ScreenEvent.Opening event) {
        if(!passwordScreenCompleted && !(event.getScreen() instanceof PasswordScreen)){
            Screen prev = event.getScreen();
            event.setNewScreen(new PasswordScreen(prev));
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(ClientPlayerNetworkEvent.LoggingIn event) {
        if(ClientPasswordManager.hasSavedPassword()){
            String storedHash = ClientPasswordManager.loadPassword();
            PasswordAutenticationPayload payload = new PasswordAutenticationPayload(event.getPlayer().getUUID(), storedHash);
            PlayerSafeLoginPacketHandler.sendToServer(payload);
            PlayerSafeLogin.LOGGER.info("Sending password to server");
        }
    }
}
