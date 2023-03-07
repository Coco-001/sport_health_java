package com.coco.controller;

import com.coco.entity.Goods;
import com.coco.service.GoodsService;
import com.coco.utils.EasyExcelUtils;
import com.coco.utils.QueryInfo;
import com.coco.utils.Result;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.support.ExcelTypeEnum;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @ApiOperation(value = "分页查询商品商品信息", httpMethod = "POST")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        return goodsService.findPage(queryInfo);
    }

    @PostMapping("/batchImport")
    @ApiOperation(value = "通过excel导入添加商品信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods", value = "从浏览器上传的Excel文档", required = true),
    })
    public Result batchImport(@RequestParam("goods") MultipartFile file) {
        try {
            List<Goods> list = EasyExcelUtils.readExcel(file.getInputStream(), Goods.class);
            return goodsService.batchImport(list);
        } catch (Exception e) {
            if (e.getCause() instanceof ExcelDataConvertException) {
                return Result.fail("表格类型错误，商品数量为整数，价格为两个小数点的数字");
            } else{
                return Result.fail("商品导入异常，请联系管理员");
            }
        }
    }

    @ApiOperation(value = "Excel导出所有的商品信息", httpMethod = "GET")
    @ApiImplicitParam(name = "response", value = "响应给客户端的输出流(用于输出excel文档)")
    @GetMapping("/batchExport")
    public void batchExport(HttpServletResponse response) throws IOException {
        List<Goods> list = goodsService.findAll();
        //代表的时Excel文件类型
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=export.xlsx");
        // 这里需要设置不关闭流
        OutputStream outputStream = response.getOutputStream();
        EasyExcel.write(outputStream)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("商品列表")
                .head(Goods.class)
                .doWrite(list);
        outputStream.flush();
        outputStream.close();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return goodsService.delete(id);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Goods goods) {
        return goodsService.edit(goods);
    }

}
