package lezchap.ttcore;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;


@Mod(modid="TTCore", name="TTCore", version="0.0.3")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, 
channels={"TTCoreTimer"}, packetHandler = PacketHandler.class)
public class TTCore{
	
	  public TTCore() {

  }
	  @Instance("TTCore")
      public static TTCore instance;
      
      @SidedProxy(clientSide="lezchap.ttcore.client.ClientProxy",
                      serverSide="lezchap.ttcore.CommonProxy")
      public static CommonProxy proxy;
      
      @PreInit
      public void preInit(FMLPreInitializationEvent event) {
              // Stub Method
      }
      
      @Init
      public void load(FMLInitializationEvent event) {
              proxy.registerRenderers();
            
      }
      
      @PostInit
      public void postInit(FMLPostInitializationEvent event) {
              // Stub Method
      }
      
  public boolean registerBus(EventBus bus, LoadController controller) {
          bus.register(this);
          return true;
  }

}
