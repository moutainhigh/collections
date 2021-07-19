package com.gerry.yitao.yitaosellerservice.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gerry.yitao.domain.SpecGroup;
import com.gerry.yitao.domain.SpecParam;
import com.gerry.yitao.seller.service.SpecService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: yitao-parent
 * @Auther: GERRY
 * @Date: 2019/4/16 22:51
 * @Description:
 */
@RestController
@RequestMapping("api/item/spec")
public class SpecController {

    @Reference(check = false,timeout = 40000)
    private SpecService specService;


    /**
     * 根据商品分类ID查询规格组
     *
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroupByCid(@PathVariable("cid") Long cid) {
        return ResponseEntity.ok(specService.querySpecGroupByCid(cid));
    }

    /**
     * 查询商品规格参数
     *
     * @param gid       规格组ID
     * @param cid       商品分类ID
     * @param searching 是否是搜索字段
     * @param generic   是否是通用字段
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> querySpecParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching,
            @RequestParam(value = "generic", required = false) Boolean generic
    ) {
        return ResponseEntity.ok(specService.querySpecParams(gid, cid, searching, generic));
    }

    /**
     * 增加商品规格组
     *
     * @param specGroup
     * @return
     */
    @PostMapping("group")
    public ResponseEntity<Void> saveSpecGroup(@RequestBody SpecGroup specGroup) {
        specService.saveSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除商品规格组
     *
     * @param id
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteSpecGroup(@PathVariable("id") Long id) {
        specService.deleteSpecGroup(id);
        return ResponseEntity.ok().build();

    }

    /**
     * 更新商品规格组
     *
     * @param specGroup
     * @return
     */
    @PutMapping("group")
    public ResponseEntity<Void> updateSpecGroup(@RequestBody SpecGroup specGroup) {
        specService.updateSpecGroup(specGroup);
        return ResponseEntity.ok().build();
    }

    /**
     * 增加商品规格参数
     *
     * @param specParam
     * @return
     */
    @PostMapping("param")
    public ResponseEntity<Void> saveSpecParam(@RequestBody SpecParam specParam) {
        specService.saveSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除商品规格参数
     *
     * @param id
     * @return
     */
    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> deleteSpecParam(@PathVariable("id") Long id) {
        specService.deleteSpecParam(id);
        return ResponseEntity.ok().build();

    }

    /**
     * 更新商品规格参数
     *
     * @param specParam
     * @return
     */
    @PutMapping("param")
    public ResponseEntity<Void> updateSpecParam(@RequestBody SpecParam specParam) {
        specService.updateSpecParam(specParam);
        return ResponseEntity.ok().build();
    }

    /**
     * 查询规格参数组，及组内参数
     *
     * @param cid
     * @return
     */
    @GetMapping("{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecsByCid(@PathVariable("cid") Long cid) {
        return ResponseEntity.ok(specService.querySpecsByCid(cid));
    }


}
