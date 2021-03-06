package protocolsupport.protocol.types.networkentity.metadata.objects;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectVector3f extends ReadableNetworkEntityMetadataObject<Vector> {

	@Override
	public void readFromStream(ByteBuf from) {
		value = new Vector(from.readFloat(), from.readFloat(), from.readFloat());
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeFloat((float) value.getX());
		to.writeFloat((float) value.getY());
		to.writeFloat((float) value.getZ());
	}

}
