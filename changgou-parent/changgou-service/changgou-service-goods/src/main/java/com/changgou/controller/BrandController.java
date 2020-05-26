package com.changgou.controller;

import com.changgou.service.BrandService;
import com.example.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
/**
 * 跨域：A域名访问B域名的数据
 *      域名或者请求端口或者协议不一致的时候就跨域了
 *
 */
@CrossOrigin        //跨域
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 条件分页查询实现
     */
    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@RequestBody Brand brand,@PathVariable(value = "page")Integer page,  //当前页
                                            @PathVariable(value = "size")Integer size){ //每页显示的条数
        int a=10/0;
        //调用Service实现查询
        PageInfo<Brand> pageInfo = brandService.findPage(brand,page, size);

        return new Result<>(true,StatusCode.OK,"分页查询成功",pageInfo);

    }

    /**
     * 分页查询实现
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@PathVariable(value = "page")Integer page,  //当前页
                                    @PathVariable(value = "size")Integer size){ //每页显示的条数
        //调用Service实现查询
        PageInfo<Brand> pageInfo = brandService.findPage(page, size);

        return new Result<>(true,StatusCode.OK,"分页查询成功",pageInfo);

    }

    /**
     * 条件查询
     */
    @PostMapping(value = "/search")
    public Result<List<Brand>> findList(@RequestBody Brand brand){
        //调用Service实现查询
        List<Brand> brands = brandService.findList(brand);

        return new Result<>(true, StatusCode.OK,"条件搜索查询！",brands);
    }

    /**
     * 根据Id删除实现
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id")Integer id){
        //调Service实现删除
        brandService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功！");
    }

    /**
     * 品牌的修改实现
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable(value = "id")Integer id,@RequestBody Brand brand){
        //将id给brand
        brand.setId(id);
        //调用Service实现修改
        brandService.update(brand);
        return new Result(true,StatusCode.OK,"修改成功！");
    }

    /**
     * 增加品牌
     */
    @PostMapping
    public Result add(@RequestBody Brand brand){

        //调用Service实现增加操作
        brandService.add(brand);
        return new Result(true,StatusCode.OK,"增加品牌成功！");
    }

    /**
     * 根据主键Id查询
     */
    @GetMapping(value = "/{id}")
    public Result<Brand> fingById(@PathVariable(value = "id")Integer id){
        //调用Service实现查询
        Brand brand = brandService.findById(id);
        //响应数据封装
        return new Result<Brand>(true, StatusCode.OK,"根据Id查询Brand成功！",brand);
    }

    /**
     * 查询所有品牌
     */
    @GetMapping
    public Result<List<Brand>> findAll(){
        //查询所有品牌
        List<Brand> brands = brandService.findAll();

        //相应结果封装
        return new Result<List<Brand>>(true, StatusCode.OK,"查询品牌集合成功！",brands);
    }

}
