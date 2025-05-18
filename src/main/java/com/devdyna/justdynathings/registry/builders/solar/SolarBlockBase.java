package com.devdyna.justdynathings.registry.builders.solar;

import javax.annotation.Nullable;

import com.devdyna.justdynathings.registry.types.zProperties;
import com.direwolf20.justdirethings.common.blocks.baseblocks.BaseMachineBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("null")
public class SolarBlockBase extends BaseMachineBlock {

    public SolarBlockBase() {
        super(zProperties.MachineProp);
    }

    @Override
    protected VoxelShape getShape(BlockState s, BlockGetter l, BlockPos p, CollisionContext c) {
        return Block.box(0, 0, 0, 16, 4, 16);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        return defaultBlockState().setValue(zProperties.ACTIVE, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> b) {
        b.add(zProperties.ACTIVE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SolarBaseBE(pos, state);
    }

    @Override
    public boolean isValidBE(BlockEntity be) {
        return be instanceof SolarBaseBE;
    }

    @Override
    public void openMenu(Player player, BlockPos blockPos) {
    }

    // brute-force fix for invalid rotation
    @Override
    public BlockState direRotate(BlockState s, Rotation r) {
        return s;
    }

    @Override
    public BlockState direRotate(BlockState s, LevelAccessor l, BlockPos p, Rotation d) {
        return s;
    }

    @Override
    public BlockState rotate(BlockState s, Rotation r) {
        return s;
    }

}
