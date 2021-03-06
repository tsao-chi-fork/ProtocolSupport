package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleMoveLook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.storage.netcache.MovementCache;

public abstract class AbstractMoveLook extends ServerBoundMiddlePacket {

	protected final MovementCache movementCache = cache.getMovementCache();

	public AbstractMoveLook(ConnectionImpl connection) {
		super(connection);
	}

	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected boolean onGround;

	@Override
	public void writeToServer() {
		int teleportId = cache.getMovementCache().tryTeleportConfirm(x, y, z);
		if (teleportId == -1) {
			codec.read(MiddleMoveLook.create(x, y, z, yaw, pitch, onGround));
		} else {
			codec.read(MiddleTeleportAccept.create(teleportId));
			codec.read(MiddleMoveLook.create(x, y, z, yaw, pitch, onGround));
		}
	}

}
