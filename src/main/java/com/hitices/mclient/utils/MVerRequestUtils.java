package com.hitices.mclient.utils;

import com.netflix.appinfo.InstanceInfo;
import com.septemberhx.common.bean.MResponse;
import com.septemberhx.common.config.MConfig;
import com.septemberhx.common.utils.MRequestUtils;
import com.septemberhx.common.utils.MUrlUtils;
import com.hitices.mclient.core.MClientSkeleton;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SeptemberHX
 * @version 0.1
 * @date 2020/3/1
 */
public class MVerRequestUtils {

    private static Random random = new Random(1000000);

    public static MResponse request(String requestId, MResponse parameters, RequestMethod requestMethod, HttpServletRequest request) {
        // Send the request dependence to the gateway in order to determine which instance should be requested
        parameters.set(MConfig.MGATEWAY_DEPENDENCY_ID, MClientSkeleton.inst().getDepListById(requestId).get(0));
        String calledUrl = request.getHeader(MConfig.PARAM_CALLED_URL);
        String callerUrl = request.getHeader(MConfig.PARAM_CALLER_URL);
        String callerInterfaceId = request.getHeader(MConfig.PARAM_CALLER_INTERFACE_ID);
        String calledInterfaceId = request.getHeader(MConfig.PARAM_CALLED_INTERFACE_ID);
        String userId = request.getHeader(MConfig.PARAM_USER_ID);
        Map<String, List<String>> customHeaders = new HashMap<>();
        List<String> p1 = new ArrayList<>();
        p1.add(callerUrl);
        List<String> p2 = new ArrayList<>();
        p2.add(calledUrl);
        List<String> p3 = new ArrayList<>();
        p3.add(userId);
        List<String> p4 = new ArrayList<>();
        p4.add(callerInterfaceId);
        List<String> p5 = new ArrayList<>();
        p5.add(calledInterfaceId);
        customHeaders.put(MConfig.PARAM_CALLER_URL, p1);
        customHeaders.put(MConfig.PARAM_CALLED_URL, p2);
        customHeaders.put(MConfig.PARAM_USER_ID, p3);
        customHeaders.put(MConfig.PARAM_CALLER_INTERFACE_ID, p4);
        customHeaders.put(MConfig.PARAM_CALLED_INTERFACE_ID, p5);

        // Get an online MGateway instance randomly, and send the request to it
        List<InstanceInfo> gatewayInstances = MClientSkeleton.inst().getServiceInstances(MConfig.MGATEWAY_NAME);
        InstanceInfo gatewayInstance = null;
        if (!MClientSkeleton.inst().getGroupGatewayIpSet().isEmpty()) {
            List<InstanceInfo> filteredList = gatewayInstances.stream().filter(info ->
                    MClientSkeleton.inst().getGroupGatewayIpSet().contains(info.getIPAddr())).collect(Collectors.toList());
            gatewayInstance = filteredList.get(random.nextInt(filteredList.size()));
        } else {
            gatewayInstance = gatewayInstances.get(random.nextInt(gatewayInstances.size()));
        }

        if (gatewayInstance != null) {
            MResponse response = null;
            response = MRequestUtils.sendRequest(
                    MUrlUtils.getRemoteUri(gatewayInstance.getIPAddr(),
                            gatewayInstance.getPort(),
                            MConfig.MGATEWAY_DEPENDENCY_CALL),
                    parameters,
                    MResponse.class,
                    requestMethod,
                    customHeaders
            );
            return response;
        } else {
            throw new RuntimeException("There is no gateway instance in current group");
        }
    }
}
