package com.haohua.erp.shiro;    /*
 * @author  Administrator
 * @date 2018/7/30
 */

import com.haohua.erp.entity.Employee;
import com.haohua.erp.entity.EmployeeLoginLog;
import com.haohua.erp.entity.Permission;
import com.haohua.erp.entity.Role;
import com.haohua.erp.service.EmployeeService;
import com.haohua.erp.service.RolePremissionService;
import com.haohua.erp.service.RoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class MyRealm extends AuthorizingRealm {

    Logger logger = LoggerFactory.getLogger(MyRealm.class);
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePremissionService rolePremissionService;
    /**
     * 授权信息
     * @param principalCollection 一个实现类对象（包含主体主要信息的）
     * @return  一个授权对象（对当前账号角色进行授权后）
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获得当前登录对象
        Employee employee = (Employee) principalCollection.getPrimaryPrincipal();
        //获得当前对象所拥有的角色
        List<Role> roleList =roleService.findRoleList(employee.getId());
        //获得员工所拥有的所有权限
        List<Permission> permissionList = new ArrayList<>();

        for(Role role : roleList){
            List<Permission> permissions = rolePremissionService.findPermissionsByRoleId(role.getId());
            permissionList.addAll(permissions);
        }
        //获得role的代号 set集合
        Set<String> roleCodeSet = new HashSet<>();
        for( Role role : roleList){
            roleCodeSet.add(role.getRoleCode());
        }
        //获得permission的代号 set集合
        Set<String> permissionCodeSet = new HashSet<>();
        for(Permission permission : permissionList){
                permissionCodeSet.add(permission.getPermissionCode());
        }
        //返回简单的授权信息对象  角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roleCodeSet);
        simpleAuthorizationInfo.setStringPermissions(permissionCodeSet);
        return simpleAuthorizationInfo;
    }
    /**
     * 认证信息
     * @param authenticationToken 一个认证方式的实现类对象（包含认证信息）
     * @return 一个认证对象（认证通过后的对象）
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;

        String userTel = usernamePasswordToken.getUsername();
        Employee employee = employeeService.findByTel(userTel);
        if(employee == null) {
            throw new UnknownAccountException();
        } else {
            if(employee.getState().equals(Employee.PROHIBIT_STATE)) {
                throw new LockedAccountException("账户被冻结");
            } else {
                // 登录成功
                String loginIp = usernamePasswordToken.getHost();
                // 记录登录日志
                EmployeeLoginLog employeeLoginLog = new EmployeeLoginLog();
                employeeLoginLog.setLoginIp(loginIp);
                employeeLoginLog.setEmployeeId(employee.getId());
                employeeService.saveLoginLog(employeeLoginLog);
                logger.info("{}-{} 在 {} 登录了系统", employee.getEmployeeName(),employee.getEmployeeTel(),new Date());
                //返回简单的 认证信息对象， 当前对象，认证证书，当前类的详情对象名(和传过来的token进行对比)
                return new SimpleAuthenticationInfo(employee, employee.getPassword(),getName());
            }
        }
        }
    }
