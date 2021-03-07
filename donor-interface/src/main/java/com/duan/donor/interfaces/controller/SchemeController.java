package com.duan.donor.interfaces.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.duan.donor.business.bo.SchemeBo;
import com.duan.donor.business.entiy.Scheme;
import com.duan.donor.business.entiy.User;
import com.duan.donor.business.service.ISchemeService;
import com.duan.donor.business.service.IUserService;
import com.duan.donor.common.util.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/scheme")
@Api(value = "方案赞助模块",tags={"方案接口"})
public class SchemeController extends BaseController {

    @Resource
    private ISchemeService schemeService;

    @Resource
    private IUserService userService;

    /**
     * 方案的发布
     * @param schemeBo
     * @return
     * @throws Exception
     */
    @ApiOperation("方案的发布")
    @RequestMapping(value = "/schemePublish",method = RequestMethod.POST)
    public AjaxResult schemePublish(@RequestBody SchemeBo schemeBo) throws Exception {
        //获取token
        String loginToken = getToken();
        if (StringUtils.isBlank(loginToken)){
            return AjaxResult.error("用户未登录！");
        }

        //根据token获取用户
        User selectByToken = userService.getOne(new QueryWrapper<User>().eq("login_token", loginToken));
        //判断
        Scheme scheme = new Scheme();
        if (StringUtils.isBlank(schemeBo.getSchemeName())){
            return AjaxResult.error("没有填写方案名称！");
        }

        if (schemeBo.getSchemePrice() == null){
            return AjaxResult.error("没有填写方案价格！");
        }
        BeanUtils.copyProperties(scheme,schemeBo);
        scheme.setCreateUser(selectByToken.getId());
        try{
            //发布
            boolean b = schemeService.save(scheme);
            if (b){
                return AjaxResult.success("发布成功！");
            }else {
                return AjaxResult.error("发布失败！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("发布失败！");
        }
    }


    /**
     * 修改赞助方案（用户本人的）
     * @param schemeBo
     * @return
     */
    @ApiOperation("方案的修改")
    @RequestMapping(value = "/editScheme",method = RequestMethod.POST)
    public AjaxResult editScheme(@RequestBody SchemeBo schemeBo){

        String token = getToken(); //获取token
        if (StringUtils.isBlank(token)){
            return AjaxResult.error("用户未登录！");
        }
        Integer schemeId = schemeBo.getSchemeId();
        Scheme selectById = schemeService.getOne(new QueryWrapper<Scheme>().eq("id", schemeId));
        if (selectById != null){
            selectById.setSchemeName(schemeBo.getSchemeName());
            selectById.setSchemeDescription(schemeBo.getSchemeDescription());
            selectById.setSchemePicture(schemeBo.getSchemePicture());
            selectById.setSchemePrice(schemeBo.getSchemePrice());
            boolean updateById = schemeService.updateById(selectById);
            if (updateById){
                return AjaxResult.success("修改成功！");
            }else {
                return AjaxResult.success("修改失败！");
            }
        }else {
            return AjaxResult.success("该用户没有可修改的方案！");
        }

    }

    /**
     * 查询用户发布的赞助方案
     * @return
     */
    @ApiOperation("查询用户已发布过的方案")
    @RequestMapping(value = "/getSchemeList",method = RequestMethod.GET)
    public AjaxResult getSchemeList(){
        String token = getToken(); //获取token
        if (StringUtils.isBlank(token)){
            return AjaxResult.error("用户未登录！");
        }
        User selectByToken = userService.getOne(new QueryWrapper<User>().eq("login_token", token));
        if (selectByToken != null){
            List<Scheme> schemeList = schemeService.list(new QueryWrapper<Scheme>().eq("create_user", selectByToken.getId()));
            return AjaxResult.success(schemeList);
        }else {
            return AjaxResult.success("该用户未发布方案！");
        }

    }

    /**
     * 删除用户发布的赞助方案
     * @return
     */
    @ApiOperation("删除用户已发布过的方案")
    @RequestMapping(value = "/deleteScheme",method = RequestMethod.GET)
    public AjaxResult deleteScheme(@RequestParam("schemeId") Integer schemeId){
        String token = getToken(); //获取token
        if (StringUtils.isBlank(token)){
            return AjaxResult.error("用户未登录！");
        }
        User selectByToken = userService.getOne(new QueryWrapper<User>().eq("login_token", token));
        if (selectByToken != null){
            QueryWrapper<Scheme> wrapper = new QueryWrapper<Scheme>().eq("create_user", selectByToken.getId()).eq("id", schemeId);
            boolean remove = schemeService.remove(wrapper);
            if (remove){
                return AjaxResult.success("删除成功！");
            }else {
                return AjaxResult.success("删除失败！");
            }
        }else {
            return AjaxResult.success("该用户未发布方案！");
        }
    }

}