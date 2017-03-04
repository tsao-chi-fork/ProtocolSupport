package protocolsupport.protocol.pipeline.version;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.api.Connection;
import protocolsupport.protocol.legacyremapper.LegacyAnimatePacketReorderer;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.utils.netty.ReplayingDecoderBuffer.EOFSignal;
import protocolsupport.zplatform.ServerPlatform;

public class AbstractLegacyPacketDecoder extends AbstractPacketDecoder {

	public AbstractLegacyPacketDecoder(Connection connection, NetworkDataCache storage) {
		super(connection, storage);
	}

	private final ByteBuf cumulation = Unpooled.buffer();
	private final LegacyAnimatePacketReorderer animateReorderer = new LegacyAnimatePacketReorderer();

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws InstantiationException, IllegalAccessException  {
		if (!input.isReadable()) {
			return;
		}
		cumulation.writeBytes(input);
		while (cumulation.isReadable()) {
			if (!decode0(ctx.channel(), list)) {
				break;
			}
		}
		cumulation.discardSomeReadBytes();
	}

	private boolean decode0(Channel channel, List<Object> list) throws InstantiationException, IllegalAccessException {
		cumulation.markReaderIndex();
		try {
			ServerBoundMiddlePacket packetTransformer = registry.getTransformer(ServerPlatform.get().getMiscUtils().getNetworkStateFromChannel(channel), cumulation.readUnsignedByte());
			packetTransformer.readFromClientData(cumulation, connection.getVersion());
			addPackets(animateReorderer.orderPackets(packetTransformer.toNative()), list);
			packetTransformer = null;
			return true;
		} catch (EOFSignal ex) {
			cumulation.resetReaderIndex();
			return false;
		}
	}

}
