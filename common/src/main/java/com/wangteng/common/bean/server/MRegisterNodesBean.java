package com.wangteng.common.bean.server;

import com.wangteng.common.base.MConnectionJson;
import com.wangteng.common.base.MServerNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author SeptemberHX
 * @version 0.1
 * @date 2019/11/24
 */
@Getter
@Setter
public class MRegisterNodesBean {
    private List<MServerNode> nodeList;
    private List<MConnectionJson> connectionInfoList;
}
