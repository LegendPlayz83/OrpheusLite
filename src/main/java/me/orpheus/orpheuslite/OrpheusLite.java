package me.orpheus.orpheuslite;

import me.orpheus.orpheuslite.entity.OrpheusEntity;
import me.orpheus.orpheuslite.item.OrpheusSummonerItem;
import me.orpheus.orpheuslite.world.OrpheusPortal;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrpheusLite implements ModInitializer {
    public static final String MODID = "orpheuslite";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final RegistryKey<World> ORPHEUS_DIMENSION_KEY =
            RegistryKey.of(RegistryKeys.WORLD, id("orpheus_dimension"));

    public static final Block BONE_BLOSSOM_BLOCK =
            new Block(AbstractBlock.Settings.copy(Blocks.BONE_BLOCK).strength(1.5F));
    public static final Block SMOOTH_QUARTZ_LOG =
            new Block(AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).strength(1.2F));
    public static final Block SMOOTH_QUARTZ_LEAVES =
            new Block(AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).strength(0.8F).nonOpaque());

    public static final EntityType<OrpheusEntity> ORPHEUS_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            id("orpheus"),
            EntityType.Builder.create(OrpheusEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.6F, 0.85F)
                    .build("orpheus")
    );

    public static final Item ORPHEUS_SUMMON_WOODEN = new OrpheusSummonerItem(new Item.Settings().maxCount(16), 1);
    public static final Item ORPHEUS_SUMMON_STONE = new OrpheusSummonerItem(new Item.Settings().maxCount(16), 2);
    public static final Item ORPHEUS_SUMMON_IRON = new OrpheusSummonerItem(new Item.Settings().maxCount(16), 3);
    public static final Item ORPHEUS_SUMMON_DIAMOND = new OrpheusSummonerItem(new Item.Settings().maxCount(16), 4);
    public static final Item ORPHEUS_SUMMON_NETHERITE = new OrpheusSummonerItem(new Item.Settings().maxCount(16), 5);

    @Override
    public void onInitialize() {
        registerBlockAndItem("bone_blossom_block", BONE_BLOSSOM_BLOCK);
        registerBlockAndItem("smooth_quartz_log", SMOOTH_QUARTZ_LOG);
        registerBlockAndItem("smooth_quartz_leaves", SMOOTH_QUARTZ_LEAVES);

        registerItem("orpheus_summon_wooden", ORPHEUS_SUMMON_WOODEN);
        registerItem("orpheus_summon_stone", ORPHEUS_SUMMON_STONE);
        registerItem("orpheus_summon_iron", ORPHEUS_SUMMON_IRON);
        registerItem("orpheus_summon_diamond", ORPHEUS_SUMMON_DIAMOND);
        registerItem("orpheus_summon_netherite", ORPHEUS_SUMMON_NETHERITE);

        FabricDefaultAttributeRegistry.register(ORPHEUS_ENTITY_TYPE, WolfEntity.createWolfAttributes());

        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if (player.getStackInHand(hand).isOf(Items.WATER_BUCKET)) {
                BlockPos interior = hit.getBlockPos().offset(hit.getSide());
                return OrpheusPortal.tryActivate(player, world, interior);
            }
            return ActionResult.PASS;
        });

        LOGGER.info("OrpheusLite initialized.");
    }

    private static void registerBlockAndItem(String name, Block block) {
        Registry.register(Registries.BLOCK, id(name), block);
        Registry.register(Registries.ITEM, id(name), new BlockItem(block, new Item.Settings()));
    }

    private static void registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, id(name), item);
    }

    public static Identifier id(String path) {
        return Identifier.of(MODID, path);
    }
}