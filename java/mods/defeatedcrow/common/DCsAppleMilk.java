/**
 * Copyright (c) defeatedcrow, 2013
 * URL:http://forum.minecraftuser.jp/viewtopic.php?f=13&t=17657
 *
 * Apple&Milk&Tea! is distributed under the terms of the Minecraft Mod Public License 1.0, or MMPL.
 * Please check the License(MMPL_1.0).txt included in the package file of this Mod.
 */

package mods.defeatedcrow.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import mods.defeatedcrow.api.*;
import mods.defeatedcrow.common.block.BlockCharcoalBox;
import mods.defeatedcrow.common.entity.*;
import mods.defeatedcrow.common.item.ItemGrater;
import mods.defeatedcrow.event.*;
import mods.defeatedcrow.handler.*;
import mods.defeatedcrow.plugin.*;
import mods.defeatedcrow.plugin.craftguide.LoadCraftGuidePlugin;
import mods.defeatedcrow.handler.recipe.*;
import mods.defeatedcrow.potion.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.src.*;
import net.minecraft.stats.Achievement;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.*;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(
		modid = "DCsAppleMilk",
		name = "Apple&Milk&Tea!",
		version = "1.7.2_1.0a",
		dependencies = "after:IC2;after:Thaumcraft;after:BambooMod;after:pamharvestcraft;after:Forestry;after:mod_ecru_MapleTree"
		)
//required-after:SampleCore;
public class DCsAppleMilk{
	
	//プロキシの登録
	@SidedProxy(clientSide = "mods.defeatedcrow.client.ClientProxy", serverSide = "mods.defeatedcrow.common.CommonProxy")
	public static CommonProxy proxy;
	
	//インスタンスの生成
	@Instance("DCsAppleMilk")
    public static DCsAppleMilk instance;
	
	//クリエイティブタブの追加
	public static final CreativeTabs applemilk = new CreativeTabDCAM("applemilk");
	
	//ブロックのインスタンス
	//ツール
	public static Block  teaMakerNext;
	public static Block  emptyCup;
	public static Block  autoMaker;
	public static Block  iceMaker;
	public static Block  emptyPan;
	public static Block  filledPan;
	public static Block  filledPan2;
	public static Block  filledChocoPan;
	public static Block  teppann;
	//たべもの
	public static Block  teacupBlock;
	public static Block  teaCup2;
	public static Block  blockIcecream;
	public static Block  cocktail;
	public static Block  alcoholCup;
	public static Block  bowlBlock;
	public static Block  bowlJP;
	public static Block  foodPlate;
	public static Block  chocoBlock;
	public static Block  largeBottle;
	public static Block  cordial;
	//コンテナ
	public static Block  woodBox;
	public static Block  appleBox;
	public static Block  vegiBag;
	public static BlockCharcoalBox  charcoalBox;
	public static Block  gunpowderContainer;
	public static Block  eggBasket;
	public static Block  mushroomBox;
	public static Block  melonBomb;
	public static Block  wipeBox;
	public static Block  wipeBox2;
	//自然
	public static Block  saplingTea;
	public static Block  teaTree;
	public static Block  cassisTree;
	public static Block  clamSand;
	public static Block  cropMint;
	//インテリア
	public static Block  bowlRack;
	public static Block  Basket;
	public static Block  chopsticksBox;
	public static Block  flintBlock;
	public static Block  chalcedony;
	public static Block  cLamp;
	public static Block  rotaryDial;
	
	//アイテムのインスタンス
	public static Item  bakedApple;
	public static Item  appleTart;
	public static Item  toffyApple;
	public static Item  icyToffyApple;
	public static Item  EXItems;
	public static Item  condensedMIlk;
	public static Item  inkStick;
	public static Item  appleSandwich;
	public static Item  leafTea;
	public static Item  teaCup;
	public static Item  gratedApple;
	public static Item  mincedFoods;
	public static Item  DCStew;
	public static Item  DCgrater;
	public static Item  chalcedonyKnife;
	public static Item  firestarter;
	public static Item  clam;
	public static Item  chopsticks;
	public static Item  chalcedonyHammer;
	public static Item  chocolateFruits;
	public static Item  icyCrystal;
	public static Item  wallMug;
	public static Item  itemLargeBottle;
	public static Item  milkBottle;
	public static Item  princessClam;
	public static Item  itemCordial;
	public static Item  itemMintSeed;
	
