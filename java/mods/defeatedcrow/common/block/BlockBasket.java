package mods.defeatedcrow.common.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import mods.defeatedcrow.common.*;
import mods.defeatedcrow.common.tile.TileBread;
import mods.defeatedcrow.common.tile.TileDummy;
import mods.defeatedcrow.handler.Util;

public class BlockBasket extends BlockContainer{	
	
	@SideOnly(Side.CLIENT)
    private IIcon[] basketTex;
	@SideOnly(Side.CLIENT)
    private IIcon[] basketSideTex;
	@SideOnly(Side.CLIENT)
    private IIcon[] basketTopTex;
	@SideOnly(Side.CLIENT)
    private IIcon[] breadTex;
		
	public BlockBasket ()
	{
		super(Material.wood);
		this.setStepSound(Block.soundTypeWood);
		this.setHardness(0.2F);
		this.setResistance(1.0F);
	}
	
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        ItemStack itemstack = par5EntityPlayer.inventory.getCurrentItem();
        int currentMeta = par1World.getBlockMetadata(par2, par3, par4);
        Block bottomBlockID = par1World.getBlock(par2, par3 - 1, par4);
        
        if (itemstack == null)
        {
    		if (currentMeta <= 0)
    		{
    			return false;
    		}
    		else
    		{
    			if (currentMeta < 6) {
    				this.getBowl(par5EntityPlayer, currentMeta);
        			par1World.setBlockMetadataWithNotify(par2, par3, par4, (currentMeta - 1), 3);
            		par1World.playSoundAtEntity(par5EntityPlayer, "random.pop", 0.4F, 1.8F);
            		return true;
    			}
    			else {
    				this.getBottle(par5EntityPlayer, currentMeta);
        			if (currentMeta == 6) {
        				par1World.setBlockToAir(par2, par3, par4);
        			}
        			else {
        				par1World.setBlockMetadataWithNotify(par2, par3, par4, (currentMeta - 1), 3);
        			}
            		par1World.playSoundAtEntity(par5EntityPlayer, "random.pop", 0.4F, 1.8F);
            		return true;
    			}
    		}
        }
        else if (itemstack.getItem() == Items.bread)
        {
        	if (currentMeta >= 5)
        	{
        		return false;
        	}
        	else
        	{
        		if (!par5EntityPlayer.capabilities.isCreativeMode && --itemstack.stackSize <= 0)
                {
            		par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            	par1World.setBlockMetadataWithNotify(par2, par3, par4, (currentMeta + 1), 3);
        		par1World.playSoundAtEntity(par5EntityPlayer, "random.pop", 0.4F, 1.8F);
        		return true;
        	}
        }
        else if (itemstack.getItem() == Item.getItemFromBlock(DCsAppleMilk.emptyBottle) && itemstack.getItemDamage() == 0)
        {
        	if (currentMeta < 6 || currentMeta > 13)
        	{
        		return false;
        	}
        	else
        	{
        		if (!par5EntityPlayer.capabilities.isCreativeMode && --itemstack.stackSize <= 0)
                {
            		par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            	par1World.setBlockMetadataWithNotify(par2, par3, par4, (currentMeta + 1), 3);
        		par1World.playSoundAtEntity(par5EntityPlayer, "random.pop", 0.4F, 1.8F);
        		return true;
        	}
        }
        else
        {
        	return false;
        }
    }
	
	private void getBowl (EntityPlayer player, int meta)
	{
		if (!player.inventory.addItemStackToInventory(new ItemStack(Items.bread,1)))
    	{
    		player.entityDropItem(new ItemStack(Items.bread,1), 1.0F);
    	}
	}
	
	private void getBottle (EntityPlayer player, int meta)
	{
		if (!player.inventory.addItemStackToInventory(new ItemStack(DCsAppleMilk.emptyBottle, 1, 0)))
    	{
    		player.entityDropItem(new ItemStack(DCsAppleMilk.emptyBottle, 1, 0), 1.0F);
    	}
	}
	
	public int damageDropped(int par1)
    {
        return par1;
    }
	
	public boolean isOpaqueCube()
	{
		return false;
	}
 
	public boolean renderAsNormalBlock() 
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return DCsAppleMilk.modelBasket;
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }
	
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }
	
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        this.TeaMakerBoundingBox(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
	
	public void TeaMakerBoundingBox (int par1)
	{
		if (par1 < 6) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.6F, 1.0F);
		}
		else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
		}
		
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int a) {
		return DCsConfig.noUseCupDirection ? null : new TileBread();
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    { 
		if (par2 < 6) {
			if (par1 == 0) return this.basketTex[0];
			if (par1 == 1) return this.basketTopTex[0];
			if (par1 == 2) return this.basketSideTex[0];
			if (par1 == 3) return this.breadTex[0];
			if (par1 == 4) return this.breadTex[1];
			else return Blocks.planks.getBlockTextureFromSide(0);
		}
		else {
			if (par1 == 0) return this.basketTex[1];
			if (par1 == 1) return this.basketTopTex[1];
			else return this.basketSideTex[1];
		}
    }
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		par3List.add(new ItemStack(this, 1, 5));
		par3List.add(new ItemStack(this, 1, 14));
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.basketTex = new IIcon[2];
		this.basketSideTex = new IIcon[2];
		this.basketTopTex = new IIcon[2];
		this.breadTex = new IIcon[2];
		this.breadTex[0] = par1IconRegister.registerIcon("defeatedcrow:bread_S");
		this.breadTex[1] = par1IconRegister.registerIcon("defeatedcrow:bread_T");
		
        for (int i = 0; i < 2; ++i)
        {
        	this.basketTex[i] = par1IconRegister.registerIcon(Util.getTexturePassNoAlt() + "basket_B" + i);
        	this.basketSideTex[i] = par1IconRegister.registerIcon(Util.getTexturePassNoAlt() + "basket_S" + i);
        	this.basketTopTex[i] = par1IconRegister.registerIcon(Util.getTexturePassNoAlt() + "basket_T" + i);
        }
	}
	
}
