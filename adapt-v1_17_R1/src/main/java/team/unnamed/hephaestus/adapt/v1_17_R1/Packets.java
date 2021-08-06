package team.unnamed.hephaestus.adapt.v1_17_R1;

import net.minecraft.network.protocol.Packet;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Utility class for packets, specific
 * for v1_16_R3 minecraft server version
 */
public final class Packets {

    private Packets() {
    }

    /**
     * Sends the given {@code packets} to the
     * specified {@code player}
     */
    public static void send(Player player, Packet<?>... packets) {
        PlayerConnection connection = ((CraftPlayer) player)
                .getHandle().b;
        for (Packet<?> packet : packets) {
            connection.sendPacket(packet);
        }
    }
}