package com.devdyna.justdynathings.common.registry.builder.repairer;

import com.devdyna.justdynathings.common.registry.Material;
import com.devdyna.justdynathings.common.registry.core.interfaces.be.SmartFEMachine;
import com.devdyna.justdynathings.utils.LevelUtil;
import com.direwolf20.justdirethings.common.blockentities.basebe.BaseMachineBE;
import com.direwolf20.justdirethings.common.blockentities.basebe.PoweredMachineContainerData;
import com.direwolf20.justdirethings.common.blockentities.basebe.RedstoneControlledBE;
import com.direwolf20.justdirethings.common.capabilities.MachineEnergyStorage;
import com.direwolf20.justdirethings.setup.Registration;
import com.direwolf20.justdirethings.util.interfacehelpers.RedstoneControlData;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import static com.devdyna.justdynathings.common.registry.builder.repairer.BlazingAnvilBlock.ACTIVE;

@SuppressWarnings("null")
public class BlazingAnvilBE extends BaseMachineBE implements RedstoneControlledBE, SmartFEMachine {

    public RedstoneControlData redstoneControlData = new RedstoneControlData();
    public final PoweredMachineContainerData poweredMachineData = new PoweredMachineContainerData(this);
    private int cost;
    private int maxsize;

    /**
     * DONT USE THIS ON BE REGISTRATION
     */
    public BlazingAnvilBE(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        MACHINE_SLOTS = 1;
    }

    public BlazingAnvilBE(BlockEntityType<?> type, BlockPos pos, BlockState state, int cost, int maxsize) {
        this(type, pos, state);
        this.maxsize = maxsize;
        this.cost = cost;
    }

    /**
     * DONT USE THIS ON BE REGISTRATION
     */
    public BlazingAnvilBE(BlockPos pos, BlockState state) {
        this(Material.BLAZINGANVIL_BE.get(), pos, state);
    }

    @Override
    public RedstoneControlData getRedstoneControlData() {
        return redstoneControlData;
    }

    @Override
    public BlockEntity getBlockEntity() {
        return this;
    }

    @Override
    public void tickClient() {
    }

    @Override
    public void tickServer() {
        checkState();
        if (getBlockState().getValue(ACTIVE).booleanValue()) {
            extractFEWhenPossible();
            repairItem();
            applySound();
        }
    }

    public void checkState() {
        level.setBlockAndUpdate(getBlockPos(),
                getBlockState().setValue(ACTIVE,
                        validEnergy() && getMachineHandler().getStackInSlot(0).isDamageableItem()
                                && getMachineHandler().getStackInSlot(0).isDamaged()
                                && !getMachineHandler().getStackInSlot(0).is(Material.BLAZINGANVIL_DENY)));
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
        return cost;
    }

    @Override
    public int getMaxEnergy() {
        return maxsize;
    }

    public void applySound() {
        if (LevelUtil.chance(5, level))
            level.playSound(null, getBlockPos(),
                    LevelUtil.chance(75, level)
                            ? SoundEvents.GRINDSTONE_USE
                            : LevelUtil.chance(50, level)
                                    ? SoundEvents.SMITHING_TABLE_USE
                                    : SoundEvents.ANVIL_USE,
                    SoundSource.BLOCKS, (level.random.nextInt(10) + 1) * 0.01F,
                    level.random.nextInt(50) + 1 * 0.01F);
    }

    public void repairItem() {

        getMachineHandler().getStackInSlot(0)
                .setDamageValue(getMachineHandler().getStackInSlot(0).getDamageValue() - 1);
    }

}