package com.devdyna.justdynathings.registry.builders.functional_anvils.blazegold;

import com.devdyna.justdynathings.Config;
import com.devdyna.justdynathings.registry.builders.functional_anvils.CAnvilBE;
import com.devdyna.justdynathings.registry.interfaces.be.FluidMachine;
import com.devdyna.justdynathings.registry.types.zBlockEntities;
import com.devdyna.justdynathings.registry.types.zHandlers;
import com.devdyna.justdynathings.registry.types.zItemTags;
import com.devdyna.justdynathings.utils.Actions;
import com.direwolf20.justdirethings.common.blockentities.basebe.FluidContainerData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class BlazeGoldAnvilBE extends CAnvilBE implements FluidMachine {

    public final FluidContainerData fluidContainerData = new FluidContainerData(this);

    public BlazeGoldAnvilBE(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        this.MACHINE_SLOTS = 1;
    }

    public BlazeGoldAnvilBE(BlockPos pos, BlockState state) {
        this(zBlockEntities.MAGMATIC_ANVIL.get(), pos, state);
    }

    @Override
    public void tickServer() {
        var tool = getMachineHandler().getStackInSlot(0);
        if (isActiveRedstone()) {
            // getMachineHandler() only work inside tick event!
            if (canExtractMB() && tool.isDamageableItem()
                    && tool.isDamaged() && !tool.is(zItemTags.BLAZEGOLD_ANVIL_DENY)) {
                extractMBWhenPossible();
                Actions.repairItem(tool);
                if (Config.ANVIL_BLAZEGOLD_SOUND_EVENT.get())
                    applySound();
            }
        }
    }

    @Override
    public ContainerData getFluidContainerData() {
        return fluidContainerData;
    }

    @Override
    public FluidTank getFluidTank() {
        return getData(zHandlers.MAGMATIC_LIQUID);
    }

    @Override
    public int getMaxMB() {
        return Config.ANVILS_BLAZEGOLD_MB_CAPACITY.get();
    }

    @Override
    public int getStandardFluidCost() {
        return Config.ANVILS_BLAZEGOLD_MB_RATE.get();
    }
}