	//ポーションのインスタンス
	public static Potion Immunization;
	public static Potion prvExplode;
	public static Potion prvProjectile;
	public static Potion reflex;
	public static Potion absEXP;
	public static Potion absHeal;
	public static Potion suffocation;
	public static PotionProtectionEX prvSuffocation;
	
	//gui
	public int guiIdAutoMaker = 1;
	public int guiIceMaker = 2;
	
	//villager関連
	public static VillagerCafe villager;
	
	//前提CoreModsの導入チェック
	public static boolean RequiredCoreEnabled = false;

	public static boolean SuccessLoadIC2 = false;
	public static boolean SuccessLoadBamboo = false;
	public static boolean SuccessLoadBoP = false;
	public static boolean SuccessLoadTofu = false;
	public static boolean SuccessLoadThaumcraft = false;
	public static boolean SuccessLoadEconomy = false;
	public static boolean SuccessLoadSSector = false;
	public static boolean SuccessLoadGummi = false;
	public static boolean[] SuccessLoadGrowth = new boolean[] {false, false, false};
	public static boolean SuccessLoadMapleTree = false;
	public static boolean SuccessLoadExtraTrees = false;
	public static boolean SuccessLoadExBucket = false;
	public static boolean SuccessLoadRC = false;
	public static boolean SuccessLoadSugi = false;
	public static boolean SuccessLoadDart = false;
	public static boolean SuccessLoadTE3 = false;
	public static boolean SuccessLoadWa = false;
	
	//内部処理用
	public static boolean fanc_78842dcs = false;
	public static boolean fanc_78853dcs = false;
	public static boolean getServerPlop1 = true;
	public static boolean getServerPlop2 = false;
	public static boolean inClient = false;
	public static boolean inServer = false;
	public static boolean thirdParty = false;
	public static boolean debugMode = false;
	public static boolean succeedAddPotion = false;
	
	//新ツール属性の追加
	public static Item.ToolMaterial enumToolMaterialChalcedony;
	
	//レンダー登録用ID
	public static int modelTeaMaker;
	public static int modelCup;
	public static int modelPan;
	public static int modelTeaTree;
	public static int modelFilledCup;
	public static int modelBowl;
	public static int modelRack;
	public static int modelCLamp;
	public static int modelBasket;
	public static int modelFoodPlate;
	public static int modelTeppann;
	public static int modelBowlJP;
	public static int modelCupSummer;
	public static int modelChopsticks;
	public static int modelKinoko;
	public static int modelEggBasket;
	public static int modelMelonBomb;
	public static int modelChocoPan;
	public static int modelMakerNext;
	public static int modelAutoMaker;
	public static int modelWipe;
	public static int modelIceMaker;
	public static int modelIceCream;
	public static int modelDial;
	public static int modelCocktail;
	public static int modelLargeBottle;
	public static int modelCanister;
	public static int modelCordial;
	public static int modelCassisTree;
	public static int modelAlcoholCup;
	
	public static final String[] TEX_PASS = new String[] {
		"defeatedcrow:",
		"defeatedcrow:x32/",
		"defeatedcrow:x32alt/"
	};
	
	public static final String[] TEX_PASS_ENTITY = new String[] {
		"defeatedcrow:textures/entity/",
		"defeatedcrow:textures/entity/x32/",
		"defeatedcrow:textures/entity/x32alt/"
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		
		(new RequiredCoreModChecker()).coreModCheck();
		
		//Configuration setting
		//コンフィグを生成する
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		(new DCsConfig()).config(cfg);
		
		//Registering
		//Material
		//ツール属性の内容を登録する
		enumToolMaterialChalcedony = EnumHelper.addToolMaterial("CHALCEDONY", 2, 128, 5.0F, 2.0F, 18);
		enumToolMaterialChalcedony.customCraftingMaterial = Items.flint;
		
		//ブロックやアイテムの読み込みと登録
		(new MaterialRegister()).load();
		
		//ポーションIDが拡張出来ているかのチェックを行い、成功時のみポーションを追加する。
		int potion = Potion.potionTypes.length;
		try
		{
			(new MaterialRegister()).addPotion();
			this.succeedAddPotion = true;
			AMTLogger.debugInfo("Succeed to add new potion effect.");
		}
		catch  (Exception e)
		{
			AMTLogger.debugInfo("Failed to add new potion effect.");
		}
		
		
		//実績の追加
		(new AchievementRegister()).register();
		
		//particle用テクスチャ登録
		proxy.registerTex();
		
	    //Registering OreDictionary  
		//ForgeのOreDicyionaryの登録部分をpreInitに移した
		(new RegisterOreHandler()).register();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) throws IOException
	{
		//Registering TileEntity
		//TileEntityの登録はプロキシクラスに任せる
		proxy.registerTileEntity();
		
		//Registering Entity
		//Entityの登録
		if (DCsConfig.entityIdMelon == 0) DCsConfig.entityIdMelon = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityMelonBomb.class, "compressedMelon", DCsConfig.entityIdMelon);
		EntityRegistry.registerModEntity(EntityMelonBomb.class, "compressedMelon", DCsConfig.entityIdMelon, this, 250, 5, true);
		
