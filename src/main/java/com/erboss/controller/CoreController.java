package com.erboss.controller;

import com.erboss.domain.CoreResp;
import com.erboss.service.CoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangyongan on 2020-04-05.
 * Email wangyongan@xueqiu.com
 */
@RestController
public class CoreController {
    @Autowired
    private CoreService coreService;

    @RequestMapping(path = {"/core/data"})
    @ResponseBody
    @CrossOrigin
    public CoreResp data(@RequestParam(name = "id") int id) {
        return coreService.getData(id);
    }
}
