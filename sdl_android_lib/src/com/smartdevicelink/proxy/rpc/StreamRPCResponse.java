package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

public class StreamRpcResponse extends RpcResponse {
	public static final String KEY_FILENAME = "fileName";
	public static final String KEY_FILESIZE = "fileSize";
	
    public StreamRpcResponse() {
        super(FunctionId.STREAM_RPC.toString());
    }
    public StreamRpcResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    
	public void setFileName(String fileName) {
    	if (fileName != null) {
    		parameters.put(KEY_FILENAME, fileName);
    	} else {
    		parameters.remove(KEY_FILENAME);
    	}
	}
	public String getFileName() {
		return (String) parameters.get(KEY_FILENAME);
	}
	
	public void setFileSize(Long fileSize) {
    	if (fileSize != null) {
    		parameters.put(KEY_FILESIZE, fileSize);
    	} else {
    		parameters.remove(KEY_FILESIZE);
    	}
	}
	public Long getFileSize() {
		return (Long) parameters.get(KEY_FILESIZE);
	}		
	
}
