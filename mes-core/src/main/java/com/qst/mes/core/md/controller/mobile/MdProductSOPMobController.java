package com.qst.mes.core.md.controller.mobile;

import com.qst.mes.common.core.controller.BaseController;
import com.qst.mes.common.core.domain.AjaxResult;
import com.qst.mes.core.md.domain.MdProductSop;
import com.qst.mes.core.md.service.IMdProductSopService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mobile/md/sop")
public class MdProductSOPMobController extends BaseController {
    @Autowired
    private IMdProductSopService mdProductSopService;

    /**
     * 查询产品SOP列表
     */
    @ApiOperation("查询产品SOP信息")
    @GetMapping("/list")
    public AjaxResult list(MdProductSop mdProdutSop)
    {
        startPage();
        List<MdProductSop> list = mdProductSopService.selectMdProductSopList(mdProdutSop);
        return AjaxResult.success(list);
    }
}
