package com.jason.piccolo.rest;

import com.jason.piccolo.domain.dto.RegionDTO;
import com.jason.piccolo.domain.entity.Region;
import com.jason.piccolo.service.RegionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/regions")
public class RegionRestController {

  private RegionService regionService;

  @Autowired
  public void setRegionService(RegionService regionService) {
    this.regionService = regionService;
  }

  /**
   * 根据id获取地区信息
   *
   * @param id 地区id
   * @return RegionDTO
   */
  @ApiOperation(value = "根据id获取地区信息")
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public RegionDTO getRegionById(@PathVariable("id") Integer id) {
    return regionService.getById(id);
  }

  /**
   * 根据父级id，获取下级地区列表
   *
   * @param parentId 父级id
   * @return RegionDTOs
   */
  @ApiOperation(value = "根据id获取下级地区列表")
  @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public List<RegionDTO> getRegionByParentId(@RequestParam(name = "parentId") Integer parentId) {
    return regionService.getByParentId(parentId);
  }

  /**
   * 获取单个节点下所有的子节点信息
   *
   * @param parentId 父节点id
   * @return Regions
   */
  @ApiOperation(value = "获取所有子节点的地区列表")
  @GetMapping(value = "/allLeaf/{parentId}")
  public List<Region> getAllLeaf(@PathVariable("parentId") Integer parentId) {
    return regionService.getAllLeaf(parentId);
  }

}