		//Villagerの登録
		villager = new VillagerCafe();
		VillagerRegistry.instance().registerVillagerId(DCsConfig.villagerRecipeID);
		VillagerRegistry.instance().registerVillageTradeHandler(DCsConfig.villagerRecipeID, villager);
		
		
		//Checking Server Prop.
		//サーバーのプロパティを取得しようとして失敗した跡
		//System.out.println("[AppleMilk]Checking server property.");
		proxy.networkUtil();
		
		//Registering new Recipe
		//レシピや言語の登録は長くなるので専用クラスに任せる
		(new DCsRecipeRegister()).addRecipe();
		
		(new AddChestGen()).addChestItems();
		
		//Langファイル同梱のため、Lang登録クラスはコメントアウトしました。
		//(new DCsLangRegister()).registerLang();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		
		//地形生成イベントの登録
		//コンフィグでONのときだけ読み込まれる
		if (!DCsConfig.notGenTeaTree)
		{
			if (DCsConfig.teaTreeGenValue < 0) DCsConfig.teaTreeGenValue = 1;
			if (DCsConfig.teaTreeGenValue > 20) DCsConfig.teaTreeGenValue = 20;
			
			GameRegistry.registerWorldGenerator(new WorldgenTeaTree(), 1);
		}
		
		if (!DCsConfig.disableClam)
		{
			GameRegistry.registerWorldGenerator(new WorldgenClam(), 2);
		}
		
		//Registering new event
		//ポーション効果の内容をLivingEventで作ったのでそれの読み込み
		MinecraftForge.EVENT_BUS.register(new DCsLivingEvent());
		MinecraftForge.EVENT_BUS.register(new DCsHurtEvent());
		//螺鈿チャームの効果
		MinecraftForge.EVENT_BUS.register(new EntityMoreDropEvent());
		//骨粉効果
		MinecraftForge.EVENT_BUS.register(new DCsBonemealEvent());
		//クラフトで耐久が減るアイテムの登録
		FMLCommonHandler.instance().bus().register(new CraftingEvent());
		
		//Registering new Render
		//新しいレンダーIDの登録もプロキシクラス内でやる
		//サーバ側で誤ってクライアント専用のクラス（レンダー関係）を読み込ませないため
		this.modelTeaMaker = proxy.getRenderID();
		this.modelCup = proxy.getRenderID();
		this.modelPan = proxy.getRenderID();
		this.modelTeaTree = proxy.getRenderID();
		this.modelFilledCup = proxy.getRenderID();
		this.modelBowl = proxy.getRenderID();
		this.modelRack = proxy.getRenderID();
		this.modelCLamp = proxy.getRenderID();
		this.modelBasket = proxy.getRenderID();
		this.modelFoodPlate = proxy.getRenderID();
		this.modelTeppann = proxy.getRenderID();
		this.modelBowlJP = proxy.getRenderID();
		this.modelCupSummer = proxy.getRenderID();
		this.modelChopsticks = proxy.getRenderID();
		this.modelEggBasket = proxy.getRenderID();
		this.modelKinoko = proxy.getRenderID();
		this.modelMelonBomb = proxy.getRenderID();
		this.modelChocoPan = proxy.getRenderID();
		this.modelMakerNext = proxy.getRenderID();
		this.modelAutoMaker = proxy.getRenderID();
		this.modelWipe = proxy.getRenderID();
		this.modelIceMaker = proxy.getRenderID();
		this.modelIceCream = proxy.getRenderID();
		this.modelCocktail = proxy.getRenderID();
		this.modelDial = proxy.getRenderID();
		this.modelLargeBottle = proxy.getRenderID();
		this.modelCordial = proxy.getRenderID();
		this.modelCassisTree = proxy.getRenderID();
		this.modelAlcoholCup = proxy.getRenderID();
		proxy.registerRenderers();
	    
	    //ティーメーカーのレシピ数の無限化のため、専用のレシピ登録クラスを用意した
	    (new RegisterMakerRecipe()).registerTea();
	    AMTLogger.trace("Registered new tea maker recipe");
	    
