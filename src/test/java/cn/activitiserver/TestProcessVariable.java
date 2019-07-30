package cn.activitiserver;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProcessVariable {

    // 存储服务
    @Autowired
    private RepositoryService repositoryService;
    // 运行时服务
    @Autowired
    private RuntimeService runtimeService;
    // 任务服务
    @Autowired
    private TaskService taskService;
    // 历史数据服务
    @Autowired
    private HistoryService historyService;

    @Test
    public void testStartProcess(){
        // 启动流程，并设置流程变量
        String processDefinitionKey = "process";
        Map<String,Object> variable = new HashMap<>();
        variable.put("请假原因","约会");
        variable.put("请假天数",5);
        variable.put("请假时间", LocalDateTime.now());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variable);
        System.out.println("启动流程成功，流程实例id："+processInstance.getId());
    }

    @Test
    public void setVariable(){
        // 设置流程变量
        // 重新设置流程变量时，如果表中有相同的key则之前的数据会被覆盖掉
        taskService.setVariable("2514","请假人","黄飞鸿");
        Map<String,Object> variable = new HashMap<>();
        variable.put("请假时间", new Date());
        taskService.setVariables("2514",variable);
    }

    @Test
    public void getVariable(){
        // 获取流程变量
        Date date = (Date) runtimeService.getVariable("2501", "请假时间");
        String person = runtimeService.getVariable("2501", "请假人", String.class);
        System.out.println(person);
        System.out.println(date);
        int days = (int) taskService.getVariable("2514", "请假天数");
        System.out.println(days);
    }

    @Test
    public void testQueryHistoryVariable(){
        // 查询历史表中的Variable
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().list();
        for(HistoricVariableInstance hi:list){
            System.out.println("Key："+hi.getVariableName());
            System.out.println("Value："+hi.getValue());
        }
    }
    @Test
    public void completeProcess(){
        // 完成任务时设置流程变量


    }
}
