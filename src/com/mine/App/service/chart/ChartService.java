package com.mine.App.service.chart;

import com.mine.App.dao.chart.ChartDao;
import com.mine.App.model.ElementValue;
import com.mine.App.model.Pie;
import com.mine.App.model.PieElement;
import com.mine.common.PubGetColorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChartService {
    @Autowired
    private ChartDao chartDao;

    public Pie queryAccessLog(Map<String, Object> inParam) {
        Pie pie = new Pie();
        List<Map<String, Object>> listPie = new ArrayList<>();
        PieElement pieElement = new PieElement();
        ElementValue elementValue = new ElementValue();
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> dataList = chartDao.queryAccessLog(inParam);
        String[] colors = new String[dataList.size()];
        int i = 0;
        for (Map<String, Object> map : dataList) {
            colors[i] = PubGetColorUtil.getOnlyRandColorCode(colors);
            elementValue.setValue(Integer.valueOf(map.get("cnt").toString()));
            elementValue.setLabel(map.get("module_name").toString() + "-" + Integer.valueOf(map.get("cnt").toString()));
            elementValue.setTip(map.get("module_name").toString() + ":" + Integer.valueOf(map.get("cnt").toString()));
            elementValue.setOnClick("");
            list.add(elementValue.encode());
            i++;
        }

        pieElement.setType("pie");
        pieElement.setColours(colors);
        pieElement.setAlpha(0.6f);
        pieElement.setBorder(2);
        pieElement.setStartAngle(90);
        pieElement.setFontSize(12);
        pieElement.setText("访问统计饼状图");
        pieElement.setValues(list);
        listPie.add(pieElement.encode());
        pie.setElements(listPie);
        return pie;
    }

}
