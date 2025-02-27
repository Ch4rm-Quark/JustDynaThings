package com.devdyna.justdynathings.common.registry.builder.clock;

import com.devdyna.justdynathings.common.registry.Material;
import com.devdyna.justdynathings.utils.Actions;
import com.direwolf20.justdirethings.common.blockentities.basebe.BaseMachineBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ClockBE extends BaseMachineBE {

    public ClockBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public ClockBE(BlockPos pos, BlockState blockState) {
        super(Material.CLOCK_BE.get(), pos, blockState);
    }

    private int i = 0;

    @Override
    public void tickServer() {

        if (i < tickSpeed)
            i++;

        if (i >= tickSpeed) {
            i = 0;
            Actions.clockUpdate(getBlockPos(), level, getBlockState());
        }

    }

}
