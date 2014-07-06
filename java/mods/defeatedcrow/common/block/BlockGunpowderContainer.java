package mods.defeatedcrow.common.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockStem;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.BonemealEvent;
import mods.defeatedcrow.common.*;
import mods.defeatedcrow.handler.Util;

public class BlockGunpowderContainer extends Block{
	
	private static final String[] boxType = new String[] {"Gunpowder", "Kayaku", "Clay", "Clam"};
	
	@SideOnly(Side.CLIENT)
    private IIcon[] boxTex;
	@SideOnly(Side.CLIENT)
    private IIcon boxSideTex;
	
	
	public BlockGunpowderContainer ()
	{
		super(Material.ground);
		this.setHardness(1.0F);
		this.setResistance(2.0F);
		this.setStepSound(Block.soundTypeStone);
		this.setTickRandomly(true);
	}
	
	public int damageDropped(int par1)
    {
        return par1;
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    { 
		int i = par2;
		if (i > 3) i = 3;
		if (par1 == 1)
        {
        	return this.boxTex[i];
        }
        else
        {
        	return this.boxSideTex;
        }
    }
	
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (!par1World.isRemote)
		{
			super.updateTick(par1World, par2, par3, par4, par5Random);
			
			int meta = par1World.getBlockMetadata(par2, par3, par4);
			
			if (!DCsConfig.noWetGContainer && meta < 2 && (par1World.isRaining()) && par1World.canBlockSeeTheSky(par2, par3 + 1, par4))
			{
				if (meta == 0) par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 2);
				else if (meta == 1) par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
			}
			
			if (meta == 3 && DCsConfig.bonemealClam)
			{
				boolean flag = false;
				int y = 0;
				//2,3,4マス上についてチェックする
				for (int i = 0 ; i < 3 ; i++)
				{
					if (this.likeBonemeal(par1World, par2, par3 + 2 + i, par4))
					{
						flag = true;
						y = par3 + 2 + i;
						break;
					}
				}
				
				if (flag)
				{
					par1World.playAuxSFX(2005, par2, y + 1, par4, 0);
					
					if (par1World.rand.nextInt(10) == 0)
					{
						par1World.setBlock(par2, par3, par4, Blocks.dirt);
					}
				}
			}
		}
	}
	
	public static boolean likeBonemeal(World par1World, int par2, int par3, int par4)
	{
		Block l = par1World.getBlock(par2, par3, par4);
		int meta = par1World.getBlockMetadata(par2, par3, par4);

        BonemealEvent event = new BonemealEvent(null, par1World, l, par2, par3, par4);
        if (MinecraftForge.EVENT_BUS.post(event))
        {
            return false;
        }

        if (event.getResult() == Result.ALLOW)
        {
            return true;
        }

        if (l instanceof IGrowable)
        {
            IGrowable igrowable = (IGrowable)l;

            if (igrowable.func_149851_a(par1World, par2, par3, par4, par1World.isRemote))
            {
                if (!par1World.isRemote)
                {
                    if (igrowable.func_149852_a(par1World, par1World.rand, par2, par3, par4))
                    {
                        igrowable.func_149853_b(par1World, par1World.rand, par2, par3, par4);
                    }
                }

                return true;
            }
        }
		return false;
	}
	
	public int tickRate(World par1World)
    {
        return 20;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.boxSideTex = par1IconRegister.registerIcon(Util.getTexturePassNoAlt() + "Container_S");
		this.boxTex = new IIcon[4];
		
        for (int i = 0; i < 4; ++i)
        {
            this.boxTex[i] = par1IconRegister.registerIcon(Util.getTexturePassNoAlt() + boxType[i] + "Container_T");
        }
	}

}