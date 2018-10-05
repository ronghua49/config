package com.haohua.erp.shiro;    /*
 * @author  Administrator
 * @date 2018/7/31
 */

import com.haohua.erp.entity.Permission;
import com.haohua.erp.service.PremissionService;
import org.apache.shiro.config.Ini;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

public class MyShiroFilter {
    @Autowired
    private PremissionService premissionService;
    private String filterChainDefinitions;
    private AbstractShiroFilter shiroFilter;

    //默认在容器启动自动执行
    @PostConstruct
    public synchronized void init() {
        System.out.println("------------权限初始化中-----------");
        // 清空原有权限
        getFilterChainManager().getFilterChains().clear();
        // 加载新的权限
        load();
    }
    //权限更新
    public synchronized void updatePermission() {
        System.out.println("------------权限初更新中-----------");
        // 清空原有权限
        getFilterChainManager().getFilterChains().clear();
        // 加载新的权限
        load();
    }
    public  void  load() {
        Ini ini = new Ini();
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        List<Permission> permissionList = premissionService.findPremissionList();
        for(Permission permission:permissionList){
            section.put(permission.getUrl(), "perms[" + permission.getPermissionCode() + "]");
        }
        section.put("/**","user");
        //获得过滤器管理器，根据资源创建过滤器链
        DefaultFilterChainManager defaultFilterChainManager = getFilterChainManager();
        for(Ini.Section.Entry<String,String> entry : section.entrySet()){
            defaultFilterChainManager.createChain(entry.getKey(),entry.getValue());
        }
    }
    public DefaultFilterChainManager getFilterChainManager() {
        PathMatchingFilterChainResolver pathMatchingFilterChainResolver = (PathMatchingFilterChainResolver) this.shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager defaultFilterChainManager = (DefaultFilterChainManager) pathMatchingFilterChainResolver.getFilterChainManager();
        return defaultFilterChainManager;
    }
    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }
    public void setShiroFilter(AbstractShiroFilter shiroFilter) {
        this.shiroFilter = shiroFilter;
    }
}
