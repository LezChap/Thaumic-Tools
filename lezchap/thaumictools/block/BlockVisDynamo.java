package lezchap.thaumictools.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import lezchap.thaumictools.block.TileVisDynamo;

public class BlockVisDynamo 
	extends BlockBase
{
	 public BlockVisDynamo(int id)
	  {
	    super(id, Material.iron);
	    setHardness(15.0F);
	    setResistance(25.0F);
	    setUnlocalizedName("tt.visdynamo");
	  }
	 
	 public TileEntity createTileEntity(World world)
	  {
	    return new TileVisDynamo();
	  }
}
