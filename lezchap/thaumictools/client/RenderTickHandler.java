package lezchap.thaumictools.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class RenderTickHandler
{
  public static RenderTickHandler instance = new RenderTickHandler();
  static Minecraft mc = Minecraft.getMinecraft();
		  
  @ForgeSubscribe
  public void renderWorldLastEvent(RenderWorldLastEvent event)
  {
    RenderEntityInfo(event.partialTicks);
  }
  
  private static void RenderEntityInfoInWorld(EntityFishHook entity, float partialTickTime)
  {
    RenderTimer.RenderEntityInfoInWorld(entity, partialTickTime);
  }
  
  public static void RenderEntityInfo(float partialTickTime)
  {
    if (mc.inGameHasFocus) {
      for (Object object : mc.theWorld.loadedEntityList) {
        if (object instanceof EntityFishHook) {
          RenderEntityInfoInWorld((EntityFishHook)object, partialTickTime);
        }
      }
    }
  }

}
