package edu.nine14.exam.controller;

import edu.nine14.annotation.AuthenticationLevel;
import edu.nine14.annotation.AuthenticationLevelType;
import edu.nine14.common.ApiResult;
import edu.nine14.common.HttpCode;
import edu.nine14.exam.choiceEntity.QuestionChoice;
import edu.nine14.exam.entity.Direction;
import edu.nine14.exam.entity.Question;
import edu.nine14.exam.entity.QuestionDirection;
import edu.nine14.exam.entity.QuestionReceive;
import edu.nine14.exam.service.QuestionDirectionService;
import edu.nine14.exam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    /**
     * question_bank和direction表连接
     */
    @Autowired
    private QuestionDirectionService questionDirectionService;

    /**
     * 获取所有题目信息
     * 需要ADMIN权限
     * @return 返回所有题目信息
     */
    @RequestMapping(path = "/api/admin/question/page/selectAll")
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object selectAllQuestion(){
        try {
            List<QuestionDirection> listQD = (List<QuestionDirection>) questionDirectionService.getAllQuestion();
            //将数据转换为前端所接受的json数据格式
            List<QuestionReceive> listQR = new ArrayList<QuestionReceive>();
            for(QuestionDirection qd:listQD){
                listQR.add(qd.toQuestionReceive());
            }
            return ApiResult.ok(listQR);
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 多条件动态查询
     * @param questionChoice 前端的json数据
     * @return 返回符合条件的题目list
     */
    @RequestMapping(path = "/api/admin/question/page",method = { RequestMethod.POST })
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object selectQuestionByCondition(@RequestBody QuestionChoice questionChoice){
        try {
            List<QuestionDirection> listQD = questionDirectionService.findByCondition(questionChoice.getPageIndex(), questionChoice.getPageSize(),
                    questionChoice.getDirection(), questionChoice.getProfession(),  questionChoice.getId(), questionChoice.getType()).getContent();
            List<QuestionReceive> listQR = new ArrayList<QuestionReceive>();
            for(QuestionDirection qd:listQD){
                listQR.add(qd.toQuestionReceive());
            }
            return ApiResult.ok(listQR);
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 查询某一类型的题目
     * @param type 题目类型
     * @return 返回符合要求的题目类型
     */
    @RequestMapping(path = "/api/admin/question/page/{type}",method = { RequestMethod.POST })
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object selectByType(@PathVariable("type") String type){
        try {
            List<QuestionDirection> listQD = (List<QuestionDirection>) questionService.selectByType(type);
            //将数据转换为前端所接受的json数据格式
            List<QuestionReceive> listQR = new ArrayList<QuestionReceive>();
            for(QuestionDirection qd:listQD){
                listQR.add(qd.toQuestionReceive());
            }
            return ApiResult.ok(listQR);
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 删除题目
     * @param id 题目的id
     * @return 删除题目成功还是失败
     */
    @RequestMapping(path = "/api/admin/question/delete/{id}")
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object deleteQuestionById(@PathVariable("id") Integer id){
        try {
            questionService.deleteQuestion(id);
            return ApiResult.ok("delete question success");
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 根据题目Id查询相应题目
     * @param id 题目Id
     * @return 返回对应Id的题目
     */
    @RequestMapping(path = "/api/admin/question/select/{id}")
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object selectQuestionById(@PathVariable("id") Integer id){
        try {
            QuestionDirection questionDirection = (QuestionDirection)questionDirectionService.selectById(id);
            return ApiResult.ok(questionDirection.toQuestionReceive());
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 编辑题目
     * @return 返回成功或是失败信息
     */
    @RequestMapping("/api/admin/question/edit")
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object editQuestion(@RequestBody QuestionReceive question){
        try {
            questionService.editQuestion(question);
            return ApiResult.ok("edit question success");
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 添加题目
     * @param question 前端的json数据
     * @return 成功或失败信息
     */
    @RequestMapping(path = "/api/admin/question/add")
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object addQuestion(@RequestBody QuestionReceive question){
        try {
            questionService.addQuestion(question);
            return ApiResult.ok("add question success");
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
