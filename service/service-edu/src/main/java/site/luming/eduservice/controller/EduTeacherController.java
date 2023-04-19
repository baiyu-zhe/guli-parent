package site.luming.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import site.luming.eduservice.entity.EduTeacher;
import site.luming.eduservice.entity.vo.TeacherQuery;
import site.luming.eduservice.service.EduTeacherService;
import site.luming.servicebase.config.exceptionhandler.GuliException;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-02-20
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    //注入service
    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R list(){
        List<EduTeacher> list = teacherService.list(null);
        System.out.println(list);
        return R.ok().data("items", list);
    }

    //2 逻辑删除讲师的方法
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                           @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){


        try {
            int i = 10/0;
        } catch (Exception e) {
            throw new GuliException(20001, "执行了自定义异常处理");
        }

        Page<EduTeacher> pageParam = new Page<>(page, limit);

        teacherService.page(pageParam, null);
        long total = pageParam.getTotal();
        List<EduTeacher> records = pageParam.getRecords();

        return  R.ok().data("total", total).data("rows", records);
    }

    // 多条件组合和查询
    @ApiOperation(value = "多条件组合查询")
    @GetMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  TeacherQuery teacherQuery) {
        // 创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        // wrapper;
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_begin", begin);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.le("gmt_end", end);
        }

        //调用方式实现条件查询
        teacherService.page(pageTeacher, wrapper);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 根据讲师id查询
    @ApiOperation(value = "根据id查询")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("Teacher", eduTeacher);
    }

    // 讲师修改功能
    @ApiOperation(value = "更新讲师数据")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

