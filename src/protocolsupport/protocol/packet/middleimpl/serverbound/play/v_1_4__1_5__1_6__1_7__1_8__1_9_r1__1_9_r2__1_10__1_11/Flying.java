package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleFlying;

public class Flying extends MiddleFlying {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		onGround = clientdata.readBoolean();
	}

}
