package com.jason.frieza.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.jason.dragon.mapper.BeanMapper;
import com.jason.frieza.domain.dto.RegionDTO;
import com.jason.frieza.domain.entity.Region;
import com.jason.frieza.exception.ServiceException;
import com.jason.frieza.repository.RegionMapper;
import com.jason.frieza.service.RegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl implements RegionService, InitializingBean {

  private static final Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);
  private static final Cache<Integer, Region> allRegionCache = CacheBuilder.newBuilder().build();
  private static final Cache<Integer, Region> regionCache = CacheBuilder.newBuilder().maximumSize(1000).build();
  private static final Cache<Integer, List<Region>> regionListCache = CacheBuilder.newBuilder().maximumSize(1000).build();
  @Resource
  private RegionMapper regionMapper;

  @Override
  public RegionDTO getById(Integer id) {
    try {
      Region region = regionCache.get(id, () -> regionMapper.findById(id));
      RegionDTO regionDTO = BeanMapper.map(region, RegionDTO.class);
      if (!region.getLeaf()) {
        List<Region> regions = regionListCache.get(id, () -> regionMapper.findByParentId(id));
        List<RegionDTO> regionDTOS = BeanMapper.mapList(regions, RegionDTO.class);
        regionDTO.setChild(regionDTOS);
      }
      return regionDTO;
    } catch (ExecutionException e) {
      throw new ServiceException(100001);
    }
  }

  @Override
  public List<RegionDTO> getByParentId(Integer parentId) {
    try {
      List<Region> regions = regionListCache.get(parentId, () -> regionMapper.findByParentId(parentId));
      return BeanMapper.mapList(regions, RegionDTO.class);
    } catch (ExecutionException e) {
      throw new ServiceException(100001);
    }
  }

  @Override
  public List<Region> getAllLeaf(Integer parentId) {
    return breadth(parentId);
  }

  /**
   * Breadth list (广度优先遍历).
   *
   * @param parentId the parent id
   * @return the list
   */
  private List<Region> breadth(Integer parentId) {
    //1将所有数据转化以id为key，存入到一个Map中，方便后面取数据
    Map<Integer, Region> allRegionMap = allRegionCache.asMap();
    List<Region> regions = Lists.newArrayList();
    //2将所有数据放入一个list中
    for (Map.Entry<Integer, Region> entry : allRegionMap.entrySet()) {
      regions.add(entry.getValue());
    }
    //3定义一个双向队列（特点：可以在首尾插入或删除元素）
    Deque<Region> nodeDeque = new ArrayDeque<>();
    //4获取父节点
    Region node = allRegionMap.get(parentId);
    //5将该父节点放入队列头部
    nodeDeque.add(node);
    List<Region> results = Lists.newArrayList();
    while (!nodeDeque.isEmpty()) {
      //6删除队列头部元素
      node = nodeDeque.removeFirst();
      //7取删除的头部元素的子节点
      List<Region> children = getChildren(regions, node);
      //8将删除的父节点放入结果集
      results.add(node);
      if (!CollectionUtils.isEmpty(children)) {
        //9将查询出来的子节点放入第3步定义的双向队列的尾部。
        children.forEach(nodeDeque::offer);
      }
    }
    return results;
  }

  /**
   * Depth list (深度优先遍历子节点).
   *
   * @param parentId the parent id
   * @return the list
   */
  private List<Region> depth(Integer parentId) {
    //1将所有数据转化以id为key，存入到一个Map中，方便后面取数据
    Map<Integer, Region> allRegionMap = allRegionCache.asMap();
    List<Region> regions = Lists.newArrayList();
    //2将所有数据放入一个list中
    for (Map.Entry<Integer, Region> entry : allRegionMap.entrySet()) {
      regions.add(entry.getValue());
    }
    //3定义一个堆栈，特点后进先出
    Stack<Region> nodeStack = new Stack<>();
    //4获取父节点
    Region node = allRegionMap.get(parentId);
    //5将父节点加入到堆栈
    nodeStack.add(node);
    List<Region> results = Lists.newArrayList();
    while (!nodeStack.isEmpty()) {
      //6父节点出栈
      node = nodeStack.pop();
      //7获取该父节点的子节点
      List<Region> children = getChildren(regions, node);
      //8将父节点加入到结果集
      results.add(node);
      if (!CollectionUtils.isEmpty(children)) {
        //9将子节点放入栈顶
        children.forEach(nodeStack::push);
      }
    }
    return results;
  }

  private List<Region> getChildren(List<Region> allData, Region parent) {
    return allData.stream()
        .filter(region -> region.getParentId().intValue() == parent.getId().intValue())
        .collect(Collectors.toList());
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Iterable<Region> allRegions = regionMapper.findAll();
    logger.info("=======================Load all Region to cache start==========================");
    allRegions.forEach(region -> allRegionCache.put(region.getId(), region));
    logger.info("=======================Load {} Region to cache end============================", allRegionCache.size());
  }
}
