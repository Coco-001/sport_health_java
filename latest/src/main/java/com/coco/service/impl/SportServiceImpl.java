package com.coco.service.impl;

import com.coco.entity.Sport;
import com.coco.entity.WxRun;
import com.coco.mapper.SportMapper;
import com.coco.service.SportService;
import com.coco.utils.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class SportServiceImpl implements SportService {

    @Autowired
    private SportMapper sportMapper;

    @Override
    public Result delete(Long id) {
        if (id == null) {
            return Result.fail("请传递删除ID");
        }
        sportMapper.delete(id);
        return Result.success("删除成功");
    }

    @Override
    public Result update(Sport sport) {
        sport.setUpdateName(SecurityUtils.getUsername());
        sport.setUpdateTime(DateUtils.getDateTime());
        sportMapper.update(sport);
        return Result.success("修改成功");
    }

    @Override
    public Result insert(Sport sport) {
        sport.setCreateName(SecurityUtils.getUsername());
        sport.setCreateTime(DateUtils.getDateTime());
        sportMapper.insert(sport);
        return Result.success("添加成功");
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("开始数据分页-->页码{}, --->{}页数--->查询内容{}", queryInfo.getPageNumber(), queryInfo.getPageSize(), queryInfo.getQueryString());
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<Sport> sports = sportMapper.findPage(queryInfo.getQueryString());
        long total = sports.getTotal();
        List<Sport> result = sports.getResult();
        log.info("查询的总条数-->{}", total);
        log.info("分页列表-->{}", result);
        return PageResult.pageRsult(total, result);
    }

    @Override
    public Result findInfo(Long id) {
        return Result.success("查询成功", sportMapper.findInfo(id));
    }

    /**
     * 添加运动步数
     * @param runs
     * @return
     */
    @Override
    public Result insertStep(List<WxRun> runs) {
        List<WxRun> wxRuns = new ArrayList<>();
        for (WxRun run : runs) {
            WxRun step = sportMapper.findStepByTime(run.getOpenid(), run.getTime());
            if (step == null) {
                wxRuns.add(run);
            } else if (!run.getStep().equals(step.getStep())) {
                sportMapper.updateStep(run.getOpenid(), run.getTime(), run.getStep());
            }
        }
        if (wxRuns.size() > 0) {
            sportMapper.insertStep(wxRuns);
        }
        return Result.success("步数更新成功", runs.get(runs.size() - 1).getStep());
    }

    @Override
    public Result stepReport() {
        String openId = SecurityUtils.getOpenId();
        String dateTime = DateUtils.getDateTime();
        Map<String, List<WxRun>> map = new HashMap<>(4);
        //从今天起第一周  获取今天是星期几 <---> 周一
        int week1 = DateUtils.getWeekOfDate(dateTime);
        //周一
        String date1 = DateUtils.getWeekBeforeDate(dateTime, week1-1);
        List<WxRun> runs1 = sportMapper.findByTime(dateTime, date1, openId);
        map.put("week1", runs1);
        //获取第二周 周天 <---> 周一
        String date2 = DateUtils.getWeekBeforeDate(date1, 1);
        String week2 = DateUtils.getWeekBeforeDate(date2, 6);
        List<WxRun> runs2 = sportMapper.findByTime(date2, week2, openId);
        map.put("week2", runs2);
        //第三周 周天 --- 周一
        String date3 = DateUtils.getWeekBeforeDate(week2, 1);
        String week3 = DateUtils.getWeekBeforeDate(date3, 6);
        List<WxRun> runs3 = sportMapper.findByTime(date3, week3, openId);
        map.put("week3", runs3);
        //第四周 周天 --- 周一
        String date4 = DateUtils.getWeekBeforeDate(date3, 1);
        String week5 = DateUtils.getWeekBeforeDate(date4, 6);
        List<WxRun> runs4 = sportMapper.findByTime(date4, week5, openId);
        map.put("week4", runs4);
        return Result.success("微信步数统计查询成功", map);
    }

}
