package edu.nine14.exam.controller;

import edu.nine14.annotation.AuthenticationLevel;
import edu.nine14.annotation.AuthenticationLevelType;
import edu.nine14.annotation.CurrentUser;
import edu.nine14.common.ApiResult;
import edu.nine14.common.HttpCode;
import edu.nine14.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.FailedLoginException;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录，只能使用POST请求
     * 同样适用于token刷新
     * @param map
     *  username:用户名
     *  password:密码
     * @return 生成的token
     */
    @RequestMapping(path = "/api/login", method = { RequestMethod.POST })
    public Object login(@RequestBody Map<String, String> map) {
        try {
            String username = map.get("username");
            String password = map.get("password");
            String token = userService.login(username, password);
            return ApiResult.ok(token);
        } catch (FailedLoginException e) {
            return ApiResult.failed(HttpCode.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    /**
     * 更新当前用户的密码
     * @param username 从token中解析出的用户名
     * @param map
     *  oldPassword:旧密码
     *  newPassword:新密码
     * @return 是否更新成功
     */
    @RequestMapping(path = "/api/student/user/update", method = { RequestMethod.POST })
    @AuthenticationLevel(AuthenticationLevelType.USER)
    public Object updatePassword(@CurrentUser String username,
                                 @RequestBody Map<String, String> map) {
        try {
            String oldPassword = map.get("oldPassword");
            String newPassword = map.get("newPassword");
            userService.updatePassword(username, oldPassword, newPassword);
            return ApiResult.ok("update password success");
        } catch (IllegalArgumentException e) {
            return ApiResult.failed(HttpCode.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 获取当前用户的个人信息，适用于所有用户
     * @param username 从token中解析的用户名
     * @return 用户信息
     */
    @RequestMapping(path = "/api/student/user/current")
    @AuthenticationLevel(AuthenticationLevelType.USER)
    public Object getUserInfo(@CurrentUser String username) {
        try {
            Map<String, String> userInfo = userService.getUserInformation(username);
            return ApiResult.ok(userInfo);
        } catch (IllegalArgumentException e) {
            return ApiResult.failed(HttpCode.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 更新当前用户的个人信息，适用于所有用户
     * @param userInfo 新的用户信息
     * @param username 从token中解析的用户名
     * @return 更新是否成功
     */
    @RequestMapping(path = "/api/student/user/update")
    @AuthenticationLevel(AuthenticationLevelType.USER)
    public Object updateUserInfo(@RequestBody Map<String, String> userInfo, @CurrentUser String username) {
        try {
            userService.updateUserInformation(username, userInfo);
            return ApiResult.ok("update success");
        } catch (IllegalArgumentException e) {
            return ApiResult.failed(HttpCode.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 注册新用户（非管理员），只能使用POST请求
     * @param map
     *  username:用户名
     *  password:密码
     * @return 注册是否成功
     */
    @RequestMapping(path = "/api/student/user/register", method = { RequestMethod.POST })
    public Object register(@RequestBody Map<String, String> map) {
        try {
            String username = map.get("username");
            String password = map.get("password");
            userService.register(username, password, "U");
            return ApiResult.ok("register success");
        } catch (IllegalArgumentException e) {
            return ApiResult.failed(HttpCode.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 获取当前所有用户列表
     * 需要ADMIN权限
     * @param page 页码
     * @param size 每页数量
     * @return 用户列表
     */
    @RequestMapping(path = "/api/admin/user/page/list")
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object getUserList(@RequestParam("page") int page,
                              @RequestParam("size") int size) {
        try {
            return ApiResult.ok(userService.getUserList(page-1, size));
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 获取指定用户的个人信息（不包括密码）
     * 需要ADMIN权限
     * @param username 用户名
     * @return 用户信息
     */
    @RequestMapping(path = "api/getUserInfoAdmin")
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object getUserInfoAdmin(@RequestParam("username") String username) {
        try {
            Map<String, String> userInfo = userService.getUserInformation(username);
            return ApiResult.ok(userInfo);
        } catch (IllegalArgumentException e) {
            return ApiResult.failed(HttpCode.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 删除指定用户
     * 需要ADMIN权限
     * @param username 用户名
     * @return 删除是否成功
     */
    @RequestMapping(path = "/api/admin/user/delete")
    @AuthenticationLevel(AuthenticationLevelType.ADMIN)
    public Object removeUser(@RequestParam("username") String username) {
        try {
            userService.removeUser(username);
            return ApiResult.ok("remove success");
        } catch (IllegalArgumentException e) {
            return ApiResult.failed(HttpCode.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ApiResult.failed(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping(path = "/test")
    public Object test() {
        return ApiResult.ok("Hello world");
    }
}
