package com.jason.piccolo.service;

import com.jason.piccolo.domain.dto.RegionDTO;
import com.jason.piccolo.domain.entity.Region;

import java.util.List;

/**
 * The interface Region service.
 */
public interface RegionService {

  /**
   * Gets by id.
   *
   * @param id the id
   * @return the by id
   */
  RegionDTO getById(Integer id);

  /**
   * Gets by parent id.
   *
   * @param parentId the parent id
   * @return the by parent id
   */
  List<RegionDTO> getByParentId(Integer parentId);

  /**
   * Gets all leaf.
   *
   * @param parentId the parent id
   * @return the all leaf
   */
  List<Region> getAllLeaf(Integer parentId);

}