	    //アイスメーカーのレシピ登録
	    (new RegisterMakerRecipe()).registerIce();
	    AMTLogger.trace("Registered new ice maker recipe");
	    
	    //TEへのIMC送信はここで行う
	    if (Loader.isModLoaded("ThermalExpansion"))
	    {
	    	AMTLogger.loadingModInfo("ThermalExpansion");
	    	try
	        {
	          this.SuccessLoadTE3 = true;
	          (new LoadTE3Plugin()).load();
	          AMTLogger.loadedModInfo("ThermalExpansion");
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("ThermalExpansion");
	          e.printStackTrace(System.err);
	        }
	    }
	}
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event) {
		
		if (debugMode)
		{
			(new LoadModHandler()).loadAppleMilk();
		}
		
	    if (Loader.isModLoaded("IC2"))
	    {
	    	AMTLogger.loadingModInfo("IC2");
	    	try
	        {
	          this.SuccessLoadIC2 = true;
	          (new LoadIC2Plugin()).load();
	          AMTLogger.loadedModInfo("IC2");
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("IC2");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    if (Loader.isModLoaded("BambooMod"))
	    {
	    	AMTLogger.loadingModInfo("BambooMod");
	    	try
	        {
	          this.SuccessLoadBamboo = true;
	          (new LoadBambooPlugin()).load();
	          (new LoadBambooPlugin()).loadBambooItems();
	          AMTLogger.loadedModInfo("BambooMod");
	          
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("BambooMod");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    if (Loader.isModLoaded("TofuCraft"))
	    {
	    	AMTLogger.loadingModInfo("TofuCraft");
	    	try
	        {
	          this.SuccessLoadTofu = true;
	          (new LoadTofuPlugin()).load();
	          AMTLogger.loadedModInfo("TofuCraft");
	          
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("TofuCraft");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    if (Loader.isModLoaded("Thaumcraft"))
	    {
	    	AMTLogger.loadingModInfo("Thaumcraft");
	    	try
	        {
	          this.SuccessLoadThaumcraft = true;
	          (new LoadThaumcraftPlugin()).load();
	          AMTLogger.loadedModInfo("Thaumcraft");
	          
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("Thaumcraft");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    if (Loader.isModLoaded("BiomesOPlenty"))
	    {
	    	AMTLogger.loadingModInfo("BiomesOPlenty");
	    	try
	        {
	          this.SuccessLoadBoP = true;
	          (new LoadBoPPlugin()).load();
	          AMTLogger.loadedModInfo("BipmesOPlenty");
	          
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("BiomesOPlenty");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    if (Loader.isModLoaded("AndanteMod_Gummi"))
	    {
	    	AMTLogger.loadingModInfo("AndanteMod_Gummi");
	    	try
	        {
	          this.SuccessLoadGummi = true;
	          (new LoadModHandler()).loadGummi();
	          AMTLogger.loadedModInfo("AndanteMod_Gummi");
	          
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("AndanteMod_Gummi");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    if (Loader.isModLoaded("AndanteMod_ExBucket"))
	    {
	    	AMTLogger.loadingModInfo("AndanteMod_ExBucket");
	    	try
	        {
	          this.SuccessLoadExBucket = true;
	          (new LoadExBucketPlugin()).load();
	          AMTLogger.loadedModInfo("AndanteMod_ExBucket");
	          
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("AndanteMod_ExBucket");
	          e.printStackTrace(System.err);
	        }
	    }
	    
//	    if (Loader.isModLoaded("MCEconomy"))
//	    {
//	    	AMTLogger.loadingModInfo("MCEconomy");
//	    	try
//	        {
//	          this.SuccessLoadEconomy = true;
//	          (new MCEconomyPlugin()).registerSellable();
//	          AMTLogger.loadedModInfo("MCEconomy");
//	          
//	        }
//	        catch (Exception e) {
//	        	AMTLogger.failLoadingModInfo("MCEconomy");
//	          e.printStackTrace(System.err);
//	        }
//	    }
	    
	    if (Loader.isModLoaded("SextiarySector"))
	    {
	    	AMTLogger.loadingModInfo("SextiarySector");
	    	try
	        {
	          this.SuccessLoadSSector = true;
	          (new LoadSSectorPlugin()).load();
	          AMTLogger.loadedModInfo("SextiarySector");
	          
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("SextiarySector");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    if (Loader.isModLoaded("Growthcraft|Rice"))
	    {
	    	AMTLogger.loadingModInfo("Growthcraft|Rice");
	    	try
	        {
	          this.SuccessLoadGrowth[0] = true;
	          (new LoadModHandler()).loadGrowthRice();
	          AMTLogger.loadedModInfo("Growthcraft|Rice");
	          
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("Growthcraft|Rice");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    if (Loader.isModLoaded("Growthcraft|Hops"))
	    {
	    	AMTLogger.loadingModInfo("Growthcraft|Hops");
	    	try
	        {
	          this.SuccessLoadGrowth[1] = true;
	          (new LoadModHandler()).loadGrowthHops();
	          AMTLogger.loadedModInfo("Growthcraft|Hops");
	          
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("Growthcraft|Hops");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    if (Loader.isModLoaded("Growthcraft|Grapes"))
	    {
	    	AMTLogger.loadingModInfo("Growthcraft|Grapes");
	    	try
	        {
	          this.SuccessLoadGrowth[2] = true;
	          (new LoadModHandler()).loadGrowthGrape();
	          AMTLogger.loadedModInfo("Growthcraft|Grapes");
	          
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("Growthcraft|Grapes");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    if (Loader.isModLoaded("mod_ecru_MapleTree"))
	    {
	    	AMTLogger.loadingModInfo("mod_ecru_MapleTree");
	    	try
	        {
	          this.SuccessLoadMapleTree = true;
	          (new LoadModHandler()).loadMaple();
	          AMTLogger.loadedModInfo("mod_ecru_MapleTree");
	          
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("mod_ecru_MapleTree");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    if (Loader.isModLoaded("ExtraTrees"))
	    {
	    	AMTLogger.loadingModInfo("ExtraTrees");
	    	try
	        {
	          this.SuccessLoadExtraTrees = true;
	          (new LoadModHandler()).loadExtraTrees();
	          AMTLogger.loadedModInfo("ExtraTrees");
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("ExtraTrees");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    if (Loader.isModLoaded("Railcraft"))
	    {
	    	AMTLogger.loadingModInfo("Railcraft");
	    	try
	        {
	          this.SuccessLoadRC = true;
	          (new LoadRailCraftPlugin()).load();
	          AMTLogger.loadedModInfo("Railcraft");
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("Railcraft");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    if (Loader.isModLoaded("kegare.sugiforest"))
	    {
	    	AMTLogger.loadingModInfo("kegare.sugiforest");
	    	try
	        {
	          this.SuccessLoadSugi = true;
	          (new LoadModHandler()).loadSugi();
	          AMTLogger.loadedModInfo("kegare.sugiforest");
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("kegare.sugiforest");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    if (Loader.isModLoaded("DartCraft"))
	    {
	    	AMTLogger.loadingModInfo("DartCraft");
	    	try
	        {
	          this.SuccessLoadDart = true;
	          (new LoadModHandler()).loadForce();
	          AMTLogger.loadedModInfo("DartCraft");
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("DartCraft");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    if (Loader.isModLoaded("Wa"))
	    {
	    	AMTLogger.loadingModInfo("Wa");
	    	try
	        {
	          this.SuccessLoadWa = true;
	          (new LoadModHandler()).loadWa();
	          AMTLogger.loadedModInfo("Wa");
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("Wa");
	          e.printStackTrace(System.err);
	        }
	    }
	    
	    //Checking another mods
	    //他のMODのブロック・アイテム登録クラスに先行しないよう、postInitメソッドでロードする
	    //当MODで勝手に追加する鉱石辞書も含めるように、読み込む位置を他MODのロード処理より後にした
	    (new LoadOreDicHandler()).load();
	    
	    //インスタントティーレシピ
	    //他MODの水入り容器をひと通り取得した後に行うので、最後のほうで呼ぶ
	    (new DCsRecipeRegister()).addInstantTea();
	    
	    //レシピ閲覧系MODの連携要素
	    (new RegisteredRecipeGet()).setRecipeList();
	    
	    //NEIのみクライアントサイドで読み込む
	    proxy.loadNEI();
	    
	    //CraftGuideへのレシピ登録
	    if (Loader.isModLoaded("craftguide"))
	    {
	    	AMTLogger.loadingModInfo("craftguide");
	    	try
	        {
	          this.SuccessLoadWa = true;
	          (new LoadCraftGuidePlugin()).load();;
	          AMTLogger.loadedModInfo("craftguide");
	        }
	        catch (Exception e) {
	        	AMTLogger.failLoadingModInfo("craftguide");
	          e.printStackTrace(System.err);
	        }
	    }
    }
	
}
