package com.devdyna.justdynathings.registry.builders.functional_anvils.time;

import com.devdyna.justdynathings.registry.builders.functional_anvils.CAnvilBE;
import com.devdyna.justdynathings.registry.interfaces.be.EnergyMachine;
import com.devdyna.justdynathings.registry.interfaces.be.FluidMachine;
import com.devdyna.justdynathings.registry.types.zBlockEntities;
import com.devdyna.justdynathings.registry.types.zItemTags;
import com.devdyna.justdynathings.utils.Actions;
import com.direwolf20.justdirethings.common.blockentities.basebe.FluidContainerData;
import com.direwolf20.justdirethings.common.blockentities.basebe.PoweredMachineContainerData;
import com.direwolf20.justdirethings.common.capabilities.MachineEnergyStorage;
import com.direwolf20.justdirethings.setup.Registration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class TimeAnvilBE extends CAnvilBE implements EnergyMachine, FluidMachine {

    public final PoweredMachineContainerData poweredMachineData = new PoweredMachineContainerData(this);
    public final FluidContainerData fluidContainerData = new FluidContainerData(this);

    public TimeAnvilBE(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        this.MACHINE_SLOTS = 1;
    }

    public TimeAnvilBE(BlockPos pos, BlockState state) {
        this(zBlockEntities.TIME_ANVIL.get(), pos, state);
    }

    @Override
    public void tickServer() {
        var tool = getMachineHandler().getStackInSlot(0);
        if (isActiveRedstone()) {
            // getMachineHandler() only work inside tick event!
            if (canExtractFE() && tool.isDamageableItem() && !tool.is(zItemTags.TIME_ANVIL_DENY)
                    && tool.isDamaged()) {
                applySound();

                if (canExtractMB() && getEnergyStored() >= 1000) {
                    if (tool.getMaxDamage() >= 1000 && tool.getDamageValue() >= tool.getMaxDamage() / 10) {
                        Actions.repairItem(tool, tool.getMaxDamage() / 10);
                    } else {
                        Actions.repairItem(tool, tool.getDamageValue());
                    }
                    extractMB(1);
                    extractEnergy(getStandardEnergyCost() * 5, remove);

                } else {
                    extractFEWhenPossible();
                    Actions.repairItem(tool);
                }

            }
        }
    }

    @Override
    public PoweredMachineContainerData getContainerData() {
        return poweredMachineData;
    }

    @Override
    public MachineEnergyStorage getEnergyStorage() {
        return getData(Registration.ENERGYSTORAGE_MACHINES);
    }

    @Override
    public int getStandardEnergyCost() {
        return FErate;
    }

    @Override
    public int getMaxEnergy() {
        return FEsize;
    }

    @Override
    public ContainerData getFluidContainerData() {
        return fluidContainerData;
    }

    @Override
    public FluidTank getFluidTank() {
        return getData(Registration.PARADOX_FLUID_HANDLER);
    }

    @Override
    public int getMaxMB() {
        return FLsize;
    }

    @Override
    public int getStandardFluidCost() {
        return 1;
    }
}