package cc.funkemunky.api.tinyprotocol.packet.out;

import cc.funkemunky.api.reflections.Reflections;
import cc.funkemunky.api.reflections.types.WrappedClass;
import cc.funkemunky.api.reflections.types.WrappedField;
import cc.funkemunky.api.tinyprotocol.api.NMSObject;
import cc.funkemunky.api.tinyprotocol.api.ProtocolVersion;
import cc.funkemunky.api.tinyprotocol.packet.types.MathHelper;
import org.bukkit.entity.Player;

import java.util.UUID;

//TODO Add the ability to use this wrapper to spawn a fake entity and implement other field support.
public class WrappedOutSpawnEntityLiving extends NMSObject {

    public WrappedOutSpawnEntityLiving(Object object, Player player) {
        super(object, player);
    }

    private static WrappedClass packet = Reflections.getNMSClass(Server.SPAWN_ENTITY_LIVING);

    public int entityId, type;
    public UUID uuid; //1.9+ only
    public double x, y, z;
    public int yaw, pitch, headPitch;
    public byte velocityX, velocityY, velocityZ;

    private static WrappedField fieldEntityId, fieldUuid, fieldType, fieldX, fieldY, fieldZ,
            fieldYaw, fieldPitch, fieldHeadPitch, fieldVelocityX, fieldVelocityY, fieldVelocityZ;

    @Override
    public void process(Player player, ProtocolVersion version) {

        entityId = fetch(fieldEntityId);

        if(ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_9)) {
            x = (int)fetch(fieldX) / 32.;
            y = (int)fetch(fieldY) / 32.;
            z = (int)fetch(fieldZ) / 32.;
        } else {
            x = fetch(fieldX);
            y = fetch(fieldY);
            z = fetch(fieldZ);
            uuid = fetch(fieldUuid);
        }

        yaw = fetch(fieldYaw);
        pitch = fetch(fieldPitch);
        headPitch = fetch(fieldHeadPitch);
        velocityX = fetch(fieldVelocityX);
        velocityY = fetch(fieldVelocityY);
        velocityZ = fetch(fieldVelocityZ);
    }

    @Override
    public void updateObject() {
        set(fieldEntityId, entityId);
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_9)) {
            set(fieldX, MathHelper.floor(x * 32.));
            set(fieldY, MathHelper.floor(y * 32.));
            set(fieldZ, MathHelper.floor(z * 32.));
        } else {
            set(fieldX, x);
            set(fieldY, y);
            set(fieldZ, z);
            set(fieldUuid, uuid);
        }

        set(fieldYaw, yaw);
        set(fieldPitch, pitch);
        set(fieldHeadPitch, headPitch);
        set(fieldVelocityX, velocityX);
        set(fieldVelocityY, velocityY);
        set(fieldVelocityZ, velocityZ);
    }

    static {
        fieldEntityId = fetchField(packet, int.class, 0);

        if(ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_9)) {
            fieldX = fetchField(packet, int.class, 1);
            fieldY = fetchField(packet, int.class, 2);
            fieldZ = fetchField(packet, int.class, 3);
            fieldYaw = fetchField(packet, int.class, 4);
            fieldPitch = fetchField(packet, int.class, 5);
            fieldHeadPitch = fetchField(packet, int.class, 6);
        } else {
            fieldX = fetchField(packet, double.class, 0);
            fieldY = fetchField(packet, double.class, 1);
            fieldZ = fetchField(packet, double.class, 2);
            fieldYaw = fetchField(packet, int.class, 1);
            fieldPitch = fetchField(packet, int.class, 2);
            fieldHeadPitch = fetchField(packet, int.class, 3);
            fieldUuid = fetchField(packet, UUID.class, 0);
        }
        fieldVelocityX = fetchField(packet, byte.class, 0);
        fieldVelocityY = fetchField(packet, byte.class, 1);
        fieldVelocityZ = fetchField(packet, byte.class, 2);
    }
}
