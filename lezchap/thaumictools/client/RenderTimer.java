package lezchap.thaumictools.client;

import java.util.Iterator;

import lezchap.thaumictools.items.ItemInfusedFishingPole;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class RenderTimer{
	
	  private static Minecraft mc = Minecraft.getMinecraft();
	  private GuiIngame guiingame = new GuiIngame(mc);

	  public RenderTimer()
	  {
	  }
	  
	  public static void RenderEntityInfoInWorld(EntityFishHook entity, float partialTickTime)
	  {
		ItemStack itemstack = entity.angler.getCurrentEquippedItem();
	    if (((mc.inGameHasFocus) || (mc.currentScreen == null) || (mc.currentScreen instanceof GuiChat) && (!mc.gameSettings.showDebugInfo)) && (itemstack.getItem() instanceof ItemInfusedFishingPole))
	    		    {
	      EntityFishHook fishhook = (EntityFishHook)entity;
	      double distanceFromMe = mc.thePlayer.getDistanceToEntity(fishhook);
	      if (distanceFromMe > 32) {
	        return;
	      }
	      RenderCountdownOverlay(fishhook, partialTickTime);
	    }
	  }
	  protected static void RenderCountdownOverlay(EntityFishHook entityfishhook, float partialTickTime)
	  {
	    float x = (float)entityfishhook.posX;
	    float y = (float)entityfishhook.posY;
	    float z = (float)entityfishhook.posZ;
	    

	    String countdown = Integer.toString((entityfishhook.hookedTimer/20)+1);
	    
	    if (countdown != null && entityfishhook.hookedTimer != 1000) {
	      RenderFloatingText(countdown, x, y, z, 16777215, true, partialTickTime);}
	    }
	    	    
	    public static void RenderFloatingText(String text, float x, float y, float z, int color, boolean renderBlackBox, float partialTickTime)
	    {
	    /*  String[] textArray = { text };
	      RenderFloatingText(textArray, x, y, z, color, renderBlackBox, partialTickTime);
	    }
	    
	    public static void RenderFloatingText(String[] text, float x, float y, float z, int color, boolean renderBlackBox, float partialTickTime)
	    { */
	      RenderManager renderManager = RenderManager.instance;
	      FontRenderer fontRenderer = mc.fontRenderer;
	      
	      float playerX = (float)(mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * partialTickTime);
	      float playerY = (float)(mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * partialTickTime);
	      float playerZ = (float)(mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * partialTickTime);
	      
	      float dx = x - playerX;
	      float dy = y - playerY;
	      float dz = z - playerZ;
	      float distance = (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
	      float multiplier = distance / 120.0F;
	      float scale = 0.45F * multiplier;
	      
	      GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
	      GL11.glPushMatrix();
	      GL11.glTranslatef(dx, dy, dz);
	      GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
	      GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
	      GL11.glScalef(-scale, -scale, scale);
	      GL11.glDisable(2896);
	      GL11.glDepthMask(false);
	      GL11.glDisable(2929);
	      GL11.glEnable(3042);
	      GL11.glBlendFunc(770, 771);
	      
	      int textWidth = 0;
	     // for (String thisMessage : text)
	      //{
	        int thisMessageWidth = mc.fontRenderer.getStringWidth(text);
	        if (thisMessageWidth > textWidth) {
	          textWidth = thisMessageWidth;
	        }
	     // }
	      int lineHeight = 10;
	      if (renderBlackBox)
	      {
	        Tessellator tessellator = Tessellator.instance;
	        GL11.glDisable(3553);
	        tessellator.startDrawingQuads();
	        int stringMiddle = textWidth / 2;
	        tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.5F);
	        tessellator.addVertex(-stringMiddle - 1, -1.0D, 0.0D);
	        tessellator.addVertex(-stringMiddle - 1, 8 + lineHeight * text.length() - lineHeight, 0.0D);
	        tessellator.addVertex(stringMiddle + 1, 8 + lineHeight * text.length() - lineHeight, 0.0D);
	        tessellator.addVertex(stringMiddle + 1, -1.0D, 0.0D);
	        tessellator.draw();
	        GL11.glEnable(3553);
	      }
	      int i = 0;
	     //for (String message : text)
	      //{
	        fontRenderer.drawString(text, -textWidth / 2, i * lineHeight, color);
	        i++;
	     // }
	      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	      GL11.glDepthMask(true);
	      GL11.glEnable(2929);
	      GL11.glPopMatrix();
	    }
}