package com.github.kaydogz.daboismod.network;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.network.client.CCancelQuestPacket;
import com.github.kaydogz.daboismod.network.client.CClaimQuestPacket;
import com.github.kaydogz.daboismod.network.client.CToggleCrownActivationPacket;
import com.github.kaydogz.daboismod.network.client.CUpdateMagneticPacket;
import com.github.kaydogz.daboismod.network.server.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class DBMNetworkHandler {
	
	private static final String PROTOCOL_VERSION = Integer.toString(1);
	private static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
			.named(DaBoisMod.modLocation("main"))
			.clientAcceptedVersions(PROTOCOL_VERSION::equals)
			.serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();
	
	/**
	 * Registers network packets.
	 */
	public static void registerPackets() {
		int packetId = 0;
		
		// Server to Client
		HANDLER.registerMessage(packetId++, SUpdateQuestsPacket.class, SUpdateQuestsPacket::encode, SUpdateQuestsPacket::decode, SUpdateQuestsPacket.Handler::handle);
		HANDLER.registerMessage(packetId++, SDisplayItemActivationPacket.class, SDisplayItemActivationPacket::encode, SDisplayItemActivationPacket::decode, SDisplayItemActivationPacket.Handler::handle);
		HANDLER.registerMessage(packetId++, SUpdateRealmFallingPacket.class, SUpdateRealmFallingPacket::encode, SUpdateRealmFallingPacket::decode, SUpdateRealmFallingPacket.Handler::handle);
		HANDLER.registerMessage(packetId++, SUpdateMagneticPacket.class, SUpdateMagneticPacket::encode, SUpdateMagneticPacket::decode, SUpdateMagneticPacket.Handler::handle);
		HANDLER.registerMessage(packetId++, SSasquatchSmashPacket.class, SSasquatchSmashPacket::encode, SSasquatchSmashPacket::decode, SSasquatchSmashPacket.Handler::handle);
		HANDLER.registerMessage(packetId++, SUpdateSkinTonePacket.class, SUpdateSkinTonePacket::encode, SUpdateSkinTonePacket::decode, SUpdateSkinTonePacket.Handler::handle);
		HANDLER.registerMessage(packetId++, SUpdateDimensionPacket.class, SUpdateDimensionPacket::encode, SUpdateDimensionPacket::decode, SUpdateDimensionPacket.Handler::handle);

		// Client to Server
		HANDLER.registerMessage(packetId++, CCancelQuestPacket.class, CCancelQuestPacket::encode, CCancelQuestPacket::decode, CCancelQuestPacket.Handler::handle);
		HANDLER.registerMessage(packetId++, CClaimQuestPacket.class, CClaimQuestPacket::encode, CClaimQuestPacket::decode, CClaimQuestPacket.Handler::handle);
		HANDLER.registerMessage(packetId++, CUpdateMagneticPacket.class, CUpdateMagneticPacket::encode, CUpdateMagneticPacket::decode, CUpdateMagneticPacket.Handler::handle);
		HANDLER.registerMessage(packetId++, CToggleCrownActivationPacket.class, CToggleCrownActivationPacket::encode, CToggleCrownActivationPacket::decode, CToggleCrownActivationPacket.Handler::handle);
	}
	
	/**
	 * Sends a packet from a client to the server.
	 * @param msg an instance of the packet to be sent.
	 */
	public static <MSG> void sendToServer(MSG msg) {
		HANDLER.send(PacketDistributor.SERVER.noArg(), msg);
	}

	/**
	 * Sends a packet to every player connected to the server.
	 * @param msg an instance of the packet to be sent.
	 */
	public static <MSG> void sendToAll(MSG msg) {
		HANDLER.send(PacketDistributor.ALL.noArg(), msg);
	}

	/**
	 * Sends a packet to the client of a {@link ServerPlayerEntity}.
	 * @param msg an instance of the packet to be sent.
	 * @param player the player to send the packet to.
	 */
	public static <MSG> void sendToPlayer(MSG msg, ServerPlayerEntity player) {
		HANDLER.send(PacketDistributor.PLAYER.with(() -> player), msg);
	}
	
	/**
	 * Sends a packet to every player tracking a specific entity, including itself if it is a player.
	 * @param msg an instance of the packet to be sent.
	 * @param entity the entity that is being tracked.
	 */
	public static <MSG> void sendToAllTrackingEntityAndSelf(MSG msg, Entity entity) {
		HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), msg);
	}

	/**
	 * Sends a packet to the client of every player tracking a specific entity.
	 * @param msg an instance of the packet to be sent.
	 * @param entity the entity that is being tracked.
	 */
	public static <MSG> void sendToAllTrackingEntity(MSG msg, Entity entity) {
		HANDLER.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), msg);
	}
}
