package mods.defeatedcrow.event;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mods.defeatedcrow.common.AMTLogger;
import mods.defeatedcrow.common.DCsAppleMilk;
import mods.defeatedcrow.handler.GenkotuHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class EntityMoreDropEvent {
	
	@SubscribeEvent
	public void EntityDropEvent(LivingDropsEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		DamageSource thisSource = event.source;
		ArrayList<EntityItem> items = event.drops;
		
		//以下、死んだモブの位置情報
		World world = entity.worldObj;
		double posX = entity.posX;
		double posY = entity.posY;
		double posZ = entity.posZ;
		
		if (thisSource instanceof EntityDamageSource)
		{
			EntityDamageSource entityDamage = (EntityDamageSource) thisSource;
			Entity destroyer = entityDamage.getEntity();
			
			/**
			 * EntityPlayerによる攻撃の時に判定する。
			 * 間接攻撃でも大丈夫だと思う
			 * */
			if (destroyer instanceof EntityPlayer)
			{
				Item radenID = DCsAppleMilk.princessClam;
				EntityPlayer player = (EntityPlayer) destroyer;
				
				if (player.inventory.hasItemStack(new ItemStack(radenID, 1, 1)))
				{
					//まずは作ってみたリフレクションクラスで叩く
					ItemStack get = GenkotuHandler.getMobsDrop(entity);
					if (get != null && world.rand.nextInt(2) == 0)
					{
						AMTLogger.debugInfo("Genkotu!");
						world.spawnEntityInWorld(new EntityItem(world, posX, posY, posZ, get));
					}
				}
				
				if (player.inventory.hasItemStack(new ItemStack(radenID, 1, 2)))
				{
					AMTLogger.debugInfo("daden (exp)");
					int exp = 1 + world.rand.nextInt(5);//1~5
					world.spawnEntityInWorld(new EntityXPOrb(world, posX, posY, posZ, exp));
				}
			}
		}
	}

}
