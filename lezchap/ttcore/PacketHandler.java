package lezchap.ttcore;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
	
	public static int hookedTimer = 1000;

	@Override
    public void onPacketData(INetworkManager manager,
                    Packet250CustomPayload packet, Player player) {
            
            if (packet.channel.equals("TTCoreTimer")) {
                    handleRandom(packet);
            }
    }
    
    private void handleRandom(Packet250CustomPayload packet) {
            DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
            
            try {
                    hookedTimer = inputStream.readInt();
            } catch (IOException e) {
                    e.printStackTrace();
                    return;
            }
    }

}
