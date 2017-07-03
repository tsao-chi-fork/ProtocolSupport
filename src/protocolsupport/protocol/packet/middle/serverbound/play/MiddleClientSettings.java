package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleClientSettings extends ServerBoundMiddlePacket {

	protected String locale;
	protected int viewDist;
	protected int chatMode;
	protected boolean chatColors;
	protected int skinFlags;
	protected int mainHand;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		cache.setLocale(locale);
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_SETTINGS);
		StringSerializer.writeString(creator, ProtocolVersionsHelper.LATEST_PC, locale);
		creator.writeByte(viewDist);
		VarNumberSerializer.writeVarInt(creator, chatMode);
		creator.writeBoolean(chatColors);
		creator.writeByte(skinFlags);
		VarNumberSerializer.writeVarInt(creator, mainHand);
		return RecyclableSingletonList.create(creator);
	}

}
