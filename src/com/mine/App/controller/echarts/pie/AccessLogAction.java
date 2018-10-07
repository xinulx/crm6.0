package com.mine.App.controller.echarts.pie;

import com.mine.App.common.Base.BaseController;
import com.mine.App.common.util.DataHelper;
import com.mine.App.model.Pie;
import com.mine.App.service.chart.ChartService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessLogAction extends BaseController {

    @Autowired
    private ChartService chartService;

    public void queryAccessLog(HttpServletRequest req, HttpServletResponse res) throws IOException {
        DataHelper dh = new DataHelper(req, res);
        Pie outMap = this.chartService.queryAccessLog(dh.getMapByEnu(req));
        res.getWriter().print(JSONObject.fromObject(outMap));
        dh.closeResponse();
    }
}
