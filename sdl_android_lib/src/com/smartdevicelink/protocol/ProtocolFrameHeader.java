package com.smartdevicelink.protocol;

import com.smartdevicelink.protocol.enums.FrameType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.util.BitConverter;

public class ProtocolFrameHeader {
	private byte version = 1;
	private boolean compressed = false;
	private FrameType frameType = FrameType.CONTROL;
	private SessionType sessionType = SessionType.RPC;
	private byte frameData = 0;
	private byte sessionId;
	private int dataSize;
	private int messageId;
	
	public static final byte FRAME_DATA_SINGLE = 0x00;
	public static final byte FRAME_DATA_FIRST = 0x00;
	public static final byte FRAME_DATA_FINAL_CONSECUTIVE = 0x00;
	
	public ProtocolFrameHeader() {}
	
	public static ProtocolFrameHeader parseWiProHeader(byte[] header) {
		ProtocolFrameHeader msg = new ProtocolFrameHeader();
		
		byte version = (byte) (header[0] >>> 4);
		msg.setVersion(version);
		
		boolean compressed = 1 == ((header[0] & 0x08) >>> 3);
		msg.setCompressed(compressed);
		
		byte frameType = (byte) (header[0] & 0x07);
		msg.setFrameType(FrameType.valueOf(frameType));
		
		byte serviceType = header[1];
		msg.setSessionType(SessionType.valueOf(serviceType));
		
		byte frameData = header[2];
		msg.setFrameData(frameData);
		
		byte sessionId = header[3];
		msg.setSessionId(sessionId);
		
		int dataSize = BitConverter.intFromByteArray(header, 4);
		msg.setDataSize(dataSize);
		
		if (version > 1) {
			int messageId = BitConverter.intFromByteArray(header, 8);
			msg.setMessageId(messageId);
		} else msg.setMessageId(0);
		
		return msg;
	}
	
	protected byte[] assembleHeaderBytes() {
		int header = 0;
		header |= (version & 0x0F);
		header <<= 1;
		header |= (compressed ? 1 : 0);
		header <<= 3;
		header |= (frameType.value() & 0x07);
		header <<= 8;
		header |= (sessionType.value() & 0xFF);
		header <<= 8;
		header |= (frameData & 0xFF);
		header <<= 8;
		header |= (sessionId & 0xFF);
		
		if (version == 1) {
			byte[] ret = new byte[8];
			System.arraycopy(BitConverter.intToByteArray(header), 0, ret, 0, 4);
			System.arraycopy(BitConverter.intToByteArray(dataSize), 0, ret, 4, 4);
			
			return ret;
		} else if (version > 1) {
			byte[] ret = new byte[12];
			System.arraycopy(BitConverter.intToByteArray(header), 0, ret, 0, 4);
			System.arraycopy(BitConverter.intToByteArray(dataSize), 0, ret, 4, 4);
			System.arraycopy(BitConverter.intToByteArray(messageId), 0, ret, 8, 4);
			
			return ret;
		} else return null;
	}
	
	public String toString() {
		String ret = "";
		ret += "version " + version + ", " + (compressed ? "compressed" : "uncompressed") + "\n";
		ret += "frameType " + frameType + ", serviceType " + sessionType;
		ret += "\nframeData " + frameData;
		ret += ", sessionID " + sessionId;
		ret += ", dataSize " + dataSize;
		ret += ", messageID " + messageId;
		return ret;
	}
	
	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public boolean isCompressed() {
		return compressed;
	}

	public void setCompressed(boolean compressed) {
		this.compressed = compressed;
	}

	public byte getFrameData() {
		return frameData;
	}

	public void setFrameData(byte frameData) {
		this.frameData = frameData;
	}

	public byte getSessionId() {
		return sessionId;
	}

	public void setSessionId(byte sessionId) {
		this.sessionId = sessionId;
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public FrameType getFrameType() {
		return frameType;
	}

	public void setFrameType(FrameType frameType) {
		this.frameType = frameType;
	}

	public SessionType getSessionType() {
		return sessionType;
	}

	public void setSessionType(SessionType sessionType) {
		this.sessionType = sessionType;
	}
}
