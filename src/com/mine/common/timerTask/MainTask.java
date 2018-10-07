package com.mine.common.timerTask;

import com.mine.svc.common.mybatis.SqlClientTemplate;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

/**
 * @description <p>创建供定时任务：
 * 定期扫描该表MKT_SUPPLIER将失效数据的状态改为1100
 * option->EFF_DATE >= EXP_DATE </p>
 * <p>
 * <p>创建PPM BIZ_MKT_RESOURCE表对营销资源接口表SYN_RES_DEF表同步数据的定时任务,
 * 每次扫描当前时间点及之前BIZ_MKT_RESOURCE新增的规格数据，然后同步至接口表</p>
 * <p>
 * <p>ScheduledExecutorService是从Java SE5的java.util.concurrent里，做为并发工具类被引进的，这是最理想的定时任务实现方式。
 * 1>相比于Timer的单线程，它是通过线程池的方式来执行任务的
 * 2>可以很灵活的去设定第一次执行任务delay时间
 * 3>提供了良好的约定，以便设定执行的时间间隔 </p>
 * <p>
 * 需要实现ApplicationListener接口，为了spring在初始化完成后调用一次
 * @date 2016-06-06 9:22:36
 */
public class MainTask {

    private Logger log = Logger.getLogger(MainTask.class);
    @Resource(name = "sqlClientTemplate")
    private SqlClientTemplate sqlClientTemplate;

    /**
     * 调度延迟
     */
    private long initialDelay;
    /**
     * 调度间隔
     */
    private long period;
    /**
     * 调度单位标识
     */
    private int unit;
    /**
     * 执行任务小时单位节点
     */
    private int hour;
    /**
     * 执行任务的分钟单位节点
     */
    private int minute;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


    /**
     * 通过execute方法执行定时任务
     */
    public void execute() {
        //定制定时任务作业
        Runnable runnable = new Runnable() {
            public synchronized void run() {
                log.info("【" + getCurrentDatetime() + "】  run->开始执行定时任务");
                this.executeTask();
                this.executeTask2();
                this.executeTask3();
            }

            //定时任务组1
            private void executeTask() {
                try {
                    Integer num = (Integer) sqlClientTemplate.selectOne("com.mine.App.mapper.UserMapper.taskTest", null);
                    log.info("--------定时任务出参------" + num);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //定时任务组2
            private void executeTask2() {

            }

            //定时任务组3
            private void executeTask3() {

            }

            //--------------------------------------------------------
            //...可以设置多个任务...
        };
        //开启定时任务策略
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  ，第四个参数是时间单位
//      service.scheduleAtFixedRate(runnable, 10, 1, TimeUnit.DAYS);//天
//      service.scheduleAtFixedRate(runnable, 10, 1, TimeUnit.HOURS);//时
//      service.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.MINUTES);//分
//      service.scheduleAtFixedRate(runnable, 10, 200, TimeUnit.SECONDS);//秒
//      service.scheduleAtFixedRate(runnable, 10, 1, TimeUnit.MILLISECONDS);//毫秒
//      service.scheduleAtFixedRate(runnable, 10, 1, TimeUnit.MICROSECONDS);//微秒
//      service.scheduleAtFixedRate(runnable, 10, 1, TimeUnit.NANOSECONDS);//纳秒
        service.scheduleAtFixedRate(runnable, initialDelay, period, getTimeUnit(unit));//读取配置数据
        log.info("【" + getCurrentDatetime() + "】 ->成功创建定时任务服务.");
    }

    /**
     * 获取调度单位
     *
     * @return
     */
    private TimeUnit getTimeUnit(int unit) {
        switch (unit) {
            case 1:
                return TimeUnit.DAYS;
            case 2:
                return TimeUnit.HOURS;
            case 3:
                return TimeUnit.MINUTES;
            case 4:
                return TimeUnit.SECONDS;
            case 5:
                return TimeUnit.MILLISECONDS;
            case 6:
                return TimeUnit.MICROSECONDS;
            default:
                return TimeUnit.NANOSECONDS;
        }
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    /**
     * 获取日志时间切点
     *
     * @return 日期时间字符串
     */
    public String getCurrentDatetime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

}