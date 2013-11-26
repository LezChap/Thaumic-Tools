package lezchap.thaumictools.block;


import cofh.api.block.IDismantleable;
import cofh.api.core.IInitializer;
import cofh.api.tileentity.IReconfigurableFacing;
import cofh.api.tileentity.ISecureTile;
import cofh.api.tileentity.ISecureTile.AccessMode;
import cofh.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import lezchap.thaumictools.utils.Utils;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ForgeHooks;

public abstract class BlockBase extends BlockContainer{
	public BlockBase(int id, Material material)
	  {
	    super(id, material);
	    setStepSound(soundStoneFootstep);
	  }
	
	public TileEntity createNewTileEntity(World world)
	  {
	    return null;
	  }
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
	  {
	    TileEntity tile = world.getBlockTileEntity(x, y, z);
	    if (((tile instanceof ISecureTile)) && (((EntityPlayer)living) instanceof EntityPlayerMP)) {
	      ((ISecureTile)tile).setOwnerName(((EntityPlayer)living).username);
	    }
	    if ((tile instanceof IReconfigurableFacing))
	    {
	      IReconfigurableFacing reconfig = (IReconfigurableFacing)tile;
	      int quadrant = Utils.floor(living.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
	      if (reconfig.allowYAxisFacing()) {
	        quadrant = living.rotationPitch < -60.0F ? 5 : living.rotationPitch > 60.0F ? 4 : quadrant;
	      }
	      switch (quadrant)
	      {
	      case 0: 
	        reconfig.setFacing(2);
	        return;
	      case 1: 
	        reconfig.setFacing(5);
	        return;
	      case 2: 
	        reconfig.setFacing(3);
	        return;
	      case 3: 
	        reconfig.setFacing(4);
	        return;
	      case 4: 
	        reconfig.setFacing(1);
	        return;
	      case 5: 
	        reconfig.setFacing(0);
	        return;
	      }
	    }
	  }			
}
