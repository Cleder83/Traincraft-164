package src.train.client.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import src.train.client.core.handlers.ClientTickHandler;
import src.train.client.core.handlers.KeyBindingHandler;
import src.train.client.core.handlers.RecipeBookHandler;
import src.train.client.core.helpers.HolidayHelper;
import src.train.client.core.helpers.KeyBindingHelper;
import src.train.client.gui.GuiBuilder;
import src.train.client.gui.GuiCrafterTier;
import src.train.client.gui.GuiCraftingCart;
import src.train.client.gui.GuiDistil;
import src.train.client.gui.GuiForney;
import src.train.client.gui.GuiFreight;
import src.train.client.gui.GuiFurnaceCart;
import src.train.client.gui.GuiGeneratorDiesel;
import src.train.client.gui.GuiJukebox;
import src.train.client.gui.GuiLantern;
import src.train.client.gui.GuiLiquid;
import src.train.client.gui.GuiLoco2;
import src.train.client.gui.GuiOpenHearthFurnace;
import src.train.client.gui.GuiRecipeBook;
import src.train.client.gui.GuiTender;
import src.train.client.gui.GuiTrainCraftingBlock;
import src.train.client.gui.GuiZepp;
import src.train.client.render.ItemRenderBridgePillar;
import src.train.client.render.ItemRenderGeneratorDiesel;
import src.train.client.render.ItemRenderLantern;
import src.train.client.render.ItemRenderSignal;
import src.train.client.render.ItemRenderStopper;
import src.train.client.render.ItemRenderWaterWheel;
import src.train.client.render.ItemRenderWindMill;
import src.train.client.render.RenderBogie;
import src.train.client.render.RenderBridgePillar;
import src.train.client.render.RenderGeneratorDiesel;
import src.train.client.render.RenderLantern;
import src.train.client.render.RenderRollingStock;
import src.train.client.render.RenderRotativeDigger;
import src.train.client.render.RenderRotativeWheel;
import src.train.client.render.RenderSignal;
import src.train.client.render.RenderStopper;
import src.train.client.render.RenderTCRail;
import src.train.client.render.RenderWaterWheel;
import src.train.client.render.RenderWindMill;
import src.train.client.render.RenderZeppelins;
import src.train.common.Traincraft;
import src.train.common.api.EntityBogie;
import src.train.common.api.EntityRollingStock;
import src.train.common.core.CommonProxy;
import src.train.common.core.Traincraft_EventSounds;
import src.train.common.core.handlers.ConfigHandler;
import src.train.common.entity.digger.EntityRotativeDigger;
import src.train.common.entity.digger.EntityRotativeWheel;
import src.train.common.entity.rollingStock.EntityJukeBoxCart;
import src.train.common.entity.zeppelin.EntityZeppelinOneBalloon;
import src.train.common.entity.zeppelin.EntityZeppelinTwoBalloons;
import src.train.common.library.BlockIDs;
import src.train.common.library.GuiIDs;
import src.train.common.library.Info;
import src.train.common.tile.TileBridgePillar;
import src.train.common.tile.TileCrafterTierI;
import src.train.common.tile.TileCrafterTierII;
import src.train.common.tile.TileCrafterTierIII;
import src.train.common.tile.TileEntityDistil;
import src.train.common.tile.TileEntityOpenHearthFurnace;
import src.train.common.tile.TileGeneratorDiesel;
import src.train.common.tile.TileLantern;
import src.train.common.tile.TileSignal;
import src.train.common.tile.TileStopper;
import src.train.common.tile.TileTCRail;
import src.train.common.tile.TileTrainWbench;
import src.train.common.tile.TileWaterWheel;
import src.train.common.tile.TileWindMill;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {

	@Override
	public void isHoliday() {
		HolidayHelper helper = new HolidayHelper();
		helper.setDaemon(true);
		helper.start();
	}
	
	@Override
	public void registerKeyBindingHandler() {
		KeyBindingRegistry.registerKeyBinding(new KeyBindingHandler());
	}

	@Override
	public void setKeyBinding(String name, int value) {
		KeyBindingHelper.addKeyBinding(name, value);
		KeyBindingHelper.addIsRepeating(false);
	}

	@Override
	public void registerRenderInformation() {
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);

		RenderingRegistry.registerEntityRenderingHandler(EntityRollingStock.class, new RenderRollingStock());
		RenderingRegistry.registerEntityRenderingHandler(EntityZeppelinTwoBalloons.class, new RenderZeppelins());
		RenderingRegistry.registerEntityRenderingHandler(EntityZeppelinOneBalloon.class, new RenderZeppelins());
		RenderingRegistry.registerEntityRenderingHandler(EntityRotativeDigger.class, new RenderRotativeDigger());
		RenderingRegistry.registerEntityRenderingHandler(EntityRotativeWheel.class, new RenderRotativeWheel());
		//bogies
		RenderingRegistry.registerEntityRenderingHandler(EntityBogie.class, new RenderBogie());

		ClientRegistry.bindTileEntitySpecialRenderer(TileStopper.class, new RenderStopper());
		MinecraftForgeClient.registerItemRenderer(BlockIDs.stopper.blockID, new ItemRenderStopper());
		
		//ClientRegistry.bindTileEntitySpecialRenderer(TileBook.class, new RenderTCBook());
		//MinecraftForgeClient.registerItemRenderer(BlockIDs.book.blockID, new ItemRenderBook());

		ClientRegistry.bindTileEntitySpecialRenderer(TileSignal.class, new RenderSignal());
		MinecraftForgeClient.registerItemRenderer(BlockIDs.signal.blockID, new ItemRenderSignal());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileLantern.class, new RenderLantern());
		MinecraftForgeClient.registerItemRenderer(BlockIDs.lantern.blockID, new ItemRenderLantern());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileWaterWheel.class, new RenderWaterWheel());
		MinecraftForgeClient.registerItemRenderer(BlockIDs.waterWheel.blockID, new ItemRenderWaterWheel());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileWindMill.class, new RenderWindMill());
		MinecraftForgeClient.registerItemRenderer(BlockIDs.windMill.blockID, new ItemRenderWindMill());

		ClientRegistry.bindTileEntitySpecialRenderer(TileGeneratorDiesel.class, new RenderGeneratorDiesel());
		MinecraftForgeClient.registerItemRenderer(BlockIDs.generatorDiesel.blockID, new ItemRenderGeneratorDiesel());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileTCRail.class, new RenderTCRail());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileBridgePillar.class, new RenderBridgePillar());
		MinecraftForgeClient.registerItemRenderer(BlockIDs.bridgePillar.blockID, new ItemRenderBridgePillar());
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		EntityPlayer riddenByEntity = null;
		Entity entity = player.ridingEntity;
		//entity = ;
		if (player.ridingEntity != null) {
			riddenByEntity = (EntityPlayer) entity.riddenByEntity;
		}

		Entity entity1 = null;
		if (y == -1) {
			for (Object ent : world.loadedEntityList) {
				if (((Entity) ent).entityId == x)
					entity1 = (Entity) ent;
			}
		}
		switch (ID) {
		case (GuiIDs.CRAFTER_TIER_I):
			return te != null && te instanceof TileCrafterTierI ? new GuiCrafterTier(player.inventory, (TileCrafterTierI) te) : null;
		case (GuiIDs.CRAFTER_TIER_II):
			return te != null && te instanceof TileCrafterTierII ? new GuiCrafterTier(player.inventory, (TileCrafterTierII) te) : null;
		case (GuiIDs.CRAFTER_TIER_III):
			return te != null && te instanceof TileCrafterTierIII ? new GuiCrafterTier(player.inventory, (TileCrafterTierIII) te) : null;
		case (GuiIDs.DISTIL):
			return te != null && te instanceof TileEntityDistil ? new GuiDistil(player.inventory, (TileEntityDistil) te) : null;
		case (GuiIDs.GENERATOR_DIESEL):
			return te != null && te instanceof TileGeneratorDiesel ? new GuiGeneratorDiesel(player.inventory, (TileGeneratorDiesel) te) : null;
		case (GuiIDs.OPEN_HEARTH_FURNACE):
			return te != null && te instanceof TileEntityOpenHearthFurnace ? new GuiOpenHearthFurnace(player.inventory, (TileEntityOpenHearthFurnace) te) : null;
		case GuiIDs.TRAIN_WORKBENCH:
			return te != null && te instanceof TileTrainWbench ? new GuiTrainCraftingBlock(player.inventory, player.worldObj, (TileTrainWbench) te) : null;
		case (GuiIDs.LOCO):
			return riddenByEntity != null && entity != null ? new GuiLoco2(riddenByEntity.inventory, entity) : null;
		case (GuiIDs.FORNEY):
			return riddenByEntity != null && entity != null ? new GuiForney(riddenByEntity.inventory, entity) : null;
		case (GuiIDs.CRAFTING_CART):
			return riddenByEntity != null && entity != null ? new GuiCraftingCart(riddenByEntity.inventory, world) : null;
		case (GuiIDs.FURNACE_CART):
			return riddenByEntity != null && entity != null ? new GuiFurnaceCart(riddenByEntity.inventory, entity) : null;
		case (GuiIDs.ZEPPELIN):
			return riddenByEntity != null && entity != null ? new GuiZepp(riddenByEntity.inventory, entity) : null;
		case (GuiIDs.DIGGER):
			return riddenByEntity != null && entity != null ? new GuiBuilder(player, riddenByEntity.inventory, entity) : null;

			//Stationary entities while player is not riding. 
		case (GuiIDs.FREIGHT):
			return entity1 != null ? new GuiFreight(player,player.inventory, entity1) : null;
		case (GuiIDs.TENDER):
			return entity1 != null ? new GuiTender(player,player.inventory, entity1) : null;
		case (GuiIDs.BUILDER):
			return entity1 != null ? new GuiBuilder(player,player.inventory, entity1) : null;
		case (GuiIDs.LIQUID):
			return entity1 != null ? new GuiLiquid(player,player.inventory, entity1) : null;
		case (GuiIDs.RECIPE_BOOK):
			return new GuiRecipeBook(player, player.getCurrentEquippedItem());
		/*case (GuiIDs.RECIPE_BOOK2):
			return te != null && te instanceof TileBook ? new GuiRecipeBook2(player, player.getCurrentEquippedItem()) : new GuiRecipeBook2(player, player.getCurrentEquippedItem());*/
		case (GuiIDs.LANTERN):
			return new GuiLantern(player, (TileLantern)te);
		case (GuiIDs.JUKEBOX):
			return entity1 != null ? new GuiJukebox(player,(EntityJukeBoxCart)entity1) : null;
		default:
			return null;
		}
	}
	
	@Override
	public void getKeysFromProperties() {
		File f = new File(Minecraft.getMinecraft().mcDataDir + "/options.txt");
		if (f.exists() && f.isFile()) {
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(f));
				String line = "";

				while ((line = br.readLine()) != null) {
					try {
						String[] split = line.split(":");
						if (split[0].contains("forward")) {
							ConfigHandler.Key_Acc = Integer.parseInt(split[1]);
						}
						if (split[0].contains("back")) {
							ConfigHandler.Key_Dec = Integer.parseInt(split[1]);
						}
						if (split[0].contains("left")) {
							ConfigHandler.Key_Left = Integer.parseInt(split[1]);
						}
						if (split[0].contains("right")) {
							ConfigHandler.Key_Right = Integer.parseInt(split[1]);
						}
					} catch (Exception e) {
						Traincraft.tcLog.fine("Skiping option in options.txt file.");
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				Traincraft.tcLog.info("Options.txt file could not be read. Defaulting keys.");
			}
		}
		else {
			Traincraft.tcLog.info("Options.txt file could not be found. Defaulting keys.");
		}
	}

	@Override
	public int addArmor(String armor) {
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}

	@Override
	public GuiScreen getCurrentScreen() {
		return Minecraft.getMinecraft().currentScreen;
	}
	@Override
	public void registerVillagerSkin(int villagerId, String textureName) {
		VillagerRegistry.instance().registerVillagerSkin(villagerId, new ResourceLocation(Info.resourceLocation,Info.villagerPrefix + textureName));
	}

	@Override
	public void registerSounds() {
		MinecraftForge.EVENT_BUS.register(new Traincraft_EventSounds());
	}
	
	@Override
	public void registerBookHandler() {
		RecipeBookHandler recipeBookHandler = new RecipeBookHandler();
	}

	@Override
	public Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}
	
	@Override
	public void getCape() {
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
	}
	
	@Override
	public EntityPlayer getPlayer() {
		return getMinecraft().thePlayer;
	}
	
	@Override
	public void doNEICheck(int id) {
		if (Minecraft.getMinecraft().thePlayer != null ) {
            Iterator modsIT = Loader.instance().getModList().iterator();
            ModContainer modc;
            while (modsIT.hasNext()) {
                modc = (ModContainer) modsIT.next();
                if ("Not Enough Items".equals(modc.getName().trim())) {
                    codechicken.nei.api.API.hideItem(id);
                    return;
                }
            }
        }
	}
}