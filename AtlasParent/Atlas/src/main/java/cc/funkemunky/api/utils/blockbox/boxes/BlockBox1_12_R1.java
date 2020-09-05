package cc.funkemunky.api.utils.blockbox.boxes;

import cc.funkemunky.api.utils.blockbox.BlockBox;
import net.minecraft.server.v1_12_R1.GenericAttributes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

@Deprecated
public class BlockBox1_12_R1 implements BlockBox {
    @Override
    public boolean isChunkLoaded(Location loc) {
        net.minecraft.server.v1_12_R1.World world =
                ((org.bukkit.craftbukkit.v1_12_R1.CraftWorld) loc.getWorld()).getHandle();

        return !world.isClientSide
                && world.isLoaded(
                        new net.minecraft.server.v1_12_R1.BlockPosition(loc.getBlockX(), 0, loc.getBlockZ()))
                && world.areChunksLoaded(
                        new net.minecraft.server.v1_12_R1.BlockPosition(loc.getBlockX(), 0, loc.getBlockZ()), 4);
    }

    @Override
    public float getMovementFactor(Player player) {
        return (float) ((CraftPlayer) player).getHandle()
                .getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue();
    }

    @Override
    public boolean isRiptiding(LivingEntity entity) {
        return false;
    }
}
