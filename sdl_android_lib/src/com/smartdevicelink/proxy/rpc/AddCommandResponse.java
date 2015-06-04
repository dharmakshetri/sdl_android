package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Add Command Response is sent, when AddCommand has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class AddCommandResponse extends RpcResponse {

    public AddCommandResponse() {
        super(FunctionId.ADD_COMMAND.toString());
    }

    public AddCommandResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}

