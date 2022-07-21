package edu.nine14.exam.controller;

import edu.nine14.annotation.AuthenticationLevel;
import edu.nine14.annotation.AuthenticationLevelType;
import edu.nine14.common.ApiResult;
import edu.nine14.common.HttpCode;
import edu.nine14.exam.entity.Direction;
import edu.nine14.exam.service.DirectionService;
import edu.nine14.exam.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class DirectionController {
    @Autowired
    private DirectionService directionService;

    @Autowired
    private ProfessionService professionService;

    /**
     * 查找所有专业方向信息
     * @return 返回所有方向信息
     */
    @RequestMapping(path = "/api/admin/education/subject/list")
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object selectAllDirection(){
        try {
            return ApiResult.ok(directionService.getAllDirection());
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 编辑专业方向信息
     * @param direction 接收新的专业方向的数据
     * @return 返回编辑成功或失败信息
     */
    @RequestMapping(path = "/api/admin/education/subject/edit",method = { RequestMethod.POST })
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object editDirection(@RequestBody Direction direction){
        try {
            directionService.editDirection(direction);
            return ApiResult.ok("edit direction success");
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 根据Id查找相应的专业
     * @param id professionId
     * @return 返回的是对应的direction list
     */
    @RequestMapping(path = "/api/admin/education/subject/page/{id}")
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object selectDirectionByProfessionId(@PathVariable("id") Integer id){
        try {
            return ApiResult.ok(directionService.selectByProfessionId(id));
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 根据Id查找相应的专业方向
     * @param id directionId
     * @return 返回的是direction
     */
    @RequestMapping(path = "/api/admin/education/subject/select/{id}")
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object selectDirectionById(@PathVariable("id") Integer id){
        try {
            return ApiResult.ok(directionService.selectById(id));
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 查找directionId
     * @param nameAndId 接收directionName和professionId为key组成的键值对
     * @return 返回满足directionName和professionId的directionId
     */
    @RequestMapping(path = "/api/admin/question/searchID",method = { RequestMethod.POST })
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object getDirectionNameByID(@RequestBody Map<String, String> nameAndId){
        try {
            String name = nameAndId.get("directionName");
            Integer id = Integer.parseInt(nameAndId.get("professionID"));
            return ApiResult.ok(directionService.getDirectionIdByName(name,id));
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 删除专业方向
     * @param id directionId
     * @return 返回删除成功或失败
     */
    @RequestMapping(path = "/api/admin/education/subject/delete/{id}")
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object deleteDirectionById(@PathVariable("id") Integer id){
        try {
            directionService.deleteDirection(id);
            return ApiResult.ok("delete direction success");
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
