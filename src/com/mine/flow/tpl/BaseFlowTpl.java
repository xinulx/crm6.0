package com.mine.flow.tpl;

import com.mine.flow.FlowUtils;
import com.mine.svc.common.base.MBean;
import org.apache.log4j.Logger;

/**
 * 公共基础流程模板
 */
public class BaseFlowTpl {

    private Logger logger = Logger.getLogger(this.getClass());

    public void createBaseFlow(MBean content, boolean isAuto){
        // 是否开启自动流程
        if(isAuto){
            FlowUtils.createAutoFlow(content);
        }
        // 创建流程视图
    }
}
