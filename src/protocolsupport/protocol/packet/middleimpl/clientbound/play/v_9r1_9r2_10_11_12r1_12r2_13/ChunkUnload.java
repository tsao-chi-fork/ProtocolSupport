package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkUnload;
import protocolsupport.protocol.serializer.PositionSerializer;

public class ChunkUnload extends AbstractChunkUnload {

	public ChunkUnload(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData chunkunload = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_UNLOAD);
		PositionSerializer.writeIntChunkCoord(chunkunload, chunk);
		codec.write(chunkunload);
	}

}
