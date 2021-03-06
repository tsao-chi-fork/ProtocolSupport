package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEffectAdd;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class EntityEffectAdd extends MiddleEntityEffectAdd {

	public EntityEffectAdd(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entityeffectadd = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_EFFECT_ADD);
		VarNumberSerializer.writeVarInt(entityeffectadd, entityId);
		entityeffectadd.writeByte(effectId);
		entityeffectadd.writeByte(amplifier);
		VarNumberSerializer.writeVarInt(entityeffectadd, duration);
		entityeffectadd.writeBoolean((flags & 0x01) != 0);
		codec.write(entityeffectadd);
	}

}
