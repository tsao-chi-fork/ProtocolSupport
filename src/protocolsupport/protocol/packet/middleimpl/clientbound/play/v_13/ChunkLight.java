package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractChunkLight;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVariesWithLight;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.TileEntity;

public class ChunkLight extends AbstractChunkLight {

	protected final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);

	public ChunkLight(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		int blockMask = ((setSkyLightMask | setBlockLightMask | emptySkyLightMask | emptyBlockLightMask) >> 1) & 0xFFFF;
		boolean hasSkyLight = cache.getAttributesCache().hasSkyLightInCurrentDimension();
		List<Collection<TileEntity>> resendTiles = new ArrayList<>();

		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
		chunkdata.writeBoolean(false); //full
		VarNumberSerializer.writeVarInt(chunkdata, blockMask);
		ArraySerializer.writeVarIntByteArray(chunkdata, to -> {
			ChunkWriterVariesWithLight.writeSectionsFlattening(
				to, blockMask, 14,
				blockDataRemappingTable, flatteningBlockDataTable,
				cachedChunk, hasSkyLight,
				sectionNumber -> resendTiles.add(cachedChunk.getTiles(sectionNumber).values())
			);
		});
		ArraySerializer.writeVarIntTArray(chunkdata, lTo -> {
			int count = 0;
			for (Collection<TileEntity> sectionTiles : resendTiles) {
				for (TileEntity tile : sectionTiles) {
					ItemStackSerializer.writeDirectTag(lTo, tile.getNBT());
				}
				count += sectionTiles.size();
			}
			return count;
		});
		codec.write(chunkdata);
	}

}
