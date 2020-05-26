package com.changgou.service;

import com.example.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {

    /**
     * 分页+条件搜索
     * @param brand:
     * @param page:当前页
     * @param size:每页显示条数
     */
    PageInfo<Brand> findPage(Brand brand,Integer page,Integer size);


    /**
     * 分页查询
     * @param page:当前页
     * @param size:每页显示条数
     */
    PageInfo<Brand> findPage(Integer page,Integer size);

    /**
     * 根据品牌信息多条件搜索
     * @Param brand
     */
    List<Brand> findList(Brand brand);

    /**
     * 根据Id删除数据
     */
    void delete(Integer id);

    /**
     * 根据Id修改品牌数据
     * @Param brand
     */
    void update(Brand brand);

    /**
     * 增加品牌
     * @Param brand
     */
    void add(Brand brand);

    /**
     * 根据id查询
     */
    Brand findById(Integer id);

    /**
     * 查询所有
     */
    List<Brand> findAll();
}
