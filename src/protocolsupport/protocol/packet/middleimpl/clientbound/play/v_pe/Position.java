package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.util.NumberConversions;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Position extends MiddlePosition {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		int chunkX = NumberConversions.floor(x) >> 4;
		int chunkZ = NumberConversions.floor(z) >> 4;
		if (!cache.isChunkMarkedAsSent(chunkX, chunkZ)) {
			packets.add(Chunk.createEmptyChunk(version, chunkX, chunkZ));
		}
		packets.add(create(version, cache.getSelfPlayerEntityId(), x, y, z, pitch, yaw));
		return packets;
	}

	public static ClientBoundPacketData create(ProtocolVersion version, int entityId, double x, double y, double z, float pitch, float yaw) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_MOVE, version);
		VarNumberSerializer.writeSVarLong(serializer, entityId);
		MiscSerializer.writeLFloat(serializer, (float) x);
		MiscSerializer.writeLFloat(serializer, (float) (y + 1.6200000047683716D));
		MiscSerializer.writeLFloat(serializer, (float) z);
		MiscSerializer.writeLFloat(serializer, pitch);
		MiscSerializer.writeLFloat(serializer, yaw);
		MiscSerializer.writeLFloat(serializer, yaw); //head yaw actually
		serializer.writeByte(1); //animation mode, 1 none
		serializer.writeBoolean(false);
		return serializer;
	}

}