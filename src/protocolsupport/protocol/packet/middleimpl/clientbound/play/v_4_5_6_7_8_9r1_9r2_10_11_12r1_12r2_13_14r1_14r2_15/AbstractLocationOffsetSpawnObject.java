package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.typeremapper.entity.EntityLocationOffset;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractLocationOffsetSpawnObject extends MiddleSpawnObject {

	protected final EntityLocationOffset entityOffsetRemapper = EntityLocationOffset.get(version);

	public AbstractLocationOffsetSpawnObject(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient0(NetworkEntityType remappedEntityType) {
		EntityLocationOffset.Offset offset = entityOffsetRemapper.get(remappedEntityType);
		if (offset != null) {
			x += offset.getX();
			y += offset.getY();
			z += offset.getZ();
			yaw += offset.getYaw();
			pitch += offset.getPitch();
		}
		writeToClientAfterOffset(remappedEntityType);
	}

	protected abstract void writeToClientAfterOffset(NetworkEntityType remappedEntityType);

}
